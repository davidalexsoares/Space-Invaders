public class SoundGenerator {

    private static final int DATA_SIZE = 0x4;

    public static void main(String [] args) {
        init();
        play(3);
        stop();
        setVolume(1);
    }

    // Inicia a classe, estabelecendo os valores iniciais.
    public static void init() {
        SerialEmitter.init();
        stop();
    }
    // Envia comando para reproduzir um som, com a identificação deste
    public static void play(int sound) {
       SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,(0x2|sound<<2));
       SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,sound);
    }
    // Envia comando para parar o som
    public static void stop(){
        SerialEmitter.send(SerialEmitter.Destination.SSC,DATA_SIZE,0x0);
    }
    // Envia comando para definir o volume do som
    public static void setVolume(int volume) {
        SerialEmitter.send(SerialEmitter.Destination.SSC,4,volume);
     ;

    }
}


