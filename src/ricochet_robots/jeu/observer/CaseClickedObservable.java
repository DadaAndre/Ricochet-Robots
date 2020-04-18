package ricochet_robots.jeu.observer;

import ricochet_robots.jeu.plateau.*;

public interface CaseClickedObservable{

	/**
	 * Notification de la case cliquée
	 *  @param casePlateau la case cliquée
	 */
	public void notifierCaseClique(Case casePlateau);

	/**
	 * Ajout de l'observer d'une case
	 * @param casePlateauObserver un observeur de la case
	 */
	public void ajouterObserveurCaseClique(CaseClickedObserver casePlateauObserver);

	/**
	 * Supression de l'observer d'une case
	 * @param casePlateauObserver un observeur de la case
	 */
	public void supprimerObserveurCaseClique(CaseClickedObserver casePlateauObserver);
}
