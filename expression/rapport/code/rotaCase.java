public void rotationCase(){
	   int temp = this.valGauche;
	   this.valGauche = this.valBas;
	   this.valBas = this.valDroit;
	   this.valDroit = this.valHaut;
	   this.valHaut = temp;

	   this.id = Utilitaire.CaseToInt(this);
}