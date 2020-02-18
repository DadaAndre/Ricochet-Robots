package ricochet_robots;

import java.util.Random;

public class  Jeton{
	private String forme;
	private String couleur;

	Random r = new Random();

	public Jeton(String forme, String couleur){
		this.forme = forme;
		this.couleur = couleur;

	}

	public String getForme(){
		return forme;
	}

	public String getCouleur(){
		return couleur;
	}

	public void tirageJeton(){
		String[] forme = {"carré","triangle","rond","étoile"};
		String[] couleur = {"rouge","vert","bleu","jaune"};
		int tirCouleur = r.nextInt(4);
		int tirForme = r.nextInt(4);
		new Jeton(forme[tirForme], couleur[tirCouleur]);
	}

}
