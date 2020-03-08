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

public class CaseJeton extends Case {
	private String forme;
	private String couleur;

	private boolean aUnJeton = true;

	public CaseJeton(int[] tableau, int xCase, int yCase, String forme, String couleur){
		this(tableau[0],tableau[1],tableau[2],tableau[3],xCase,yCase,forme,couleur);
	}

	public CaseJeton(int valHaut, int valDroit, int valBas, int valGauche, int xCase, int yCase, String forme, String couleur){
		super(valHaut, valDroit, valBas, valGauche, xCase, yCase, false);

		this.forme = forme;
		this.couleur = couleur;

		//On associe une case jeton avec un ajout d'un dessin de jeton
		this.addImage(new Image("images/imgJeton/" + forme + "-" + couleur + ".png"));
		refresh();
	}

	public String getForme(){
		return this.forme;
	}

	public String getCouleur(){
		return this.couleur;
	}

 	 @Override
	public void rotationCase(){
		super.rotationCase();
		//On redessine par dessus la nouvelle case rotationné, son jeton
		this.addImage(new Image("images/imgJeton/" + forme + "-" + couleur + ".png"));
	}

	@Override
	public String toString(){
		return "[" + getValHaut() + "," + getValDroit() + "," + getValBas() + "," + getValGauche() +","+ this.forme + "," + this.couleur +"]";
	}
}
