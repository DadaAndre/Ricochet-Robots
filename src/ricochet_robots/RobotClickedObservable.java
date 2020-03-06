package ricochet_robots;

public interface RobotClickedObservable{

	public void notifierRobotClique(Robot robot);

	public void ajouterObserveurRobotClique(RobotClickedObserver robot);

	public void supprimerObserveurRobotClique(RobotClickedObserver robot);
}
