package ricochet_robots.jeu.plateau;

import ricochet_robots.jeu.pions.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CaseJeton extends Case {

	private String forme;
	private String couleur;

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

	/**
	 * Récupère la forme du jeton sur la case
	 * @return la forme du jeton sur la case
	 */
	public String getForme(){
		return this.forme;
	}

	/**
	 * Récupére la couleur du jeton sur la case
	 * @return la couleur du jeton sur la case
	 */
	public String getCouleur(){
		return this.couleur;
	}

	/**
	 * Vérifie si cette case correspond à la case "objectif"
	 * @param jetonTire le jeton tiré
	 * @return true si il s'agit de la case "objectif", false sinon
	 */
	public boolean estSurCaseJetonTire(Jeton jetonTire){
		return this.couleur == jetonTire.getCouleur() && this.forme == jetonTire.getForme();
	}

	/**
	 * Redéfinition de la méthode rotationCase de la classe mère
	 */
 	 @Override
	public void rotationCase(){
		//Traitement identique à la classe mère
		super.rotationCase();
		//On redessine par dessus la nouvelle case rotationné, son jeton
		this.addImage(new Image("images/imgJeton/" + forme + "-" + couleur + ".png"));
	}

	/**
	 * Redéfinition de la méthode toString
	 * @return l'affichage en console de la caseJeton
	 */
	@Override
	public String toString(){
		//Visualisations des valeurs des murs de la case et du jeton quelle contient (forme et couleur)
		return "[" + getValHaut() + "," + getValDroit() + "," + getValBas() + "," + getValGauche() +","+ this.forme + "," + this.couleur +"]";
	}
}
