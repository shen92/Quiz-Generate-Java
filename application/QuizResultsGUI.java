package application;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class QuizResultsGUI implements IGUI {
  private Scene quizResultScene;


  QuestionDatabase questionList;
  private LinkedList<Question> quizQuestions;
  int[] result;

  public QuizResultsGUI(Stage primaryStage, int[] result, QuestionDatabase questionList,
      LinkedList<Question> quizQuestions) {
    this.quizQuestions = quizQuestions;
    this.questionList = questionList;
    this.result = result;
    setup(primaryStage);
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        Stage window = new Stage();
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent event) {
            event.consume();
          }
        });
        window.setTitle("Quit");
        window.setMinWidth(320);
        window.setMinHeight(180);
        VBox root = new VBox();

        VBox labelsVBox = new VBox();
        labelsVBox.setAlignment(Pos.CENTER);
        labelsVBox.setPadding(new Insets(40, 0, 10, 0));
        Label warning1 = new Label("Would you like to quit?");
        warning1.setFont(Font.font(16));
        labelsVBox.getChildren().add(warning1);

        Label warning2 = new Label("Your unsaved changes will lost!");
        warning2.setFont(Font.font(16));
        labelsVBox.getChildren().add(warning2);
        root.getChildren().add(labelsVBox);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPadding(new Insets(40, 0, 20, 0));
        buttonHBox.setSpacing(60);

        Button saveButton = addButton("Save File", 80, 20);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            event.consume();
            window.close();
            return;
          }
        });
        buttonHBox.getChildren().add(saveButton);

        Button quitButton = addButton("Exit", 80, 20);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            System.exit(0);
            return;
          }
        });
        buttonHBox.getChildren().add(quitButton);
        root.getChildren().add(buttonHBox);

        Scene alert = new Scene(root, 320, 180);
        window.setScene(alert);
        window.showAndWait();
      }
    });
  }

  private void setup(Stage primaryStage) {
    VBox root = new VBox();
    // 1) Quiz Result Title Label
    BorderPane titlePane = new BorderPane();
    titlePane.setPadding(new Insets(20.0, 0.0, 100.0, 0));

    Text quizResultsLabel = new Text("Quiz Results");
    quizResultsLabel.setFont(Font.font(40));
    titlePane.setCenter(quizResultsLabel);
    root.getChildren().add(titlePane);

    // 2) Quiz Results Label VBox
    VBox resultsLabelVBox = new VBox();
    resultsLabelVBox.setPadding(new Insets(120.0, 40.0, 120.0, 0));
    resultsLabelVBox.setAlignment(Pos.CENTER);
    resultsLabelVBox.setSpacing(10);

    Label totalQuestionLabel = new Label();
    totalQuestionLabel.setText("Total Questions: " + result[0]);
    totalQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(totalQuestionLabel);

    Label answeredQuestionLabel = new Label();
    answeredQuestionLabel.setText("Answered Questions: " + result[1]);
    answeredQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(answeredQuestionLabel);

    Label correctQuestionLabel = new Label();
    correctQuestionLabel.setText("Correct Questions: " + result[2]);
    correctQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(correctQuestionLabel);



    Label quizScoreLabel = new Label();
    quizScoreLabel
        .setText("Quiz Score: " + String.format("%.2f", (double) (100 * result[2] / result[0])));
    quizScoreLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(quizScoreLabel);
    root.getChildren().add(resultsLabelVBox);


    // 3) Finish Quiz Button HBox
    HBox finishQuizHBox = new HBox();
    finishQuizHBox.setAlignment(Pos.CENTER);
    finishQuizHBox.setSpacing(200);
    finishQuizHBox.setPadding(new Insets(150.0, 0.0, 0.0, 0.0));

    Button saveToFileButton = addButton("Save to File", 180, 40);
    Tooltip saveToFileTooltip = new Tooltip();
    saveToFileTooltip.setText("Exit with saving to file");
    saveToFileButton.setTooltip(saveToFileTooltip);
    saveToFileButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO RESET NEEDED
        primaryStage.setTitle("Save to File");
        if (questionList == null) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText("Cannot write the file!");
          alert.setContentText("There is no questions in the question list!");
          alert.showAndWait();
          return;
        } else
          try {
            questionList.writeQuestions(questionList.getAllQuestion());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("File Written Complete!");
            alert.setContentText("All questions have been successfully written");
            alert.showAndWait();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          }
        QuizGeneratorGUI quizGeneratorGUI = new QuizGeneratorGUI(primaryStage, questionList);
        primaryStage.setScene(quizGeneratorGUI.getScene());
        primaryStage.setTitle("Quiz Generator");
      }
    });
    finishQuizHBox.getChildren().add(saveToFileButton);

    Button restartQuizButton = addButton("Restart", 180, 40);
    Tooltip restartQuizTooltip = new Tooltip();
    restartQuizTooltip.setText("Restart quiz");
    restartQuizButton.setTooltip(restartQuizTooltip);
    restartQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        ShowQuestionGUI showQuestionGUI =
            new ShowQuestionGUI(primaryStage, quizQuestions, questionList);
        primaryStage.setScene(showQuestionGUI.getScene());
        primaryStage.setTitle("Quiz");
      }
    });
    finishQuizHBox.getChildren().add(restartQuizButton);

    Button finishQuizButton = addButton("Finish", 180, 40);
    Tooltip finishQuizTooltip = new Tooltip();
    finishQuizTooltip.setText("Exit without saving");
    finishQuizButton.setTooltip(finishQuizTooltip);
    finishQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        QuizGeneratorGUI quizGeneratorGUI = new QuizGeneratorGUI(primaryStage, questionList);
        primaryStage.setScene(quizGeneratorGUI.getScene());
        primaryStage.setTitle("Quiz Generator");
      }
    });
    finishQuizHBox.getChildren().add(finishQuizButton);
    root.getChildren().add(finishQuizHBox);

    this.quizResultScene = new Scene(root, 1200, 800);
  }

  public Scene getScene() {
    return this.quizResultScene;
  }

  /**
   * This method adds a Button component to a scene
   * 
   * @param String name
   * @param int width
   * @param int height
   * 
   * @return Button button
   */
  private Button addButton(String name, int width, int height) {
    Button button = new Button(name);
    button.setPrefWidth(width);
    button.setPrefHeight(height);
    return button;
  }


}
