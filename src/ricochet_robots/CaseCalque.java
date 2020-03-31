package ricochet_robots;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.Group;

public class CaseCalque extends Group {

	public CaseCalque(){

	}

	public void addImage(Image image) {
		ImageView view = new ImageView(image);
		view.setFitWidth(Case.DIM);
		view.setFitHeight(Case.DIM);
		this.getChildren().add(view);
	}

}
