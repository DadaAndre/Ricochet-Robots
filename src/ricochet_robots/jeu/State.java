package ricochet_robots.jeu;

import ricochet_robots.jeu.pions.*;
import ricochet_robots.jeu.observer.*;
import ricochet_robots.jeu.plateau.*;
import ricochet_robots.algorithme.*;
import ricochet_robots.jeu.*;

import java.util.Random;
import java.util.ArrayList;

import javafx.event.EventHandler;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.Scene;

import javafx.scene.input.*;

public class State implements RobotClickedObserver, CaseClickedObserver, Comparable<State>{

	/**
	 * Plateau de jeu
	 */
	private Plateau plateauJeu;

	/**
	 * Liste des robots instanciés
	 */
	private ArrayList<Robot> tableauRobots = new ArrayList<>();

	private Scene scene;

	/**
	 * Robot sélectionné
	 */
	private Robot robotSelect;

	/**
	 * Heuristique d'une partie
	 */
	private Heuristique heuristiquePlateau;

	/**
	 * Dernière direction effectuée
	 */
	private Deplacement lastDirection = null;

	/**
	 * Dernier robot joué
	 */
	private Robot lastRobot = null;

	private Random r = new Random();

	/**
	 * Image de sélection du robot selectionné
	 */
	private ImageView imgSelect;

	//Les nombres aléatoires
	private int aleaX = -1;
	private int aleaY = -1;

	/**
	 * Jeton tiré
	 */
	private Jeton jetonTire;

	/**
	 * distance parcourue pour atteindre cet état
	 */
	private int cost;

	public State(int x, int y, Scene scene){
		this.scene = scene;
		//Création du plateau
		this.plateauJeu = new Plateau(x,y);
		//Tirage du jeton
		jetonTire = Jeton.tirageJeton();

		this.plateauJeu.addGroupPlateau(jetonTire);
		this.plateauJeu.ajoutObserveurCases(this);

		creerRobot();
		dessinerSelecteur();
		actionClavier();
		//Génération de l'heuristique de chaque case pour cet état initial
		heuristiquePlateau = new Heuristique(this);
	}

	//Constructeur de copie
	public State(State state){
		//Copie du plateau, du jeton tiré, du coup et de l'heuristique de chaque case
		this.plateauJeu = state.plateauJeu;
		this.jetonTire = state.jetonTire;
		this.heuristiquePlateau = new Heuristique(this);
		this.cost = state.cost;
		//Copie des robots
		this.tableauRobots = new ArrayList<>();
		for(int i = 0 ; i < state.tableauRobots.size() ; i++) {
			Robot r = new Robot(this, state.tableauRobots.get(i).getCouleur(), state.tableauRobots.get(i).getPositionX(), state.tableauRobots.get(i).getPositionY(), state.tableauRobots.get(i).getPositionInitialeX(), state.tableauRobots.get(i).getPositionInitialeY());
			addTableauRobots(r);
			//Copie du robot selectionné
			if(state.robotSelect == state.tableauRobots.get(i)) {
				this.robotSelect = r;
			}
		}
	}

	/**
	 * Redéfinition de la méthode compareTo.
	 * @return la différence entre 2 coups
	 */
	@Override
	public int compareTo(State s){
		return (cost() - s.cost());
	}

	/**
	 * Récupère le coup de cet état
	 * @return le coup de cet état
	 */
	public int cost(){
		//Le cout correspond distance parcourue pour atteindre cet état et de l'heuristique
		return cost + heuris();
	}

	/**
	 * Récupère la distance parcourue pour atteindre cet état
	 * @return la distance parcourue pour atteindre cet état
	 */
	public int getVarCost(){
		return cost;
	}

	/**
	 * Modifie la valeur du coup (distance parcourue pour atteindre cet état
	 * @param cost la nouvelle valeur
	 */
	public void setValCost(int cost){
		this.cost = cost;
	}

	/**
	 * Récupère l'heuristique de cet état
	 * @return l'heuristique de cet état
	 */
	public int heuris(){
		Robot r = getRobotGagnant();
		//L'heuristique correspond à celui de la case où se situe le robot qui doit récupérer le jeton
		return plateauJeu.getCasePlateau(r.getPositionX() , r.getPositionY()).getHeuristique();
	}

