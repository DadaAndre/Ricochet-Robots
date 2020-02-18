package ricochet_robots;

import java.util.Random;

public class Robot{

	private String couleur;
	private int positionInitialeX;
	private int positionInitialeY;
	private int positionX;
	private int positionY;
	private Plateau plateauJeu;

	public Robot(Plateau plateauJeu, String couleur, int positionX, int positionY){
		this.couleur = couleur;
		this.positionInitialeX = positionX;
		this.positionInitialeY = positionY;
		this.positionX = positionX;
		this.positionY = positionY;
		this.plateauJeu = plateauJeu;
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

	public void setPosition(int x, int y){
		this.positionY = positionY;
		this.positionX = positionX;
	}

	public String getCouleur(){
		return couleur;
	}

	public boolean estUneCollisionRobot(Deplacement direction){
		switch(direction){
			case DIRECTION_HAUT:
				for(int i = 0; i< this.plateauJeu.getTableauRobot().size(); i++){
					if(this.plateauJeu.getTableauRobot().get(i).getCouleur() != couleur && this.plateauJeu.getTableauRobot().get(i).getPositionX() == positionX &&  this.plateauJeu.getTableauRobot().get(i).getPositionY() == (positionY - 1)){
						return true;
					}
				}
				return false;

			case DIRECTION_BAS:
				for(int i = 0; i< this.plateauJeu.getTableauRobot().size(); i++){
					if(this.plateauJeu.getTableauRobot().get(i).getCouleur() != couleur && this.plateauJeu.getTableauRobot().get(i).getPositionX() == positionX &&  this.plateauJeu.getTableauRobot().get(i).getPositionY() == (positionY + 1)){
						return true;
					}
				}
				return false;

			case DIRECTION_GAUCHE:
				for(int i = 0; i< this.plateauJeu.getTableauRobot().size(); i++){
					if(this.plateauJeu.getTableauRobot().get(i).getCouleur() != couleur && this.plateauJeu.getTableauRobot().get(i).getPositionX() == (positionX - 1)  &&  this.plateauJeu.getTableauRobot().get(i).getPositionY() == positionY){
						return true;
					}
				}
				return false;

			case DIRECTION_DROITE:
				for(int i = 0; i< this.plateauJeu.getTableauRobot().size(); i++){
					if(this.plateauJeu.getTableauRobot().get(i).getCouleur() != couleur && this.plateauJeu.getTableauRobot().get(i).getPositionX() == (positionX + 1)  &&  this.plateauJeu.getTableauRobot().get(i).getPositionY() == positionY){
						return true;
					}
				}
				return false;
		}
		return true;
	}

	//Déplacement du robot
	public void deplacementRobot(Deplacement direction){
		//Vérification de la direction choisie
		if(direction == Deplacement.DIRECTION_HAUT){
			//Tant que le robot ne rencontre pas un mur en haut, il se dirige vers le haut
			while(this.plateauJeu.getCase(positionX, positionY - 1).getValHaut() != 1 && !estUneCollisionRobot(direction)){
				this.positionY -= 1;
			}
		}else if(direction == Deplacement.DIRECTION_BAS){
			//Tant que le robot ne rencontre pas un mur en bas, il se dirige vers le bas
			while(this.plateauJeu.getCase(positionX, positionY + 1).getValBas() != 1 && !estUneCollisionRobot(direction)){
				this.positionY += 1;
			}
		}else if(direction == Deplacement.DIRECTION_GAUCHE){
			//Tant que le robot ne rencontre pas un mur à gauche, il se dirige vers la gauche
			while(this.plateauJeu.getCase(positionX - 1, positionY).getValGauche() != 1 && !estUneCollisionRobot(direction)){
				this.positionX -= 1;
			}
		}else if(direction == Deplacement.DIRECTION_DROITE){
			//Tant que le robot ne rencontre pas un mur à droite, il se dirige vers la droite
			while(this.plateauJeu.getCase(positionX + 1, positionY).getValDroit() != 1 && !estUneCollisionRobot(direction)){
				this.positionX += 1;
			}
		}
	}
}
