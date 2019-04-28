package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * This is the main class of the program.
 * 
 * @author Yingjie Shen
 */

public class Main extends Application {
  private PrimaryGUI primaryGUI;

  @Override
  public void start(Stage primaryStage) {
    try {
      this.primaryGUI = new PrimaryGUI(primaryStage);
      primaryStage.setScene(primaryGUI.getScene());
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
