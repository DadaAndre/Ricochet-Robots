package ricochet_robots.algorithme;

import ricochet_robots.jeu.*;
import ricochet_robots.jeu.pions.*;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public class AlgoAStar{
	//liste des noeuds restants à explorer
	PriorityQueue<State> frontier = new PriorityQueue<>();
	//Associe une case suivante vers sa précédente
	HashMap<State,State> came_from = new HashMap<>();
	//Associe une case à un coût
	HashMap<State,Integer> cost_so_far = new HashMap<>();
	//liste les états successeurs à aller
	ArrayList<State> path = new ArrayList<>();
	//Stocke le robot à utiliser pour la direction à aller
	ArrayList<Robot> listRobot = new ArrayList<>();
	//Stocke la direction à aller
	ArrayList<Deplacement> listDeplacement = new ArrayList<>();

	State etatInitial;
	State etatFinal;

	int coup = 0;

	public AlgoAStar(State etatInitial){
		this.etatInitial = etatInitial;
	}

	//Algorithme de parcours en largeur (Breadth-first search)
	public void parcoursTotal(State etatInitial){
		//On initialise l'ensemble des tableaux
		frontier.clear();
		came_from.clear();
		cost_so_far.clear();
		path.clear();
		listRobot.clear();
		listDeplacement.clear();

		etatInitial.cost = 0;
		//ajout de l'état initial
		frontier.add(etatInitial);
		came_from.put(etatInitial, null);
		cost_so_far.put(etatInitial, 0);

		//On parcours les noeuds restants tant qu'il en reste
		while(frontier.size() != 0){
			//On récupère le premier élément et on le supprime
			State noeudEnCours = frontier.poll();
			//Sortie anticipée si on a trouvé la case de sortie
			if(noeudEnCours.estEtatFinal()){
				System.out.println("fin");
				etatFinal = noeudEnCours;
				break;
			}
			//On affecte chaque déplacement à chaque robot
			for(Deplacement direction : Deplacement.values()){
				//Affectation d'une direction à chaque robot
				for(Robot robot : noeudEnCours.getListeRobot()){
					//Récupère le nouvel état (noeud)
					State etatSuivant = noeudEnCours.etatSuivant(direction, robot);

					int new_cost = cost_so_far.get(noeudEnCours) + 1;

					//Vérifie si le noeud n'a pas déja été exploré
					if(!cost_so_far.containsKey(etatSuivant) || new_cost < cost_so_far.get(etatSuivant)){
						cost_so_far.put(etatSuivant, new_cost);
						etatSuivant.cost = new_cost;
						//Si il est nouveau, alors on l'ajoute à la liste des noeuds restants à explorer
						frontier.add(etatSuivant);
						//On affecte ce nouveau noeud au noeud d'ou il vient
						came_from.put(etatSuivant, noeudEnCours);
					}
				}
			}
		}

		//On récupère l'état final
		State current = etatFinal;

		//On parcours le chemin inverse à partir de l'objectif
		while(current != etatInitial && current != null){
			//On l'ajoute l'état à la liste du chemin à faire
			path.add(current);
			current = came_from.get(current);
			System.out.println(""+current);
		}

		if(current != null){
			//On ajoute l'état initial qui est manquant
			path.add(etatInitial);

			//On récupère les différentes directions et les différents robots à bouger
			for(State c : path){
				listDeplacement.add(c.getLastDeplacement());
				listRobot.add(c.getLastRobot());
			}

			//on inverse la liste de deplacement et la liste des robots
			Collections.reverse(listRobot);
			Collections.reverse(listDeplacement);

			//Affichage des instruction à faire
			for(int i = 1; i < listRobot.size(); i++){
				System.out.println("robot " + listRobot.get(i).getCouleur() + " en "+ listDeplacement.get(i));
			}
		}
		else{
			System.out.println("Pas de solution");
		}
	}
}
