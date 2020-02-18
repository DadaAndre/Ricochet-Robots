package ricochet_robots;

public class Score{

    private int score;
    private int nbCoups;
    private int meilleurScore;

    public Score(){
        score = 0;
        nbCoups = 0;
        meilleurscore = 0;
    }

    public getScore(){
        return this.score;
    }

    public getMeilleurScore(){
        return this.meilleurScore;
    }

    public getNbCoups(){
        return this.nbCoups;
    }

    public setScore(int nouveauScore){
        this.score += nouveauScore;
    }

    public setMeilleurScore(int nouveauMeilleurScore){
        if(this.meilleurScore < nouveauMeilleurScore){
            this.meilleurScore = nouveauMeilleurScore;
        }
    }
}
