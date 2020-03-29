package ricochet_robots;

import java.util.Random;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.input.*;

public class State implements RobotClickedObserver, CaseClickedObserver{

	private Plateau plateauJeu;
	//Liste des robots instanciés
	public ArrayList<Robot> tableauRobots = new ArrayList<>();

	Score score;
	Scene scene;

	Robot robotSelect;

	int hashCodeState;

	Deplacement lastDirection = null;
	Robot lastRobot = null;

	Random r = new Random();

	//Les nombres aléatoires
	private int aleaX = -1;
	private int aleaY = -1;

	Jeton jetonTire;

	public State(int x, int y, Score score, Scene scene){
		this.score = score;
		this.scene = scene;

		this.plateauJeu = new Plateau(x,y,score);
		jetonTire = Jeton.tirageJeton();
		this.plateauJeu.addGroupPlateau(jetonTire);
		this.plateauJeu.ajoutObserveurCases(this);
		creerRobot();
		refreshHashCode();
		actionClavier();
	}

	//Constructeur de copie
	public State(State state){
		this.plateauJeu = state.plateauJeu;
		this.jetonTire = state.jetonTire;

		this.tableauRobots = new ArrayList<>();
		for(int i = 0 ; i < state.tableauRobots.size() ; i++) {
			Robot r = new Robot(this, state.tableauRobots.get(i).getCouleur(), state.tableauRobots.get(i).getPositionX(), state.tableauRobots.get(i).getPositionY(), state.tableauRobots.get(i).getPositionInitialeX(), state.tableauRobots.get(i).getPositionInitialeY());
			if(state.robotSelect == state.tableauRobots.get(i)) {
				this.robotSelect = r;
			}
		}
	}

	public void deplacerRobot(Deplacement direction){
		deplacerRobot(direction, robotSelect);
	}

	public void deplacerRobot(Deplacement direction, Robot robot){
		//Déplacement du robot
		move(direction, robot);
		//On sauvegarde la direction et le robot;
		lastRobot = robot;
		lastDirection = direction;

		//On remet à jour l'affichage du robot
		robot.refreshPosRobot();
		//on recalcule le hashcode
		refreshHashCode();
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
		//On ajoute le robot créé au groupe de dessin
		plateauJeu.addGroupPlateau(robotJaune);
        //On ajoute la possibilité du robot à avoir des événements clics
		robotJaune.ajouterObserveurRobotClique(this);

        int[] posRobotBleu = positionRobotNonUtilise();
        Robot robotBleu = new Robot(this, "bleu", posRobotBleu[0], posRobotBleu[1]);
		plateauJeu.addGroupPlateau(robotBleu);
		robotBleu.ajouterObserveurRobotClique(this);

        int[] posRobotRouge = positionRobotNonUtilise();
        Robot robotRouge = new Robot(this, "rouge", posRobotRouge[0], posRobotRouge[1]);
		plateauJeu.addGroupPlateau(robotRouge);
        //tableauRobots.add(robotRouge);
		robotRouge.ajouterObserveurRobotClique(this);

        int[] posRobotVert = positionRobotNonUtilise();
        Robot robotVert = new Robot(this, "vert", posRobotVert[0], posRobotVert[1]);
		plateauJeu.addGroupPlateau(robotVert);
        //tableauRobots.add(robotVert);
		robotVert.ajouterObserveurRobotClique(this);

		robotAJouer();
	}

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

	public boolean estSurCaseInterdite(){
		for(int i = 0; i < plateauJeu.getZoneInterdite().length; i++){
			if(this.aleaX == plateauJeu.getZoneInterdite()[i][0] && this.aleaY == plateauJeu.getZoneInterdite()[i][1]){
				return true;
			}
		}
		return false;
	}

	public boolean estSurJeton(){
		if(plateauJeu.getCasePlateau(this.aleaX, this.aleaY) instanceof CaseJeton){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean estUneCollisionRobot(Deplacement direction){
		switch(direction){
			case UP:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur() != robotSelect.getCouleur() && this.tableauRobots.get(i).getPositionX() == robotSelect.getPositionX() &&  this.tableauRobots.get(i).getPositionY() == (robotSelect.getPositionY() - 1)){
						return true;
					}
				}
				return false;

			case DOWN:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur() != robotSelect.getCouleur() && this.tableauRobots.get(i).getPositionX() == robotSelect.getPositionX() &&  this.tableauRobots.get(i).getPositionY() == (robotSelect.getPositionY() + 1)){
						return true;
					}
				}
				return false;

