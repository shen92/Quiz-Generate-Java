package application;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ShowQuestionGUI {
  private Scene quizQuestionsScene;


  private Text questionIndexLabel = new Text();

  // Back-End Fields
  // TODO
  QuestionDatabase questionList;
  private int questionIndex;
  private LinkedList<Question> quizQuestions;
  private int[] result;// 0=>numQuestions, 1=> numAnswered, 2=> numCorrect

  public ShowQuestionGUI(Stage primaryStage, LinkedList<Question> quizQuestions,
      QuestionDatabase questionList) {
    this.questionList = questionList;
    this.result = new int[3];
    loadQuiz(primaryStage, quizQuestions);
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

        Button quitButton = addButton("Exit", 80, 20);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            System.exit(0);
            return;
          }
        });
        buttonHBox.getChildren().add(quitButton);

        Button cancelButton = addButton("Cancel", 80, 20);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            event.consume();
            window.close();
            return;
          }
        });
        buttonHBox.getChildren().add(cancelButton);
        root.getChildren().add(buttonHBox);

        Scene alert = new Scene(root, 320, 180);
        window.setScene(alert);
        window.showAndWait();
      }
    });
  }

  public void loadQuiz(Stage primaryStage, LinkedList<Question> quizQuestions) {
    this.questionIndex = 1;
    this.quizQuestions = new LinkedList<>();
    this.quizQuestions = quizQuestions;
    result[0] = this.quizQuestions.size();
  }

  private void setup(Stage primaryStage) {
    VBox questionDisplay = new VBox();
    // questionDisplay.setPrefWidth(1200);
    // questionDisplay.setPrefHeight(800);
    questionDisplay.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setPrefWidth(1200);
    scrollPane.setPrefHeight(800);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);

    VBox root = new VBox();
    root.setPrefWidth(1200);
    root.setPrefHeight(800);
    scrollPane.setContent(root);
    // questionDisplay.getChildren().add(scrollPane);

    // 1) Quiz Title Label
    BorderPane titlePane = new BorderPane();
    titlePane.setPadding(new Insets(20.0, 0.0, 0.0, 0));

    questionIndexLabel
        .setText("Question " + this.questionIndex + " of " + this.quizQuestions.size());

    // questionIndexLabel.setText("Question 1 of 20");
    questionIndexLabel.setFont(Font.font(40));
    titlePane.setCenter(questionIndexLabel);
    root.getChildren().add(titlePane);

    // actucal

    Question currentQuestion = quizQuestions.get(questionIndex - 1);

    VBox questionHBox = new VBox();
    questionHBox.setPadding(new Insets(40.0, 150.0, 25.0, 150.0));
    questionHBox.setSpacing(10);

    HBox questionTextHBox = new HBox();
    questionTextHBox.setAlignment(Pos.TOP_LEFT);
    questionHBox.getChildren().add(questionTextHBox);
    Label questionText = new Label();
    questionText.setWrapText(true);
    questionText.setText(currentQuestion.getQuestionText());
    questionText.setFont(Font.font(20));
    questionTextHBox.getChildren().add(questionText);

    HBox questionImageHBox = new HBox();
    questionImageHBox.setAlignment(Pos.TOP_LEFT);
    questionHBox.getChildren().add(questionImageHBox);
    if (!currentQuestion.getImage().equals("none")) {
      Image img = null;
      try {
        img = new Image(currentQuestion.getImage());
      } catch (IllegalArgumentException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Cannot find image!");
        alert.showAndWait();
        return;
      }
      ImageView questionImageView = new ImageView();
      // questionImageView.setAlignment(Pos.TOP_RIGHT);
      questionImageView.setImage(img);
      questionImageHBox.getChildren().add(questionImageView);
    }
    root.getChildren().add(questionHBox);

    // 3) Choice VBox
    VBox choiceVBox = new VBox();
    choiceVBox.setPadding(new Insets(0.0, 40.0, 0.0, 150.0));
    choiceVBox.setPrefHeight(360);
    choiceVBox.setAlignment(Pos.TOP_LEFT);
    choiceVBox.setSpacing(25);

    // TODO Choice class added
    ChoiceGroup choiceGroup = currentQuestion.getChoiceGroup();
    ArrayList<RadioButton> choice = new ArrayList<RadioButton>(choiceGroup.size());
    ArrayList<String> choiceGroupKeys = choiceGroup.getChoiceGroupKeys();
    for (int i = 0; i < choiceGroupKeys.size(); i++) {
      choice.add(choiceGroup.getRadioButton(choiceGroupKeys.get(i)));
      choice.get(i).setText(choiceGroupKeys.get(i));
      try {
        choiceVBox.getChildren().add(choice.get(i));
      } catch (IllegalArgumentException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Do not put same content in different choices!");
        alert.showAndWait();
        return;
      }
    }
    root.getChildren().add(choiceVBox);

    // 4) Previous and Next Buttons
    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(40.0, 40.0, 20.0, 80.0));
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(200);

    Button prevButton = addButton("Previous", 180, 40);
    prevButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO While has prev, goto previous scene

        if (questionIndex == 1) {
          Stage window = new Stage();
          window.setTitle("First Question");
          window.setMinWidth(320);
          window.setMinHeight(180);

          Text remind = new Text("This is the first Question!");
          remind.setFont(Font.font(20));
          Button closeButton = new Button("Confirm");
          closeButton.setOnAction(e -> window.close());

          VBox vb = new VBox();
          vb.getChildren().addAll(remind, closeButton);
          vb.setAlignment(Pos.CENTER);

          vb.setSpacing(40);
          BorderPane layout = new BorderPane();
          layout.setCenter(vb);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        } else {
          questionIndex--;
          setup(primaryStage);

        }

      }
    });
    buttonHBox.getChildren().add(prevButton);

    Button checkButton = addButton("check", 180, 40);
    checkButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        Alert alert;
        if (!currentQuestion.isAnswered()) {
          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Result Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Question is not answered!");
          alert.showAndWait();
        } else if (currentQuestion.isCorrect()) {
          alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Result Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Your answer is correct!");
          alert.showAndWait();
        } else {
          alert = new Alert(AlertType.WARNING);
          alert.setTitle("Result Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Your answer is incorrect!");
          alert.showAndWait();
        }
      }
    });
    buttonHBox.getChildren().add(checkButton);

    Button nextButton = addButton("Next", 180, 40);
    nextButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO While has prev, goto previous scene,
        // else to to quiz results
        if (questionIndex == quizQuestions.size()) {
          Stage window = new Stage();
          window.setTitle("Last question");
          window.setMinWidth(320);
          window.setMinHeight(180);
          Text remind = new Text("Would you like to submit?");
          remind.setFont(Font.font(20));
          Button confirmButton = new Button("Confirm");
          getResult(result);
          confirmButton.setOnAction(e -> {
            window.close();
            for (Question q : quizQuestions) {
              q.reset();
            }


            QuizResultsGUI quizResultsGUI =
                new QuizResultsGUI(primaryStage, result, questionList, quizQuestions);
            primaryStage.setScene(quizResultsGUI.getScene());
            primaryStage.setTitle("Quiz Results");

          });

          Button cancelButton = new Button("Cancel");
          cancelButton.setOnAction(e -> window.close());

          HBox buttonBox = new HBox();
          buttonBox.setAlignment(Pos.CENTER);
          buttonBox.setSpacing(50);
          buttonBox.getChildren().addAll(confirmButton, cancelButton);

          VBox vb = new VBox();
          vb.getChildren().addAll(remind, buttonBox);
          vb.setAlignment(Pos.CENTER);
          vb.setSpacing(50);

          BorderPane layout = new BorderPane();
          layout.setCenter(vb);

          Scene scene = new Scene(layout);
          window.setScene(scene);
          window.showAndWait();

        } else {
          questionIndex++;
          setup(primaryStage);
        }

      }


    });
    buttonHBox.getChildren().add(nextButton);
    root.getChildren().add(buttonHBox);


    this.quizQuestionsScene = new Scene(scrollPane, 1200, 800);
    primaryStage.setScene(this.quizQuestionsScene);

  }

  public Scene getScene() {
    return this.quizQuestionsScene;
  }

  private void getResult(int[] result) {
    for (Question q : quizQuestions) {
      if (q.isCorrect()) {
        result[2]++;
      }
    }
    for (Question q : quizQuestions) {
      if (q.isAnswered()) {
        result[1]++;
      }
    }
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
