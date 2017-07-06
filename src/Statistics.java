public class Statistics {

    private static Files file;

    public static void callWriteCount(int coins, int games){
        Files.writeCounters(coins,games);
    }

    public static void callWritePoints(Scores[] points){
        file.writePoints(points);
    }

    public static void callReadPoints(){
        Files.readPoints();
    }

    public static int callReadGames(){
        return Files.readGames();
    }

    public static int callReadCoins(){
        return Files.readCoins();
    }

}
