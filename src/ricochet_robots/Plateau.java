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
		// for(int i = 0; i< 2; i++){
		// 	System.out.println("chaine" + (i+1) + " a sa bonne position? : " + positionEstVraie(tableauMiniGrille.get(i),  i+1));
		// }
		afficheMiniGrille(tableauMiniGrille.get(0));
		System.out.println("........");
		afficheMiniGrille(tableauMiniGrille.get(1));
		System.out.println("........");
		afficheMiniGrille(tableauMiniGrille.get(2));
		System.out.println("........");
		afficheMiniGrille(tableauMiniGrille.get(3));
		System.out.println("........");
		System.out.println("chaine" + 1 + " a sa bonne position? : " + positionEstVraie(tableauMiniGrille.get(0), 1));
		System.out.println("chaine" + 2 + " a sa bonne position? : " + positionEstVraie(tableauMiniGrille.get(1), 2));
		System.out.println("chaine" + 3 + " a sa bonne position? : " + positionEstVraie(tableauMiniGrille.get(2), 3));
		System.out.println("chaine" + 4 + " a sa bonne position? : " + positionEstVraie(tableauMiniGrille.get(3), 4));

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

		public void  rotationGrille(Case[][] miniGrille, int position){
			// for(int i = 0; i< miniGrille.length; i++){
			// 	for(int j = 0; j<miniGrille[i].length; j++){
			// 		System.out.print(miniGrille[i][j]);
			// 	}System.out.println();
			// }
		}

		public boolean positionEstVraie(Case[][] miniGrille, int position){
			if(position == 1){

				//------------- verifGauche ---------------------

				//------------- verifHaut -----------------------

				return verifGauche(miniGrille) && verifHaut(miniGrille);

			}else if(position == 2){
				return verifHaut(miniGrille) && verifDroite(miniGrille);


				//------------- verifHaut -----------------------
				//------------- verifDroit ----------------------


			}else if(position == 3){
				//------------- verifBas ------------------------
				//------------- verifDroit ----------------------

				return verifDroite(miniGrille) && verifBas(miniGrille);

			}else if(position == 4){
				return verifBas(miniGrille) && verifGauche(miniGrille);

				//------------- verifGauche ---------------------
				//------------- verifBas ------------------------
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
				//return false;
				System.out.println("ce s'rait faux en haut, 1,0,0,0");
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
				//return false;
				System.out.println("ce s'rait faux en droit, 0,1,0,0");
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
				// false;
				System.out.println("ce s'rait faux en bas, 0,0,1,0");
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
				System.out.println("ce s'rait faux en gauche, 0,0,0,1");
				//return false;
			}
		}
		return ok;
	}
	//	def rotation(self,piece):

	// #on initialise la hauteur puis la largeur de la pièce sélectionner
	// self.heightP = len(piece)
	// self.widthP = len(piece[0])
	//
	// #la variable grille devient une liste
	// self.grille = list()
	//
	// #la grille devient un liste en 2D qui aura les mêmes dimensions que la prochaine forme de la pièce
	// for i in range(self.widthP):
	// 	self.grille.append([0]*self.heightP)
	//
	// #grille prend la valeur de la prochaine "forme" de la pièce
	// for y in range (self.heightP):
	// 	for x in range (self.widthP):
	// 		self.grille[x][y] = piece[y][self.widthP-1-x]
	//
	// #on donne sa nouvelle forme à la pièce et on "return" la pièce
	// piece = self.grille
	// return piece
}
