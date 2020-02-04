package ricochet_robots;

import java.util.ArrayList;

public class Plateau{

	private Case[][] miniGrille1;
	private Case[][] miniGrille2;
	private Case[][] miniGrille3;
	private Case[][] miniGrille4;
	private ArrayList<Case[][]> tableauMiniGrille = new ArrayList<>();

	public final String chaine1 = "1,2,4,5,6,7,5,14,15";
	public final String chaine2 = "6,2,4,5,6,7,5,12,15";
	public final String chaine3 = "1,2,6,7,9,2,1,11,10";
	public final String chaine4 = "9,8,1,5,6,7,5,12,13";
	public final String[] chaines = {chaine1, chaine2, chaine3, chaine4};

	private Case[][] plateau;

	public Plateau(){
		creerMiniGrille(4); //on crée 4 mini-grilles
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

			for(int i = 0; i<tailleTab; i++){
                for(int j = 0; j < tailleTab; j++){
					miniGrille[i][j] = new Case(intToBinary(Integer.parseInt(tab1D[index])));
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
			for(int i = 0; i< miniGrille.length; i++){
				for(int j = 0; j<miniGrille[i].length; j++){
					System.out.print(miniGrille[i][j]);
				}System.out.println();
			}
		}
}
