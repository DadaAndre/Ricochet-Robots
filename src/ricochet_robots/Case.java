package ricochet_robots;

public class Case{

	private int valHaut;
	private int valDroit;
	private int valBas;
	private int valGauche;

	public Case(int[] tableau) {
	//	this.tableau = tableau;
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

   public boolean isValueEquals(Case caseSelect){
	   return caseSelect.valHaut == valHaut && caseSelect.valBas == valBas && caseSelect.valGauche == valGauche && caseSelect.valDroit == valDroit;
   }

	@Override
	public String toString(){
		return "[" + valHaut + "," + valDroit + "," + valBas + "," + valGauche + "]";
	}

}