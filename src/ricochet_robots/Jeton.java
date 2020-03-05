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

	public static Jeton tirageJeton(){
		Random r = new Random();
		String[] forme = {"carre","triangle","rond","etoile"};
		String[] couleur = {"rouge","vert","bleu","jaune"};
		int tirCouleur = r.nextInt(4);
		int tirForme = r.nextInt(4);
		return new Jeton(forme[tirForme], couleur[tirCouleur]);
	}

	public void dessinerJetonTire(){
		this.imageJeton = new ImageView(new Image("images/imgJeton/" + forme + "-" + couleur + ".png"));
		this.getChildren().add(this.imageJeton);
		refresh(this.imageJeton);
	}

	public void dessinerFond(){
		this.fondPiece = new ImageView(new Image("images/imgJeton/fondPiece.png"));
		this.getChildren().add(this.fondPiece);
		refresh(this.fondPiece);
	}

	public void refresh(ImageView image){
		image.setX(8 * Case.DIM + 0.5  * Case.DIM);
		image.setY(8 * Case.DIM + 0.5  * Case.DIM);
	}


}
