package application;

import java.io.File;
import java.io.IOException;
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

/**
 * This class represents the start scene of the program
 * 
 * @author Yingjie Shen
 */
public class PrimaryGUI2 {
  private Scene primaryGUI;


  /**
   * Constructor of the PrimaryGUI Object
   * 
   * @param Stage primaryStage
   */
  public PrimaryGUI2(Stage primaryStage) {
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
   * This method adds a Button component to a scene
   * 
   * @param int width
   * @param int height
   * 
   * @return TableView<Question> table
   */
  private TableView<Question> addTableView(int width, int height) {
    TableView<Question> table = new TableView<>();
    table.setPrefWidth(width);
    table.setPrefHeight(height);
    return table;
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
    root.getChildren().addAll(addQuestionList(), addQuizList(primaryStage));
    Scene quizGeneratorScene = new Scene(root, 1200, 800);
    primaryStage.setTitle("Quiz Generator");
    return quizGeneratorScene;
  }

  private VBox addQuestionList() {
    VBox root = new VBox();
    // 1) Question List Label
    Label questionListLabel = new Label("Question List");
    questionListLabel.setFont(Font.font(20));
    root.getChildren().add(questionListLabel);


    // 2) Question Database TableView
    TableView<Question> questionDatabaseTable = addTableView(540, 400);
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
        // TODO Load Data
        test("Load Data");
      }
    });
    buttonsHBox.getChildren().add(loadDataButton);

    Button addQuestionButton = addButton("Add Question", 135, 40);
    addQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add User Question
        test("Add Question");
      }
    });
    buttonsHBox.getChildren().add(addQuestionButton);

    Button addToQuizButton = addButton("Add To Quiz", 135, 40);
    addToQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add Selected Question to Quiz List
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
    Label questionDatabaseCountLabel = new Label();
    questionDatabaseCountLabel.setText("Total Questions: 0");
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
    TableView<Question> quizQuestionTable = addTableView(540, 500);
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
    Label quizQuestionCountLabel = new Label();
    quizQuestionCountLabel.setText("Total Questions: 0");
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
        test("Start Quiz");
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
    Scene quizQuestionsScene = new Scene(root, 1200, 800);
    return quizQuestionsScene;
  }

  /**
   * This method generates Part I: Quiz Generator
   * 
   * @return quizQuestionsScene
   */
  public Scene quizResultsScene(Stage primaryStage) {
    Scene quizResultsScene = new Scene(null, 1200, 800);
    return quizResultsScene;
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
