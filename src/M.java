public class M {

    private static int M_MASK= 0X40;

    public static boolean isM(){
       return HAL.isBit(M_MASK);
    }

}

