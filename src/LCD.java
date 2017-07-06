import isel.leic.utils.Time;

public class LCD { // Escreve no LCD usando a interface a 8 bits.

    public static void main (String [] args) {
        SerialEmitter.init();
        init();
        spaceInvadersWrite();
    }

    private static void spaceInvadersWrite(){
        cursor(0,0);
        write(" Space Invaders ");
        cursor(1,0);
        writeDATA(SHIP);write("  ");
        writeDATA(INVADER);write(" ");writeDATA(INVADER);
        write("        ");writeDATA(EURO);write("2");
    }

    private static final int LINES = 2, COLS = 16, // Dimensão do display.
            DATA_SIZE = 9,
            FUNCTION_SET = 0X30, FUNCTION_SET_FINAL = 0X38,
            DISPLAY_CLEAR = 0X1, DISPLAY_OFF = 0X8, DISPLAY_ON_OFF_CONTROL = 0XF,
            ENTRY_MODE_SET = 0X6;

    public static final int SHIP = 0x0, INVADER = 0x1, EURO = 0x2, IDLE = 0x5D;
    public static final int[] SHIP_CODE = {0x40,0x1E,0x18,0x1C,0x1F,0x1C,0x18,0x1E,0x00},
                               INVADOR_CODE = {0x48,0x1F,0x1F,0x15,0x1F,0x1F,0x11,0x11,0x00},
                                EURO_CODE = {0x50,0x07,0x08,0x1e,0x08,0x1e,0x08,0x07,0x00};

    // Escreve um comando/dados no LCD
    private static void writeByte(boolean rs, int data) {
        data <<= 1;
        if (rs)
            data |= 0x1;
        SerialEmitter.send(SerialEmitter.Destination.SLCD,DATA_SIZE,data);

    }
    // Escreve um comando no LCD
    public static void writeCMD(int data) {writeByte(false,data);}

    // Escreve um dado no LCD
    public static void writeDATA(int data) {writeByte(true,data);}

    // Envia a sequência de iniciação para comunicação a 8 bits.
    public static void init() {
        writeCMD(FUNCTION_SET);
        Time.sleep(5);
        writeCMD(FUNCTION_SET);
        Time.sleep(1);
        writeCMD(FUNCTION_SET);
        writeCMD(FUNCTION_SET_FINAL);
        writeCMD(DISPLAY_OFF);
        writeCMD(DISPLAY_CLEAR);
        writeCMD(ENTRY_MODE_SET);

        specialCharsInit();

        cursor(0,0);
        writeCMD(DISPLAY_ON_OFF_CONTROL);
    }

    private static void specialCharsInit() {
        createChar(SHIP_CODE);
        createChar(INVADOR_CODE);
        createChar(EURO_CODE);
    }

    // Escreve um caráter na posição corrente.
    public static void write(char c) {
        writeDATA(c);
    }

    // Escreve uma string na posição corrente.
    public static void write(String txt) {
        for (int i = 0; i < txt.length(); i++) {
            write(txt.charAt(i));
        }
    }

    // Envia comando para posicionar cursor (‘lin’:0..LINES-1 , ‘col’:0..COLS-1)
    public static void cursor(int lin, int col) {
        writeCMD(lin== 0 ? 0x80|col : 0xC0|col);
    }
    
    public static void createChar(int[] draw){
        for (int i = 0; i < draw.length ; i++) {
            if(i== 0) writeCMD(draw[0]);
            else writeDATA(draw[i]);
        }
    }

}