package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class of the program.
 * 
 * @author Yingjie Shen
 */
public class Main extends Application {

  PrimaryGUI startScene;

  @Override
  public void start(Stage primaryStage) {
    try {
      startScene = new PrimaryGUI(primaryStage);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
