package ricochet_robots;

public class  CaseJeton extends Case{
	private String forme;
	private String couleur;


	public CaseJeton(int[] tableau, String forme, String couleur){
		super(tableau);
		this.forme = forme;
		this.couleur = couleur;

	}

	public CaseJeton(int valHaut, int valDroit, int valBas, int valGauche, String forme, String couleur){
		super(valHaut, valDroit, valBas, valGauche);
		this.forme = forme;
		this.couleur = couleur;
	}

	public String getForme(){
		return forme;
	}

	public String getCouleur(){
		return couleur;
	}

	@Override
	public String toString(){
		return "[" + getValHaut() + "," + getValDroit() + "," + getValBas() + "," + getValGauche() +","+ forme + "," + couleur +"]";
	}
}
