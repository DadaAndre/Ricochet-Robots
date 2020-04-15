package ricochet_robots.algorithme;

import ricochet_robots.jeu.plateau.*;
import ricochet_robots.jeu.pions.*;
import ricochet_robots.jeu.*;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;

public class Heuristique{

	Plateau plateauJeu;
	Case caseGagnante;
	State state;
	ArrayList<Case> listeCase = new ArrayList<>();
	Robot robotBlanc;

	public Heuristique(State state){
		this.state = state;
		this.plateauJeu = state.getEtatPlateau();
		creeHeuristique();
		addHeuristiquePlateau();
		affichageHeuristique();
	 	robotBlanc = null;
	}

	public void addHeuristiquePlateau(){
		Case caseGagnante = state.getCaseJetonTire();
		caseGagnante.addHeuristique(0);
		listeCase.add(caseGagnante);
		robotBlanc = new Robot(state, "blanc", 0, 0);
		//int cout = caseAcuelle.getHeuristique();
		while(listeCase.size() != 0){
			robotBlanc.setPosition(listeCase.get(0).getXCase(), listeCase.get(0).getYCase());
			int coup = listeCase.get(0).getHeuristique();
			for(Deplacement direction : Deplacement.values()){
				parcoursMapHeuristique(direction, robotBlanc, coup+1);
				robotBlanc.setPosition(listeCase.get(0).getXCase(), listeCase.get(0).getYCase());
			}
			listeCase.remove(0);

		}
	}

	public void affichageHeuristique(){
		for(int y = 0; y < plateauJeu.getTaillePlateau(); y++){
			for(int x = 0; x < plateauJeu.getTaillePlateau(); x++){
				if(plateauJeu.getCasePlateau(x,y).getHeuristique() != -1){
					System.out.print("| " + plateauJeu.getCasePlateau(x,y).getHeuristique());
				}else{
					System.out.print("|" + plateauJeu.getCasePlateau(x,y).getHeuristique());

				}
			}
			System.out.println("");
		}
	}

	public void creeHeuristique(){
		for(int y = 0; y < plateauJeu.getTaillePlateau(); y++){
			for(int x = 0; x < plateauJeu.getTaillePlateau(); x++){
				plateauJeu.getCasePlateau(x,y).addHeuristique(-1);
			}
		}
	}

	//Déplacement du robot
	public void parcoursMapHeuristique(Deplacement direction, Robot robot, int heuristique){
		//this.robot = robot;
		//Vérification de la direction choisie
		if(direction == Deplacement.UP){
			//Tant que le robot ne rencontre pas un mur en haut, ou un autre robot, il se dirige vers le haut
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValHaut() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY() - 1).getValBas() != 1){
				robot.translatePositionY(-1);
				Case caseAcuelle = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
				if(caseAcuelle.getHeuristique() == -1){
					caseAcuelle.addHeuristique(heuristique);
					listeCase.add(caseAcuelle);
				}
			}
		}else if(direction == Deplacement.DOWN){
			//Tant que le robot ne rencontre pas un mur en bas, ou un autre robot, il se dirige vers le bas
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValBas() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY() +1).getValHaut() != 1){
				robot.translatePositionY(1);
				Case caseAcuelle = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
				if(caseAcuelle.getHeuristique() == -1){
					caseAcuelle.addHeuristique(heuristique);
					listeCase.add(caseAcuelle);
				}
			}
		}else if(direction == Deplacement.LEFT){
			//Tant que le robot ne rencontre pas un mur à gauche,  ou un autre robot, il se dirige vers la gauche
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValGauche() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX() -1, robot.getPositionY()).getValDroit() != 1){
				robot.translatePositionX(-1);
				Case caseAcuelle = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
				if(caseAcuelle.getHeuristique() == -1){
					caseAcuelle.addHeuristique(heuristique);
					listeCase.add(caseAcuelle);
				}

			}
		}else if(direction == Deplacement.RIGHT){
			//Tant que le robot ne rencontre pas un mur à droite,  ou un autre robot, il se dirige vers la droite
			while(this.plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY()).getValDroit() != 1 && this.plateauJeu.getCasePlateau(robot.getPositionX() + 1, robot.getPositionY()).getValGauche() != 1){
				robot.translatePositionX(1);
				Case caseAcuelle = plateauJeu.getCasePlateau(robot.getPositionX(), robot.getPositionY());
				if(caseAcuelle.getHeuristique() == -1){
					caseAcuelle.addHeuristique(heuristique);
					listeCase.add(caseAcuelle);
				}
			}
		}
	}
}
