package ricochet_robots.jeu.observer;

import ricochet_robots.jeu.plateau.*;

public interface CaseClickedObservable{

	public void notifierCaseClique(Case casePlateau);

	public void ajouterObserveurCaseClique(CaseClickedObserver casePlateauObserver);

	public void supprimerObserveurCaseClique(CaseClickedObserver casePlateauObserver);
}
