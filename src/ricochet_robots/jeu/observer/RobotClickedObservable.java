package ricochet_robots.jeu.observer;

import ricochet_robots.jeu.pions.*;

public interface RobotClickedObservable{

	public void notifierRobotClique(Robot robot);

	public void ajouterObserveurRobotClique(RobotClickedObserver robot);

	public void supprimerObserveurRobotClique(RobotClickedObserver robot);
}
