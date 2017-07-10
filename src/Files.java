import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static sun.awt.AppContext.getAppContext;

// Tambem tem como objectivo escrever num ficheiro o numero de jogos jogados e moedas tmbem
public class Files {

    public static void readPoints() {
        try {
            FileReader in = new FileReader("Points.txt");
            Scanner input = new Scanner(in);
            String line;
            while (input.hasNext()){
                line = input.nextLine();
                Scores.fromString(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int idx = 0;
    private static String counters;
    public static int readCoins() {
        int coins = 0;
        try {
            FileReader in = new FileReader("Counters.txt");
            Scanner input = new Scanner(in);
            if(input.hasNextLine()){
                counters = input.nextLine();
                for (idx = 0; counters.charAt(idx)!=';'; idx++) {
                    coins += (counters.charAt(idx)-'0');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coins;
    }

    public static int readGames() {
        int games = 0;

        if(counters!=null){
            for (int i = idx+1; i < counters.length(); i++) {
                games += (counters.charAt(i)-'0');
            }
        }

        return games;
    }

    // escreve no ficheiro de texto Points.txt o numero de pontos
    public static void writePoints(Scores[] points) {
        try {
            FileWriter out = new FileWriter("Points.txt");
            for (Scores score : points) {
                if(score==null) break;
                out.write(score.toString()+"\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCounters(int coins,int games){
        try{
            FileWriter out = new FileWriter("Counters.txt");
            out.write( coins+";"+games);
            out.close();
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }

}