	/**
	 * Déplacement du robot selon une direction
	 * @param direction la direction de déplacement
	 */
	public void deplacerRobot(Deplacement direction){
		//par défaut, le déplacement s'effectue sur le robot sélectionné
		deplacerRobot(direction, robotSelect);
	}

	/**
	 * Déplacement d'un robot selon une direction
	 * @param direction la direction de déplacement
	 * @param robot le robot que l'on veut déplacer
	 */
	public void deplacerRobot(Deplacement direction, Robot robot){
		//Déplacement du robot
		move(direction, robot);
		//On sauvegarde la direction et le robot
		lastRobot = robot;
		lastDirection = direction;

		//On remet à jour l'affichage graphique du robot
		robot.refreshPosRobot();
	}

	/**
	 * Redéfinition de la méthode clicSurRobot de l'interface CaseClickedObserver
	 */
	@Override
	public void clicSurRobot(Robot robot){
		//Au clic sur un robot, il devient le robot sélectionné
		robotSelect = robot;
		refreshSelecteurRobot();
	}

	/**
	 * Redéfinition de la méthode clicSurCase de l'interface RobotClickedObserver
	 * @param casePlateau la case cliquée
	 */
	@Override
	public void clicSurCase(Case casePlateau){
		//Au clic sur la case, on remet le robot qui doit jouer comme robot séléctionné
		robotAJouer();
	}

	/**
	 * Création des robots
	 */
	public void creerRobot(){
		//Tirage de deux nombre aléatoires pour les coordonnées initiale d'un robot
        int aleaX = r.nextInt(16);
        int aleaY = r.nextInt(16);

        //On vérifie si la position tirée n'existe pas
        int[] posRobotJaune = positionRobotNonUtilise();
        //Création d'un robot Jaune
        Robot robotJaune = new Robot(this, "jaune", posRobotJaune[0], posRobotJaune[1]);
		addTableauRobots(robotJaune);

		//On vérifie si la position tirée n'existe pas
        int[] posRobotBleu = positionRobotNonUtilise();
		//Création d'un robot Bleu
        Robot robotBleu = new Robot(this, "bleu", posRobotBleu[0], posRobotBleu[1]);
		addTableauRobots(robotBleu);

		//On vérifie si la position tirée n'existe pas
		int[] posRobotRouge = positionRobotNonUtilise();
		//Création d'un robot Rouge
        Robot robotRouge = new Robot(this, "rouge", posRobotRouge[0], posRobotRouge[1]);
		addTableauRobots(robotRouge);

		//On vérifie si la position tirée n'existe pas
		int[] posRobotVert = positionRobotNonUtilise();
		//Création d'un robot Vert
        Robot robotVert = new Robot(this, "vert", posRobotVert[0], posRobotVert[1]);
		addTableauRobots(robotVert);

		for(int i = 0; i < tableauRobots.size(); i++){
			Robot robotActuel = tableauRobots.get(i);
			//Affichage des robots
			robotActuel.robotRender();
			//On ajoute le robots créés au groupe de dessin
			plateauJeu.addGroupPlateau(robotActuel);
			//On ajoute chaque robot comme étant observable, à recevoir des clics
			robotActuel.ajouterObserveurRobotClique(this);
		}

		//Défini le robot à jouer
		robotAJouer();
	}

