package ricochet_robots;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class DessinRobot{

    //Liste des robots
    ArrayList<Robot> tableauRobot;
    //Liste des images du robots
    ArrayList<ImageView> tabImageRobot;
    //Associe une couleur à l'image du robot
    HashMap<String, ImageView> listeImageRobot;

    //Liste des couleurs du robots
    String[] listeCouleurRobot = {"rouge", "bleu", "vert", "jaune"};

    public DessinRobot(ArrayList<Robot> tableauRobot){
        this.tableauRobot = tableauRobot;
        creerListeImageRobot();
    }

    public void creerListeImageRobot(){
        this.listeImageRobot = new HashMap<>();
		this.tabImageRobot = new ArrayList<>();

		for(int i = 0; i < listeCouleurRobot.length; i++){
			tabImageRobot.add(new ImageView(new Image("images/imgRobot/" + listeCouleurRobot[i] + ".png")));

			listeImageRobot.put(listeCouleurRobot[i] , tabImageRobot.get(i));

		}
    }

    public void dessinerRobot(Plateau plateau, Group root, int departX, int departY){

		for(int i = 0; i < tableauRobot.size(); i++){
            root.getChildren().add(tabImageRobot.get(i));
			//on récupère la couleur d'un robot
			String couleurRobotActuel = tableauRobot.get(i).getCouleur();
			for (HashMap.Entry<String,ImageView> m : listeImageRobot.entrySet()) {
			   if(couleurRobotActuel == m.getKey()){
				   m.getValue().setX(tableauRobot.get(i).getPositionX()* Case.DIM + departX);
			       m.getValue().setY(tableauRobot.get(i).getPositionY()* Case.DIM + departY);
				   System.out.println("robot" + i + "(colo: "+ couleurRobotActuel +"): posX: " + tableauRobot.get(i).getPositionX() + " posY: " + tableauRobot.get(i).getPositionY());
				   break;
			   }
			  //System.out.println("clé: "+ m.getKey() + " | valeur: " + m.getValue());
			}
		}


        // tabImageRobot.get(0).setX(0);
        // tabImageRobot.get(0).setY(0);
	}
}
