package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QuizResultsGUI {
  private Scene quizResultScene;

  // JavaFX Components
  // TODO

  // Back-End Fields
  // TODO

  public QuizResultsGUI(Stage primaryStage) {
    setup(primaryStage);
  }

  private void setup(Stage primaryStage) {
    VBox root = new VBox();
    // 1) Quiz Result Title Label
    BorderPane titlePane = new BorderPane();
    titlePane.setPadding(new Insets(20.0, 40.0, 100.0, 0));

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
    totalQuestionLabel.setText("Total Questions: "/* TODO */);
    totalQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(totalQuestionLabel);

    Label correctQuestionLabel = new Label();
    correctQuestionLabel.setText("Correct Questions: "/* TODO */);
    correctQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(correctQuestionLabel);

    Label quizScoreLabel = new Label();
    quizScoreLabel.setText("Quiz Score: "/* TODO */);
    quizScoreLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(quizScoreLabel);
    root.getChildren().add(resultsLabelVBox);


    // 3) Finish Quiz Button HBox
    HBox finishQuizHBox = new HBox();
    finishQuizHBox.setAlignment(Pos.CENTER);
    finishQuizHBox.setSpacing(120);
    finishQuizHBox.setPadding(new Insets(150.0, 0.0, 0.0, 0.0));

    Button saveToFileButton = addButton("Save to File", 270, 40);
    saveToFileButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO RESET NEEDED
        primaryStage.setTitle("Save to File");
      }
    });
    finishQuizHBox.getChildren().add(saveToFileButton);

    Button finishQuizButton = addButton("Finish", 270, 40);
    finishQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        QuizGeneratorGUI quizGeneratorGUI = new QuizGeneratorGUI(primaryStage);
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

  /**
   * WARNING: Developer use only.
   */
  private void test(String func) {
    Stage window = new Stage();
    window.setTitle(func);
    window.setMinWidth(400);
    window.setMinHeight(200);

    Button button = new Button("Close");
    button.setOnAction(e -> window.close());

    BorderPane layout = new BorderPane();

    layout.setCenter(button);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }
}
