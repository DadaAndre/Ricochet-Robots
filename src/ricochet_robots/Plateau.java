package ricochet_robots;

import java.util.Random;
import java.util.ArrayList;

public class Plateau{

	//Un tableau de miniGrille
	private ArrayList<Case[][]> tableauMiniGrille = new ArrayList<>();

	//les différentes miniGrilles représentées par une chaine de caractère
	public final String chaine1 = "9,8,8,1,0,0,1,0,0";
	public final String chaine2 = "8,8,12,0,0,4,0,0,4";
	public final String chaine3 = "0,0,4,0,0,4,2,2,6";
	public final String chaine4 = "1,0,0,1,0,0,3,2,2";
	public final String[] chaines = {chaine1, chaine2, chaine3, chaine4};

	private Case[][] plateau;

	public Plateau(){

		//On créer les miniGrille
		for(int i = 0 ; i < chaines.length; i++){
			creerMiniGrille(chaines[i]);
		}
		//creerPlateau();

	}

	//Convertit un nombre entier en un tableau binaire
	public int[] intToBinary(int number) {
	   int divNumber = number;
	   int div = 8;
	   int[] tabBinary = new int[4];
	   for(int i = 0; i<4; i++){
		   tabBinary[i] = divNumber/div;
		   divNumber = divNumber%div;
		   div = div/2;
	   }
	   return tabBinary;
   	}


	//Transforme une chaine de caractère en une mini-grille
    public Case[][]  stringToMiniGrille(String chaine){

		//On "découpe" la chaine de caractère entre chaque virgule, et on récupère dans tab1D un tableau de chaine de caractère
        String[] tab1D = chaine.split(",");
        int tailleTab = (int) Math.sqrt(tab1D.length);
		int index = 0;

		//On créer une miniGrille, un tableau 2D de Case
        Case[][] miniGrille = new Case[tailleTab][tailleTab];

		for(int y = 0; y<tailleTab; y++){
            for(int x = 0; x < tailleTab; x++){
				/*A chaque case de la miniGrille, on créer une instance de Case,
				 dans laquelle on envoie un tableau de nombre (ex: [1,0,0,1]), récolté à partir d'un entier,
				 qui lui même est dù à une chaine de caractère contenu dans chaine1, chaine2,chaine3 ou chaine4
				*/
				miniGrille[x][y] = new Case(intToBinary(Integer.parseInt(tab1D[index])));
				index++;
            }
        }
		return miniGrille;
    }

	//Créer les mini-grilles
	public void creerMiniGrille(String chaine){
		//on ajoute à tableauMiniGrille la miniGrille obtenue avec la méthode stringToMiniGrille
		this.tableauMiniGrille.add(stringToMiniGrille(chaine));
	}

	public void afficheMiniGrille(Case[][] miniGrille){
		for(int i = 0; i < miniGrille.length; i++){
			for(int j = 0; j<miniGrille[i].length; j++){
				System.out.print(miniGrille[j][i]);
			}
			System.out.println();
		}
	}

	//Fait la rotation de la mini-grille en fonction de la position choisie
	public Case[][] rotation(Case[][] miniGrille, int position){
		//On créer un tableau 2D (vide ici), qui représentera une copie de la miniGrille reçue
		Case[][] miniGrilleRota = new Case[miniGrille.length][miniGrille.length];
		//on remplis le tableau miniGrilleRota avec les valeurs de la miniGrille recue
		for(int y = 0; y < miniGrille[0].length; y++){
			for(int x = 0; x < miniGrille.length; x++){
				miniGrilleRota[x][y] = miniGrille[x][y];
			}
		}
		//On fait une rotation de la mini-grille tant qu'elle n'est pas adéquate avec sa position
		while(positionEstVraie(miniGrilleRota, position) != true){
			miniGrilleRota = rotationMiniGrille(miniGrilleRota);
		}
		//on retourne la miniGrille dans sa bonne rotation
		return miniGrilleRota;
	}

	//Fait une rotation de 90° d'un tableau
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

	//Retourne si la mini-grille correspond bien à la position choisie dans le plateau
	public boolean positionEstVraie(Case[][] miniGrille, int position){
		if(position == 1){
			//On vérifie si la mini-grille possède bien des "murs" à gauche et en haut
			return verifGauche(miniGrille) && verifHaut(miniGrille);

		}else if(position == 2){
			//On vérifie si la mini-grille possède bien des "murs" en haut et à droite
			return verifHaut(miniGrille) && verifDroite(miniGrille);

		}else if(position == 3){
			//On vérifie si la mini-grille possède bien des "murs" à droite et en bas
			return verifDroite(miniGrille) && verifBas(miniGrille);

		}else if(position == 4){
			//On vérifie si la mini-grille possède bien des "murs" en bas et à gauche
			return verifBas(miniGrille) && verifGauche(miniGrille);
		}
		return false;
	}

	//Retourne la position de la mini-grille
	public int getPositionMiniGrille(Case[][] miniGrille){
		if(verifGauche(miniGrille) && verifHaut(miniGrille)){
			return 1;

		}else if(verifHaut(miniGrille) && verifDroite(miniGrille)){
			return 2;

		}else if(verifDroite(miniGrille) && verifBas(miniGrille)){
			return 3;

		}else if(verifBas(miniGrille) && verifGauche(miniGrille)){
			return 4;
		}

		return 0;
	}

	//Vérifie si la miniGrille envoyée à bien des murs en haut
	public boolean verifHaut(Case[][] miniGrille){
		boolean ok = false;
		int y = 0;
		//on parcours tout les valeurs x , à y = 0 de la mini-grille
		for(int x = 0; x <miniGrille.length; x++){
			//Avoir un mur en haut, signifie que la valeur valHaut de Case est bien à 1
			if(miniGrille[x][y].getValHaut() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

	//Vérifie si la miniGrille envoyée à bien des murs à droite
	public boolean verifDroite(Case[][] miniGrille){
		boolean ok = false;
		int x = miniGrille.length - 1;
		//on parcours à la dernière valeur de x, tout les valeurs de y de la mini-grille
		for(int y = 0; y < miniGrille.length; y++){
			//Avoir un mur à droite, signifie que la valeur valDroit de Case est bien à 1
			if(miniGrille[x][y].getValDroit() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

	//Vérifie si la miniGrille envoyée à bien des murs en bas
	public boolean verifBas(Case[][] miniGrille){
		boolean ok = false;
		int y = miniGrille.length - 1;
		//on parcours à la dernière valeur de y, tout les valeurs de x de la mini-grille
		for(int x = 0; x < miniGrille.length; x++){
			//Avoir un mur en bas, signifie que la valeur valBas de Case est bien à 1
			if(miniGrille[x][y].getValBas() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

	//Vérifie si la miniGrille envoyée à bien des murs à gauche
	public boolean verifGauche(Case[][] miniGrille){
		boolean ok = false;
		int x = 0;
		//on parcours à x = 0, tout les valeurs de y de la mini-grille
		for(int y = 0; y <miniGrille.length; y++){
			//Avoir un mur à gauche, signifie que la valeur valGauche de Case est bien à 1
			System.out.println(miniGrille[x][y] + "Gauche");
			if(miniGrille[x][y].getValGauche() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

}
