package ricochet_robots;

import java.util.ArrayList;
import java.util.Random;

public class Main{

    public static void main(String[] args){

		Random r = new Random();

		int x = 0;
		int y = 0;

        System.out.println("coucou");
        Plateau plateauJeu = new Plateau(16,16);

		int aleaX = r.nextInt(15);
		int aleaY = r.nextInt(15);

		int[] posRobotJaune = Robot.positionRobotNonUtilise(plateauJeu);
        Robot robotJaune = new Robot(plateauJeu, "jaune", posRobotJaune[0], posRobotJaune[1]);
		plateauJeu.ajouterRobot(robotJaune);

		int[] posRobotBleu = Robot.positionRobotNonUtilise(plateauJeu);
        Robot robotBleu = new Robot(plateauJeu, "bleu", posRobotBleu[0], posRobotBleu[1]);
		plateauJeu.ajouterRobot(robotBleu);

		int[] posRobotRouge = Robot.positionRobotNonUtilise(plateauJeu);
        Robot robotRouge = new Robot(plateauJeu, "rouge", posRobotRouge[0], posRobotRouge[1]);
		plateauJeu.ajouterRobot(robotRouge);

		int[] posRobotVert = Robot.positionRobotNonUtilise(plateauJeu);
        Robot robotVert = new Robot(plateauJeu, "vert", posRobotVert[0], posRobotVert[1]);
		plateauJeu.ajouterRobot(robotVert);

		System.out.println("posRJ x= " + robotJaune.getPositionX() + " posRJ y = " + robotJaune.getPositionY());
		System.out.println("posRB x= " + robotBleu.getPositionX() + " posRB y = " + robotBleu.getPositionY());
		System.out.println("posRV x= " + robotVert.getPositionX() + " posRV y = " + robotVert.getPositionY());
		System.out.println("posRR x= " + robotRouge.getPositionX() + " posRR y = " + robotRouge.getPositionY());
	}


}
