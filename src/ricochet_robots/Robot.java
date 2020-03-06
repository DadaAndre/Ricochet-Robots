package ricochet_robots;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;

public class Robot extends Parent implements RobotClickedObservable{

	Random r = new Random();

	private String couleur;
	private int positionInitialeX;
	private int positionInitialeY;
	private int positionX;
	private int positionY;
	private Plateau plateauJeu;

	private boolean clic = false;

	private ImageView imageRobot;

	private ArrayList<RobotClickedObserver> listObserver;

	public Robot(Plateau plateauJeu, String couleur, int positionX, int positionY){
		this.couleur = couleur;
		this.positionInitialeX = positionX;
		this.positionInitialeY = positionY;
		this.positionX = positionX;
		this.positionY = positionY;
		this.plateauJeu = plateauJeu;

		dessinerRobot();

		listObserver = new ArrayList<>();

		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				//System.out.println("Robot " + couleur +  " appuyé!!" );
				notifierRobotClique(Robot.this);
			}
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				//relacher();
				//System.out.println("Robot " + couleur +" relaché!!" );
			}
		});
		clic = false;

	}

	@Override
	public void notifierRobotClique(Robot robot){
		for(int i = 0; i < listObserver.size(); i++){
			plateauJeu.clicSurRobot(robot);
		}
	}

	@Override
	public void ajouterObserveurRobotClique(RobotClickedObserver robotObserver){
		listObserver.add(robotObserver);
	}

	@Override
	public void supprimerObserveurRobotClique(RobotClickedObserver robotObserver){
		listObserver.remove(robotObserver);
	}

	public int getPositionInitialeX(){
		return positionInitialeX;
	}

	public void setPositionInitialeX(int positionInitialeX){
		this.positionInitialeX = positionInitialeX;
	}

	public int getPositionInitialeY(){
		return positionInitialeY;
	}

	public void setPositionInitialeY(int positionInitialeY){
		this.positionInitialeY = positionInitialeY;
	}

	public int getPositionX(){
		return positionX;
	}

	public void setPositionX(int positionX){
		this.positionX = positionX;
	}

	public int getPositionY(){
		return positionY;
	}

	public void setPositionY(int positionY){
		this.positionY = positionY;
	}

	public void setPosition(int x, int y){
		this.positionY = positionY;
		this.positionX = positionX;
	}

	public String getCouleur(){
		return this.couleur;
	}

	public void setCouleur(String couleur){
		this.couleur = couleur;
	}

	public static boolean estSurAutresRobots(int aleaX, int aleaY, ArrayList<Robot> tableauRobots){
		for(int i = 0; i <= tableauRobots.size() -1 ; i++){
			//Vérifie si les coordonnées X et Y tirées sont déja affectés a un robot déja crée
			if(aleaX == tableauRobots.get(i).getPositionInitialeX() && aleaY == tableauRobots.get(i).getPositionInitialeY()){
				//Si c'est le cas, on retrourne vrai
				return true;
			}
		}
		//Si ce n'est pas le cas, on retrourne false
		return false;
	}

	public boolean estUneCollisionRobot(Deplacement direction, ArrayList<Robot> tableauRobots){
		switch(direction){
			case UP:
				for(int i = 0; i< tableauRobots.size(); i++){
					if(tableauRobots.get(i).getCouleur() != couleur && tableauRobots.get(i).getPositionX() == positionX &&  tableauRobots.get(i).getPositionY() == (positionY - 1)){
						return true;
					}
				}
				return false;

			case DOWN:
				for(int i = 0; i< tableauRobots.size(); i++){
					if(tableauRobots.get(i).getCouleur() != couleur && tableauRobots.get(i).getPositionX() == positionX &&  tableauRobots.get(i).getPositionY() == (positionY + 1)){
						return true;
					}
				}
				return false;

			case LEFT:
				for(int i = 0; i< tableauRobots.size(); i++){
					if(tableauRobots.get(i).getCouleur() != couleur && tableauRobots.get(i).getPositionX() == (positionX - 1)  &&  tableauRobots.get(i).getPositionY() == positionY){
						return true;
					}
				}
				return false;

			case RIGHT:
				for(int i = 0; i< tableauRobots.size(); i++){
					if(tableauRobots.get(i).getCouleur() != couleur && tableauRobots.get(i).getPositionX() == (positionX + 1)  &&  tableauRobots.get(i).getPositionY() == positionY){
						return true;
					}
				}
				return false;
		}
		return true;
	}

	public boolean estRobotAJouer(String couleurTire){
		if(this.couleur.equals(couleurTire)){
			return true;
		}
		return false;
	}

	public boolean caseAvecRobot(int xCase, int yCase){
		return xCase == this.positionX && yCase == this.positionY;
	}

	//Déplacement du robot
	public void move(ArrayList<Robot> listeRobot,Deplacement direction){

		//Vérification de la direction choisie
		if(direction == Deplacement.UP){
			//Tant que le robot ne rencontre pas un mur en haut, il se dirige vers le haut
			while(this.plateauJeu.getCase(positionX, positionY).getValHaut() != 1 && this.plateauJeu.getCase(positionX, positionY - 1).getValBas() != 1 && !estUneCollisionRobot(direction, listeRobot)){
				this.positionY -= 1;
			}
		}else if(direction == Deplacement.DOWN){
			//Tant que le robot ne rencontre pas un mur en bas, il se dirige vers le bas
			while(this.plateauJeu.getCase(positionX, positionY).getValBas() != 1 && this.plateauJeu.getCase(positionX, positionY +1).getValHaut() != 1 && !estUneCollisionRobot(direction, listeRobot)){
				this.positionY += 1;
			}
		}else if(direction == Deplacement.LEFT){
			//Tant que le robot ne rencontre pas un mur à gauche, il se dirige vers la gauche
			while(this.plateauJeu.getCase(positionX, positionY).getValGauche() != 1 && this.plateauJeu.getCase(positionX -1, positionY).getValDroit() != 1&& !estUneCollisionRobot(direction, listeRobot)){
				this.positionX -= 1;
			}
		}else if(direction == Deplacement.RIGHT){
			//Tant que le robot ne rencontre pas un mur à droite, il se dirige vers la droite
			while(this.plateauJeu.getCase(positionX, positionY).getValDroit() != 1 && this.plateauJeu.getCase(positionX + 1, positionY).getValGauche() != 1  && !estUneCollisionRobot(direction, listeRobot)){
				this.positionX += 1;
			}
		}
	}

	public void dessinerRobot(){
		this.imageRobot = new ImageView(new Image("images/imgRobot/" + this.couleur + ".png"));
		this.getChildren().add(this.imageRobot);
		refresh();
	}

	public void refresh(){
		this.imageRobot.setX(this.positionX * Case.DIM + Plateau.DEPART_X);
		this.imageRobot.setY(this.positionY * Case.DIM + Plateau.DEPART_X);
	}
}
