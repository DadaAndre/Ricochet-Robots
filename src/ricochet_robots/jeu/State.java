package ricochet_robots.jeu;

import ricochet_robots.jeu.pions.*;
import ricochet_robots.jeu.observer.*;
import ricochet_robots.jeu.plateau.*;
import ricochet_robots.jeu.*;

import java.util.Random;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.input.*;

public class State implements RobotClickedObserver, CaseClickedObserver, Comparable<State>{

	private Plateau plateauJeu;
	//Liste des robots instanciés
	public ArrayList<Robot> tableauRobots = new ArrayList<>();

	Score score;
	Scene scene;

	Robot robotSelect;

	Deplacement lastDirection = null;
	Robot lastRobot = null;

	Random r = new Random();

	//Les nombres aléatoires
	private int aleaX = -1;
	private int aleaY = -1;

	Jeton jetonTire;

	public int cost;

	@Override
	public int compareTo(State s) {
		return (cost() - s.cost());
	}

	public int cost() {
		return cost + heuris();
	}

	public int heuris() {
		Robot r = getRobotGagnant();
		return plateauJeu.getCasePlateau(r.getPositionX() , r.getPositionY()).getHeuristique();
	}

	public State(int x, int y, Score score, Scene scene){
		this.score = score;
		this.scene = scene;

		this.plateauJeu = new Plateau(x,y,score);
		jetonTire = Jeton.tirageJeton();
		this.plateauJeu.addGroupPlateau(jetonTire);
		this.plateauJeu.ajoutObserveurCases(this);
		creerRobot();
		actionClavier();
	}

	//Constructeur de copie
	public State(State state){
		//Copie du plateau et du jeton tiré
		this.plateauJeu = state.plateauJeu;
		this.jetonTire = state.jetonTire;

		//Copie des robots
		this.tableauRobots = new ArrayList<>();
		for(int i = 0 ; i < state.tableauRobots.size() ; i++) {
			Robot r = new Robot(this, state.tableauRobots.get(i).getCouleur(), state.tableauRobots.get(i).getPositionX(), state.tableauRobots.get(i).getPositionY(), state.tableauRobots.get(i).getPositionInitialeX(), state.tableauRobots.get(i).getPositionInitialeY());

			//Copie du robot selectionné
			if(state.robotSelect == state.tableauRobots.get(i)) {
				this.robotSelect = r;
			}
		}
	}

	public void deplacerRobot(Deplacement direction){
		deplacerRobot(direction, robotSelect);
	}

	//Déplacement du robot et de l'image
	public void deplacerRobot(Deplacement direction, Robot robot){
		//Déplacement du robot
		move(direction, robot);
		//On sauvegarde la direction et le robot;
		lastRobot = robot;
		lastDirection = direction;

		//On remet à jour l'affichage du robot
		robot.refreshPosRobot();
	}

	//On sélectionne le robot à déplacer si on clique dessus
	@Override
	public void clicSurRobot(Robot robot){
		System.out.println("Robot " + robot.getCouleur() + " cliqué");
		robotSelect = robot;
	}

	//Création des robots
	public void creerRobot(){
		//Tirage de deux nombre aléatoires pour les coordonnées initiale d'un robot
        int aleaX = r.nextInt(16);
        int aleaY = r.nextInt(16);

        //On vérifie si la position tirée n'existe pas
        int[] posRobotJaune = positionRobotNonUtilise();
        //Création d'un robot Jaune
        Robot robotJaune = new Robot(this, "jaune", posRobotJaune[0], posRobotJaune[1]);

		robotJaune.robotRender();
		//On ajoute le robot créé au groupe de dessin
		plateauJeu.addGroupPlateau(robotJaune);
        //On ajoute la possibilité du robot à avoir des événements clics
		robotJaune.ajouterObserveurRobotClique(this);

        int[] posRobotBleu = positionRobotNonUtilise();
        Robot robotBleu = new Robot(this, "bleu", posRobotBleu[0], posRobotBleu[1]);
		robotBleu.robotRender();
		plateauJeu.addGroupPlateau(robotBleu);
		robotBleu.ajouterObserveurRobotClique(this);

        int[] posRobotRouge = positionRobotNonUtilise();
        Robot robotRouge = new Robot(this, "rouge", posRobotRouge[0], posRobotRouge[1]);
		robotRouge.robotRender();
		plateauJeu.addGroupPlateau(robotRouge);
        //tableauRobots.add(robotRouge);
		robotRouge.ajouterObserveurRobotClique(this);

        int[] posRobotVert = positionRobotNonUtilise();
        Robot robotVert = new Robot(this, "vert", posRobotVert[0], posRobotVert[1]);
		robotVert.robotRender();
		plateauJeu.addGroupPlateau(robotVert);
        //tableauRobots.add(robotVert);
		robotVert.ajouterObserveurRobotClique(this);

		robotAJouer();
	}

