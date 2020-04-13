package ricochet_robots.algorithme;

import ricochet_robots.jeu.*;
import ricochet_robots.jeu.pions.*;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public class AlgoBFS{
	//liste des noeuds restants à explorer
	ArrayList<State> listeNoeudsRestant = new ArrayList<>();
	//Associe une case suivante vers sa précédente
	HashMap<State,State> listeStateVersState = new HashMap<>();
	//liste les états successeurs à aller
	ArrayList<State> path = new ArrayList<>();
	//Stocke le robot à utiliser pour la direction à aller
	ArrayList<Robot> listRobot = new ArrayList<>();
	//Stocke la direction à aller
	ArrayList<Deplacement> listDeplacement = new ArrayList<>();

	State etatInitial;
	State etatFinal;

	int coup = 0;

	public AlgoBFS(State etatInitial){
		this.etatInitial = etatInitial;
	}

	//Permet de parcourir l'ensemble des noeuds possibles dans toute les directions possibles
	public void parcoursNoeud(State noeudEnCours){
		//Si on a plus de noeud à explorer alors on arrête
		if(listeNoeudsRestant.size() == 0){
			return;
		}
		//Sortie anticipée si on a trouvé la case de sortie
		if(noeudEnCours.estEtatFinal()){
			System.out.println("fin");
			etatFinal = noeudEnCours;
			return;
		}

		for(Deplacement direction : Deplacement.values()){
			//Affectation d'une direction à chaque robot
			for(Robot robot : noeudEnCours.getListeRobot()){
				//Récupère le nouvel état (noeud)
				State etatSuivant = noeudEnCours.etatSuivant(direction, robot);
				//Vérifie si le noeud n'a pas déja été exploré
				if(!listeStateVersState.containsKey(etatSuivant)){
					//Si il est nouveau, alors on l'ajoute à la liste des noeuds restants à explorer
					listeNoeudsRestant.add(etatSuivant);
					//On affecte ce nouveau noeud au noeud d'ou il vient
					listeStateVersState.put(etatSuivant, noeudEnCours);
				}
			}
		}

		//après avoir explorer le noeud, on supprime le noeuds des noeuds à explorer
		listeNoeudsRestant.remove(noeudEnCours);

		//On passe à un noeud suivant tant qu'il existe des noeuds à explorer
		if(!listeNoeudsRestant.isEmpty()){
			parcoursNoeud(listeNoeudsRestant.get(0));
		}
	}

	//Algorithme de parcours en largeur (Breadth-first search)
	public void parcoursTotal(){
		//On ajoute l'état initial comme noeud existant et à explorer
		listeNoeudsRestant.add(etatInitial);
		listeStateVersState.put(etatInitial, null);

		//on parcours ce premier noeud
		parcoursNoeud(etatInitial);

		//On récupère l'état final
		State current = etatFinal;

		//On parcours le chemin inverse à partir de l'objectif
		while(current != etatInitial && current != null){
			//On l'ajoute l'état à la liste du chemin à faire
			path.add(current);
			current = listeStateVersState.get(current);
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
