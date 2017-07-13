import isel.leic.utils.Time;

public class App {
    private static int[] invaders = new int[14];
    private static final int SHIP = 0x0,
                             INVADER = 0x1,
                             EURO = 0x2,
                             IDLE = 0x5D;
    private static int pointsAux = 0;
    private static final int INVADERS_DELAY = 1000;
    private static final int VIEW_DELAY = 2000;

    public static void main (String[] args){
        init();
        run();
        terminate();
    }

    private static void init(){
        TUI.init();
        SoundGenerator.init();
        Coins.init();
        initCounts();
        Coins.setCoinsTotal(Statistics.callReadCoins());
        Scores.setNumberOfGames(Statistics.callReadGames());
        Statistics.callReadPoints();
    }

    private static void run(){
        do{
            if(!idle())break;
            play();
            gameOver();
        }while (true);
    }

    private static int idx = 0;
    private static void idleView(boolean trade){ //Switch between the idleScreen and a score
        TUI.cursor(0,0);
        TUI.write(" Space Invaders ");

        TUI.cursor(1,0);
        if(trade){
            idleScreen(Coins.coinsTotal);
        }else{
            if(Scores.pointAr[0]==null){
                idleScreen(Coins.coinsTotal);
            }else {
                Scores scores = Scores.pointAr[idx];

                scoresView(scores.getName(),scores.getPoints());

                if(Scores.pointAr[idx+1]!=null) idx++;
                else idx = 0;
            }
        }
    }

    private static void scoresView(String name, int score){
        TUI.clearLine(1);

        TUI.cursor(1,0);

        if(idx<10){
            TUI.write("0"+(idx+1)+"-");
        }else{
            TUI.write(idx+"-");
        }
        TUI.write(name);

        TUI.cursor(1,16-getDigits(score));
        TUI.write(score+"");
    }

    private static int getDigits(int num) {
        int dig = 0;
        do {
            dig++;
            num/=10;
        } while( num > 0 );

        return dig;
    }

    private static void idleScreen(int coins){
        TUI.cursor(1,0);
        TUI.write("GAME ");
        TUI.writeDATA(SHIP);TUI.write("  ");
        TUI.writeDATA(INVADER);TUI.write(" ");TUI.writeDATA(INVADER);
        TUI.write("   ");TUI.writeDATA(EURO);TUI.write(toView((char)(coins+'0')+""));
    }

    private static boolean idle() {
        long timeStart = Time.getTimeInMillis();
        boolean trade = true;
        do{
            Coins.acceptCoin();
            switch (keyOperations(TUI.getKey())){
                case 0: //Idle
                    break;
                case 1: //Enter Play Mode
                    if(Coins.decCoins())return true;
                    else break;
                case 2: //Exit Game
                    return false;
            }

            if((Time.getTimeInMillis()-timeStart)>VIEW_DELAY){
                idleView(trade); trade = !trade;    //Switches between the views
                timeStart = Time.getTimeInMillis();
            }

        }while (true);

    }

    private static int keyOperations(char key) {
        if(M.isM()){ //Is in M mode
            if(!mKey(key)) return 2;
        }
        else{
            if(!idleKey(key)) return 1;
        }
        return 0;
    }

    private static boolean idleKey(char key){
        return !(key == '*') ;  //Exit idle and enter play //startGame();
    }

    private static boolean mKey(char key) { //return true = proceed, return false = close game
        while (M.isM()){
            mView();

            switch (key){
                case '#':
                    shutDView();
                    while (true){
                        key = TUI.getKey();
                        if(key=='5'){
                            return false;
                        }else if(key >= '0' && key <='9' ||
                                key =='*' || key == '#') break;
                    }
                    break;
                case '*':
                    viewCoinsAndGames();
                    if (TUI.waitKey(4000)=='#'){
                        initCounts();
                    }
                    break;
                case '0': case '1': case '2':case '3':case '4':case '5':
                    case '6': case '7':case '8':case '9':
                        startGameM();
                        break;

            }
            key = TUI.getKey();
        }

        return true;
    }

