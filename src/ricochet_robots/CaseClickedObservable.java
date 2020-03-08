package ricochet_robots;

public interface CaseClickedObservable{

	public void notifierCaseClique(Case casePlateau);

	public void ajouterObserveurCaseClique(CaseClickedObserver casePlateauObserver);

	public void supprimerObserveurCaseClique(CaseClickedObserver casePlateauObserver);
}
