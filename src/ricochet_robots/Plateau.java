package ricochet_robots;

import static java.lang.Integer.toBinaryString;
import java.util.Arrays;

public class Plateau{

    public Plateau(){}

        //Methode qui convertit un entier a un nombre binaire 

        public String intTobinary(int number) {
            String str = toBinaryString(number);
            while(str.length()<4){
                str="0"+str;
            }
            return "["+str.charAt(0)+","+str.charAt(1)+","+str.charAt(2)+","+str.charAt(3)+"]";
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
  
