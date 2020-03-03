package ricochet_robots;

import java.util.ArrayList;
import java.util.Random;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {

    public static void main(String[] args) {
		//Lancement de l'application avec javaFx
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
        Random r = new Random();

        primaryStage.setTitle("Ricochet Robots");

		//Créatoin du groupe contenant tout les objets graphiques
        Group root = new Group();

		//Créatoin de la fenêtre, contenant l'objet Group
        Scene scene = new Scene(root, 1000, 900, Color.LIGHTGREEN);

		//Ajout de la fenêtre à l'objet Stage
		primaryStage.setScene(scene);

        //On créer un plateau
		Plateau plateauJeu = new Plateau(16,16);

        //Un tableau de Robots
    	ArrayList<Robot> tableauRobots = new ArrayList<>();

        //Tirage de deux nombre aléatoires pour les coordonnées initiale d'un robot
        int aleaX = r.nextInt(16);
        int aleaY = r.nextInt(16);

        //On vérifie si la position tirée n'existe pas
        int[] posRobotJaune = plateauJeu.positionRobotNonUtilise(tableauRobots);
        //Création d'un robot Jaune
        Robot robotJaune = new Robot(plateauJeu, "jaune", posRobotJaune[0], posRobotJaune[1]);
        //On ajoute le robot créé à une ArrayList de Robot
        tableauRobots.add(robotJaune);

        int[] posRobotBleu = plateauJeu.positionRobotNonUtilise(tableauRobots);
        Robot robotBleu = new Robot(plateauJeu, "bleu", posRobotBleu[0], posRobotBleu[1]);
        tableauRobots.add(robotBleu);

        int[] posRobotRouge = plateauJeu.positionRobotNonUtilise(tableauRobots);
        Robot robotRouge = new Robot(plateauJeu, "rouge", posRobotRouge[0], posRobotRouge[1]);
        tableauRobots.add(robotRouge);

        int[] posRobotVert = plateauJeu.positionRobotNonUtilise(tableauRobots);
        Robot robotVert = new Robot(plateauJeu, "vert", posRobotVert[0], posRobotVert[1]);
        tableauRobots.add(robotVert);

		//Initialisation des dessins du plateau
		DessinPlateau desp = new DessinPlateau(root);
        DessinRobot desR = new DessinRobot(root, tableauRobots);

		//On dessine le plateau
		desp.dessinerPlateau(plateauJeu, Case.DIM, Case.DIM);
        desR.dessinerRobot(plateauJeu, Case.DIM, Case.DIM);

		//On montre les dessins
        primaryStage.show();

		// Random r = new Random();
		//
		// //Initialisation du plateau
        // Plateau plateauJeu = new Plateau(16,16);
		//


		// System.out.println("posRJ x= " + robotJaune.getPositionX() + " posRJ y = " + robotJaune.getPositionY());
		// System.out.println("posRB x= " + robotBleu.getPositionX() + " posRB y = " + robotBleu.getPositionY());
		// System.out.println("posRV x= " + robotVert.getPositionX() + " posRV y = " + robotVert.getPositionY());
		// System.out.println("posRR x= " + robotRouge.getPositionX() + " posRR y = " + robotRouge.getPositionY());
    }
}
