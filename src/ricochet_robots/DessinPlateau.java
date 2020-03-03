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

	//Associe un chiffre du plateau à une image correspondante au chiffre
	HashMap<Integer, Image> listeImagesPlateau;
	//L'ensemble des valeurs possibles au tableau
	int[] tableauValeur = {0,1,2,3,4,5,6,8,9,10,12};
	//L'ensemble des images possibles au tableau
	ArrayList<Image> tabImagePlateau;


	//Associe un string (forme et couleur) à une image correspondante
	HashMap<String, Image> listeImagesJeton;





	public DessinPlateau(){
		crerListeImagePlateau();
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

	}

	public HashMap<Integer, Image> getImagesPlateau(){
		return listeImagesPlateau;
	}

	//Dessine le plateau
	public void dessinerPlateau(Plateau plateau, Group root, int departGrilleX, int departGrilleY){

		//Création d'un ArrayList contenant l'ensemble des vues des tiles dessinées pour le plateau
		ArrayList<ImageView> ensDessinCase = new ArrayList<>();

		int index = 0;
		//On parcours le plateau
		for(int y = 0; y < plateau.getTaillePlateau(); y++){
			for(int x = 0; x < plateau.getTaillePlateau() ; x++){

				//On transforme la case à la position X,Y en un int, correspondant à une clé
				int key = Utilitaire.CaseToInt(plateau.getCase(x,y));

				if(plateau.getCase(x,y) instanceof CaseJeton){
					CaseJeton caseJeton = (CaseJeton) plateau.getCase(x,y);
					System.out.println(caseJeton.getCouleur());
				}

				/*On cherche la tile parmis l'ensemble de tile, qui correspond
				  à la clé et on ajoute la vue de cette tile à l'ArrayList
				*/
				ensDessinCase.add(new ImageView(listeImagesPlateau.get(key)));
				//ensuite, on l'ajoute au groupe d'objets graphique
				root.getChildren().add(ensDessinCase.get(index));
				//On place cette tile à une position donnée
				ensDessinCase.get(index).setX(departGrilleX + (Case.DIM * x));
				ensDessinCase.get(index).setY(departGrilleY + (Case.DIM * y));
				index++;
			}
		}
	}

}
