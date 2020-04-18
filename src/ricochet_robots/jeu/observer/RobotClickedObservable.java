package ricochet_robots.jeu.observer;

import ricochet_robots.jeu.pions.*;

public interface RobotClickedObservable{

	/**
	 * Notification du robot cliqué
	 * @param robot le robot cliqué
	 */
	public void notifierRobotClique(Robot robot);

	/**
	 * Ajout de l'observer d'un robot
	 * @param robot un observeur du robot
	 */
	public void ajouterObserveurRobotClique(RobotClickedObserver robot);

	/**
	 * Supression de l'observer d'un robot
	 * @param robot un observeur du robot
	 */
	public void supprimerObserveurRobotClique(RobotClickedObserver robot);
}