	//Déplacement du robot selon l'entrée clavier
	public void actionClavier(){
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke){
				if (ke.getCode() == KeyCode.DOWN) {
					System.out.println("Key Pressed: " + ke.getCode());
					deplacerRobot(Deplacement.DOWN);
					reinitState();
					//score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.LEFT) {
					System.out.println("Key Pressed: " + ke.getCode());
					deplacerRobot(Deplacement.LEFT);
					reinitState();
					//score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.UP) {
					System.out.println("Key Pressed: " + ke.getCode());
					deplacerRobot(Deplacement.UP);
					reinitState();
					//score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.RIGHT) {
					System.out.println("Key Pressed: " + ke.getCode());
					deplacerRobot(Deplacement.RIGHT);
					reinitState();
					//score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				}
			}
		});
	}

	//On remet le robot à jouer si on clique sur une case
	@Override
	public void clicSurCase(Case casePlateau){
		System.out.println("case " + casePlateau +"cliqué");
		robotAJouer();
	}

	//Vérifie si l'on est sur une case interdite, définies dans plateau
	public boolean estSurCaseInterdite(){
		for(int i = 0; i < plateauJeu.getZoneInterdite().length; i++){
			if(this.aleaX == plateauJeu.getZoneInterdite()[i][0] && this.aleaY == plateauJeu.getZoneInterdite()[i][1]){
				return true;
			}
		}
		return false;
	}

	//Vérifie si l'on se situe sur une case jeton ou non
	public boolean estSurJeton(){
		if(plateauJeu.getCasePlateau(this.aleaX, this.aleaY) instanceof CaseJeton){
			return true;
		}
		else{
			return false;
		}
	}

	//Détermine si le robot séléctionné est en collision avec un autre robot ou non
	public boolean estUneCollisionRobot(Deplacement direction){
		return estUneCollisionRobot(direction, this.robotSelect);
	}

	//Détermine si le robot envoyé est en collision avec un autre robot ou non
	public boolean estUneCollisionRobot(Deplacement direction, Robot robot){
		switch(direction){
			case UP:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false && this.tableauRobots.get(i).getPositionX() == robot.getPositionX() && this.tableauRobots.get(i).getPositionY() == (robot.getPositionY() - 1)){
						return true;
					}
				}
				return false;

			case DOWN:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false && this.tableauRobots.get(i).getPositionX() == robot.getPositionX() &&  this.tableauRobots.get(i).getPositionY() == (robot.getPositionY() + 1)){
						return true;
					}
				}
				return false;

			case LEFT:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false && this.tableauRobots.get(i).getPositionX() == (robot.getPositionX() - 1)  &&  this.tableauRobots.get(i).getPositionY() == robot.getPositionY()){
						return true;
					}
				}
				return false;

			case RIGHT:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur().equals(robot.getCouleur()) == false&& this.tableauRobots.get(i).getPositionX() == (robot.getPositionX() + 1)  &&  this.tableauRobots.get(i).getPositionY() == robot.getPositionY()){
						return true;
					}
				}
				return false;
		}
		return true;
	}

	//Vérifie si deux robots sont en collisions
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

	//Retourne si le jeu est un état ganant ou non
	public boolean estGagnant(){
		Robot robot = getRobotGagnant();
		//score.ajouterCoup();
		Case caseDessus = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
		if(caseDessus instanceof CaseJeton){
			CaseJeton caseJetonDessus = (CaseJeton) caseDessus;
			if(robot.getCouleur().equals(caseJetonDessus.getCouleur()) && caseJetonDessus.estSurCaseJetonTire(jetonTire)){
				return true;
			}
		}
		return false;
	}

	//si le jeu est un état ganant ou non
	public boolean estEtatFinal(){
		return this.estGagnant();
	}

	//Tire aléatoirement des coordonnées pour un robot et vérifie qu'un robot ne les a pas déjà
	public int[] positionRobotNonUtilise(){
		boolean surJeton = false;
		boolean surRobot = false;
		boolean surCaseInterdite = false;;

		//On fait le procédé tant que les positions du robot générées ne sont ni sur un jeton, ni sur un robot, ni sur case interdite
		do{
			this.aleaX = r.nextInt(16);
			this.aleaY = r.nextInt(16);

			surJeton = estSurJeton();
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

	//Récupère le robot qui peermet de gagner la partie
	public Robot getRobotGagnant(){
		for(int i =0; i< this.tableauRobots.size(); i++){
			if(this.tableauRobots.get(i).estRobotAJouer(jetonTire.getCouleur())){
				return this.tableauRobots.get(i);
			}
		}
		return null;
	}

	//désigner quel robot doit "attraper" le jeton
	public void robotAJouer(){
		for(int i =0; i< this.tableauRobots.size(); i++){
			if(this.tableauRobots.get(i).estRobotAJouer(jetonTire.getCouleur())){
				robotSelect = this.tableauRobots.get(i);
				break;
			}
		}
	}

	//Modifie le robot séléctionné à partir d'une couleur
	public void setRobotbyColor(String couleur){
		for(int i = 0; i < tableauRobots.size(); i++){
			if(tableauRobots.get(i).getCouleur().equals(couleur)){
				robotSelect = tableauRobots.get(i);
			}
		}
	}

	//Déplacement du robot sélectionné
	public void move(Deplacement direction){
		move(direction, robotSelect);
	}

	//Déplacement du robot
	public void move(Deplacement direction, Robot robot){
		//this.robot = robot;
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
	}

	//Réinitialiser le state, c'est-à-dire regénération du jeton et repositionnement des robots à leurs spawns
	public void reinitState(){
		reinitState(robotSelect);
	}

	//Réinitialiser le state, c'est-à-dire regénération du jeton et repositionnement des robots à leurs spawns
	public void reinitState(Robot robot){
		boolean gagnant = estGagnant();
		if(gagnant){
			//System.out.println("déplacement réalisé en " + //score.getNbCoup() + " coups");
			//score.reinitialiserCoup();
			robot.nouvellePositionSocle();
			for(int i = 0; i < this.tableauRobots.size(); i++){
				this.tableauRobots.get(i).reinitialiserPosition();
			}
			this.jetonTire = Jeton.tirageJeton();
			plateauJeu.addGroupPlateau(jetonTire);
			robotAJouer();
		}
	}

	//récupère la case gagnante
	public CaseJeton getCaseJetonTire(){
		CaseJeton caseGagne;
		for(int y = 0; y < plateauJeu.getTaillePlateau(); y++){
			for(int x = 0; x < plateauJeu.getTaillePlateau(); x++){
				if(plateauJeu.getCasePlateau(x, y) instanceof CaseJeton){
					caseGagne = (CaseJeton) plateauJeu.getCasePlateau(x, y);
					if(jetonTire.getCouleur().equals(caseGagne.getCouleur()) && jetonTire.getForme().equals(caseGagne.getForme())){
						return caseGagne;
					}
				 }
			}
		}
		return null;
	}

	//Ajoute un robot à la liste des robots
	public void addTableauRobots(Robot robot){
		this.tableauRobots.add(robot);
	}

	//Création d'un état suivant après déplacement d'un robot
	public State etatSuivant(Deplacement direction, Robot robot){
		State newState = new State(this);
		newState.setRobotbyColor(robot.getCouleur());
		newState.deplacerRobot(direction);
		//System.out.println(robot.getCouleur() + " - " + direction);

		return newState;
	}

	//getters/setters

	public ArrayList<Robot> getListeRobot(){
		return this.tableauRobots;
	}

	public Jeton getJetonTire(){
		return this.jetonTire;
	}

	public Robot getRobotSelect(){
		return this.robotSelect;
	}

	public Robot getLastRobot(){
		return this.lastRobot;
	}

	public Deplacement getLastDeplacement(){
		return this.lastDirection;
	}

	public Plateau getEtatPlateau(){
		return this.plateauJeu;
	}

	public State getStateActuel(){
		return this;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		State state = (State) obj;
		for(int i = 0; i< state.tableauRobots.size(); i++){
			if(!state.tableauRobots.get(i).equals(tableauRobots.get(i))){
				return false;
			}
		}

		if(!state.jetonTire.equals(jetonTire)){
			return false;
		}


		return true;
	}

	@Override
	public int hashCode(){
		 int result = 1;
		 result += 1 * tableauRobots.get(0).hashCode();
		 result += 345 * tableauRobots.get(1).hashCode();
		 result += 7564 * tableauRobots.get(2).hashCode();
		 result += 31 * tableauRobots.get(3).hashCode();

		 result += 7 * jetonTire.hashCode();
		 return result;
	}

	/**
	* Create string representation of State for printing
	* @return
	*/
	@Override
	public String toString() {
		String str = "State [tableauRobots=";
		for(Robot r : tableauRobots)
			str += r;
		return str + ", cost=" + cost +", jeton=" + jetonTire + "id mem" + super.toString() + "]";

		//return "getLastDeplacement" + ((getLastDeplacement() != null) ? getLastDeplacement() : "") + " robot " + ((getLastRobot() == null) ? "" : getLastRobot().getCouleur());
	}
}
