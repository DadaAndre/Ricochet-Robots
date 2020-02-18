package ricochet_robots;

import java.util.Random;

public class Robot{

	private String couleur;
	private int positionInitialeX;
	private int positionInitialeY;
	private int positionX;
	private int positionY;
	private Plateau plateau;

	public Robot(Plateau plateau, String couleur, int positionX, int positionY){
		this.couleur = couleur;
		this.positionInitialeX = positionX;
		this.positionInitialeY = positionY;
		this.positionX = positionX;
		this.positionY = positionY;
		this.plateau = plateau;
	}

	public int getPositionInitialeX(){
		return positionInitialeX;
	}

	public void setPositionInitialeX(int positionInitialeX){
		this.positionInitialeX = positionInitialeX;
	}

	public int getPositionInitialeY(){
		return positionInitialeY;
	}

	public void setPositionInitialeY(int positionInitialeY){
		this.positionInitialeY = positionInitialeY;
	}

	public int getPositionX(){
		return positionX;
	}

	public void setPositionX(int positionX){
		this.positionX = positionX;
	}

	public int getPositionY(){
		return positionY;
	}

	public void setPositionY(int positionY){
		this.positionY = positionY;
	}

	public String couleur(){
		return couleur;
	}

	//Déplacement du robot
	public void deplacementRobot(Deplacement direction){
		//Vérification de la direction choisie
		if(direction == Deplacement.DIRECTION_HAUT){
			//Tant que le robot ne rencontre pas un mur en haut, il se dirige vers le haut
			while(this.plateau.getCase(positionX, positionY - 1).getValHaut() != 1){
				this.positionY -= 1;
			}
		}else if(direction == Deplacement.DIRECTION_BAS){
			//Tant que le robot ne rencontre pas un mur en bas, il se dirige vers le bas
			while(this.plateau.getCase(positionX, positionY + 1).getValBas() != 1){
				this.positionY += 1;
			}
		}else if(direction == Deplacement.DIRECTION_GAUCHE){
			//Tant que le robot ne rencontre pas un mur à gauche, il se dirige vers la gauche
			while(this.plateau.getCase(positionX - 1, positionY).getValGauche() != 1){
				this.positionX -= 1;
			}
		}else if(direction == Deplacement.DIRECTION_DROITE){
			//Tant que le robot ne rencontre pas un mur à droite, il se dirige vers la droite
			while(this.plateau.getCase(positionX + 1, positionY).getValDroit() != 1){
				this.positionX += 1;
			}
		}
	}
}
