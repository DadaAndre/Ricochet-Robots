package ricochet_robots;

import java.util.HashSet;
import java.util.ArrayList;

public class AlgoAStar{

	HashSet<Case> listeNoeuds = new HashSet<>();
	ArrayList<Case> listeNoeudsRestant = new ArrayList<>();

	Robot robotInitial;
	State etatInitial;
	Jeton jetonArrivee;

	public AlgoAStar(State etatInitial){
		this.etatInitial = etatInitial;
		this.robotInitial = etatInitial.getRobotAJouer();
		this.jetonArrivee = etatInitial.getJetonTire();
	}

	//Permet de donner la case sur laquelle le robot est positionné
	public Case getCaseRobot(Robot robot){
		Plateau plateau = etatInitial.getEtatPlateau();
		return plateau.getCasePlateau(robot.getPositionX(),robot.getPositionY());
	}

	//Permet de parcourir l'ensemble des noeuds (cases) possibles dans toute les directions possibles
	public void parcoursNoeud(Case noeudAParcourir){
		//Si on a plus de noeud, alors on arrête
		if(listeNoeudsRestant.size() == 0){
			return;
		}
		//On met le robot sur le noeud auquel on affecte différentes directions
		robotInitial.setPosition(noeudAParcourir.getXCase(),noeudAParcourir.getYCase());

		//On affecte les différentes positions
		for(Deplacement direction : Deplacement.values()){
			robotInitial.move(direction);
			Case caseDuRobot = this.getCaseRobot(robotInitial);
			//On vérifie que le noeud n'a pas été déjà exploré
			if(!listeNoeuds.contains(caseDuRobot)){
				/*Si il est nouveau, alors on l'ajoute à la liste des noeuds
				 existants et celle qui contient les noeuds restants à explorer
				*/
				listeNoeuds.add(caseDuRobot);
				listeNoeudsRestant.add(caseDuRobot);
			}
			//On remet le robot au noeud après un mouvement, pour pouvoir affecter le mouvement suivant
			robotInitial.setPosition(noeudAParcourir.getXCase(),noeudAParcourir.getYCase());
		}

		//après lui avoir affecté les 4 directions, on supprime le noeuds des noeuds à explorer puisqu'il à été exploré
		listeNoeudsRestant.remove(noeudAParcourir);

		//On passe à un noeud suivant tant qu'il existe des noeuds à explorer
		if(!listeNoeudsRestant.isEmpty()){
			parcoursNoeud(listeNoeudsRestant.get(0));
		}
	}

	//Permet de
	public void parcoursTotal(){
		//On ajoute la case de départ du robot comme noeud existant et à explorer
		Case initialeCase = getCaseRobot(robotInitial);
		listeNoeuds.add(initialeCase);
		listeNoeudsRestant.add(initialeCase);

		//on parcours ce premier noeud
		parcoursNoeud(initialeCase);

		//après avoir parcouru tout les noeuds, on remet le robot à sa case de départ
		robotInitial.setPosition(initialeCase.getXCase(), initialeCase.getYCase());

		//Affichage de l'ensemble des noeuds existants
		for(Case c : listeNoeuds) {
			System.out.println(c.getXCase() + " - " + c.getYCase());
		}
	}
}
