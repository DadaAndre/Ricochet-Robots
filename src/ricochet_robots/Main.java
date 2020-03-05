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
import javafx.scene.shape.Rectangle;


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

		//Tirage du jeton aléatoirement
		Jeton jetonTire = Jeton.tirageJeton();

		//Initialisation des dessins du plateau
		DessinPlateau desp = new DessinPlateau(root, jetonTire);
        DessinRobot desR = new DessinRobot(root, tableauRobots,  Case.DIM, Case.DIM);

		//On dessine le plateau
		desp.dessinerPlateau(plateauJeu, Case.DIM, Case.DIM);
        desR.dessinerRobot(plateauJeu);


		Robot robotSelect = new Robot(plateauJeu, "", 0, 0);
		for(int i =0; i< tableauRobots.size(); i++){
			if(tableauRobots.get(i).estRobotAJouer(jetonTire.getCouleur())){
				robotSelect.setCouleur(tableauRobots.get(i).getCouleur());
				robotSelect.setPositionX(tableauRobots.get(i).getPositionX());
				robotSelect.setPositionY(tableauRobots.get(i).getPositionY());
				break;
			}
		}

		System.out.println("couleur robot: " + robotSelect.getCouleur());

		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.DOWN) {
					System.out.println("Key Pressed: " + ke.getCode());
					robotSelect.move(Deplacement.DOWN, tableauRobots);
					desR.refresh(robotSelect);
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.LEFT) {
					System.out.println("Key Pressed: " + ke.getCode());
					robotSelect.move(Deplacement.LEFT, tableauRobots);
					desR.refresh(robotSelect);
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.UP) {
					System.out.println("Key Pressed: " + ke.getCode());
					robotSelect.move(Deplacement.UP, tableauRobots);
					desR.refresh(robotSelect);
					ke.consume(); // <-- stops passing the event to next node
				} else if (ke.getCode() == KeyCode.RIGHT) {
					System.out.println("Key Pressed: " + ke.getCode());
					robotSelect.move(Deplacement.RIGHT, tableauRobots);
					desR.refresh(robotSelect);
					ke.consume(); // <-- stops passing the event to next node
				}
			}
		});


		//
		// EventHandler handler = new EventHandler(<ActionEvent>() {
		// 	public void handle(ActionEvent event) {
		// 		// on a cliquésurlebouton
		// 		System.out.println("Handling event "+ event.getEventType());
		// 		// on signifieà JavaFX queriend’autre n’a besoindecetévénementevent.consume();
		// 	}
 		// });


		// Rectangle rectangle = new Rectangle(100, 100, 150, 100);
		// rectangle.setFocusTraversable(true);
		// rectangle.setOnKeyTyped(new EventHandler<KeyEvent>() {
		//
		// 	@Override
		// 	public void handle(KeyEvent event) {
		// 		if (event.getCode() == KeyCode.ENTER) {
		// 			System.out.println("Enter Pressed");
		// 		}
		// 	}
		// });
		//
        // root.getChildren().setAll(rectangle);

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
