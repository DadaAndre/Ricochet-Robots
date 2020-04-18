package ricochet_robots.jeu.observer;

import ricochet_robots.jeu.pions.*;

public interface RobotClickedObserver{

	/**
	 * Action effectuée en cas de clic sur le robot
	 * @param robot le robot cliqué
	 */
	public void clicSurRobot(Robot robot);
}
