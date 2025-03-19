package ui;

public class PostloginMenu {

    public static String eval(String input){
        String[] parts = input.split(" ");
        switch(parts[0]){
            case "logout":
                //call logout endpoint
                return "prelogin";
            case "create":
                String gameName = parts[1];
                return "postlogin";
            case "list":
                //print list
                return "postlogin";
            default:
                return "postlogin";
        }
    }
}
