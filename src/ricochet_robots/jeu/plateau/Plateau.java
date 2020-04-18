package ricochet_robots.jeu.plateau;

import ricochet_robots.jeu.plateau.*;
import ricochet_robots.jeu.observer.*;
import ricochet_robots.utilitaire.*;
import ricochet_robots.jeu.*;

import java.util.Random;
import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.Parent;

public class Plateau extends Parent{
	/**
	 * La position de départ de dessin du plateau (en pixels)
	 */
	public static int DEPART_X = Case.DIM;
	public static int DEPART_Y = Case.DIM;

	/**
	 * Le tableau de miniGrille
	 */
	private ArrayList<Case[][]> tableauMiniGrille = new ArrayList<>();

	/**
	 * les différentes miniGrilles représentées par une chaine de caractère
	 */
	public final String chaine1 = "9,8,8,12,9,8,8,8,1,0,0,0,0,0,0,0,1,0,0,0,0,6,1,0,1,0,2,0,0,8,0,0,3,0,12,1,0,0,0,0,9,2,0,0,0,0,4,3,5,9,0,0,0,0,0,10,1,0,0,0,0,0,4,9"; //haut, gauche
	public final String chaine2 = "8,12,9,8,8,8,8,12,0,0,0,0,0,6,1,6,0,2,0,0,0,8,0,12,4,9,0,0,0,0,2,4,0,0,0,0,0,0,12,5,0,0,0,0,0,0,0,4,2,0,0,4,3,0,0,4,12,1,0,0,8,0,0,4"; //haut droit
	public final String chaine3 = "6,1,0,0,0,0,0,4,8,0,0,0,4,3,0,4,0,0,0,0,0,8,0,4,0,6,1,0,0,0,0,6,0,8,0,0,0,0,2,12,0,0,2,0,0,0,12,5,0,4,9,0,0,0,0,4,2,2,2,6,3,2,2,6"; //bas droit
	public final String chaine4 = "1,0,0,0,0,0,4,3,1,0,0,4,3,0,2,8,3,0,0,0,8,4,9,0,9,0,0,0,0,0,0,2,1,2,0,0,0,0,0,12,1,12,1,0,0,0,0,0,1,0,0,6,1,0,0,0,3,2,2,10,6,3,2,2"; //bas gauche
	public final String[] chaines = {chaine1, chaine2, chaine3, chaine4};

	/**
	 * Le plateau de jeu
	 */
	private Case[][] plateau;

	/**
	 * Zone de positionnement interdite des robots;
	 */
	private int[][] deadZone = {{7,7},{8,7},{7,8},{8,8}};


	public Plateau(int tailleX, int tailleY){
		//Initialisation du plateau
		 this.plateau = new Case[tailleX][tailleY];

		//Création des miniGrille
		for(int i = 0 ; i < chaines.length; i++){
			creerMiniGrille(chaines[i]);
		}
		//Positionnement des jetons sur les cases
		positonJeton();

		//Création du  de jeu
		creerPlateau();

		//Affichage du plateau de jeu
		afficheGrille();
	}

	/**
	 * Récupère une case à une position donnée
	 * @param x la position en x de la case souhaitée
	 * @param y  la position en x de la case souhaitée
	 * @return la case voulue si elle existe aux coordonnées
	 */
	public Case getCasePlateau(int x, int y) {
		//Si les corrdonnées d=ne dépassent pas le plateau
		if(x >= 0 && x <= plateau.length -1 && y >= 0 && y <= plateau.length -1) {
			return this.plateau[x][y];
		}
		return null;
	}

	/**
	 * Création des mini-grilles
	 * @param chaine la chaine de caractère correspondant à la mini-grille
	 */
	public void creerMiniGrille(String chaine){
		//on ajoute à tableauMiniGrille la miniGrille obtenue avec la méthode stringToMiniGrille
		this.tableauMiniGrille.add(Utilitaire.stringToMiniGrille(chaine));
	}

	/**
	 * Affichage de la grille en console
	 */
	public void afficheGrille(){
		for(int y = 0; y < plateau[0].length; y++){
			for(int x = 0; x < plateau.length; x++){
				System.out.print(plateau[x][y]);
				this.plateau[x][y].setPositionsXY(x,y);
				//Ajout de chaque case au groupe d'images
				this.getChildren().add(plateau[x][y]);
			}
			System.out.println();
		}
	}

