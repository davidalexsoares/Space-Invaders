import java.lang.reflect.Array;
import java.util.Arrays;

public class Scores {

    public static Scores[] pointAr = new Scores[20];
    public static int numberOfGames=0;
    public String name;
    private int points;

    public Scores(String name, int points){
        this.name = name;
        this.points = points;
        add(this);
    }

    public static void setNumberOfGames(int games) {
        numberOfGames = games;
    }

    public int getPoints() {
        return points;
    }

    public String getName(){
        return name;
    }

    public static void add (Scores score){
        if(pointAr[0]==null){
            pointAr[0] = score;
            return;
        }
        for (int i = 0; i < pointAr.length; i++){
            if(pointAr[i]!=null){
                if (pointAr[i].points < score.points){
                    System.arraycopy(pointAr, i, pointAr, i+1, pointAr.length-i-1);
                    pointAr[i]=score;
                }
            }
        }
        System.out.println("Name:"+score.getName()+",Points:"+score.getPoints());
    }

    public void addPoints(int points) {
        this.points += points + 1;
    }

    public String toString() {
        return points + ";" + name;
    }

    public static void fromString(String str){
        int i, points = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (i = 0; str.charAt(i)!=';'; i++) { //Builds the points
            points=points*10+(str.charAt(i)-'0');
        }
        for (int j = i+1; j < str.length(); j++) { //Builds the name
            stringBuilder.append(str.charAt(j));
        }

        String name = stringBuilder.toString();
        new Scores(name, points); //Creates a new Score
    }

    public static int getNumberofGames() {
        return numberOfGames;
    }

    public void resetPoints(){
        points = 0;
    }

    public static void resetNumberGames() {
        numberOfGames = 0;
    }

    public static void addGames() {
        numberOfGames ++;
    }
}
