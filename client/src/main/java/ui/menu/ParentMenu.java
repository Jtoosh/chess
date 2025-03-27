package ui.menu;

public class ParentMenu {
  public static boolean validateInput(String[] input, int intendedLen){
    if (input.length != intendedLen){
      System.out.println(ErrorStrings.INVALID_ARG_NUMBER);
      return false;
    } else { return true;}
  }
}
