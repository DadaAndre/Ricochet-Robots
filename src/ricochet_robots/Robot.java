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

	public static int[] positionRobotNonUtilise(Plateau plateau){

		Random r = new Random();

		int aleaX = r.nextInt(15);
		int aleaY = r.nextInt(15);
		int[] positions = {aleaX, aleaY};
		int index = 0;

		if(plateau.getListeRobot().size() == 0){
			return positions;
		}

		while(index <= (plateau.getListeRobot().size() +1)){
			for(int i = 0; i < plateau.getListeRobot().size() ; i++){
				if(aleaX == plateau.getListeRobot().get(i).getPositionInitialeX() && aleaY == plateau.getListeRobot().get(i).getPositionInitialeY()){
					aleaX = r.nextInt(15);
					aleaY = r.nextInt(15);
					index = 0;
				}else{
					index +=1;
				}
			}
		}
		return positions;
	}

	public void deplacementRobot(Deplacement direction){
		if(direction == Deplacement.DIRECTION_HAUT){
			while(this.plateau.getCase(positionX, positionY - 1).getValHaut() != 1){
				this.positionY -= 1;
			}
		}else if(direction == Deplacement.DIRECTION_BAS){
			while(this.plateau.getCase(positionX, positionY + 1).getValBas() != 1){
				this.positionY += 1;
			}
		}else if(direction == Deplacement.DIRECTION_GAUCHE){
			while(this.plateau.getCase(positionX - 1, positionY).getValGauche() != 1){
				this.positionX -= 1;
			}
		}else if(direction == Deplacement.DIRECTION_DROITE){
			while(this.plateau.getCase(positionX + 1, positionY).getValDroit() != 1){
				this.positionX += 1;
			}
		}
	}
}
