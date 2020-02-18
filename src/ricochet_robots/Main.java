package ricochet_robots;

import java.util.ArrayList;
import java.util.Random;

public class Main{

    public static void main(String[] args){

		Random r = new Random();

		//Initialisation du plateau
        Plateau plateauJeu = new Plateau(16,16);

		//Tirage de deux nombre aléatoires pour les coordonnées initiale d'un robot
		int aleaX = r.nextInt(15);
		int aleaY = r.nextInt(15);

		//On vérifie si la position tirée n'existe pas
		int[] posRobotJaune = plateauJeu.positionRobotNonUtilise();
		//Création d'un robot Jaune
        Robot robotJaune = new Robot(plateauJeu, "jaune", posRobotJaune[0], posRobotJaune[1]);
		//On ajoute le robot créé à une ArrayList de Robot
		plateauJeu.ajouterRobot(robotJaune);

		int[] posRobotBleu = plateauJeu.positionRobotNonUtilise();
        Robot robotBleu = new Robot(plateauJeu, "bleu", posRobotBleu[0], posRobotBleu[1]);
		plateauJeu.ajouterRobot(robotBleu);

		int[] posRobotRouge = plateauJeu.positionRobotNonUtilise();
        Robot robotRouge = new Robot(plateauJeu, "rouge", posRobotRouge[0], posRobotRouge[1]);
		plateauJeu.ajouterRobot(robotRouge);

		int[] posRobotVert = plateauJeu.positionRobotNonUtilise();
        Robot robotVert = new Robot(plateauJeu, "vert", posRobotVert[0], posRobotVert[1]);
		plateauJeu.ajouterRobot(robotVert);

		System.out.println("posRJ x= " + robotJaune.getPositionX() + " posRJ y = " + robotJaune.getPositionY());
		System.out.println("posRB x= " + robotBleu.getPositionX() + " posRB y = " + robotBleu.getPositionY());
		System.out.println("posRV x= " + robotVert.getPositionX() + " posRV y = " + robotVert.getPositionY());
		System.out.println("posRR x= " + robotRouge.getPositionX() + " posRR y = " + robotRouge.getPositionY());
	}


}
