package ricochet_robots;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import java.util.HashMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

public class DessinPlateau{

	Group root;

	//Associe un chiffre du plateau à une image correspondante au chiffre
	HashMap<Integer, Image> listeImagesPlateau;
	//L'ensemble des valeurs possibles au tableau
	int[] tableauValeur = {0,1,2,3,4,5,6,8,9,10,12};
	//L'ensemble des images possibles au tableau
	ArrayList<Image> tabImagePlateau;


	//Associe un string (forme et couleur) à une image correspondante
	HashMap<String, Image> listeImagesJeton;
	//L'ensemble des couleurs possibles au jeton
	String[] tableauCouleurs = {"rouge","bleu","vert","jaune","multi"};
	String[] tableauForme = {"carre","triangle","rond","etoile","spirale"};
	//L'ensemble des images possibles aux jetons
	ArrayList<Image> tabImageJeton;

	public DessinPlateau(Group root){
		this.root = root;
		crerListeImagePlateau();
		creeListeImageJeton();
	}

	public void crerListeImagePlateau(){
		this.listeImagesPlateau = new HashMap<>();
		this.tabImagePlateau = new ArrayList<>();

		for(int i = 0; i < tableauValeur.length; i++){
			tabImagePlateau.add(new Image("images/imgPlateau/img" + Integer.toString(tableauValeur[i])+ ".png"));

			listeImagesPlateau.put(tableauValeur[i] , tabImagePlateau.get(i));

		}
	}

	public void creeListeImageJeton(){
		this.listeImagesJeton = new HashMap<>();
		this.tabImageJeton = new ArrayList<>();

		int count = 0;
		//on s'arrêtre avant multi
		for(int i = 0; i < tableauCouleurs.length-1; i++){
			//on s'arrête avant la spirale
			for (int j = 0 ; j < tableauForme.length-1 ; j++){
				tabImageJeton.add(new Image("images/imgJeton/" + (tableauForme[j] + "-" + tableauCouleurs[i])+ ".png"));
				listeImagesJeton.put(tableauForme[j] + "," + tableauCouleurs[i] , tabImageJeton.get(count));

				count++;
			}
		}
	}

	public HashMap<Integer, Image> getImagesPlateau(){
		return listeImagesPlateau;
	}

	//Dessine le plateau
	public void dessinerPlateau(Plateau plateau, int departGrilleX, int departGrilleY){

		//Création d'un ArrayList contenant l'ensemble des vues des tiles dessinées pour le plateau
		ArrayList<ImageView> ensDessinCase = new ArrayList<>();
		ArrayList<ImageView> ensDessinJeton = new ArrayList<>();

		int index = 0;
		int count = 0;

		Jeton jetonTire = Jeton.tirageJeton();

		//On parcours le plateau
		for(int y = 0; y < plateau.getTaillePlateau(); y++){
			for(int x = 0; x < plateau.getTaillePlateau() ; x++){

				//On transforme la case à la position X,Y en un int, correspondant à une clé
				int key = Utilitaire.CaseToInt(plateau.getCase(x,y));

				/*On cherche la tile parmis l'ensemble de tile, qui correspond
				  à la clé et on ajoute la vue de cette tile à l'ArrayList
				*/
				ensDessinCase.add(new ImageView(listeImagesPlateau.get(key)));
				//ensuite, on l'ajoute au groupe d'objets graphique
				this.root.getChildren().add(ensDessinCase.get(index));
				//On place cette tile à une position donnée
				ensDessinCase.get(index).setX(departGrilleX + (Case.DIM * x));
				ensDessinCase.get(index).setY(departGrilleY + (Case.DIM * y));
				index++;

				if(plateau.getCase(x,y) instanceof CaseJeton){
					CaseJeton caseJeton = (CaseJeton) plateau.getCase(x,y);
					for(HashMap.Entry<String,Image> m : listeImagesJeton.entrySet()) {
						String coulActuel = caseJeton.getCouleur();
						String formeActuel = caseJeton.getForme();
						if((formeActuel + "," + coulActuel).equals(m.getKey())){
							ensDessinJeton.add(new ImageView(m.getValue()));
							this.root.getChildren().add(ensDessinJeton.get(count));
							ensDessinJeton.get(count).setX(departGrilleX + (x * Case.DIM));
							ensDessinJeton.get(count).setY(departGrilleY + (y * Case.DIM));
							count++;
						}
						System.out.println("tire: " + jetonTire.getForme() + "," + jetonTire.getCouleur());
						if((jetonTire.getForme() + "," + jetonTire.getCouleur()).equals(m.getKey())){
							ensDessinJeton.add(new ImageView(m.getValue()));
							this.root.getChildren().add(ensDessinJeton.get(count));
							ensDessinJeton.get(count).setX(8 * Case.DIM + 0.5  * Case.DIM);
							ensDessinJeton.get(count).setY(8 * Case.DIM + 0.5  * Case.DIM);
							count++;
						}
					}
				}
			}
		}
	}
}
