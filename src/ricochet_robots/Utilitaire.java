package ricochet_robots;

public class Utilitaire{

	public Utilitaire(){

	}

	//Convertit un nombre entier en un tableau binaire
	public static int[] intToBinary(int number) {
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

	public static int CaseToInt(Case casePlateau){
		int chiffre1 = casePlateau.getValHaut();
		int chiffre2 = casePlateau.getValDroit();
		int chiffre3 = casePlateau.getValBas();
		int chiffre4 = casePlateau.getValGauche();

		return (chiffre1*8 + chiffre2*4 + chiffre3*2 + chiffre4*1);
	}

	//Transforme une chaine de caractère en une mini-grille
	public static Case[][] stringToMiniGrille(String chaine){

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
				miniGrille[x][y] = new Case(Utilitaire.intToBinary(Integer.parseInt(tab1D[index])), x, y);
				index++;
			}
		}
		return miniGrille;
	}
}
