package ricochet_robots;

public class Score{

    private int score;
    private int nbCoups;
    private int meilleurScore;

    public Score(){
        score = 0;
        nbCoups = 0;
        meilleurScore = 0;
    }

    public int getScore(){
        return this.score;
    }

    public int getMeilleurScore(){
        return this.meilleurScore;
    }

    public int getNbCoups(){
        return this.nbCoups;
    }

    public void setScore(int nouveauScore){
        this.score += nouveauScore;
    }

    public void setMeilleurScore(int nouveauMeilleurScore){
        if(this.meilleurScore < nouveauMeilleurScore){
            this.meilleurScore = nouveauMeilleurScore;
        }
    }
}