	/**
	 * Déplacement du robot selon l'entrée clavier
	 */
	public void actionClavier(){
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke){
				//Si on appuie sur la flèche du bas
				if (ke.getCode() == KeyCode.DOWN) {
					//Déplacement du robot vers le bas
					deplacerRobot(Deplacement.DOWN);
					//On regénère le jeton si on a atteint la case objectif
					reinitState();
					//Bloqie la propagation de l'événement
					ke.consume();
				}
				//Si on appuie sur la flèche de gauche
				else if (ke.getCode() == KeyCode.LEFT) {
					//Déplacement du robot vers la gauche
					deplacerRobot(Deplacement.LEFT);
					//On regénère le jeton si on a atteint la case objectif
					reinitState();
					//Bloqie la propagation de l'événement
					ke.consume();
				}
				//Si on appuie sur la flèche du haut
				else if (ke.getCode() == KeyCode.UP) {
					//Déplacement du robot vers le haut
					deplacerRobot(Deplacement.UP);
					//On regénère le jeton si on a atteint la case objectif
					reinitState();
					//Bloqie la propagation de l'événement
					ke.consume();
				}
				//Si on appuie sur la flèche de droite
				 else if (ke.getCode() == KeyCode.RIGHT){
					//Déplacement du robot vers la droite
					deplacerRobot(Deplacement.RIGHT);
					//On regénère le jeton si on a atteint la case objectif
					reinitState();
					//Bloqie la propagation de l'événement
					ke.consume();
				}
			}
		});
	}

	/**
	 * Vérifie si l'on est sur une des cases interdites
	 * @return true si on est sur une case interdite, false sinon
	 */
	public boolean estSurCaseInterdite(){
		for(int i = 0; i < plateauJeu.getZoneInterdite().length; i++){
			if(this.aleaX == plateauJeu.getZoneInterdite()[i][0] && this.aleaY == plateauJeu.getZoneInterdite()[i][1]){
				return true;
			}
		}
		return false;
	}

	/**
	 * Vérifie si l'on se situe sur une case jeton ou non
	 * @param x la coordonnée X de la case
	 * @param y la coordonnée Y de la case
	 * @return true si les coordonnées coorespondant à une caseJeton, false sinon
	 */
	public boolean estSurJeton(int x, int y){
		if(plateauJeu.getCasePlateau(x, y) instanceof CaseJeton){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Vérifie si l'on se situe sur une case jeton ou non
	 * @param caseSelect la case à tester
	 * @return true si on est sur une caseJeton, false sinon
	 */
	public boolean estSurJeton(Case caseSelect){
		if(caseSelect instanceof CaseJeton){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Détermine si le robot séléctionné est en collision avec un autre robot ou un mur
	 * @param direction la direction de déplacement
	 * @return true si il y a une collision, false sinon
	 */
	public boolean estUneCollisionRobot(Deplacement direction){
		//Par défaut, le test de connision s'effectue sur le robot sélectionné
		return estUneCollisionRobot(direction, this.robotSelect);
	}

	/**
	 * Détermine si un robot est en collision avec un autre robot ou un mur lors d'un déplacement
	 * @param direction la direction de déplacement
	 * @param robot le robot testé pour la collision
	 * @return true si il y a une collision, false sinon
	 */
	public boolean estUneCollisionRobot(Deplacement direction, Robot robot){
		switch(direction){
			case UP:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					//Si le robot rencontre un mur ou autre robot, alors il y a collision
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false && this.tableauRobots.get(i).getPositionX() == robot.getPositionX() && this.tableauRobots.get(i).getPositionY() == (robot.getPositionY() - 1)){
						return true;
					}
				}
				return false;

			case DOWN:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					//Si le robot rencontre un mur ou autre robot, alors il y a collision
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false && this.tableauRobots.get(i).getPositionX() == robot.getPositionX() &&  this.tableauRobots.get(i).getPositionY() == (robot.getPositionY() + 1)){
						return true;
					}
				}
				return false;

			case LEFT:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					//Si le robot rencontre un mur ou autre robot, alors il y a collision
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false && this.tableauRobots.get(i).getPositionX() == (robot.getPositionX() - 1)  &&  this.tableauRobots.get(i).getPositionY() == robot.getPositionY()){
						return true;
					}
				}
				return false;

			case RIGHT:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					//Si le robot rencontre un mur ou autre robot, alors il y a collision
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false&& this.tableauRobots.get(i).getPositionX() == (robot.getPositionX() + 1)  &&  this.tableauRobots.get(i).getPositionY() == robot.getPositionY()){
						return true;
					}
				}
				return false;
		}
		return true;
	}

	/**
	 * Détermine si des coordonnées correspondent à un autre robot ou non
	 * @param aleaX coordonné X testée
	 * @param aleaY coordonné Y testée
	 * @return true si les coordonnées sonr existantes, false sinon
	 */
	public boolean estSurAutresRobots(int aleaX, int aleaY){
		for(int i = 0; i <= this.tableauRobots.size() -1 ; i++){
			//Vérifie si les coordonnées X et Y tirées sont déja affectés a un robot déja crée
			if(aleaX == this.tableauRobots.get(i).getPositionInitialeX() && aleaY == this.tableauRobots.get(i).getPositionInitialeY()){
				//Si c'est le cas, on retrourne vrai
				return true;
			}
		}
		//Si ce n'est pas le cas, on retrourne false
		return false;
	}

	/**
	 * Vérifie si le robot a atteint son objectif
	 * @return true si le robot a atteint son objectif, false sinon
	 */
	public boolean estGagnant(){
		//On récupère le robot qui doit aller à l'objectif
		Robot robot = getRobotGagnant();
		//On récupère la case sur laquelle le robot se situe
		Case caseDessus = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
		//On vérifie que la case contient bien un jeton
		if(estSurJeton(caseDessus)){
			//On cast cette case en caseJeton
			CaseJeton caseJetonDessus = (CaseJeton) caseDessus;
			//On vérifie que le jeton de la case correspond à celui tiré
			if(robot.getCouleur().equals(caseJetonDessus.getCouleur()) && caseJetonDessus.estSurCaseJetonTire(jetonTire)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Vérifie si cet état est un état final
	 * @return true s'il s'agit de l'état final, false sinon
	 */
	public boolean estEtatFinal(){
		return this.estGagnant();
	}

	/**
	 * Tire aléatoirement des coordonnées pour un robot et vérifie qu'elles sont correctes
	 * @return des coordonnées correctes pour un robot
	 */
	public int[] positionRobotNonUtilise(){
		boolean surJeton = false;
		boolean surRobot = false;
		boolean surCaseInterdite = false;;

		//On fait le procédé tant que les positions du robot générées ne sont ni sur un jeton, ni sur un robot, ni sur case interdite
		do{
			this.aleaX = r.nextInt(16);
			this.aleaY = r.nextInt(16);

			surJeton = estSurJeton(aleaX,aleaY);
			//si les coordonnées sont sur un jeton, alors on re-génère des coordonnées et on recommence
			if(surJeton){
				continue;
			}
			surCaseInterdite = estSurCaseInterdite();

			//si les coordonnées sont sur une case interdite, alors on re-génère, et on recommence tout
			if(surCaseInterdite){
				continue;
			}

			//On vérifie si on a déja des robots de créer
			if(this.tableauRobots.size() != 0){
				surRobot = estSurAutresRobots(this.aleaX, this.aleaY);

				//si les coordonnées sont sur un robot déja crée, alors on re-génère, et on recommence tout
				if(surRobot){
					continue;
				}
			}
		}
		while(surJeton || surCaseInterdite || surRobot);

		int[] position = {this.aleaX, this.aleaY};
		return position;
	}

	/**
	 * Récupère le robot qui permet de gagner la partie
	 * @return le robot qui doit jouer
	 */
	public Robot getRobotGagnant(){
		for(int i =0; i< this.tableauRobots.size(); i++){
			if(this.tableauRobots.get(i).estRobotAJouer(jetonTire.getCouleur())){
				return this.tableauRobots.get(i);
			}
		}
		return null;
	}
	/**
	 * désigner quel robot doit "attraper" le jeton
	 */
	public void robotAJouer(){
		setRobotbyColor(jetonTire.getCouleur());
		refreshSelecteurRobot();
	}

	/**
	 * Modifie le robot séléctionné à partir d'une couleur
	 * @param couleur la couleur de robot voulue
	 */
	public void setRobotbyColor(String couleur){
		//On vérifie chaque robot s'il correspond à la couleur
		for(int i = 0; i < tableauRobots.size(); i++){
			//Si la couleur correspond, alors on change le robot sélectionné
			if(tableauRobots.get(i).getCouleur().equals(couleur)){
				robotSelect = tableauRobots.get(i);
			}
		}
	}

	/**
	 * Déplacement du robot sélectionné dans une direction
	 * @param direction la direction de déplacement
	 */
	public void move(Deplacement direction){
		//Par défaut, le déplacement s'effectue avec le robot sélectionné
		move(direction, robotSelect);
	}

	/**
	 * Déplacement d'un robot dans une direction
	 * @param direction la direction de déplacement
	 * @param robot le robot à déplacer
	 */
	public void move(Deplacement direction, Robot robot){
		//Vérification de la direction choisie
		if(direction == Deplacement.UP){
			//Tant que le robot ne rencontre pas un mur en haut, ou un autre robot, il se dirige vers le haut
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValHaut() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY() - 1).getValBas() != 1 && !estUneCollisionRobot(direction, robot)){
				robot.translatePositionY(-1);
			}
		}else if(direction == Deplacement.DOWN){
			//Tant que le robot ne rencontre pas un mur en bas, ou un autre robot, il se dirige vers le bas
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValBas() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY() +1).getValHaut() != 1 && !estUneCollisionRobot(direction, robot)){
				robot.translatePositionY(1);
			}
		}else if(direction == Deplacement.LEFT){
			//Tant que le robot ne rencontre pas un mur à gauche,  ou un autre robot, il se dirige vers la gauche
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValGauche() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX() -1, robot.getPositionY()).getValDroit() != 1&& !estUneCollisionRobot(direction, robot)){
				robot.translatePositionX(-1);
			}
		}else if(direction == Deplacement.RIGHT){
			//Tant que le robot ne rencontre pas un mur à droite,  ou un autre robot, il se dirige vers la droite
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValDroit() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX() + 1, robot.getPositionY()).getValGauche() != 1  && !estUneCollisionRobot(direction, robot)){
				robot.translatePositionX(1);
			}
		}

		refreshSelecteurRobot();
	}

	/**
	 * Réinitialiser le state, c'est-à-dire regénération du jeton et repositionnement des robots à leurs socle
	 */
	public void reinitState(){
		if(estGagnant()){
			//Positionnment du socle du robot à l'endroit où il attrapé le jeton
			robotSelect.nouvellePositionSocle();
			//On remet tout les robot sur leurs socles
			for(int i = 0; i < this.tableauRobots.size(); i++){
				this.tableauRobots.get(i).reinitialiserPosition();
			}
			//Re-tirage du jeton
			this.jetonTire = Jeton.tirageJeton();
			//Calcul de l'heuristique sur chaque case
			heuristiquePlateau = new Heuristique(this);
			//Ajouter le jeton au groupe de plateau
			plateauJeu.addGroupPlateau(jetonTire);
			//On sélectionne le robot à jouer
			robotAJouer();
		}
	}

	/**
	 * récupèration la case gagnante
	 * @return la case gagante
	 */
	public CaseJeton getCaseJetonTire(){
		CaseJeton caseGagne;
		for(int y = 0; y < plateauJeu.getTaillePlateau(); y++){
			for(int x = 0; x < plateauJeu.getTaillePlateau(); x++){
				if(estSurJeton(x, y)){
					caseGagne = (CaseJeton) plateauJeu.getCasePlateau(x, y);
					if(jetonTire.getCouleur().equals(caseGagne.getCouleur()) && jetonTire.getForme().equals(caseGagne.getForme())){
						return caseGagne;
					}
				 }
			}
		}
		return null;
	}

	/**
	 * Ajoute un robot à la liste des robots
	 * @param robot le robot à ajouter
	 */
	public void addTableauRobots(Robot robot){
		this.tableauRobots.add(robot);
	}

	/**
	 * Création d'un état suivant après déplacement d'un robot
	 * @param direction la durection du déplacement
	 * @param robot le robot à daplacer
	 * @return le nouvel état
	 */
	public State etatSuivant(Deplacement direction, Robot robot){
		//Copie de cet état
		State newState = new State(this);

		newState.setRobotbyColor(robot.getCouleur());
		//Déplacement sur la copie de l'état
		newState.deplacerRobot(direction);

		return newState;
	}

	/**
	 * Récupère la liste des robots
	 * @return la liste des robots
	 */
	public ArrayList<Robot> getListeRobot(){
		return this.tableauRobots;
	}

	/**
	 * Récupère le jeton tiré
	 * @return le jeton tiré
	 */
	public Jeton getJetonTire(){
		return this.jetonTire;
	}

	/**
	 * Récupère le robot séléctionné
	 * @return le robot séléctionné
	 */
	public Robot getRobotSelect(){
		return this.robotSelect;
	}


	/**
	 * Récupère le dernier robot ayant été déplacé
	 * @return le dernier robot ayant été déplacé
	 */
	public Robot getLastRobot(){
		return this.lastRobot;
	}

	/**
	 * Récupère le dernier déplacement éffectué
	 * @return le dernier déplacement éffectué
	 */
	public Deplacement getLastDeplacement(){
		return this.lastDirection;
	}

	/**
	 * Récupère le plateau de jeu
	 * @return le plateau de jeu
	 */
	public Plateau getEtatPlateau(){
		return this.plateauJeu;
	}

	/**
	 * Récupère l'état actuel
	 * @return l'état actuel
	 */
	public State getStateActuel(){
		return this;
	}

	/**
	 * Redéfinition de la méthode équals
	 * @param obj un Objet à comparer avec cet Objet
	 * @return true si les deux sont égales, false sinon
	 */
	@Override
	public boolean equals(Object obj){
		//si l'Objet est égal a cette classe, on retourne true
		if(this == obj){
			return true;
		}
		//si l'Objet envoyé vaut null, on retourne false
		if(obj == null){
			return false;
		}
		//si le deux classes de ces objets sont différents, on retourne false
		if(getClass() != obj.getClass()){
			return false;
		}
		//Transtypage de l'objet en State
		State state = (State) obj;

		//Si un seul robot est différent, on retourne false
		for(int i = 0; i< state.tableauRobots.size(); i++){
			if(!state.tableauRobots.get(i).equals(tableauRobots.get(i))){
				return false;
			}
		}

		//Si le jeton tiré n'est pas le même, on retourne false
		if(!state.jetonTire.equals(jetonTire)){
			return false;
		}

		//On retourne true si toute les conditions sont remplies
		return true;
	}

	/**
	 * Affichage graphique du sélecteur
	 */
	public void dessinerSelecteur(){
		this.imgSelect = new ImageView(new Image("images/select.png"));
		//taille du sélécteur
		this.imgSelect.setFitWidth(Case.DIM);
		this.imgSelect.setFitHeight(Case.DIM);
		//ajout au groupe d'images
		plateauJeu.addGroupPlateau(this.imgSelect);
		//Mise a jour de son positionnemnet
		refreshSelecteurRobot();
	}

	/**
	 * Positionnment du sélecteur sur le robot sélectionné
	 */
	public void refreshSelecteurRobot(){
		if(imgSelect != null){
			this.imgSelect.setX(robotSelect.getPositionX() * Case.DIM + Plateau.DEPART_X);
			this.imgSelect.setY(robotSelect.getPositionY() * Case.DIM + Plateau.DEPART_X);
		}
	}

	/**
	 * Redéfinition du hashCode
	 * @return le hashcode de l'état
	 */
	@Override
	public int hashCode(){
		 int result = 1;
		 result += 1 * tableauRobots.get(0).hashCode();
		 result += 345 * tableauRobots.get(1).hashCode();
		 result += 7564 * tableauRobots.get(2).hashCode();
		 result += 31 * tableauRobots.get(3).hashCode();

		 result += 700 * jetonTire.hashCode();
		 return result;
	}

	/**
	* Redéfinition du toString
	* @return l'affichage en console de l'état
	*/
	@Override
	public String toString() {
		String str = "State [tableauRobots=";
		for(Robot r : tableauRobots){
			str += r;
		}
		return str + ", cost=" + cost +", jeton=" + jetonTire + "id mem" + super.toString() + "]";
	}
}
