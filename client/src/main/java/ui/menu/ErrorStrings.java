package ui.menu;

import ui.EscapeSequences;

public class ErrorStrings {

  public static final String INVALID_ARG_NUMBER = EscapeSequences.RESET_TEXT_COLOR +
          "Whoops, your command didn't have the right number of arguments, please try again. Use "
          + EscapeSequences.SET_TEXT_COLOR_BLUE + "help" + EscapeSequences.RESET_TEXT_COLOR +
          " if needed.";

  public static final String IO_EXCEPTION = EscapeSequences.SET_TEXT_COLOR_RED + "Sorry, we experienced an error connecting to the server.";
  //Register Strings
  public static final String BAD_REGISTER_REQUEST = EscapeSequences.RESET_TEXT_COLOR + "Whoops, that didn't quite work. " +
          "Please format your registration like" +
          " this:\n " + EscapeSequences.SET_TEXT_COLOR_BLUE + "register <Username> <Password> <Email>" +
          EscapeSequences.RESET_TEXT_COLOR;

  //Login Strings
  public static final String BAD_LOGIN_REQUEST = EscapeSequences.RESET_TEXT_COLOR + "Whoops, that didn't quite work." +
          " Please format your login like" +
          " this:\n " + EscapeSequences.SET_TEXT_COLOR_BLUE + "login <Username> <Password>" +
          EscapeSequences.RESET_TEXT_COLOR;
  public static final String WRONG_PASSWORD = EscapeSequences.RESET_TEXT_COLOR + "Incorrect password";

  //Logout Strings
  public static final String LOGOUT_UNAUTH = EscapeSequences.RESET_TEXT_COLOR + "Unauthorized";

  //Join Strings
  public static final String JOIN_BAD_REQUEST = EscapeSequences.RESET_TEXT_COLOR + "Whoops, that didn't quite work. " +
          "Please format your join request like" +
          " this:\n " + EscapeSequences.SET_TEXT_COLOR_BLUE + "join <id> <WHITE|BLACK>" +
  EscapeSequences.RESET_TEXT_COLOR + ". Or, make sure the gameID you entered in is correct.";
  public static final String JOIN_TEAM_COLOR_TAKEN = EscapeSequences.RESET_TEXT_COLOR + "Sorry, but that team color has " +
          "already been claimed. Pick the other one.";

}
