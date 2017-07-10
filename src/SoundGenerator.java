import isel.leic.utils.Time;

public class SoundGenerator {

    private static final int CMD_STOP = 0X0, CMD_PLAY = 0X1, CMD_SOUND = 0X2, CMD_VOLUME = 0X3,
            DATA_SIZE = 0x4;

    public static void main(String [] args) {
        init();
        setVolume(1);
        setSound(1); //Quando queres que toque tens de fazer play (na app)
        play();
    }


    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init() {
        HAL.init();
        HAL.setBits(SerialEmitter.SSC_SELECT_MASK);
    }

    // Envia comando para iniciar o som
    public static void play(){
        SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,CMD_PLAY);
        Time.sleep(2000);
        stop();
    }

    // Envia comando para parar o som
    public static void stop(){
        SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,CMD_STOP);
    }

    // Envia comando definir o som
    public static void setSound(int sound) {
        SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,(CMD_SOUND|sound<<2));
    }

    // Envia comando para definir o volume do som
    public static void setVolume(int volume) {
        SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,(CMD_VOLUME | volume<<2));

    }
}
