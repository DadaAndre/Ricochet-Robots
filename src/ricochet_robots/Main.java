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


        //Un tableau de Robots
    	//ArrayList<Robot> tableauRobots = new ArrayList<>();


		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.DOWN) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.DOWN);
					plateauJeu.collisionJetonTire();
					plateauJeu.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.LEFT) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.LEFT);
					plateauJeu.collisionJetonTire();
					plateauJeu.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.UP) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.UP);
					plateauJeu.collisionJetonTire();
					plateauJeu.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.RIGHT) {
					System.out.println("Key Pressed: " + ke.getCode());
					plateauJeu.deplacerRobot(Deplacement.RIGHT);
					plateauJeu.collisionJetonTire();
					plateauJeu.afficherCoup();
					ke.consume(); // <-- stops passing the event to next node
				}
			}
		});

		//On montre les dessins
		primaryStage.show();

    }
}
