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
      // Text t = new Text("123");
      Text questionLabel = new Text("Question " + indexOfQuestion + "/" + totalNumOfQuestion);
      questionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
      // questionLabel.

      // VBox titleVBox = new VBox();
      HBox titleHBox = new HBox();
      titleHBox.setPrefHeight(100);
      titleHBox.setPrefWidth(1200);
      titleHBox.setAlignment(Pos.CENTER);

      titleHBox.getChildren().add(questionLabel);
      // titleVBox.setPadding(new Insets(25.0, 400.0, 40.0, 400.0));
      // titleHBox.getChildren().add(titleHBox);


      // questionLabel.setAlignment(Pos.CENTER);

      // Label indexOfQuestionLabel = new Label(indexOfQuestion + "");
      // indexOfQuestionLabel.setTranslateY(50);
      // Label totalNumOfQuestionLabel = new Label(totalNumOfQuestion + "");
      Text questionsText = new Text("" + "QuestionClass.getQuestion()");
      questionsText.setFont(Font.font("Arial", 20));
      VBox questionVBox = new VBox();
      questionVBox.setPrefWidth(1200);
      questionVBox.setPrefHeight(100);
      questionVBox.setPadding(new Insets(25.0, 30.0, 40.0, 150.0));
      questionVBox.getChildren().add(questionsText);

      // questionVBox.getChildren().add(questionsText);


      ToggleGroup group = new ToggleGroup();



      VBox VBoxAnswers = new VBox();
      VBoxAnswers.setPadding(new Insets(25.0, 40.0, 40.0, 150.0));
      VBoxAnswers.setSpacing(40);
      VBoxAnswers.setPrefWidth(1200);
      VBoxAnswers.setPrefHeight(600);


      int size = 3;
      for (int i = 0; i < size; i++) {
        RadioButton button = new RadioButton("QuestionClass.getAnswers");
        button.setToggleGroup(group);
        button.setSelected(true);
        VBoxAnswers.getChildren().add(button);
        // VBoxAnswers.setAlignment(Pos.CENTER);
      }



      // HBoxAnswers.getChildren().add(questionsText);

      // HBoxQuestion.getPadding();

      Button toPreviousButton = new Button("Previous");
      Button toNextButton = new Button("Next");
      // toPreviousButton.setAlignment(Pos.BOTTOM_LEFT);
      // toNextButton.setAlignment(Pos.BOTTOM_RIGHT);

      HBox buttonHBox = new HBox();
      buttonHBox.setPadding(new Insets(25, 0, 25, 300));
      buttonHBox.setPrefWidth(1200);
      buttonHBox.setPrefHeight(100);
      buttonHBox.setSpacing(300);
      buttonHBox.getChildren().add(toPreviousButton);
      buttonHBox.getChildren().add(toNextButton);


      // toPreviousButton.setOnAction( e -> buttonAction());

      // questionLabel.setStyle("-fx-border-color: blue;");
      // BorderPane canvas = new BorderPane();
      // canvas.setPadding(new Insets(25.0, 40.0, 40.0, 25.0));


      VBox root = new VBox();
      root.getChildren().add(titleHBox);
      root.getChildren().add(questionVBox);
      root.getChildren().add(VBoxAnswers);
      root.getChildren().add(buttonHBox);

      // root.setRight(toNextButton);
      // root.setLeft(toPreviousButton);
      // root.setBottom(toNextButton);
      // root.setTop(indexOfQuestionLabel);
      // root.setTop(totalNumOfQuestionLabel);

      // root.getChildren().add(root);
      // root.getChildren().add(toPreviousButton);
      // root.getChildren().add(toNextButton);

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
