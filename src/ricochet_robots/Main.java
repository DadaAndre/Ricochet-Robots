package ricochet_robots;

import ricochet_robots.jeu.plateau.*;
import ricochet_robots.jeu.*;
import ricochet_robots.algorithme.*;

import java.util.Random;
import java.util.List;
import java.io.FileNotFoundException;
//-----------------------------------
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//-------------------------------------
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application{

	/**
	 * Lancement du programme
	 */
    public static void main(String[] args){
		//Lancement de l'application avec javaFx
        Application.launch(Main.class, args);
    }

	/**
	 * Démarage de l'application avec JavaFX
	 */
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

		//Taille de la fenêtre et des cases par défaut (sans arguments)
		int tailleFenetre = 500;
		Case.DIM = tailleFenetre/18;

		//Récupère la liste des paramètres
		Parameters params = getParameters();

		//Place les paramètres dans une liste
		List<String> list = params.getRaw();

		//Si il y a un argument, la taille de la fenêtre prends la valeur de l'argument
		if(list.size() == 1){
			try{
				tailleFenetre = Integer.parseInt(list.get(0));
				Case.DIM = tailleFenetre/18;
			}catch(NumberFormatException e){
				System.out.println("l'argument doit être un nombre");
			}

		}
		//Si il y a plus d'un argument, incorrect
		if(list.size() > 1){
			System.out.println("./build.sh <WindowSize>");
			System.exit(1);
		}

        Random r = new Random();

        primaryStage.setTitle("Ricochet Robots");

		//Création du groupe contenant tout les groupes graphiques
        Group root = new Group();

		//Création de la fenêtre, contenant l'objet Group
        Scene scene = new Scene(root, tailleFenetre, tailleFenetre, Color.WHITE);

		//Ajout de la fenêtre à l'objet Stage
		primaryStage.setScene(scene);

		State etatInitial = new State(16,16,scene);
		root.getChildren().add(etatInitial.getEtatPlateau());

		Button sampleButton = new Button("Solution");
		sampleButton.setTranslateX(Case.DIM* 8);
		sampleButton.setTranslateY(Case.DIM*17 + Case.DIM/4);
		sampleButton.setOnAction(new EventHandler<ActionEvent>() {
		   @Override
		   public void handle(ActionEvent event) {
			   System.out.println("Solution en cours....");
			   AlgoAStar algo2 = new AlgoAStar(etatInitial);
			    algo2.parcoursTotal(new State(etatInitial));
		   }
	   	});

		root.getChildren().add(sampleButton);

		//On montre les dessins
		primaryStage.show();
    }
}
