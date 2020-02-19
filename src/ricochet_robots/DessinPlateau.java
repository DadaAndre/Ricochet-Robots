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

	HashMap<Integer, Image> listeImagesPlateau;
	int[] tableauValeur = {0,1,2,3,4,5,6,8,9,10,12};
	ArrayList<Image> tabImage;

	public DessinPlateau(){
		crerListeImage();
	}

	public void crerListeImage(){
		this.listeImagesPlateau = new HashMap<>();
		this.tabImage = new ArrayList<>();

		for(int i = 0; i < tableauValeur.length; i++){
			tabImage.add(new Image("images/img" + Integer.toString(tableauValeur[i])+ ".png"));

			listeImagesPlateau.put(tableauValeur[i] , tabImage.get(i));

		}
	}

	public HashMap<Integer, Image> getImagesPlateau(){
		return listeImagesPlateau;
	}


		// for(Map<TypeCle, TypeValeur> entry : s) {
		//     TypeCle cle = entry.getKey();
		//     TypeValeur valeur = entry.getValue();
		//     // traitements
}