	/**
	 * Ajout de l'observeur à toute les cases observées
	 * @param observer l'observeur des cases
	 */
	public void ajoutObserveurCases(CaseClickedObserver observer){
		for(int y = 0; y < plateau[0].length; y++){
			for(int x = 0; x < plateau.length; x++){
				this.plateau[x][y].ajouterObserveurCaseClique(observer);
			}
		}
	}

	/**
	 * Rotation de la mini-grille en fonction de la position choisie
	 * @param miniGrille la mini-grille qui doit avoir une rotation
	 * @param position la position souhaitée
	 * @return la mini-grille dans sa bonne rotation
	 */
	public Case[][] rotation(Case[][] miniGrille, int position){
		//Création d'un tableau 2D (vide ici), qui représentera une copie de la miniGrille reçue
		Case[][] miniGrilleRota = new Case[miniGrille.length][miniGrille.length];
		//Remplis le tableau miniGrilleRota avec les valeurs de la miniGrille reçue
		for(int y = 0; y < miniGrille[0].length; y++){
			for(int x = 0; x < miniGrille.length; x++){
				miniGrilleRota[x][y] = miniGrille[x][y];
			}
		}
		//Rotation de la mini-grille tant qu'elle n'est pas adéquate avec sa position
		while(positionEstVraie(miniGrilleRota, position) != true){
			miniGrilleRota = rotationMiniGrille(miniGrilleRota);
		}
		//on retourne la miniGrille dans sa bonne rotation
		return miniGrilleRota;
	}

	/**
	 * Rotation de la mini-grille de 90°
	 * @param miniGrille la mini-grille qui doit avoir une rotation
	 * @return la mini-grille dans une rotation de 90°
	 */
	public Case[][] rotationMiniGrille(Case[][] miniGrille) {
		Case[][] miniGrilleRota = new Case[miniGrille.length][miniGrille.length];
		//On fait une rotation de la mini-grille
		for(int y = 0; y < miniGrille[0].length; y++){
			for(int x = 0; x < miniGrille.length; x++){
				//On change une valeur de la mini-grille par une autre valeur de mini-grille à un autre endroit
				miniGrilleRota[x][y] = miniGrille[y][miniGrille.length-x-1];
				//On fait une "rotation de la case"
				miniGrilleRota[x][y].rotationCase();
			}
		}
		//On retourne la mini-grille dans une nouvelle rotation
		return miniGrilleRota;
	}


	/**
	 * Vérifie que la rotation de la mini-grille correspond bien à la position choisie dans le plateau
	 * @param miniGrille la mini-grille à positionner
	 * @param position la position souhaitée
	 * @return true si la rotation est correcte, false sinon
	 */
	public boolean positionEstVraie(Case[][] miniGrille, int position){
		//si la position est en haut, à gauche
		if(position == 1){
			//On vérifie si la mini-grille possède bien des "murs" à gauche et en haut
			return verifGauche(miniGrille) && verifHaut(miniGrille);

		}
		//si la position est en haut, à droite
		else if(position == 2){
			//On vérifie si la mini-grille possède bien des "murs" en haut et à droite
			return verifHaut(miniGrille) && verifDroite(miniGrille);

		}
		//si la position est en bas, à droite
		else if(position == 3){
			//On vérifie si la mini-grille possède bien des "murs" à droite et en bas
			return verifDroite(miniGrille) && verifBas(miniGrille);

		}
		//si la position est en bas, à droite
		else if(position == 4){
			//On vérifie si la mini-grille possède bien des "murs" en bas et à gauche
			return verifBas(miniGrille) && verifGauche(miniGrille);
		}
		return false;
	}

	/**
	 * Défini la position de la mini-grille selon sa rotation
	 * @param miniGrille la mini-grille
	 * @return la position de la mini-grille correspondante
	 */
	public int getPositionMiniGrille(Case[][] miniGrille){
		//Si la mini-grille à des murs à gauche et en haut, c'est une position "1"
		if(verifGauche(miniGrille) && verifHaut(miniGrille)){
			return 1;

		}
		//Si la mini-grille à des murs en haut et à droite, c'est une position "2"
		else if(verifHaut(miniGrille) && verifDroite(miniGrille)){
			return 2;

		}
		//Si la mini-grille à des murs à droite et en bas, c'est une position "3"
		else if(verifDroite(miniGrille) && verifBas(miniGrille)){
			return 3;

		}
		//Si la mini-grille à des murs en bas et à gauche, c'est une position "4"
		else if(verifBas(miniGrille) && verifGauche(miniGrille)){
			return 4;
		}

		return 0;
	}

