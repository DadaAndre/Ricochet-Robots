package ricochet_robots.jeu.pions;

import ricochet_robots.jeu.plateau.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.Parent;

import java.util.Random;

public class Jeton extends Parent{
	private String forme;
	private String couleur;
	private ImageView fondPiece;
	private ImageView imageJeton;

	private Random r = new Random();

	public Jeton(String forme, String couleur){
		this.forme = forme;
		this.couleur = couleur;
		//affichage graphique
		dessinerFond();
		dessinerJetonTire();
	}

	/**
	 * Récupère la forme du jeton tiré
	 * @return la forme du jeton
	 */
	public String getForme(){
		return forme;
	}

	/**
	 * Récupère la couleur du jeton tiré
	 * @return la couleur du jeton
	 */
	public String getCouleur(){
		return couleur;
	}

	/**
	 * Tirage du jeton de manière aléatoire
	 * @return le jeton tiré
	 */
	public static Jeton tirageJeton(){
		Random r = new Random();
		String[] forme = {"carre","triangle","rond","etoile"};
		String[] couleur = {"rouge","vert","bleu","jaune"};
		int tirCouleur = r.nextInt(4);
		int tirForme = r.nextInt(4);
		return new Jeton(forme[tirForme], couleur[tirCouleur]);
	}

	/**
	 * Affichage graphique du jeton tiré
	 */
	public void dessinerJetonTire(){
		this.imageJeton = new ImageView(new Image("images/imgJeton/" + forme + "-" + couleur + ".png"));
		this.imageJeton.setFitWidth(Case.DIM  * 2);
		this.imageJeton.setFitHeight(Case.DIM * 2);
		this.getChildren().add(this.imageJeton);
		posGraph(this.imageJeton);
	}

	/**
	 * Affichage graphique du scole sous le jeton
	 */
	public void dessinerFond(){
		this.fondPiece = new ImageView(new Image("images/imgJeton/fondPiece.png"));
		this.fondPiece.setFitWidth(Case.DIM*2);
		this.fondPiece.setFitHeight(Case.DIM*2);
		this.getChildren().add(this.fondPiece);
		posGraph(this.fondPiece);
	}

	/**
	 * positionnement graphique du jeton tiré et de son fond
	 * @param image une image
	 */
	public void posGraph(ImageView image){
		image.setX(8 * Case.DIM);
		image.setY(8 * Case.DIM);
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

		//Transtypage de l'objet en jeton
		Jeton jeton = (Jeton) obj;

		//retourne si la forme et la couleur des deux jetons comparés sont simmilaires ou non
		return this.forme.equals(jeton.forme) && this.couleur.equals(jeton.couleur);
	}

	/**
	 * Redéfinition du hashCode
	 * @return le hashcode du jeton
	 */
	@Override
	public int hashCode(){
		//retourne le hashcode de la couleur et de la forme
		return couleur.hashCode() + forme.hashCode();
	}
}
