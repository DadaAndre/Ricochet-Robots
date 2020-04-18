package ricochet_robots.jeu.plateau;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.Group;

public class CaseCalque extends Group {

	public CaseCalque(){}

	/**
	 * Ajout de la vue de l'image d'une case au groupe de vue de case
	 * @param image l'image à ajouter
	 */
	public void addImage(Image image) {
		//Création de la vue à partir de l'image
		ImageView view = new ImageView(image);
		//Modification de la taille dela vue
		view.setFitWidth(Case.DIM);
		view.setFitHeight(Case.DIM);
		//Ajout de la vue au groupe 
		this.getChildren().add(view);
	}

}
