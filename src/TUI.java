public class TUI {
    public static void main (String[] args){
        init();
    }

    private static final char UP='2', DOWN= '8',LEFT='4',RIGHT='6',ENTER='5',BACK='A';

    public static final int SHIP = 0x0, INVADER = 0x1, EURO = 0x2, IDLE = 0x5D;

    public static void init() {
        KBD.init();
        LCD.init();
    }

    public static char getKey() {
        return KBD.getKey();
    }

    public static char waitKey(int time){
        return KBD.waitKey(time);
    }

    private static char LETTER = 'A';

    public static String insertName(){ // writes Letter , like users name;
        boolean cont = true; int col = 0;
        StringBuilder str = new StringBuilder();

        while (cont){
            char key = getKey();
            LCD.cursor(0,5+col);
            LCD.write(LETTER);
            switch (key){
                case UP:
                    if(LETTER!='Z') LETTER++;
                    break;
                case DOWN:
                    if(LETTER!='A') LETTER--;
                    break;
                case LEFT:
                    if(col>0) {
                        col--;
                        str.charAt(col);
                    }
                    break;
                case RIGHT:
                    if(col<8) {
                        str.append(LETTER);
                        col++;
                        LETTER='A';
                    }
                    break;
                case ENTER:
                    str.append(LETTER);
                    cont = false;
                    break;
                case BACK:
                    break;
            }

            LCD.cursor(0,5);
            LCD.write(str.toString());
        }

        return str.toString();
    }

    public static void cursor(int line, int col){
        LCD.cursor(line,col);
    }

    public static void write(String str){
        LCD.write(str);
    }

    public static void clearLine(int line){ //Clears the line passed as the parameter
        cursor(line,0);
        write("                ");
    }

    public static void writeChar(char aux){
        LCD.write(aux);
    }

    public static void turnOff() {
        LCD.writeCMD(LCD.DISPLAY_OFF);
    }

    public static void writeDATA(int aux){
        LCD.writeDATA(aux);
    }

    /**
     * LCD.write(String)
     * @param line LCD line
     * @param col LCD col
     * @param str String
     */
    public static void stringView(int line, int col, String str){
        LCD.cursor(line, col);
        LCD.write(str);
    }

    /**
     * LCD.write(char)
     * @param line LCD line
     * @param col LCD col
     * @param ch char
     */
    public static void charView(int line,int col,char ch){
        LCD.cursor(line,col);
        LCD.write(ch);
    }

    /**
     * LCD.writeDATA()
     * @param line LCD line
     * @param col LCD col
     * @param data int
     */
    public static void dataView(int line, int col, int data) {
        LCD.cursor(line,col);
        LCD.writeDATA(data);
    }


}





