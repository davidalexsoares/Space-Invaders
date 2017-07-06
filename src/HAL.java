import isel.leic.UsbPort;

public class HAL { // Virtualiza o acesso ao sistema UsbPort
    private static int portOut = 0; //Guarda o UsbPort.out();
    // Inicia a classe
    public static void init() {
        UsbPort.out(~portOut);
    }

    // Retorna true se o bit tiver o valor lógico ‘1’
    public static boolean isBit(int mask) {
        return (~UsbPort.in() & mask) !=0;
    }

    // Retorna os valores dos bits representados por mask presentes no UsbPort
    public static int readBits(int mask) {
        return ~UsbPort.in() & mask;
    }

    // Escreve nos bits representados por mask o valor de value
    public static void writeBits(int mask, int value) {
        portOut = (mask&value) | (portOut & ~mask);
        UsbPort.out(~portOut);
    }

    // Coloca os bits representados por mask no valor lógico ‘1’
    public static void setBits(int mask) {
        portOut |= mask;
        UsbPort.out(~portOut);
    }

    // Coloca os bits representados por mask no valor lógico ‘0’
    public static void clrBits(int mask) {
        portOut &= ~mask;
        UsbPort.out(~portOut);
    }
}
