package ricochet_robots;

public class Jeton{
	private String couleur;
	private String forme;
	private int positionX;
	private int positionY;

	public Jeton(String couleur, String forme, int positionX, int positionY){
		this.couleur = couleur;
		this.forme = forme;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public String couleur(){
		return couleur;
	}

	public String forme(){
		return forme;
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
}
