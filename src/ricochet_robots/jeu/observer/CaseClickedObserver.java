package ricochet_robots.jeu.observer;

import ricochet_robots.jeu.plateau.*;

public interface CaseClickedObserver{

	/**
	 * Action effectuée en cas de clic sur la case
	 * @param casePlateau la case cliquée
	 */
	public void clicSurCase(Case casePlateau);
}
