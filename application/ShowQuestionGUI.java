package application;

import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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


  public ShowQuestionGUI(Stage primaryStage) {
    setup(primaryStage);
  }

  // public void loadQuiz(Stage primaryStage) {
  // this.questionIndex = 1;
  // this.quizQuestions = new LinkedList<>();
  // }

  private void setup(Stage primaryStage) {
    VBox root = new VBox();
    // 1) Quiz Title Label
    BorderPane titlePane = new BorderPane();
    titlePane.setPadding(new Insets(20.0, 0.0, 0.0, 0));

    // questionIndexLabel.setText("Question " + questionIndex + " of " + quizQuestions.size());
    questionIndexLabel.setText("Question 1 of 20");
    questionIndexLabel.setFont(Font.font(40));
    titlePane.setCenter(questionIndexLabel);
    root.getChildren().add(titlePane);


    // 2) Question Text/ImageView
    HBox questionHBox = new HBox();
    questionHBox.setPadding(new Insets(40.0, 150.0, 25.0, 150.0));
    questionHBox.setSpacing(10);

    HBox questionTextHBox = new HBox();
    questionTextHBox.setAlignment(Pos.TOP_LEFT);
    questionHBox.getChildren().add(questionTextHBox);
    Text questionText = new Text();
    questionText.setText(
        "The _____ command will copy files from a local repository to a remote repository.");
    questionText.setFont(Font.font(20));
    questionTextHBox.getChildren().add(questionText);

    HBox questionImageHBox = new HBox();
    questionImageHBox.setAlignment(Pos.TOP_RIGHT);
    questionHBox.getChildren().add(questionImageHBox);
    Image img = new Image("image.jpg");
    ImageView questionImageView = new ImageView();
    // questionImageView.setAlignment(Pos.TOP_RIGHT);
    questionImageView.setImage(img);
    questionImageHBox.getChildren().add(questionImageView);
    root.getChildren().add(questionHBox);

    // 3) Choice VBox
    VBox choiceVBox = new VBox();
    choiceVBox.setPadding(new Insets(0.0, 40.0, 0.0, 150.0));
    choiceVBox.setPrefHeight(360);
    choiceVBox.setAlignment(Pos.TOP_LEFT);
    choiceVBox.setSpacing(25);

    ToggleGroup group = new ToggleGroup();
    // total number of choices
    int size = 5;
    for (int i = 0; i < size; i++) {
      RadioButton button = new RadioButton("" + (i + 1));
      button.setToggleGroup(group);
      button.setFont(Font.font(18));
      button.setSelected(true);
      choiceVBox.getChildren().add(button);
    }
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
        test("Previous");
      }
    });
    buttonHBox.getChildren().add(prevButton);

    Button nextButton = addButton("Next", 270, 40);
    nextButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO While has prev, goto previous scene,
        // else to to quiz results
        QuizResultsGUI quizResultsGUI = new QuizResultsGUI(primaryStage);
        primaryStage.setScene(quizResultsGUI.getScene());
        primaryStage.setTitle("Quiz Results");
      }
    });
    buttonHBox.getChildren().add(nextButton);
    root.getChildren().add(buttonHBox);

    this.quizQuestionsScene = new Scene(root, 1200, 800);
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