			case LEFT:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur() != robotSelect.getCouleur() && this.tableauRobots.get(i).getPositionX() == (robotSelect.getPositionX() - 1)  &&  this.tableauRobots.get(i).getPositionY() == robotSelect.getPositionY()){
						return true;
					}
				}
				return false;

			case RIGHT:
				for(int i = 0; i< this.tableauRobots.size(); i++){
					if(this.tableauRobots.get(i).getCouleur() != robotSelect.getCouleur() && this.tableauRobots.get(i).getPositionX() == (robotSelect.getPositionX() + 1)  &&  this.tableauRobots.get(i).getPositionY() == robotSelect.getPositionY()){
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

	public boolean estGagnant(){
		return estGagnant(this.robotSelect);
	}

	public boolean estEtatFinal(){
		return this.estGagnant();
	}

	public void refreshHashCode(){
		robotSelect.hashCode();
		this.hashCodeState = hashCode();
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

	//désigner quel robot doit être déplacé
	public void robotAJouer(){
		for(int i =0; i< this.tableauRobots.size(); i++){
			if(this.tableauRobots.get(i).estRobotAJouer(jetonTire.getCouleur())){
				robotSelect = this.tableauRobots.get(i);
				break;
			}
		}
	}

	public void move(Deplacement direction){
		move(direction, robotSelect);
	}

	//Déplacement du robot
	public void move(Deplacement direction, Robot robotSelect){
		this.robotSelect = robotSelect;
		//Vérification de la direction choisie
		if(direction == Deplacement.UP){
			//Tant que le robot ne rencontre pas un mur en haut, ou un autre robot, il se dirige vers le haut
			while(this.plateauJeu.getCasePlateau(robotSelect.getPositionX(), robotSelect.getPositionY()).getValHaut() != 1 && this.plateauJeu.getCasePlateau(robotSelect.getPositionX(), robotSelect.getPositionY() - 1).getValBas() != 1 && !estUneCollisionRobot(direction)){
				robotSelect.translatePositionY(-1);
			}
		}else if(direction == Deplacement.DOWN){
			//Tant que le robot ne rencontre pas un mur en bas, ou un autre robot, il se dirige vers le bas
			while(this.plateauJeu.getCasePlateau(robotSelect.getPositionX(), robotSelect.getPositionY()).getValBas() != 1 && this.plateauJeu.getCasePlateau(robotSelect.getPositionX(), robotSelect.getPositionY() +1).getValHaut() != 1 && !estUneCollisionRobot(direction)){
				robotSelect.translatePositionY(1);
			}
		}else if(direction == Deplacement.LEFT){
			//Tant que le robot ne rencontre pas un mur à gauche,  ou un autre robot, il se dirige vers la gauche
			while(this.plateauJeu.getCasePlateau(robotSelect.getPositionX(), robotSelect.getPositionY()).getValGauche() != 1 && this.plateauJeu.getCasePlateau(robotSelect.getPositionX() -1, robotSelect.getPositionY()).getValDroit() != 1&& !estUneCollisionRobot(direction)){
				robotSelect.translatePositionX(-1);
			}
		}else if(direction == Deplacement.RIGHT){
			//Tant que le robot ne rencontre pas un mur à droite,  ou un autre robot, il se dirige vers la droite
			while(this.plateauJeu.getCasePlateau(robotSelect.getPositionX(), robotSelect.getPositionY()).getValDroit() != 1 && this.plateauJeu.getCasePlateau(robotSelect.getPositionX() + 1, robotSelect.getPositionY()).getValGauche() != 1  && !estUneCollisionRobot(direction)){
				robotSelect.translatePositionX(1);
			}
		}
	}

	public void reinitState(){
		reinitState(robotSelect);
	}
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

	public boolean estGagnant(Robot robot){
		//score.ajouterCoup();
		Case caseDessus = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
		if(caseDessus instanceof CaseJeton){
			CaseJeton caseJetonDessus = (CaseJeton) caseDessus;
			if(robot.getCouleur() == caseJetonDessus.getCouleur() && caseJetonDessus.estSurCaseJetonTire(jetonTire)){
				return true;
			}
		}
		return false;
	}

	public CaseJeton getCaseJetonTire(){
		CaseJeton caseGagne;
		for(int y = 0; y < plateauJeu.getTaillePlateau(); y++){
			for(int x = 0; x < plateauJeu.getTaillePlateau(); x++){
				if(plateauJeu.getCasePlateau(x, y) instanceof CaseJeton){
					caseGagne = (CaseJeton) plateauJeu.getCasePlateau(x, y);
					if(jetonTire.getCouleur() == caseGagne.getCouleur() && jetonTire.getForme() == caseGagne.getForme()){
						return caseGagne;
					}
				 }
			}
		}
		return null;
	}

	public void addTableauRobots(Robot robot){
		this.tableauRobots.add(robot);
	}

	public State etatSuivant(Deplacement direction, Robot robot){
		State newState = new State(this);
		newState.deplacerRobot(direction);

		return newState;
	}

	public ArrayList<Robot> getListeRobot(){
		return this.tableauRobots;
	}

	public Jeton getJetonTire(){
		return this.jetonTire;
	}

	public Robot getRobotAJouer(){
		return this.robotSelect;
	}

	// public void getIdRobot(int i){
	// 	System.out.println("id robot " + tableauRobots.get(i).getCouleur() + ": " + tableauRobots.get(i).hashCode() );
	// }

	public Robot getLastRobot(){
		return this.lastRobot;
	}

	public Deplacement getLastDeplacement(){
		return this.lastDirection;
	}

	public Plateau getEtatPlateau(){
		return this.plateauJeu;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

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
	public int hashCode() {
		 final int prime = 31;
		 int result = 1;
		 for(int i = 0; i < this.tableauRobots.size(); i++){
			  result += tableauRobots.get(i).hashCode();
		 }
		 result *= prime;
		 return result;
	}
}