    private static void mView(){
        TUI.stringView(0,0,toView("On Maintenance"));
        TUI.stringView(1,0,"*-Count  #-ShutD");
    }

    private static void shutDView(){
        TUI.stringView(0,0,"    Shutdown    ");
        TUI.stringView(1,0,"5-Yes   other-No");
    }

    private static void startGameM() { //**
        play();
        overView();
    }

    private static void initCounts() {
        Coins.resetCoins();
        Scores.resetNumberGames();
    }

    private static void viewCoinsAndGames() {
        TUI.cursor(0,0);
        TUI.write(toView("Coins:"+Coins.getCoinsTotal()));

        TUI.cursor(1,0);
        TUI.write(toView("Games:"+Scores.getNumberOfGames()));
    }

    private static void initPlay(){
        initInvaders();
        addInvader();
        play = true;
    }

    private static boolean play = true;
    private static char prevKey;
    private static void play(){
        long timeStart = Time.getTimeInMillis();
        initPlay();

        char currKey;

        do {
            playView();             // View of the game

            currKey = TUI.getKey(); // Read a key

            if(currKey=='*'){
                int spaceCheck = killInvader(prevKey);
                if(spaceCheck!=10){                  //Killed the invader
                    pointsAux+=spaceCheck+1;         //Adds the corresponding score
                    currKey=0;                       //Makes the user only be able to shoot once at a time
                }
            }

            if((Time.getTimeInMillis()-timeStart)>INVADERS_DELAY){ //Adds a invader every 1 sec
                if (!addInvader()) play = false;
                timeStart = Time.getTimeInMillis();
            }

            prevKey = currKey;

        } while (play);  //Game Cycle
    }

    private static void playView(){
        if(prevKey=='*' || prevKey == 0){
            TUI.dataView(0,0,IDLE);
        }else {
            TUI.charView(0,0,prevKey);
        }

        TUI.dataView(0,1,SHIP);
        TUI.stringView(0,2,invadersToString());

        TUI.stringView(1,0,toView("Score:"+pointsAux));
    }

    private static String toView(String str){ //Adds the remaining spaces
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = str.length(); i < 16; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private static void gameOver() {
        overView();

        if(pointsAux != 0) {
            new Scores(name(), pointsAux);
            pointsAux = 0;
        }
    }

    private static void overView(){
        TUI.stringView(0, 0, "*** Game Over **");
        TUI.stringView(1, 0, toView("Score:"+pointsAux));

        Scores.addGames();

        SoundGenerator.setVolume(1);
        SoundGenerator.setSound(1);
        SoundGenerator.play();

        Time.sleep(2500);
    }

    private static String name(){
        TUI.clearLine(0);
        TUI.stringView(0,0,"NAME:");
        return TUI.insertName();
    }

    private static void initInvaders(){
        for (int i = 0; i < invaders.length; i++) {
            invaders[i] = 10;
        }
    }

    private static boolean addInvader(){ //int 10 = no invader
        if(invaders[0]!=10)return false;
        System.arraycopy(invaders,1, invaders, 0, invaders.length - 1);
        invaders[13]= (int) (Math.random()*9);
        return true;
    }

    private static int killInvader(char key){
        for (int i = 0; i < invaders.length ; i++) {
            if((key-'0')== invaders[i]){
                invaders[i]=10;
                return key-'0';
            }
        }
        return 10;
    }

    private static String invadersToString(){
        StringBuilder str = new StringBuilder();

        for (int invader : invaders) {
            if(invader==10){
                str.append(" ");
            }
            else str.append(invader);
        }

        return str.toString();
    }

    private static void terminate(){
        TUI.stringView(0,2,toView("Shutting"));
        TUI.clearLine(1);
        TUI.stringView(1,8,toView("Down..."));
        Statistics.callWritePoints(Scores.pointAr);
        Statistics.callWriteCount(Coins.getCoinsTotal(),Scores.getNumberOfGames());
        TUI.turnOff();
    }

}
