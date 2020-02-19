package ricochet_robots;

public class Case{

	public static final int DIM = 42;

	private int valHaut;
	private int valDroit;
	private int valBas;
	private int valGauche;

	public Case(int[] tableau) {
		this.valHaut = tableau[0];
		this.valDroit = tableau[1];
		this.valBas = tableau[2];
		this.valGauche = tableau[3];
	}

	public Case(int valHaut, int valDroit, int valBas, int valGauche) {
		this.valHaut = valHaut;
		this.valDroit = valDroit;
		this.valBas = valBas;
		this.valGauche = valGauche;
	}

	public int getValHaut() {
	   return this.valHaut;
	}

	public int getValDroit() {
	   return this.valDroit;
	}

	public int getValBas() {
	   return this.valBas;
	}

	public int getValGauche() {
	   return this.valGauche;
	}

	//Retourne vrai si les valeurs de la case envoyée sont équivalentes à celles qu'on teste
	public boolean isValueEquals(Case caseSelect){
	   return caseSelect.valHaut == valHaut && caseSelect.valBas == valBas && caseSelect.valGauche == valGauche && caseSelect.valDroit == valDroit;
	}

	//fait une rotation de la case, c'est à dire qu'elle décale tout les valeurs da la case d'un cran
	public void rotationCase(){
	   int temp = this.valGauche;
	   this.valGauche = this.valBas;
	   this.valBas = this.valDroit;
	   this.valDroit = this.valHaut;
	   this.valHaut = temp;
	}

	//affichage de la Case
	@Override
	public String toString(){
		return "[" + valHaut + "," + valDroit + "," + valBas + "," + valGauche + "]";
	}
}
