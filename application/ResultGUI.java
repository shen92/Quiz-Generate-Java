package application;

//Author: Dongxia Wu

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class ResultGUI extends Application {
  private int score = 100;
  private int totalNumOfQuestion = 20;
  private int numOfCorrectAnswer = 20;

  @Override
  public void start(Stage primaryStage) {
  try {
    //Title: Results
    Text resultLabel = new Text("Quiz Results");
    resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
    HBox titleHBox = new HBox();
    titleHBox.setPrefHeight(100);
    titleHBox.setPrefWidth(1200);
    titleHBox.setAlignment(Pos.CENTER);
    titleHBox.getChildren().add(resultLabel);
    
    //Number of Correct Answers 
    Text CorrectNumText = new Text("Number of Correct Answers: "+ numOfCorrectAnswer);
    CorrectNumText.setFont(Font.font("Arial", 30));
    VBox CorrectNumVBox = new VBox();
    CorrectNumVBox.setPrefWidth(1200);
    CorrectNumVBox.setPrefHeight(100);
    CorrectNumVBox.setPadding(new Insets(100.0, 30.0, 40.0, 150.0));
    CorrectNumVBox.getChildren().add(CorrectNumText);
    
    //Total Number of Questions Answered 
    Text TotalNumText = new Text("Total Number of Questions Answered: "+ totalNumOfQuestion);
    TotalNumText.setFont(Font.font("Arial", 30));
    VBox TotalNumVBox = new VBox();
    TotalNumVBox.setPrefWidth(1200);
    TotalNumVBox.setPrefHeight(100);
    TotalNumVBox.setPadding(new Insets(100.0, 30.0, 40.0, 150.0));
    TotalNumVBox.getChildren().add(TotalNumText);
    
    //Score
    Text ScoreText = new Text("Score: "+ score);
    ScoreText.setFont(Font.font("Arial", 30));
    VBox ScoreVBox = new VBox();
    ScoreVBox.setPrefWidth(1200);
    ScoreVBox.setPrefHeight(100);
    ScoreVBox.setPadding(new Insets(100.0, 30.0, 40.0, 150.0));
    ScoreVBox.getChildren().add(ScoreText);
    
  //restart and finish button
    Button toPreviousButton = new Button("Restart Quiz");
    Button toNextButton = new Button("Finish");
    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(25, 0, 25, 300));
    buttonHBox.setPrefWidth(1200);
    buttonHBox.setPrefHeight(200);
    buttonHBox.setSpacing(300);
    buttonHBox.getChildren().add(toPreviousButton);
    buttonHBox.getChildren().add(toNextButton);
    
    VBox root = new VBox();
    root.getChildren().add(titleHBox);
    root.getChildren().add(CorrectNumVBox);
    root.getChildren().add(TotalNumVBox);
    root.getChildren().add(ScoreVBox);
    root.getChildren().add(buttonHBox);
    
    Scene scene = new Scene(root, 1200, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("result");
    primaryStage.show();
    
  }catch(Exception e) {
    e.printStackTrace();
  }
  }
  public static void main(String[] args) {
    launch(args);
  }
}
  
  




