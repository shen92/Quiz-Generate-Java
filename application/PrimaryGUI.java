package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import org.json.simple.parser.ParseException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
public class PrimaryGUI {
  private Scene primaryGUI;
  private QuestionDatabase questionList;

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
   * @param        int width
   * @param        int height
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
    selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
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
        try {// TODO: DUPLICATE QUESTIONS
          FileChooser fileChooser = new FileChooser();
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
          fileChooser.getExtensionFilters().add(extFilter);
          File jsonFile = fileChooser.showOpenDialog(primaryStage);
          questionList = new QuestionDatabase();
          questionList.loadQuestions(jsonFile);
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }
        // Add questions to the question list table
        for (int i = 0; i < questionList.getAllQuestion().size(); i++) {
          addQuestionToQuestionList(questionList.getAllQuestion().get(i));
        }
      }
    });
    buttonsHBox.getChildren().add(loadDataButton);

    Button addQuestionButton = addButton("Add Question", 135, 40);
    addQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Add User Question, choice not added
        addQuestionForm();
      }
    });
    buttonsHBox.getChildren().add(addQuestionButton);

    Button selectAllButton = addButton("Select All", 135, 40);
    selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO BUG: TOGGLE LEFT&RIGHT
        for (Question question : questionDatabaseTable.getItems()) {
          question.setSelected(true);
        }
      }
    });
    buttonsHBox.getChildren().add(selectAllButton);

    Button unselectAllButton = addButton("Unselect All", 135, 40);
    unselectAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO: BUG TOGGLE LEFT&RIGHT
        for (Question question : questionDatabaseTable.getItems()) {
          question.setSelected(false);
        }
      }
    });
    buttonsHBox.getChildren().add(unselectAllButton);
    root.getChildren().add(buttonsHBox);

    // 4) Question Count Label
    questionDatabaseCountLabel
        .setText("Total Questions: " + questionDatabaseTable.getItems().size());
    questionDatabaseCountLabel.setFont(Font.font(18));
    root.getChildren().add(questionDatabaseCountLabel);

    // 5) Add Selected Question Label
    HBox addToQuizPane = new HBox();
    addToQuizPane.setPadding(new Insets(30, 0, 0, 0));
    addToQuizPane.setAlignment(Pos.CENTER);
    addToQuizPane.setSpacing(40);
    Button addToQuizButton = addButton("Add Selected To Quiz", 240, 40);
    addToQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO: BUG CHECKBOX DISAPPEAR
        for (Question question : questionDatabaseTable.getItems()) {
          if (question.getSelected() == true) {
            quizQuestionTable.getItems().add(question);
          }
        }
        quizQuestionCountLabel.setText("Total Questions: " + quizQuestionTable.getItems().size());
        // test("Add To Quiz");
      }
    });
    addToQuizPane.getChildren().add(addToQuizButton);
    Button saveToFileButton = addButton("Save To File", 135, 40);
    saveToFileButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO: SAVE TO FILE
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
      }
    });
    addToQuizPane.getChildren().add(saveToFileButton);
    root.getChildren().add(addToQuizPane);

    // 6) Search By Topic VBox
    VBox filterVBox = new VBox();
    filterVBox.setPadding(new Insets(30.0, 0.0, 0.0, 0.0));

    filterVBox.setSpacing(10);
    Label filterLabel = new Label("Filter By Topic");
    filterLabel.setFont(Font.font(20));
    filterVBox.getChildren().add(filterLabel);

    HBox searchHBox = new HBox();// Filter Box

    searchHBox.setSpacing(20);
    Label topicLabel = new Label("Topic:");
    topicLabel.setFont(Font.font(18));
    searchHBox.getChildren().add(topicLabel);

    TextField topicTextField = new TextField();
    topicTextField.setPrefWidth(200);
    searchHBox.getChildren().add(topicTextField);
    filterVBox.getChildren().add(searchHBox);

    HBox filterButtonHBox = new HBox();// Button Box
    filterButtonHBox.setSpacing(120);
    filterButtonHBox.setAlignment(Pos.CENTER);

    Button applyFilterButton = addButton("Apply Filter", 135, 40);
    applyFilterButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Apply Filter
        String filterTopic = topicTextField.getText();
        if (questionList == null) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("There is no questions in the list!");
          alert.showAndWait();
          return;
        }
        if (questionList.getAllTopic().contains(filterTopic)) {
          questionDatabaseTable.getItems().clear();
          for (int i = 0; i < questionList.filteredQuestionList(filterTopic).size(); i++) {
            questionDatabaseTable.getItems()
                .add(questionList.filteredQuestionList(filterTopic).get(i));
          }
        } else {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Topic not found!");
          alert.showAndWait();
        }
      }
    });
    filterButtonHBox.getChildren().add(applyFilterButton);

    Button removeFilterButton = addButton("Remove Filter", 135, 40);
    removeFilterButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Remove Filter
        questionDatabaseTable.getItems().clear();
        if (questionList == null)
          return;
        for (int i = 0; i < questionList.getAllQuestion().size(); i++) {
          addQuestionToQuestionList(questionList.getAllQuestion().get(i));
        }
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
    selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
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
        for (Question question : quizQuestionTable.getItems()) {
          question.setSelected(true);
        }
      }
    });
    buttonsHBox.getChildren().add(loadDataButton);

    Button addQuestionButton = addButton("Remove Selected", 270, 40);
    addQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO BUG: REMOVE ONE BY ONE
        for (Question question : quizQuestionTable.getItems()) {
          if (question.getSelected() == true) {
            quizQuestionTable.getItems().remove(question);
          }
        }
        quizQuestionCountLabel.setText("Total Questions: " + quizQuestionTable.getItems().size());
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
        // TODO BUG UNKNOWN
        for (Question question : quizQuestionTable.getItems()) {
          quizQuestions.add(question);
        }
        primaryStage.setScene(quizQuestionsScene(primaryStage));
        primaryStage.setTitle("Quiz");
      }
    });
    root.getChildren().add(startQuizPane);

    return root;
  }


  /**
   * This method generates Part II: Quiz
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


    // 3) Start Quiz Button
    BorderPane finishQuizPane = new BorderPane();
    finishQuizPane.setPadding(new Insets(150.0, 0.0, 0.0, 0.0));

    Button finishQuizButton = addButton("Finish", 270, 40);
    finishQuizPane.setCenter(finishQuizButton);
    finishQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO RESET NEEDED
        primaryStage.setScene(quizGeneratorScene(primaryStage));
        primaryStage.setTitle("Quiz Generator");
      }
    });
    root.getChildren().add(finishQuizPane);

    Scene quizResultsScene = new Scene(root, 1200, 800);
    return quizResultsScene;
  }

  /**
   * This method will pop up a new window and gather information from user and add a new question to
   * the question database.
   */
  private void addQuestionForm() {
    Stage window = new Stage();
    window.setTitle("Add Question");
    window.setMinWidth(640);
    window.setMinHeight(480);

    VBox root = new VBox();

    VBox getQuestionVBox = new VBox();
    getQuestionVBox.setPadding(new Insets(18.0, 0.0, 0.0, 25.0));
    getQuestionVBox.setSpacing(10);

    HBox h1 = new HBox();
    h1.setPadding(new Insets(0.0, 60.0, 0.0, 0.0));
    h1.setSpacing(10);
    h1.setAlignment(Pos.CENTER_RIGHT);
    Label questionLabel = new Label();
    questionLabel.setText("Question:");
    questionLabel.setFont(Font.font(18));
    h1.getChildren().add(questionLabel);
    TextArea questionTextArea = new TextArea();
    questionTextArea.setPrefWidth(450);
    questionTextArea.setPrefHeight(75);
    h1.getChildren().add(questionTextArea);
    getQuestionVBox.getChildren().add(h1);

    HBox h2 = new HBox();
    h2.setSpacing(10);
    h2.setPadding(new Insets(0.0, 60.0, 0.0, 0.0));
    h2.setAlignment(Pos.CENTER_RIGHT);
    Label topicLabel = new Label();
    topicLabel.setText("Topic:");
    topicLabel.setFont(Font.font(18));
    h2.getChildren().add(topicLabel);
    TextField topicTextField = new TextField();
    topicTextField.setPrefWidth(450);
    h2.getChildren().add(topicTextField);
    getQuestionVBox.getChildren().add(h2);

    HBox h3 = new HBox();
    h3.setSpacing(10);
    h3.setPadding(new Insets(0.0, 60.0, 0.0, 0.0));
    h3.setAlignment(Pos.CENTER_RIGHT);
    Label imageLabel = new Label();
    imageLabel.setText("Image:");
    imageLabel.setFont(Font.font(18));
    h3.getChildren().add(imageLabel);
    TextField imageTextField = new TextField();
    imageTextField.setPrefWidth(450);
    h3.getChildren().add(imageTextField);
    getQuestionVBox.getChildren().add(h3);

    Label choiceLabel = new Label();
    choiceLabel.setText("Choice: (Toggle to set answer)");
    choiceLabel.setFont(Font.font(18));
    choiceLabel.setPadding(new Insets(0, 0, 0, 27));
    getQuestionVBox.getChildren().add(choiceLabel);

    ToggleGroup group = new ToggleGroup();

    for (int i = 0; i < 5; i++) {
      HBox choice = new HBox();
      choice.setSpacing(10);
      choice.setPadding(new Insets(0.0, 60.0, 0.0, 0.0));
      choice.setAlignment(Pos.CENTER_RIGHT);
      RadioButton choiceButton = new RadioButton();
      choiceButton.setToggleGroup(group);
      choiceButton.setSelected(false);
      choice.getChildren().add(choiceButton);
      Label choiceALabel = new Label();
      choiceALabel.setText((char) ('A' + i) + ": ");
      choiceALabel.setFont(Font.font(18));
      choice.getChildren().add(choiceALabel);
      TextField choiceATextField = new TextField();
      choiceATextField.setPrefWidth(450);
      choice.getChildren().add(choiceATextField);
      getQuestionVBox.getChildren().add(choice);
    }

    root.getChildren().add(getQuestionVBox);

    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(20.0, 0.0, 0.0, 0.0));
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(80);

    Button confirmButton = addButton("Confirm", 135, 40);
    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO add Question, BUG EXIST
        Question question = new Question();
        String questionText = null;
        String topic = null;
        HashMap<String, String> answer = null;
        String imageFile = null;
        question.setQuestionText(questionTextArea.getText());
        question.setTopic(topic = topicTextField.getText());
        question.setImage(imageTextField.getText());
        if (question.getQuestionText().length() != 0
            && question.getTopic().length() != 0 /* TODO add answer */) {
          // allQuestions.add(question);
          addQuestionToQuestionList(question);
          window.close();
        } else {
          // Stage window = new Stage();
          // window.setTitle("Warning");
          // window.setMinWidth(320);
          // window.setMinHeight(240);
          // VBox root = new VBox();
          // root.setAlignment(Pos.CENTER);
          // root.setSpacing(80);
          //
          // Label warningLabel = new Label("Empty Field!");
          // root.getChildren().add(warningLabel);
          //
          // Button closeButton = addButton("Got it", 135, 40);
          // closeButton.setOnAction(new EventHandler<ActionEvent>() {
          // @Override
          // public void handle(ActionEvent arg0) {
          // window.close();
          // }
          // });
          // root.getChildren().add(closeButton);
          //
          // Scene scene = new Scene(root);
          //
          // window.setScene(scene);
          // window.showAndWait();
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("There are empty fields in the form!");
          alert.showAndWait();
        }
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

  private void addQuestionToQuestionList(Question question) {
    questionDatabaseTable.getItems().add(question);
    questionDatabaseCountLabel
        .setText("Total Questions: " + questionDatabaseTable.getItems().size());
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