	/**
	 * Vérifie si la miniGrille envoyée possède des murs en haut, sur chaque case de la première ligne
	 * @param miniGrille la mini-grille à tester
	 * @return true si la mini-grille possède des murs en haut, sur chaque case de la première ligne, false sinon
	 */
	public boolean verifHaut(Case[][] miniGrille){
		boolean ok = false;
		int y = 0;
		//on parcours tout les valeurs x , à y = 0 de la mini-grille
		for(int x = 0; x <miniGrille.length; x++){
			//Avoir un mur en haut, signifie que la valeur valHaut de Case est bien à 1
			if(miniGrille[x][y].getValHaut() == 1){
				ok = true;
			}
			//Si sur une seule case de la première ligne, il n'y a pas de mur en haut, on retourne false
			else{
				return false;
			}
		}
		return ok;
	}

	/**
	 * Vérifie si la miniGrille envoyée possède des murs à droite, sur chaque case de la dernière colone
	 * @param miniGrille la mini-grille à tester
	 * @return true si la mini-grille possède des murs à droite, sur chaque case de la dernière colone, false sinon
	 */
	public boolean verifDroite(Case[][] miniGrille){
		boolean ok = false;
		int x = miniGrille.length - 1;
		//on parcours à la dernière valeur de x, tout les valeurs de y de la mini-grille
		for(int y = 0; y < miniGrille.length; y++){
			//Avoir un mur à droite, signifie que la valeur valDroit de Case est bien à 1
			if(miniGrille[x][y].getValDroit() == 1){
				ok = true;
			}
			//Si sur une seule case de la dernière colone, il n'y a pas de mur à droite, on retourne false
			else{
				return false;
			}
		}
		return ok;
	}

	/**
	 * Vérifie si la miniGrille envoyée possède des murs en bas, sur chaque case de la dernière ligne
	 * @param miniGrille la mini-grille à tester
	 * @return true si la mini-grille possède des murs en bas, sur chaque case de la dernière ligne, false sinon
	 */
	public boolean verifBas(Case[][] miniGrille){
		boolean ok = false;
		int y = miniGrille.length - 1;
		//on parcours à la dernière valeur de y, tout les valeurs de x de la mini-grille
		for(int x = 0; x < miniGrille.length; x++){
			//Avoir un mur en bas, signifie que la valeur valBas de Case est bien à 1
			if(miniGrille[x][y].getValBas() == 1){
				ok = true;
			}
			//Si sur une seule case de la dernière ligne, il n'y a pas de mur en bas, on retourne false
			else{
				return false;
			}
		}
		return ok;
	}

	/**
	 * Vérifie si la miniGrille envoyée possède des murs à gauche, sur chaque case de la première colone
	 * @param miniGrille la mini-grille à tester
	 * @return true si la mini-grille possède des murs à gauche, sur chaque case de la première colone, false sinon
	 */
	public boolean verifGauche(Case[][] miniGrille){
		boolean ok = false;
		int x = 0;
		//on parcours à x = 0, tout les valeurs de y de la mini-grille
		for(int y = 0; y <miniGrille.length; y++){
			//Avoir un mur à gauche, signifie que la valeur valGauche de Case est bien à 1
			if(miniGrille[x][y].getValGauche() == 1){
				ok = true;
			}
			//Si sur une seule case de lapremière colone, il n'y a pas de mur à gauche, on retourne false
			else{
				return false;
			}
		}
		return ok;
	}

	/**
	 * Création du plateau
	 */
	public void creerPlateau(){
		//Création d'une ArrayList conteannt la liste des position tirées de manière aléatoire
		ArrayList<Integer> position = new ArrayList<>();
		int alea = 0;
		Random r = new Random();

		//On tire un nombre àléatoire tant qu'on a pas 4 valeurs
		while(position.size() != 4){

			//On tire un nombre entre 1 et 4 (compris)
			alea = r.nextInt(4) + 1;
			//Si notre ArrayList n'a pas cette valeur, on l'ajoute
			if(!position.contains(alea)){
				position.add(alea);
			}
			//..... si non on la perd et on retire
		}

		for(int i = 0; i< tableauMiniGrille.size(); i++){
			/*On modifie la miniGrille correspondante pour qu'elle puisse
			aller dans sa position, choisie de manière aléatoire
			*/
			tableauMiniGrille.set(i, rotation(tableauMiniGrille.get(i), position.get(i)));
			//On ajoute la mini-grille correspondante au plateau
			ajouterMiniGrille(tableauMiniGrille.get(i));
		}
	}

