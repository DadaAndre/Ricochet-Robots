package ricochet_robots;

public class Case{

	private boolean valHaut;
	private boolean valDroit;
	private boolean valBas;
	private boolean valGauche;

	public Case() {

	}

	public Case(boolean valHaut, boolean valDroit, boolean valBas, boolean valGauche) {
		this.valHaut = valHaut;
		this.valDroit = valDroit;
		this.valBas = valBas;
		this.valGauche = valGauche;
	}

	public boolean getValHaut() {
	   return this.valHaut;
	}

	public boolean getValDroit() {
	   return this.valDroit;
	}

	public boolean getValBas() {
	   return this.valHaut;
	}

	public boolean getValGauche() {
	   return this.valBas;
	}

}
