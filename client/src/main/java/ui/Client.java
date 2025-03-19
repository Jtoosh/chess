package ui;

public class Client {
    private final Repl menuDrawer = new Repl();
    public void run(){
        String STATE;
        STATE = "prelogin";

        System.out.println(MenuStrings.PRELOGINMENU);
        menuDrawer.repl(STATE);
    }

}
