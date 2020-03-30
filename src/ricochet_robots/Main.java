package ricochet_robots;

import java.util.ArrayList;
import java.util.Random;

import java.io.FileNotFoundException;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;

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
        Scene scene = new Scene(root, 900, 900, Color.WHITE);

		//Ajout de la fenêtre à l'objet Stage
		primaryStage.setScene(scene);

		Score score = new Score();
		root.getChildren().add(score);

        //On créer un plateau
		//Plateau plateauJeu = new Plateau(16,16, score);
		State etatInitial = new State(16,16, score, scene);
		root.getChildren().add(etatInitial.getEtatPlateau());

		Button sampleButton = new Button("Solution");
		sampleButton.setTranslateX(400);
		sampleButton.setTranslateY(850);
		sampleButton.setOnAction(new EventHandler<ActionEvent>() {
		   @Override
		   public void handle(ActionEvent event) {
			   System.out.println("Hello World!");
			   etatInitial.getStateActuel();
			   etatInitial.robotAJouer();
			   AlgoAStarBis algo2 = new AlgoAStarBis(etatInitial);
	   		   algo2.parcoursTotal();
		   }
	   	});

		root.getChildren().add(sampleButton);

		//On montre les dessins
		primaryStage.show();
    }
}
