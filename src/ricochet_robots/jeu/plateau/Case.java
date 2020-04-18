package ricochet_robots.jeu.plateau;

import ricochet_robots.jeu.observer.*;
import ricochet_robots.jeu.plateau.*;
import ricochet_robots.utilitaire.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.*;

public class Case extends CaseCalque implements CaseClickedObservable{

	/**
	 * Dimension laissée pour une case
	 */
	public static int DIM = 26;

	/**
	 * Valeurs des murs de la case
	 */
	private int valHaut;
	private int valDroit;
	private int valBas;
	private int valGauche;

	/**
	 * Positions de la case
	 */
	protected int xCase;
	protected int yCase;


	/**
	 * Heuristique de la case
	 */
	private int heurist;

	/**
	 * Liste des observeurs de cette case
	 */
	private ArrayList<CaseClickedObserver> listObserver;

	/**
	 * ID de la case (numéro correspondant au positionnement des murs)
	 */
	private int id;

	public Case(int[] tableau, int xCase, int yCase) {
		this(tableau[0], tableau[1], tableau[2], tableau[3], xCase, yCase, true);
	}

	public Case(int valHaut, int valDroit, int valBas, int valGauche, int xCase, int yCase) {
		this(valHaut, valDroit, valBas, valGauche, xCase, yCase, true);
	}

	public Case(int valHaut, int valDroit, int valBas, int valGauche, int xCase, int yCase, boolean loadImage) {
		this.valHaut = valHaut;
		this.valDroit = valDroit;
		this.valBas = valBas;
		this.valGauche = valGauche;
		this.xCase = xCase;
		this.yCase = yCase;

		//Calcul de l'ID de la case
		this.id = Utilitaire.CaseToInt(this);

		//Associe une image à une case
		this.addImage(new Image("images/imgPlateau/img" + id + ".png"));

//		refresh();

		//évènement clic sur la case
		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				notifierCaseClique(Case.this);
			}
		});
		listObserver = new ArrayList<>();

	}

	/**
	 * Redéfiniton de la méthode notifierCaseClique de CaseClickedObservable
	 * @param casePlateau la case de cette classe
	 */
	@Override
	public void notifierCaseClique(Case casePlateau){
		//Notification à chaque observeur que cette case à été cliquée
		for(int i = 0; i < listObserver.size(); i++){
			listObserver.get(i).clicSurCase(this);
		}
	}

	/**
	 * Redéfiniton de la méthode ajouterObserveurCaseClique de CaseClickedObservable
	 * @param casePlateauObserver un observeur de cette case
	 */
	@Override
	public void ajouterObserveurCaseClique(CaseClickedObserver casePlateauObserver){
		//Ajout de cet observeur à la liste des observeurs
		listObserver.add(casePlateauObserver);
	}

	/**
	 * Redéfiniton de la méthode ajouterObserveurCaseClique de CaseClickedObservable
	 * @param casePlateauObserver un observeur de cette case
	 */
	@Override
	public void supprimerObserveurCaseClique(CaseClickedObserver casePlateauObserver){
		//Supression de cet observeur à la liste des observeurs
		listObserver.remove(casePlateauObserver);
	}

	/**
	 * Récupère la valeur du mur en haut
	 * @return 1 si il y a un mur en haut de la case, 0 sinon
	 */
	public int getValHaut() {
	   return this.valHaut;
	}

	/**
	 * Récupère la valeur du mur à droite
	 * @return 1 si il y a un mur à droite de la case, 0 sinon
	 */
	public int getValDroit() {
	   return this.valDroit;
	}

	/**
	 * Récupère la valeur du mur en bas
	 * @return 1 si il y a un mur en bas de la case, 0 sinon
	 */
	public int getValBas() {
	   return this.valBas;
	}

	/**
	 * Récupère la valeur du mur à gauche
	 * @return 1 si il y a un mur à gauche de la case, 0 sinon
	 */
	public int getValGauche() {
	   return this.valGauche;
	}

	/**
	 * Récupère la position en X de la case
	 * @return la position en X de la case
	 */
	public int getXCase() {
	   return xCase;
	}

	/**
	 * Récupère la position en Y de la case
	 * @return la position en Y de la case
	 */
	public int getYCase() {
	   return yCase;
	}

	/**
	 * Récupère l'ID de la case
	 * @return l'ID de la case
	 */
	public int getID() {
	   return id;
	}

	/**
	 * Ajout de l'heuristique de la case
	 * @param heurist l'heuristique à ajouter
	 */
	public void addHeuristique(int heurist){
		this.heurist = heurist;
	}

	/**
	 * Récupère l'heuristique de la case
	 * @return l'heuristique de la case
	 */
	public int getHeuristique(){
		return heurist;
	}

	/**
	 * Modifie les positions en X et en Y de la case
	 * @param xCase la position en X de la case
	 * @param yCase la position en Y de la case
	 */
	public void setPositionsXY(int xCase, int yCase){
		this.xCase = xCase;
		this.yCase = yCase;
		refresh();
	}

	/**
	 * Vérifie que les valeurs de deux cases sont identiques
	 * @param caseSelect la case à comparer
	 * @return true si les valeurs des deux cases sont identiques ou non
	 */
	public boolean isValueEquals(Case caseSelect){
	   return caseSelect.valHaut == valHaut && caseSelect.valBas == valBas && caseSelect.valGauche == valGauche && caseSelect.valDroit == valDroit;
	}

	/**
	 * Rotation de la case dans un angle de 90°
	 */
	public void rotationCase(){
		//décale toute les valeurs des murs d'un cran
		int temp = this.valGauche;
		this.valGauche = this.valBas;
		this.valBas = this.valDroit;
		this.valDroit = this.valHaut;
		this.valHaut = temp;

		//Calcul du nouvel ID de cette case
		this.id = Utilitaire.CaseToInt(this);

	   //On change l'image de la case par la nouvelle correspondant à la rotation effectuée
	   this.addImage(new Image("images/imgPlateau/img" + id + ".png"));

	}

	/**
	 * Mise à jour graphique des positions de cette case
	 */
	public void refresh(){
		this.setLayoutX(this.xCase * Case.DIM + Plateau.DEPART_X);
		this.setLayoutY(this.yCase * Case.DIM + Plateau.DEPART_Y);
	}

	/**
	 * Redéfinition de la méthode toString
	 * @return l'affichage en console de la case
	 */
	@Override
	public String toString(){
		//Visualisations des valeurs des murs de la case
		return "[" + valHaut + "," + valDroit + "," + valBas + "," + valGauche + "]";
	}
}
