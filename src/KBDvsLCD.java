public class KBDvsLCD {
    public static void main (String [] args) {
        KBD.init(); LCD.init();
        LCD.cursor(0,0);
        LCD.write("LCD: Check ");
        LCD.writeDATA(LCD.SHIP);LCD.write(" ");LCD.writeDATA(LCD.INVADER);
        LCD.write(" ");LCD.writeDATA(LCD.EURO);

        char key = ' ';

        while (key!='#'){
            LCD.cursor(1,0);
            LCD.write("KBD: Key = ");
            key = KBD.waitKey(4000);
            System.out.println("KBD : Key = "+key);
            LCD.write(key+"    ");
        }

        /*
        LCD.cursor(0,0);
        LCD.write("LCD: Check      ");
        LCD.cursor(1,0);
        LCD.write("KBD: Check      ");
        */

        LCD.cursor(0,0);
        LCD.write("      YEAH      ");
        LCD.cursor(1,0);
        LCD.write("BOYYYYYYYYYYYYY!");
    }
}
