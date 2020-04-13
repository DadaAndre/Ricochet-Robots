package ricochet_robots.jeu.pions;

import ricochet_robots.jeu.observer.*;
import ricochet_robots.jeu.plateau.*;
import ricochet_robots.jeu.*;

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
	private State etatJeu;

	private int id;

	private ImageView imageRobot;
	private ImageView imageSocle;

	private ArrayList<RobotClickedObserver> listObserver;

	public Robot(State etatJeu, String couleur, int positionX, int positionY){
		this(etatJeu,couleur,positionX,positionY,positionX,positionY);
	}

	public Robot(State etatJeu, String couleur, int positionX, int positionY,  int posXInitial, int posYInitial){
		//On ajoute le robot crée à la liste des robots
		this.couleur = couleur;
		this.positionInitialeX = posXInitial;
		this.positionInitialeY = posYInitial;
		this.positionX = positionX;
		this.positionY = positionY;
		this.etatJeu = etatJeu;
		etatJeu.addTableauRobots(this);
	}

	public void robotRender(){
		dessinerSocleRobot();
		dessinerRobot();

		listObserver = new ArrayList<>();

		//Evènement clic sur le robot
		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				notifierRobotClique(Robot.this);
			}
		});
	}

	@Override
	public void notifierRobotClique(Robot robot){
		for(int i = 0; i < listObserver.size(); i++){
			etatJeu.clicSurRobot(robot);
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

	public void translatePositionX(int position){
		this.positionX += position;
	}

	public void translatePositionY(int position){
		this.positionY += position;
	}

	public void setPosition(int x, int y){
		this.positionY = y;
		this.positionX = x;
	}

	public String getCouleur(){
		return this.couleur;
	}

	public void setCouleur(String couleur){
		this.couleur = couleur;
	}

	//Permet de savoir quel robot doit jouer
	public boolean estRobotAJouer(String couleurTire){
		if(this.couleur.equals(couleurTire)){
			return true;
		}
		return false;
	}

	//Vérifie si il y a un robot sur une case
	public boolean caseAvecRobot(int xCase, int yCase){
		return xCase == this.positionX && yCase == this.positionY;
	}

	//Mise a jour des nouvelles positions du socle
	public void nouvellePositionSocle(){
		this.positionInitialeX = this.positionX;
		this.positionInitialeY = this.positionY;
		refreshPosSocleRobot();
	}

	//Replacement du robot sur son socle
	public void reinitialiserPosition(){
		this.positionX = this.positionInitialeX;
		this.positionY = this.positionInitialeY;
		refreshPosRobot();

	}

	//On dessine le robot
	public void dessinerRobot(){
		this.imageRobot = new ImageView(new Image("images/imgRobot/" + this.couleur + ".png"));
		this.imageRobot.setFitWidth(Case.DIM);
		this.imageRobot.setFitHeight(Case.DIM);
		this.getChildren().add(this.imageRobot);
		refreshPosRobot();
	}

	//On dessine la "socle" du robot
	public void dessinerSocleRobot(){
		this.imageSocle = new ImageView(new Image("images/imgSocleRobot/" + this.couleur + ".png"));
		this.imageSocle.setFitWidth(Case.DIM);
		this.imageSocle.setFitHeight(Case.DIM);
		this.getChildren().add(this.imageSocle);
		refreshPosSocleRobot();
	}

	//Mise à jour des positionnements des dessins du robot
	public void refreshPosRobot(){
		if(imageRobot != null){
			this.imageRobot.setX(this.positionX * Case.DIM + Plateau.DEPART_X);
			this.imageRobot.setY(this.positionY * Case.DIM + Plateau.DEPART_X);
		}
	}

	//Mise à jour des positionnements des dessins du robot
	public void refreshPosSocleRobot(){
		this.imageSocle.setX(this.positionInitialeX * Case.DIM + Plateau.DEPART_X);
		this.imageSocle.setY(this.positionInitialeY * Case.DIM + Plateau.DEPART_X);
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Robot robot = (Robot) obj;

		return this.couleur.equals(robot.couleur)
			&& this.positionX == robot.positionX
			&& this.positionY == robot.positionY
			&& this.positionInitialeX == robot.positionInitialeX
			&& this.positionInitialeY == robot.positionInitialeY;
	}

	@Override
	public int hashCode(){
		return couleur.hashCode() +  37 * positionX + 787 * positionY + 31987 * positionInitialeX + 1 * positionInitialeY;
	}
}
