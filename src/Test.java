import isel.leic.UsbPort;
import isel.leic.utils.*;

public class Test {
    public static void main (String[] args){
        int x=1,time=500;
        boolean dir = true;

        do {
            for (int i = 0; i < 7; i++) {
                UsbPort.out(x);
                Time.sleep(time);

                if (dir)x=x<<1;
                else x=x>>1;
            }

            dir=!dir;
        } while (true);

    }
}
