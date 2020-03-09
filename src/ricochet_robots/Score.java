package ricochet_robots;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public class Score extends FlowPane{

    private int score;
    private int nbCoups;
    private int meilleurScore;

	Label labelScore;
	Label labelCoup;
	Label labelMeilleurScore;

    public Score(){
        score = 1000;
        nbCoups = 0;
        meilleurScore = 0;

		labelCoup = new Label("Coups: " + nbCoups);
		labelScore = new Label("Score: " + score);
		labelMeilleurScore = new Label("Highscore: " + meilleurScore);

		crerLabel(labelCoup, 50);
		crerLabel(labelScore, 200);
		crerLabel(labelMeilleurScore, 350);
    }

    public int getScore(){
        return this.score;
    }

    public int getMeilleurScore(){
        return this.meilleurScore;
    }

    public void afficherCoup(){
        System.out.println("coup " + nbCoups);
		labelScore.setText("Score: " + score);
		labelCoup.setText("Coups: " + nbCoups);
    }

	public void ajouterCoup(){
		nbCoups += 1;
	}

	public void reinitialiserCoup(){
		nbCoups = 0;
	}

	public int getNbCoup(){
		return this.nbCoups;
	}

    public void setScore(){
        this.score -= (nbCoups*10);
    }

	public void crerLabel(Label labelVar, int posX){
		this.getChildren().add(labelVar);
		labelVar.setTranslateX(posX);
		labelVar.setTranslateY(5);
		labelVar.setFont(new Font("Arial", 25));
	}

    public void setMeilleurScore(int nouveauMeilleurScore){
        if(this.meilleurScore < nouveauMeilleurScore){
            this.meilleurScore = nouveauMeilleurScore;
        }
    }
}
