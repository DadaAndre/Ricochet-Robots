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

import javafx.scene.Parent;

import java.util.Random;

public class Jeton extends Parent{
	private String forme;
	private String couleur;
	private ImageView fondPiece;
	private ImageView imageJeton;

	Random r = new Random();

	public Jeton(String forme, String couleur){
		this.forme = forme;
		this.couleur = couleur;

		dessinerFond();
		dessinerJetonTire();
	}

	public String getForme(){
		return forme;
	}

	public String getCouleur(){
		return couleur;
	}

	//Tire un jeton de manière aléatoire
	public static Jeton tirageJeton(){
		Random r = new Random();
		String[] forme = {"carre","triangle","rond","etoile"};
		String[] couleur = {"rouge","vert","bleu","jaune"};
		int tirCouleur = r.nextInt(4);
		int tirForme = r.nextInt(4);
		return new Jeton(forme[tirForme], couleur[tirCouleur]);
	}

	//dessine le jeton qui est tiré
	public void dessinerJetonTire(){
		this.imageJeton = new ImageView(new Image("images/imgJeton/" + forme + "-" + couleur + ".png"));
		this.getChildren().add(this.imageJeton);
		posGraph(this.imageJeton);
	}

	//dessiner un fond pour le jeton tiré
	public void dessinerFond(){
		this.fondPiece = new ImageView(new Image("images/imgJeton/fondPiece.png"));
		this.getChildren().add(this.fondPiece);
		posGraph(this.fondPiece);
	}

	//positionnement graphique du jeton tiré et de son fond
	public void posGraph(ImageView image){
		image.setX(8 * Case.DIM + 0.5  * Case.DIM);
		image.setY(8 * Case.DIM + 0.5  * Case.DIM);
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Jeton jeton = (Jeton) obj;

		return this.forme.equals(jeton.forme) && this.couleur.equals(jeton.couleur);
	}

	@Override
	public int hashCode(){
		return couleur.hashCode() + forme.hashCode();
	}

}
