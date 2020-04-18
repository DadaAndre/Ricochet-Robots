package ricochet_robots.jeu.pions;

import ricochet_robots.jeu.observer.*;
import ricochet_robots.jeu.plateau.*;
import ricochet_robots.jeu.*;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.*;
import javafx.event.EventHandler;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;

public class Robot extends Parent implements RobotClickedObservable{

	private Random r = new Random();

	private String couleur;
	private int positionInitialeX;
	private int positionInitialeY;
	private int positionX;
	private int positionY;
	private State etatJeu;

	//Image de robot et de son socle
	private ImageView imageRobot;
	private ImageView imageSocle;

	//Liste des observeurs de ce robot
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
	}

	/**
	 * Affichage du robot, événements sur le robot
	 */
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

	/**
	 * Redéfiniton de la méthode notifierRobotClique de RobotClickedObservable
	 * @param robot le robot de cette classe
	 */
	@Override
	public void notifierRobotClique(Robot robot){
		//Notification à chaque observeur que ce robot à été cliqué
		for(int i = 0; i < listObserver.size(); i++){
			etatJeu.clicSurRobot(robot);
		}
	}

	/**
	 * Redéfiniton de la méthode ajouterObserveurRobotClique de RobotClickedObservable
	 * @param robotObserveur un observeur de ce robot
	 */
	@Override
	public void ajouterObserveurRobotClique(RobotClickedObserver robotObserver){
		//ajout de l'observeur à la liste des observeurs
		listObserver.add(robotObserver);
	}

	/**
	 * Redéfiniton de la méthode supprimerObserveurRobotClique de RobotClickedObservable
	 * @param robotObserveur un observeur de ce robot
	 */
	@Override
	public void supprimerObserveurRobotClique(RobotClickedObserver robotObserver){
		//ajout de l'observeur à la liste des observeurs
		listObserver.remove(robotObserver);
	}

	/**
	 * Récupère la position en X du socle du robot
	 * @return la position en X du socle
	 */
	public int getPositionInitialeX(){
		return positionInitialeX;
	}

	/**
	 * Récupère la position en Y du socle du robot
	 * @return la position en Y du socle
	 */
	public int getPositionInitialeY(){
		return positionInitialeY;
	}

	/**
	 * Modifie la position en X du socle du robot
	 * @param positionInitialeX la nouvelle position en X du socle
	 */
	public void setPositionInitialeX(int positionInitialeX){
		this.positionInitialeX = positionInitialeX;
	}

	/**
	 * Modifie la position en Y du socle du robot
	 * @param positionInitialeY la nouvelle position en Y du socle
	 */
	public void setPositionInitialeY(int positionInitialeY){
		this.positionInitialeY = positionInitialeY;
	}

	/**
	 * Récupère la position en X du robot
	 * @return la position en X du robot
	 */
	public int getPositionX(){
		return positionX;
	}

	/**
	 * Récupère la position en Y du robot
	 * @return la position en Y du robot
	 */
	public int getPositionY(){
		return positionY;
	}

	/**
	 * Modifie la position en X du robot
	 * @param positionX la nouvelle position en X du robot
	 */
	public void setPositionX(int positionX){
		this.positionX = positionX;
	}

	/**
	 * Modifie la position en X du robot
	 * @param positionX la nouvelle position en X du robot
	 */
	public void setPositionY(int positionY){
		this.positionY = positionY;
	}

	/**
	 * Modifie la position en X et en Y du robot
	 * @param x la nouvelle position en X du robot
	 * @param y la nouvelle position en Y du robot
	 */
	public void setPosition(int x, int y){
		this.positionY = y;
		this.positionX = x;
	}

	/**
	 * Déplacement du robot selon l'axe des abscisse
	 * @param positionX la nouvelle position en X du robot
	 */
	public void translatePositionX(int positionX){
		this.positionX += positionX;
	}

	/**
	 * Déplacement du robot selon l'axe des ordonnées
	 * @param positionX la nouvelle position en Y du robot
	 */
	public void translatePositionY(int position){
		this.positionY += position;
	}

	/**
	 * Récupère la couleur du robot
	 * @return la couleur du robot
	 */
	public String getCouleur(){
		return this.couleur;
	}

	/**
	 * Modifie la couleur du robot
	 * @param couleur la nouvelle couleur du robot
	 */
	public void setCouleur(String couleur){
		this.couleur = couleur;
	}

	/**
	 * Permet de savoir si ce robot doit récupérer le jeton
	 * @param couleurTire la couleur du jeton tiré
	 * @return true si il s'agit de ce robot, false sinon
	 */
	public boolean estRobotAJouer(String couleurTire){
		//si le robot est de la même couleur que le jeton tiré
		if(this.couleur.equals(couleurTire)){
			return true;
		}
		return false;
	}

	/**
	 * Vérifie si ce robot est sur la case souhaitée
	 * @param XCase la position X de la case
	 * @param YCase la position Y de la case
	 * @return true si ce robot est sur cette case, false sinon
	 */
	public boolean caseAvecRobot(int xCase, int yCase){
		return xCase == this.positionX && yCase == this.positionY;
	}

	/**
	 * Mise a jour des nouvelles positions du socle
	 */
	public void nouvellePositionSocle(){
		this.positionInitialeX = this.positionX;
		this.positionInitialeY = this.positionY;
		refreshPosSocleRobot();
	}

	/**
	 * Replace ce robot sur son socle
	 */
	public void reinitialiserPosition(){
		this.positionX = this.positionInitialeX;
		this.positionY = this.positionInitialeY;
		refreshPosRobot();

	}

	/**
	 * Affichage graphique du robot
	 */
	public void dessinerRobot(){
		this.imageRobot = new ImageView(new Image("images/imgRobot/" + this.couleur + ".png"));
		//changement de la taille de l'image du socle en X et en Y
		this.imageRobot.setFitWidth(Case.DIM);
		this.imageRobot.setFitHeight(Case.DIM);
		this.getChildren().add(this.imageRobot);
		//Mise à jour graphique de la position du robot
		refreshPosRobot();
	}

	/**
	 * Affichage graphique du socle du robot
	 */
	public void dessinerSocleRobot(){
		this.imageSocle = new ImageView(new Image("images/imgSocleRobot/" + this.couleur + ".png"));
		//changement de la taille de l'image du socle en X et en Y
		this.imageSocle.setFitWidth(Case.DIM);
		this.imageSocle.setFitHeight(Case.DIM);
		this.getChildren().add(this.imageSocle);
		//Mise à jour graphique de la position du socle
		refreshPosSocleRobot();
	}

	/**
	 * Mise à jour des positionnements de l'affichage graphique du robot
	 */
	public void refreshPosRobot(){
		if(imageRobot != null){
			//changement de la position en X
			this.imageRobot.setX(this.positionX * Case.DIM + Plateau.DEPART_X);
			//changement de la position en Y
			this.imageRobot.setY(this.positionY * Case.DIM + Plateau.DEPART_X);
		}
	}
	/**
	 * Mise à jour des positionnements des dessins du robot
	 */
	public void refreshPosSocleRobot(){
		//changement de la position en X
		this.imageSocle.setX(this.positionInitialeX * Case.DIM + Plateau.DEPART_X);
		//changement de la position en Y
		this.imageSocle.setY(this.positionInitialeY * Case.DIM + Plateau.DEPART_X);
	}

	/**
	 * Redéfinition de la méthode équals
	 * @param obj un Objet à comparer avec cet Objet
	 * @return true si les deux sont égales, false sinon
	 */
	@Override
	public boolean equals(Object obj){
		//si l'Objet est égal a cette classe, on retourne true
		if (this == obj){
			return true;
		}
		//si l'Objet envoyé vaut null, on retourne false
		if (obj == null){
			return false;

		}
		//si le deux classes de ces objets sont différents, on return false
		if(getClass() != obj.getClass()){
			return false;
		}

		//Transtypage de l'objet en robot
		Robot robot = (Robot) obj;

		//retourne si les deux robot ont la meme couleur, les mêmes positions en X et Y, et les mêmes positions de leurs socles
		return this.couleur.equals(robot.couleur)
			&& this.positionX == robot.positionX
			&& this.positionY == robot.positionY
			&& this.positionInitialeX == robot.positionInitialeX
			&& this.positionInitialeY == robot.positionInitialeY;
	}

	/**
	 * Redéfinition du hashCode
	 * @return le hashcode du robot
	 */
	@Override
	public int hashCode(){
		//Calcul permettant d'éviter d'avoir des hashcodes identiques, hashcode calculé en fonction de la couleur du robot, des positions du robot et du socle
		return couleur.hashCode() +  37 * positionX + 787 * positionY + 31987 * positionInitialeX + 100 * positionInitialeY;
	}
}
