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

	Group root;
	int departX;
	int departY;

    //Liste des couleurs du robots
    String[] listeCouleurRobot = {"rouge", "bleu", "vert", "jaune"};

    public DessinRobot(Group root, ArrayList<Robot> tableauRobot, int departX, int departY){
		this.departX = departX;
		this.departY = departY;
		this.root = root;
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

    public void dessinerRobot(Plateau plateau){
		for(int i = 0; i < tableauRobot.size(); i++){
            this.root.getChildren().add(tabImageRobot.get(i));
			//on récupère la couleur d'un robot
			refresh(tableauRobot.get(i));
		}
	}

	public void refresh(Robot robot){
		for (HashMap.Entry<String,ImageView> m : listeImageRobot.entrySet()) {
		   if(robot.getCouleur() == m.getKey()){
			   m.getValue().setX(robot.getPositionX()* Case.DIM + departX);
			   m.getValue().setY(robot.getPositionY()* Case.DIM + departX);
		   }
		}
	}
}
