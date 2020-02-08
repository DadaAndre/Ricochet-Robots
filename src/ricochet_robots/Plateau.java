package ricochet_robots;

import java.util.ArrayList;

public class Plateau{

	private Case[][] miniGrille1;
	private Case[][] miniGrille2;
	private Case[][] miniGrille3;
	private Case[][] miniGrille4;
	private ArrayList<Case[][]> tableauMiniGrille = new ArrayList<>();

	public final String chaine1 = "9,8,8,1,0,0,1,0,0";
	public final String chaine2 = "8,8,12,0,0,4,0,0,4";
	public final String chaine3 = "0,0,4,0,0,4,2,2,6";
	public final String chaine4 = "1,0,0,1,0,0,3,2,2";
	public final String[] chaines = {chaine1, chaine2, chaine3, chaine4};

	private Case[][] plateau;

	public Plateau(){
		creerMiniGrille(4); //on crée 4 mini-grilles
		// for(int i = 0; i< chaines.length; i++){
		// 	System.out.println("........");
		// }
		// afficheMiniGrille(tableauMiniGrille.get(0));
		// System.out.println("........");
		// afficheMiniGrille(rotationMiniGrille(tableauMiniGrille.get(0), 2));

		System.out.println("chaine 1 est a sa bonne position? : " + positionEstVraie(tableauMiniGrille.get(1),  1));

		System.out.println("Après rotation........");
		Case[][] a = rotation(tableauMiniGrille.get(1), 1);

		System.out.println("chaine 1 est a sa bonne position? : " + positionEstVraie(a,  1));

	}

		//Méthode qui convertit un nombre entier en un tableau binaire
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


		//Méthode qui transforme une chaine de caractère en une mini-grille
        public Case[][]  stringToMiniGrille(String chaine){
            String[] tab1D = chaine.split(",");
            int tailleTab = (int) Math.sqrt(tab1D.length);
			int index = 0;

            Case[][] miniGrille = new Case[tailleTab][tailleTab];

			for(int y = 0; y<tailleTab; y++){
                for(int x = 0; x < tailleTab; x++){
					miniGrille[x][y] = new Case(intToBinary(Integer.parseInt(tab1D[index])));
					index++;

                }
            }
			return miniGrille;
        }

		//Méthode pour créer les mini-grilles
		public void creerMiniGrille(int nombreOccurence){
			if(nombreOccurence <= this.chaines.length){
				for(int i = 0 ; i < nombreOccurence; i++){
				   this.tableauMiniGrille.add(stringToMiniGrille(this.chaines[i]));
				}
			}
		}

		public void afficheMiniGrille(Case[][] miniGrille){
			for(int i = 0; i < miniGrille.length; i++){
				for(int j = 0; j<miniGrille[i].length; j++){
					System.out.print(miniGrille[j][i]);
				}System.out.println();
			}
		}

		public Case[][] rotation(Case[][] miniGrille, int position){
			Case[][] miniGrilleRota = new Case[miniGrille.length][miniGrille.length];
			//remplissage de la copie de la liste
			for(int y = 0; y < miniGrille[0].length; y++){
				for(int x = 0; x < miniGrille.length; x++){
					//System.out.println("iciiii" + miniGrille[miniGrille.length-y-1][x]);
					miniGrilleRota[x][y] = miniGrille[x][y];
				}
			}

			while(positionEstVraie(miniGrilleRota, position) != true){
				miniGrilleRota = rotationMiniGrille(miniGrilleRota);
			}

			return miniGrilleRota;
		}

		public Case[][] rotationMiniGrille(Case[][] miniGrille) {
			Case[][] miniGrilleRota = new Case[miniGrille.length][miniGrille.length];
			for(int y = 0; y < miniGrille[0].length; y++){
				for(int x = 0; x < miniGrille.length; x++){
					//System.out.println("iciiii" + miniGrille[miniGrille.length-y-1][x]);
					miniGrilleRota[x][y] = miniGrille[y][miniGrille.length-x-1];
					miniGrilleRota[x][y].rotationCase();
				}
			}

			return miniGrilleRota;
		}

		public boolean positionEstVraie(Case[][] miniGrille, int position){
			if(position == 1){
				return verifGauche(miniGrille) && verifHaut(miniGrille);

			}else if(position == 2){
				return verifHaut(miniGrille) && verifDroite(miniGrille);

			}else if(position == 3){
				return verifDroite(miniGrille) && verifBas(miniGrille);

			}else if(position == 4){
				return verifBas(miniGrille) && verifGauche(miniGrille);
			}
			return false;
		}

	public boolean verifHaut(Case[][] miniGrille){
		boolean ok = false;
		int y = 0;
		for(int x = 0; x <miniGrille.length; x++){
			System.out.println(miniGrille[x][y] + "Haut");
			if(miniGrille[x][y].getValHaut() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

	public boolean verifDroite(Case[][] miniGrille){
		boolean ok = false;
		int x = miniGrille.length - 1;
		for(int y = 0; y < miniGrille.length; y++){
			System.out.println(miniGrille[x][y] + "Droit");
			if(miniGrille[x][y].getValDroit() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

	public boolean verifBas(Case[][] miniGrille){
		boolean ok = false;
		int y = miniGrille.length - 1;
		for(int x = 0; x < miniGrille.length; x++){
			System.out.println(miniGrille[x][y] + "Bas");
			if(miniGrille[x][y].getValBas() == 1){
				ok = true;
			}else{
				return false;
			}
		}
		return ok;
	}

	public boolean verifGauche(Case[][] miniGrille){
		boolean ok = false;
		int x = 0;
		for(int y = 0; y <miniGrille.length; y++){
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
