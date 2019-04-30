package application;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowQuestionGUI {
  private Scene quizQuestionsScene;

  // JavaFX Components
  // TODO
  private Text questionIndexLabel = new Text();

  // Back-End Fields
  // TODO
  private int questionIndex;
  private LinkedList<Question> quizQuestions;


  public ShowQuestionGUI(Stage primaryStage, LinkedList<Question> quizQuestions) {
    loadQuiz(primaryStage, quizQuestions);
    setup(primaryStage);
  }

  public void loadQuiz(Stage primaryStage, LinkedList<Question> quizQuestions) {
    this.questionIndex = 1;
    this.quizQuestions = new LinkedList<>();
    this.quizQuestions = quizQuestions;
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
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    VBox root = new VBox();
    scrollPane.setContent(root);
    questionDisplay.getChildren().add(scrollPane);

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
      Image img = new Image(currentQuestion.getImage());
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
    ToggleGroup group = new ToggleGroup();
    RadioButton[] choiceButton = new RadioButton[currentQuestion.getChoice().size()];
    for (int i = 0; i < currentQuestion.getChoice().size(); i++) {
      choiceButton[i] = new RadioButton();
      choiceButton[i].setSelected(false);
      choiceButton[i].setText("Choice " + (char) ('A' + i));
      choiceButton[i].setToggleGroup(group);
      choiceButton[i].setFont(Font.font(18));
      choiceVBox.getChildren().add(choiceButton[i]);
    }

    // total number of choices
    // int size = currentQuestion.getChoice().size();
    // for (String choice : currentQuestion.getChoice().keySet()) {
    // RadioButton button = new RadioButton(choice);
    // button.setToggleGroup(group);
    // button.setFont(Font.font(18));
    // choiceVBox.getChildren().add(button);
    // }
    root.getChildren().add(choiceVBox);



    // 4) Previous and Next Buttons
    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(0.0, 40.0, 40.0, 80.0));
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(300);

    Button prevButton = addButton("Previous", 270, 40);
    prevButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO While has prev, goto previous scene

        if (questionIndex == 1) {
          Stage window = new Stage();
          window.setTitle("First Question");
          window.setMinWidth(400);
          window.setMinHeight(200);

          Text remind = new Text("This is the first Question!");
          remind.setFont(Font.font(20));
          Button closeButton = new Button("Close");
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

    Button nextButton = addButton("Next", 270, 40);
    nextButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO While has prev, goto previous scene,
        // else to to quiz results
        if (questionIndex == quizQuestions.size()) {
          Stage window = new Stage();
          window.setTitle("Last question");
          window.setMinWidth(400);
          window.setMinHeight(200);
          Text remind = new Text("This is the the last question, would you like to submit?");
          remind.setFont(Font.font(20));
          Button confirmButton = new Button("confirm");

          confirmButton.setOnAction(e -> {
            window.close();
            QuizResultsGUI quizResultsGUI = new QuizResultsGUI(primaryStage);
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

    this.quizQuestionsScene = new Scene(questionDisplay, 1200, 800);
    primaryStage.setScene(this.quizQuestionsScene);

  }

  public Scene getScene() {
    return this.quizQuestionsScene;
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
