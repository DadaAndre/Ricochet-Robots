package ricochet_robots;

import static java.lang.Integer.toBinaryString;
import java.util.Arrays;

public class Plateau{

    public Plateau(){}

		//Methode qui convertit un nombre entier en un tableau binaire
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


        //Methode qui transforme une chaine de caractere en une mini-grille

        public String[]  stringToMiniGrille(String chaine){
            String [] tab1D = chaine.split(",");

            for (int i=0 ; i<tab1D.length;i++) {
                int num = Integer.parseInt(tab1D[i]);
                tab1D[i] = intTobinary(num);
            }
            return tab1D;
        }

        //Methode pour creer une mini-grille

        public String  creerMiniGrille(String chaine){
            String[] str =stringToMiniGrille(chaine);
            String str2 = Arrays.toString(str);
            return str2;
        }


}
