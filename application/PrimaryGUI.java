package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import org.json.simple.parser.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This class represents the start scene of the program
 * 
 * @author Yingjie Shen
 */
public class PrimaryGUI {
  private Scene primaryGUI;
  private QuestionDatabase questionList;

  private ArrayList<Question> allQuestions;
  private LinkedList<Question> quizQuestions;

  private Label questionDatabaseCountLabel = new Label();
  private Label quizQuestionCountLabel = new Label();
  private TableView<Question> questionDatabaseTable;
  private TableView<Question> quizQuestionTable;

  /**
   * Constructor of the PrimaryGUI Object
   * 
   * @param Stage primaryStage
   */
  public PrimaryGUI(Stage primaryStage) {
    this.primaryGUI = quizGeneratorScene(primaryStage);
  }

  /**
   * This method returns the primaryGUI
   * 
   * @return Scene primaryGUI
   */
  public Scene getScene() {
    return this.primaryGUI;
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
   * This method generates Part I: Quiz Generator
   * 
   * @return Scene quizGeneratorScene
   */
  public Scene quizGeneratorScene(Stage primaryStage) {
    HBox root = new HBox();
    // Setups for the Quiz Generator Scene
    root.setPadding(new Insets(25.0, 25.0, 40.0, 40.0));
    root.setSpacing(25);


    // Add components to the scene
    root.getChildren().addAll(addQuestionList(primaryStage), addQuizList(primaryStage));
    primaryStage.setTitle("Quiz Generator");

    Scene quizGeneratorScene = new Scene(root, 1200, 800);
    return quizGeneratorScene;
  }

  private VBox addQuestionList(Stage primaryStage) {
    VBox root = new VBox();

    // 1) Question List Label
    Label questionListLabel = new Label("Question List");
    questionListLabel.setFont(Font.font(20));
    root.getChildren().add(questionListLabel);


    // 2) Question Database TableView
    questionDatabaseTable = new TableView<>();
    questionDatabaseTable.setPrefWidth(540);
    questionDatabaseTable.setPrefHeight(400);
    questionDatabaseTable.setEditable(true);

    TableColumn<Question, CheckBox> selectCol = new TableColumn<>("Select");
    selectCol.setCellValueFactory(new PropertyValueFactory<Question, CheckBox>("select"));
    selectCol.setPrefWidth(60);
    questionDatabaseTable.getColumns().add(selectCol);

    TableColumn<Question, String> topicCol = new TableColumn<>("Topic");
    topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
    topicCol.setPrefWidth(100);
    questionDatabaseTable.getColumns().add(topicCol);

    TableColumn<Question, String> contentCol = new TableColumn<>("Question");
    contentCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
    contentCol.setPrefWidth(370);
    questionDatabaseTable.getColumns().add(contentCol);
    root.getChildren().add(questionDatabaseTable);


    // 3) Buttons HBox
    HBox buttonsHBox = new HBox();
    Button loadDataButton = addButton("Load Data", 135, 40);
    loadDataButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        try {
          FileChooser fileChooser = new FileChooser();
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("JSON files (*.JSON)", "*.JSON");
          fileChooser.getExtensionFilters().add(extFilter);
          File jsonFile = fileChooser.showOpenDialog(primaryStage);
          questionList = new QuestionDatabase();
          questionList.loadQuestions(jsonFile);
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }
        for (int i = 0; i < questionList.getAllQuestion().size(); i++) {
          questionDatabaseTable.getItems().add(questionList.getAllQuestion().get(i));
        }
        questionDatabaseCountLabel
            .setText("Total Questions: " + questionDatabaseTable.getItems().size());
      }
    });
    buttonsHBox.getChildren().add(loadDataButton);

    Button addQuestionButton = addButton("Add Question", 135, 40);
    addQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add User Question
        addQuestion();
      }
    });
    buttonsHBox.getChildren().add(addQuestionButton);

    Button addToQuizButton = addButton("Add To Quiz", 135, 40);
    addToQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add Selected Question to Quiz List
        quizQuestionCountLabel.setText("Total Questions: " + quizQuestionTable.getItems().size());
        test("Add To Quiz");
      }
    });
    buttonsHBox.getChildren().add(addToQuizButton);

    Button saveToFileButton = addButton("Save To File", 135, 40);
    saveToFileButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add Selected Question to Quiz List
        test("Save To File");
      }
    });
    buttonsHBox.getChildren().add(saveToFileButton);
    root.getChildren().add(buttonsHBox);

    // 4) Question Count Label
    questionDatabaseCountLabel
        .setText("Total Questions: " + questionDatabaseTable.getItems().size());
    questionDatabaseCountLabel.setFont(Font.font(18));
    root.getChildren().add(questionDatabaseCountLabel);

    // 5) Search By Topic VBox
    VBox filterVBox = new VBox();
    filterVBox.setPadding(new Insets(50.0, 0.0, 0.0, 0.0));

    filterVBox.setSpacing(10);
    Label filterLabel = new Label("Filter By Topic");
    filterLabel.setFont(Font.font(20));
    filterVBox.getChildren().add(filterLabel);

    HBox searchHBox = new HBox();// Filter Box

    searchHBox.setSpacing(30);
    Label topicLabel = new Label("Topic:");
    topicLabel.setFont(Font.font(18));
    searchHBox.getChildren().add(topicLabel);

    TextField topicTextField = new TextField();
    topicTextField.setPrefWidth(200);
    searchHBox.getChildren().add(topicTextField);
    filterVBox.getChildren().add(searchHBox);

    HBox filterButtonHBox = new HBox();// Button Box
    filterButtonHBox.setSpacing(180);

    Button applyFilterButton = addButton("Apply Filter", 135, 40);
    applyFilterButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Apply Filter
        test("Apply Filter");
      }
    });
    filterButtonHBox.getChildren().add(applyFilterButton);

    Button removeFilterButton = addButton("Remove Filter", 135, 40);
    removeFilterButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Remove Filter
        test("Remove Filter");
      }
    });
    filterButtonHBox.getChildren().add(removeFilterButton);
    filterVBox.getChildren().add(filterButtonHBox);
    root.getChildren().add(filterVBox);


    return root;
  }

  private VBox addQuizList(Stage primaryStage) {
    VBox root = new VBox();


    // 1) Quiz List Label
    Label quizListLabel = new Label("Quiz List");
    quizListLabel.setFont(Font.font(20));
    root.getChildren().add(quizListLabel);

    // 2) Question Database TableView
    quizQuestionTable = new TableView<>();
    quizQuestionTable.setPrefWidth(540);
    quizQuestionTable.setPrefHeight(500);
    quizQuestionTable.setEditable(true);

    TableColumn<Question, CheckBox> selectCol = new TableColumn<>("Select");
    selectCol.setCellValueFactory(new PropertyValueFactory<Question, CheckBox>("select"));
    selectCol.setPrefWidth(60);
    quizQuestionTable.getColumns().add(selectCol);

    TableColumn<Question, String> topicCol = new TableColumn<>("Topic");
    topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
    topicCol.setPrefWidth(100);
    quizQuestionTable.getColumns().add(topicCol);

    TableColumn<Question, String> contentCol = new TableColumn<>("Question");
    contentCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
    contentCol.setPrefWidth(370);
    quizQuestionTable.getColumns().add(contentCol);
    root.getChildren().add(quizQuestionTable);

    // 3) Buttons HBox
    HBox buttonsHBox = new HBox();
    Button loadDataButton = addButton("Select All", 270, 40);
    loadDataButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Load Data
        test("Select All");
      }
    });
    buttonsHBox.getChildren().add(loadDataButton);

    Button addQuestionButton = addButton("Remove Selected", 270, 40);
    addQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add User Question
        test("Remove Selected");
      }
    });
    buttonsHBox.getChildren().add(addQuestionButton);
    root.getChildren().add(buttonsHBox);

    // 4) Quiz question Count Label
    quizQuestionCountLabel.setText("Total Questions: " + quizQuestionTable.getItems().size());
    quizQuestionCountLabel.setFont(Font.font(18));
    root.getChildren().add(quizQuestionCountLabel);

    // 5) Start Quiz Button
    BorderPane startQuizPane = new BorderPane();
    startQuizPane.setPadding(new Insets(50.0, 0.0, 0.0, 0.0));

    Button startQuizButton = addButton("Start Quiz", 270, 40);
    startQuizPane.setCenter(startQuizButton);
    startQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add User Question
        primaryStage.setScene(quizQuestionsScene(primaryStage));
        primaryStage.setTitle("Quiz");
      }
    });
    root.getChildren().add(startQuizPane);

    return root;
  }


  /**
   * This method generates Part I: Quiz Generator
   * 
   * @return quizQuestionsScene
   */
  public Scene quizQuestionsScene(Stage primaryStage) {
    VBox root = new VBox();
    // 1) Quiz Title Label
    BorderPane titlePane = new BorderPane();
    titlePane.setPadding(new Insets(20.0, 40.0, 20.0, 0));

    Text questionLabel = new Text("Question 1 of 1");
    questionLabel.setFont(Font.font(40));
    titlePane.setCenter(questionLabel);
    root.getChildren().add(titlePane);


    // 2) Question Text
    HBox questionHBox = new HBox();
    questionHBox.setPadding(new Insets(80.0, 40.0, 40.0, 150.0));
    questionHBox.setPrefHeight(150);
    questionHBox.setAlignment(Pos.TOP_LEFT);
    questionHBox.setSpacing(100);

    Text questionsText = new Text("1 + 1 = ?");
    questionsText.setFont(Font.font(20));
    questionHBox.getChildren().add(questionsText);
    root.getChildren().add(questionHBox);


    // 3) Choice VBox
    VBox choiceVBox = new VBox();
    choiceVBox.setPadding(new Insets(25.0, 40.0, 40.0, 150.0));
    choiceVBox.setPrefHeight(400);
    choiceVBox.setAlignment(Pos.TOP_LEFT);
    choiceVBox.setSpacing(40);

    ToggleGroup group = new ToggleGroup();
    // total number of choices
    int size = 3;
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
    buttonHBox.setPadding(new Insets(25.0, 40.0, 40.0, 80.0));
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
        primaryStage.setScene(quizResultsScene(primaryStage));
        primaryStage.setTitle("Quiz Results");
      }
    });
    buttonHBox.getChildren().add(nextButton);
    root.getChildren().add(buttonHBox);

    Scene quizQuestionsScene = new Scene(root, 1200, 800);
    return quizQuestionsScene;
  }

  /**
   * This method generates Part I: Quiz Generator
   * 
   * @return quizQuestionsScene
   */
  public Scene quizResultsScene(Stage primaryStage) {
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
    totalQuestionLabel.setText("Total Questions: ");
    totalQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(totalQuestionLabel);

    Label correctQuestionLabel = new Label();
    correctQuestionLabel.setText("Correct Questions: ");
    correctQuestionLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(correctQuestionLabel);

    Label quizScoreLabel = new Label();
    quizScoreLabel.setText("Quiz Score: ");
    quizScoreLabel.setFont(Font.font(25));
    resultsLabelVBox.getChildren().add(quizScoreLabel);
    root.getChildren().add(resultsLabelVBox);


    // 3) Start Quiz Button
    BorderPane finishQuizPane = new BorderPane();
    finishQuizPane.setPadding(new Insets(150.0, 0.0, 0.0, 0.0));

    Button finishQuizButton = addButton("Finish", 270, 40);
    finishQuizPane.setCenter(finishQuizButton);
    finishQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add User Question
        primaryStage.setScene(quizGeneratorScene(primaryStage));
        primaryStage.setTitle("Quiz Generator");
      }
    });
    root.getChildren().add(finishQuizPane);

    Scene quizResultsScene = new Scene(root, 1200, 800);
    return quizResultsScene;
  }

  private void addQuestion() {
    Stage window = new Stage();
    window.setTitle("Add Question");
    window.setMinWidth(640);
    window.setMinHeight(480);

    VBox root = new VBox();

    HBox buttonHBox = new HBox();
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(80);

    Button confirmButton = addButton("Confirm", 135, 40);
    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO
        window.close();
      }
    });
    buttonHBox.getChildren().add(confirmButton);

    Button cancelButton = addButton("Cancel", 135, 40);
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        window.close();
      }
    });
    buttonHBox.getChildren().add(cancelButton);
    root.getChildren().add(buttonHBox);

    Scene scene = new Scene(root);

    window.setScene(scene);
    window.showAndWait();
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