	/**
	 * Ajout des mini-grilles au plateau
	 * @param miniGrille la mini-grille à ajouter
	 */
	public void ajouterMiniGrille(Case[][] miniGrille){

		int demiTabX = this.plateau[0].length/2;
		int demiTabY = this.plateau.length/2;

		//si la position de la mini-grille est la première, elle va dans la partie en haut, à gauche du plateau
		if(getPositionMiniGrille(miniGrille) == 1){
			for(int y = 0; y < demiTabY; y++){
				for(int x = 0; x < demiTabX; x++){
					this.plateau[x][y] = miniGrille[x][y];
				}
			}
		}

		//si la position de la mini-grille est la deuxième, elle va dans la partie en haut, à droite du plateau
		else if(getPositionMiniGrille(miniGrille) == 2){
			for(int y = 0; y < demiTabY; y++){
				for(int x = demiTabX; x < this.plateau[0].length ; x++){
					this.plateau[x][y] = miniGrille[x-demiTabX][y];
				}
			}
		}

		//si la position de la mini-grille est la troisième, elle va dans la partie en bas, à droite du plateau
		else if(getPositionMiniGrille(miniGrille) == 3){
			for(int y = demiTabY; y < this.plateau.length ; y++){
				for(int x = demiTabX ; x < this.plateau[0].length; x++){
					this.plateau[x][y] = miniGrille[x-demiTabX][y-demiTabY];
				}
			}
		}

		//si la position de la mini-grille est la quatrième, elle va dans la partie en bas, à gauche du plateau
		else if(getPositionMiniGrille(miniGrille) == 4){
			for(int y = demiTabY; y < this.plateau.length ; y++){
				for(int x = 0; x < demiTabX; x++){
					this.plateau[x][y] = miniGrille[x][y-demiTabX];
					//this.plateau[x][y].setValue(x,y);
				}
			}
		}
	}

	/**
	 * Positionnement des jetons
	 */
	public void positonJeton(){
		int k=0;
		//Liste des formes disponibles au jeton
		String[] forme = {"carre","triangle","rond","etoile"};
		//Liste des couleurs disponibles au jeton
		String[] couleur = {"rouge","vert","bleu","jaune"};
		int[][] coordonnee = {{2,6,5},{3,3,6},{1,1,3},{0,1,6},{0,7,5},{1,5,1},{2,5,1},{3,4,1},{1,6,4},{0,2,4},{3,6,2},{2,1,3},{3,1,5},{2,2,6},{0,5,2},{1,4,6}};
		for(int i=0; i <= forme.length -1; i++){
			for(int j=0; j <= couleur.length -1; j++){
				tableauMiniGrille.get(coordonnee[k][0])[coordonnee[k][1]][coordonnee[k][2]] = new CaseJeton(tableauMiniGrille.get(coordonnee[k][0])[coordonnee[k][1]][coordonnee[k][2]].getValHaut(), tableauMiniGrille.get(coordonnee[k][0])[coordonnee[k][1]][coordonnee[k][2]].getValDroit(), tableauMiniGrille.get(coordonnee[k][0])[coordonnee[k][1]][coordonnee[k][2]].getValBas(), tableauMiniGrille.get(coordonnee[k][0])[coordonnee[k][1]][coordonnee[k][2]].getValGauche(),i,j, forme[i], couleur[j]);
				k=k+1;
			}
		}
		//tableauMiniGrille.get(3)[7][4] = new CaseJeton(tableauMiniGrille.get(3)[7][4].getValHaut(), tableauMiniGrille.get(3)[7][4].getValDroit(), tableauMiniGrille.get(3)[7][4].getValBas(), tableauMiniGrille.get(3)[7][4].getValGauche(), "spirale", "multi");
	}

	/**
	 * Récupération de la liste des cases interdites
	 * @return le tableau de coordonnées de case interdites
	 */
	public int[][] getZoneInterdite(){
		return this.deadZone;
	}

	/**
	 * Récupération de la taille du tableau
	 * @return la taille du tableau
	 */
	public int getTaillePlateau(){
		return plateau.length;
	}

	/**
	 * Ajout d'un parent au groupe de ce plateau
	 * @param parent le parent à ajouter
	 */
	public void addGroupPlateau(Parent parent){
		if(!this.getChildren().contains(parent))
			this.getChildren().add(parent);
	}

	/**
	 * Ajout d'une vue d'image au groupe de ce plateau
	 * @param image la vue à ajouter
	 */
	public void addGroupPlateau(ImageView image){
		if(!this.getChildren().contains(image))
			this.getChildren().add(image);
	}
}
