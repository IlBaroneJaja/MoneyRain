package be.ecam.moneyrain;

/**
 * Created by Alessandro on 02/05/2016.
 */
public class Score implements Comparable<Score> {

    private String scoreDate;
    public int scoreNum;

    public Score(String date, int num){
        this.scoreDate = date;
        this.scoreNum = num;
    }

    @Override
    public int compareTo(Score sc) {
        // La fonction renvoie 0 si les 2 scores à comparer sont identiques
        // La fonction renvoie 1 si le score à tester est plus grand que celui en mémoire
        // La fonction renvoie -1 si le score à tester est plus petite que celui en mémoire
        return sc.scoreNum>scoreNum? 1 : sc.scoreNum<scoreNum? -1 : 0;

    }

    public String getScoreText()
    {
        return scoreDate+" - "+scoreNum;
    }
}
