import isel.leic.utils.Time;

public class KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’A’..’F’ ou NONE.
    public static final char NONE = 0;
    private static final char[] kbd = {'1','2','3','A',
                                       '4','5','6','B',
                                       '7','8','9','C',
                                       '*','0','#','D'};
    /*private static final char[] kbd = {'1','2','3',
                                       '4','5','6',
                                       '7','8','9',
                                       '*','0','#'};*/

    private static final int KVAL_MASK = 0x10,   //PIN 16 - Key Decode
                             KBD_MASK = 0x0F,    //PIN 16-19 - Register 74574
                             KACK_MASK = 0x10;   //PIN 3 - Key Decode

    public static void main (String[] args){
        init();
        System.out.println("Tecla:"+waitKey(2000));
    }

    // Inicia a classe
    public static void init() {
        HAL.init();
    }

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    public static char getKey() {

        if(HAL.isBit(KVAL_MASK)) {
            int i = HAL.readBits(KBD_MASK);

            HAL.setBits(KACK_MASK);

            if(!HAL.isBit(KVAL_MASK))
                HAL.clrBits(KACK_MASK);

            return kbd[i];
        } return NONE;

    }

    // Retorna quando a tecla for premida ou NONE após decorrido ‘timeout’ milisegundos.
    public static char waitKey(long timeout) {
        long init = Time.getTimeInMillis();
        do{
            char chr = getKey();
            if (chr != NONE){
                return chr;
            }
        }while (((Time.getTimeInMillis()-init) <= timeout));
        return NONE;
    }
}
