// package ricochet_robots.algorithme;
//
// import java.util.HashSet;
// import java.util.ArrayList;
// import java.util.Set;
// import java.util.HashMap;
// import java.util.Collections;
//
// public class AlgoAStar{
// 	//liste des noeuds restants à explorer
// 	ArrayList<Case> listeNoeudsRestant = new ArrayList<>();
// 	//Associe une case suivante vers sa précédente
// 	HashMap<Case,Case> listeCaseVersCase = new HashMap<>();
// 	//liste des cases à aller (deviendra les mouvements à faire)
// 	ArrayList<Case> path = new ArrayList<>();
//
// 	Robot robotInitial;
// 	State etatInitial;
// 	CaseJeton caseGagne;
//
// 	public AlgoAStar(State etatInitial){
// 		this.etatInitial = etatInitial;
// 		//récupère le robot qui doit aller à l'objectif
// 		this.robotInitial = etatInitial.getRobotAJouer();
// 		//récupère la case à aller
// 		this.caseGagne = etatInitial.getCaseJetonTire();
// 	}
//
// 	//Permet de donner la case sur laquelle le robot est positionné
// 	public Case getCaseRobot(Robot robot){
// 		Plateau plateau = etatInitial.getEtatPlateau();
// 		return plateau.getCasePlateau(robot.getPositionX(),robot.getPositionY());
// 	}
//
// 	//Permet de parcourir l'ensemble des noeuds (cases) possibles dans toute les directions possibles
// 	public void parcoursNoeud(Case noeudAParcourir){
// 		//Si on a plus de noeud, alors on arrête
// 		if(listeNoeudsRestant.size() == 0){
// 			return;
// 		}
// 		//Sortie anticipée si on a trouvé la case de sortie
// 		if(this.caseGagne == noeudAParcourir){
// 			System.out.println("Raccourci");
// 			System.out.println(""+listeNoeudsRestant.size());
// 			return;
// 		}
//
// 		//On met le robot sur le noeud auquel on affecte différentes directions
// 		robotInitial.setPosition(noeudAParcourir.getXCase(),noeudAParcourir.getYCase());
//
// 		//On affecte les différentes positions
// 		for(Deplacement direction : Deplacement.values()){
// 			etatInitial.move(direction);
// 			Case caseDuRobot = this.getCaseRobot(robotInitial);
// 			//On vérifie que le noeud n'a pas été déjà exploré
// 			if(!listeCaseVersCase.containsKey(caseDuRobot)){
// 				//Si il est nouveau, alors on l'ajoute à la liste des noeuds restants à explorer
// 				listeNoeudsRestant.add(caseDuRobot);
// 				//On affecte ce nouveau noeud au noeud d'ou il vient
// 				listeCaseVersCase.put(caseDuRobot, noeudAParcourir);
// 				System.out.println("Possibilite : " + caseDuRobot.getXCase() + " - " + caseDuRobot.getYCase());
// 			}
// 			//On remet le robot au noeud après un mouvement, pour pouvoir affecter le mouvement suivant
// 			robotInitial.setPosition(noeudAParcourir.getXCase(),noeudAParcourir.getYCase());
// 		}
//
// 		//après lui avoir affecté les 4 directions, on supprime le noeuds des noeuds à explorer puisqu'il à été exploré
// 		listeNoeudsRestant.remove(noeudAParcourir);
//
// 		//On passe à un noeud suivant tant qu'il existe des noeuds à explorer
// 		if(!listeNoeudsRestant.isEmpty()){
// 			parcoursNoeud(listeNoeudsRestant.get(0));
// 		}
// 	}
//
// 	//Algorithme de parcours en largeur (Breadth-first search)
// 	public void parcoursTotal(){
// 		//On ajoute la case de départ du robot comme noeud existant et à explorer
// 		Case initialeCase = getCaseRobot(robotInitial);
// 		// listeNoeuds.add(initialeCase);
// 		listeNoeudsRestant.add(initialeCase);
// 		listeCaseVersCase.put(initialeCase, null);
//
// 		//on parcours ce premier noeud
// 		parcoursNoeud(initialeCase);
//
// 		//après avoir parcouru tout les noeuds, on remet le robot à sa case de départ
// 		robotInitial.setPosition(initialeCase.getXCase(), initialeCase.getYCase());
//
// 		Case current = caseGagne;
//
// 		System.out.println("------------------------------------------------------");
// 		//Affichage du départ et de l'objectif
// 		System.out.println("Départ : " + initialeCase.getXCase() + " - " + initialeCase.getYCase());
// 		System.out.println("Objectif : " + current.getXCase() + " - " + current.getYCase());
//
// 		//On parcours le chemin inverse à partir de l'objectif
// 		while(current != initialeCase && current != null){
// 			//On l'ajoute la case la liste du chemin à faire
// 			path.add(current);
// 			current = listeCaseVersCase.get(current);
// 			//A la fin de la while, current ne peut valoir que la même valeur que la case avant initialeCase ou null
// 		}
//
// 		//Si current vaut la même valeur que la case avant initialeCase, on lui ajoute initialeCase, pusqu'elle est manquante
// 		if(current != null){
// 			path.add(initialeCase);
//
// 			//on inverse le tableau pour le remettre dans le bon ordre
// 			Collections.reverse(path);
//
// 			//on affiche le chemin à faire
// 			for(Case c : path){
// 				System.out.println(c.getXCase() + "-" + c.getYCase());
// 			}
//
// 		//Si current vaut null, c'est qu'il n'y a pas de chemin possible
// 		}else{
// 			System.out.println("Pas de solution sans bouger les robots");
// 		}
//
//
// 	}
// }
