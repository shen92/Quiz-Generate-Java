package application;

//Author: ???

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


public class QuizGUI extends Application {
  private static int indexOfQuestion = 0;
  private static int totalNumOfQuestion = 0;

  @Override
  public void start(Stage primaryStage) {



    try {
      //Title: Question 01/20
      Text questionLabel = new Text("Question " + indexOfQuestion + "/" + totalNumOfQuestion);
      questionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
      HBox titleHBox = new HBox();
      titleHBox.setPrefHeight(100);
      titleHBox.setPrefWidth(1200);
      titleHBox.setAlignment(Pos.CENTER);
      titleHBox.getChildren().add(questionLabel);
      
      //get question from the question list.
      Text questionsText = new Text("" + "QuestionClass.getQuestion()");
      questionsText.setFont(Font.font("Arial", 20));
      VBox questionVBox = new VBox();
      questionVBox.setPrefWidth(1200);
      questionVBox.setPrefHeight(100);
      questionVBox.setPadding(new Insets(25.0, 30.0, 40.0, 150.0));
      questionVBox.getChildren().add(questionsText);

    
      //choises: use for loop to get the choices
      VBox VBoxAnswers = new VBox();
      VBoxAnswers.setPadding(new Insets(25.0, 40.0, 40.0, 150.0));
      VBoxAnswers.setSpacing(40);
      VBoxAnswers.setPrefWidth(1200);
      VBoxAnswers.setPrefHeight(600);
      ToggleGroup group = new ToggleGroup();
      //total number of choices
      int size = 3;
      for (int i = 0; i < size; i++) {
        RadioButton button = new RadioButton("QuestionClass.getAnswers");
        button.setToggleGroup(group);
        button.setSelected(true);
        VBoxAnswers.getChildren().add(button);
      }

     
      //pre and next button
      Button toPreviousButton = new Button("Previous");
      Button toNextButton = new Button("Next");
      HBox buttonHBox = new HBox();
      buttonHBox.setPadding(new Insets(25, 0, 25, 300));
      buttonHBox.setPrefWidth(1200);
      buttonHBox.setPrefHeight(100);
      buttonHBox.setSpacing(300);
      buttonHBox.getChildren().add(toPreviousButton);
      buttonHBox.getChildren().add(toNextButton);
     
      VBox root = new VBox();
      root.getChildren().add(titleHBox);
      root.getChildren().add(questionVBox);
      root.getChildren().add(VBoxAnswers);
      root.getChildren().add(buttonHBox);
      Scene scene = new Scene(root, 1200, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("quiz");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
