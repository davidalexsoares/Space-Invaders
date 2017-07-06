public class Coins {

    public static int coinsTotal = 0;

    private static final int ACCEPT_MASK = 0x20,  //output
                             COIN_MASK = 0x20;      //input

    public static void init(){
        HAL.clrBits(ACCEPT_MASK);
    }

    public static int getCoinsTotal() {
        return coinsTotal;
    }

    public static void setCoinsTotal(int coins){
        coinsTotal = coins;
    }

    public static void resetCoins(){
        coinsTotal=0;
    }

    public static boolean decCoins(){
        if(coinsTotal>0){
            coinsTotal--;
            return true;
        }else {
            return false;
        }
    }

    public static void acceptCoin(){
        if(HAL.isBit(COIN_MASK)){
            coinsTotal+=2;
            HAL.setBits(ACCEPT_MASK);

            while (HAL.isBit(COIN_MASK)){} //Wait until the user releases the button
            HAL.clrBits(ACCEPT_MASK);
        }
    }



}
