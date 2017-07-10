import isel.leic.UsbPort;
import isel.leic.usbio.OutputPort;
import isel.leic.utils.Time;
import sun.security.krb5.internal.crypto.Des;

public class SerialEmitter { //Envia tramas para os diferentes módulos Serial Receiver.
    public static enum Destination {SLCD,SSC}

    private static int SCLK = 0x01;                 //PIN 1 - LCD
    private static int SDXMASK = 0x02;              //PIN 2 - LCD
    private static int SLCD_SELECT_MASK = 0x04;     //PIN 4 - LCD
    public static int SSC_SELECT_MASK = 0x08;      //PIN 4 - SOUND
    private static int DESTINATION_MASKS[] = {SLCD_SELECT_MASK, SSC_SELECT_MASK};

    public static void main (String [] args) {
         init();
         send(Destination.SLCD,9,0x155);
    }

    // Inicia a classe
    public static void init() {
        HAL.init();
        HAL.setBits(SLCD_SELECT_MASK);
        HAL.setBits(SSC_SELECT_MASK);
    }
    // Envia uma trama para o SerialReceiver identificado por addr, com a dimensão de size e os bits de ‘data’.
    public static void send(Destination addr, int size, int data){

        HAL.clrBits(DESTINATION_MASKS[addr.ordinal()]);
        HAL.clrBits(SCLK);
        HAL.clrBits(SDXMASK);

        data = checkParity(size++,data);
        //envia bit a bit
        for(int i =0; i<size; i++){
            if((data&1)==1){
                HAL.setBits(SDXMASK);
            }
            else{
                HAL.clrBits(SDXMASK);
            }
            data>>=1;
            //dar impulso de SCLK sobe e desce
            //Time.sleep(5);  //Simul
            HAL.setBits(SCLK);
            HAL.clrBits(SCLK);
        }
        HAL.setBits(DESTINATION_MASKS[addr.ordinal()]);
    }

    public static int checkParity(int size,int data){
        int result = data;
        int count = 0;
        for(int i = 0; i<size ; i++) {
            if((data&1)== 1)
                count++;
            data>>=1;
        }
        if(count%2==1)
            result|=1<<size;
        return result;
    }


}
