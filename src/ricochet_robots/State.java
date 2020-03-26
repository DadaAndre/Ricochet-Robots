package ricochet_robots;

import java.util.Random;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.input.*;

public class State{

	private Plateau plateauJeu;
	//Liste des robots instanciés
	public static ArrayList<Robot> tableauRobots = new ArrayList<>();

	Score score;
	Scene scene;

	Random r = new Random();

	public State(int x, int y, Score score, Scene scene){
		this.score = score;
		this.scene = scene;
		this.plateauJeu = new Plateau(x,y,score);
		creerRobot();
		action();
	}

	public State(Plateau plateau){
		this.plateauJeu = plateau;
	}

	public Plateau getEtatPlateau(){
		return this.plateauJeu;
	}

	//Création des robots
	public void creerRobot(){
		//Tirage de deux nombre aléatoires pour les coordonnées initiale d'un robot
        int aleaX = r.nextInt(16);
        int aleaY = r.nextInt(16);

        //On vérifie si la position tirée n'existe pas
        int[] posRobotJaune = plateauJeu.positionRobotNonUtilise();
        //Création d'un robot Jaune
        Robot robotJaune = new Robot(this, "jaune", posRobotJaune[0], posRobotJaune[1]);
		//On ajoute le robot créé au groupe de dessin
		plateauJeu.addGroupPlateau(robotJaune);
        //On ajoute la possibilité du robot à avoir des événements clics
		robotJaune.ajouterObserveurRobotClique(plateauJeu);

        int[] posRobotBleu = plateauJeu.positionRobotNonUtilise();
        Robot robotBleu = new Robot(this, "bleu", posRobotBleu[0], posRobotBleu[1]);
		plateauJeu.addGroupPlateau(robotBleu);
		robotBleu.ajouterObserveurRobotClique(plateauJeu);

        int[] posRobotRouge = plateauJeu.positionRobotNonUtilise();
        Robot robotRouge = new Robot(this, "rouge", posRobotRouge[0], posRobotRouge[1]);
		plateauJeu.addGroupPlateau(robotRouge);
        //tableauRobots.add(robotRouge);
		robotRouge.ajouterObserveurRobotClique(plateauJeu);

        int[] posRobotVert = plateauJeu.positionRobotNonUtilise();
        Robot robotVert = new Robot(this, "vert", posRobotVert[0], posRobotVert[1]);
		plateauJeu.addGroupPlateau(robotVert);
        //tableauRobots.add(robotVert);
		robotVert.ajouterObserveurRobotClique(plateauJeu);

		plateauJeu.robotAJouer();
	}

	public void action(){
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.DOWN) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.DOWN);
					plateauJeu.collisionJetonTire();
					score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.LEFT) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.LEFT);
					plateauJeu.collisionJetonTire();
					score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.UP) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.UP);
					plateauJeu.collisionJetonTire();
					score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.RIGHT) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.RIGHT);
					plateauJeu.collisionJetonTire();
					score.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				}
			}
		});
	}

	public Jeton getJetonTire(){
		return this.plateauJeu.getJetonTire();
	}

	public Robot getRobotAJouer(){
		return this.plateauJeu.getRobotSelect();
	}

	public State etatSuivant(Deplacement direction){
		plateauJeu.deplacerRobot(direction);
		// plateauJeu.collisionJetonTire();
		return new State(plateauJeu);
	}

	public boolean etatFinal(){
		return plateauJeu.collisionJetonTire();
	}

}
