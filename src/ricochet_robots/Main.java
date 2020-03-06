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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;


public class Main extends Application{

    public static void main(String[] args){
		//Lancement de l'application avec javaFx
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Random r = new Random();

        primaryStage.setTitle("Ricochet Robots");

		//Créatoin du groupe contenant tout les groupes graphiques
        Group root = new Group();

		//Créatoin de la fenêtre, contenant l'objet Group
        Scene scene = new Scene(root, 1000, 900, Color.LIGHTGREEN);

		//Ajout de la fenêtre à l'objet Stage
		primaryStage.setScene(scene);

        //On créer un plateau
		Plateau plateauJeu = new Plateau(16,16);
		root.getChildren().add(plateauJeu);


		//Tirage du jeton aléatoirement
		Jeton jetonTire = Jeton.tirageJeton();
		root.getChildren().add(jetonTire);

        //Un tableau de Robots
    	ArrayList<Robot> tableauRobots = new ArrayList<>();

        //Tirage de deux nombre aléatoires pour les coordonnées initiale d'un robot
        int aleaX = r.nextInt(16);
        int aleaY = r.nextInt(16);

        //On vérifie si la position tirée n'existe pas
        int[] posRobotJaune = plateauJeu.positionRobotNonUtilise(tableauRobots);
        //Création d'un robot Jaune
        Robot robotJaune = new Robot(plateauJeu, "jaune", posRobotJaune[0], posRobotJaune[1]);
		root.getChildren().add(robotJaune);
        //On ajoute le robot créé à une ArrayList de Robot
        tableauRobots.add(robotJaune);
		robotJaune.ajouterObserveurRobotClique(plateauJeu);

        int[] posRobotBleu = plateauJeu.positionRobotNonUtilise(tableauRobots);
        Robot robotBleu = new Robot(plateauJeu, "bleu", posRobotBleu[0], posRobotBleu[1]);
		root.getChildren().add(robotBleu);
        tableauRobots.add(robotBleu);
		robotBleu.ajouterObserveurRobotClique(plateauJeu);

        int[] posRobotRouge = plateauJeu.positionRobotNonUtilise(tableauRobots);
        Robot robotRouge = new Robot(plateauJeu, "rouge", posRobotRouge[0], posRobotRouge[1]);
		root.getChildren().add(robotRouge);
        tableauRobots.add(robotRouge);
		robotRouge.ajouterObserveurRobotClique(plateauJeu);

        int[] posRobotVert = plateauJeu.positionRobotNonUtilise(tableauRobots);
        Robot robotVert = new Robot(plateauJeu, "vert", posRobotVert[0], posRobotVert[1]);
		root.getChildren().add(robotVert);
        tableauRobots.add(robotVert);
		robotVert.ajouterObserveurRobotClique(plateauJeu);

		//
		// for(int i =0; i< tableauRobots.size(); i++){
		// 	if(tableauRobots.get(i).estRobotAJouer(jetonTire.getCouleur())){
		// 		robotSelect = tableauRobots.get(i);
		// 		break;
		// 	}
		// }
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.DOWN) {
					plateauJeu.deplacerRobot(tableauRobots,Deplacement.DOWN);
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.LEFT) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(tableauRobots,Deplacement.LEFT);
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.UP) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(tableauRobots,Deplacement.UP);
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.RIGHT) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(tableauRobots,Deplacement.RIGHT);
					ke.consume(); // <-- stops passing the event to next node
				}
			}
		});

		//On montre les dessins
		primaryStage.show();

    }
}
