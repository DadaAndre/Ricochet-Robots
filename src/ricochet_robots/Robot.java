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

	//Tire aléatoirement des coordonnées pour un robot et vérifie qu'un robot ne les a pas déjà
	public static int[] positionRobotNonUtilise(Plateau plateau){

		Random r = new Random();
		//Génération de 2 nombres aléatoires pour des coordonnées X et Y du robot
		int aleaX = r.nextInt(15);
		int aleaY = r.nextInt(15);
		int[] positions = {aleaX, aleaY};
		int index = 0;

		//Si aucun robot n'a été créé, alors on renvoie dirrectement les coordonnées tirées
		if(plateau.getListeRobot().size() == 0){
			return positions;
		}

		//Sinon, on vérifie les coordonnées de chaque robot
		while(index <= (plateau.getListeRobot().size() +1)){
			for(int i = 0; i < plateau.getListeRobot().size() ; i++){
				//Vérifie si les coordonnées X et Y tirées existe
				if(aleaX == plateau.getListeRobot().get(i).getPositionInitialeX() && aleaY == plateau.getListeRobot().get(i).getPositionInitialeY()){
					//Si c'est le cas, on re-génère des coordonnées
					aleaX = r.nextInt(15);
					aleaY = r.nextInt(15);
					index = 0;
				}
				//Si non, on passe au robot suivant
				else{
					index +=1;
				}
			}
		}
		return positions;
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
