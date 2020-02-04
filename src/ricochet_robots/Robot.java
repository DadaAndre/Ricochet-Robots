package ricochet_robots;

public class Robot{

	private String couleur;
	private int positionInitialeX;
	private int positionInitialeY;
	private int positionX;
	private int positionY;

	public Robot(String couleur, int positionInitialeX, int positionInitialeY){
		this.couleur = couleur;
		this.positionInitialeX = positionInitialeX;
		this.positionInitialeY = positionInitialeY;
		this.positionX = positionInitialeX;
		this.positionY = positionInitialeY;
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
}
