package application;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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
  private QuestionDatabase questionDatabase;

  @SuppressWarnings("unchecked")
  public PrimaryGUI(Stage primaryStage) {
    this.questionDatabase = new QuestionDatabase();

    // Start GUI of the program
    HBox startLayOut = new HBox();
    VBox quizLayOut = new VBox();
    Scene quizGeneratorScene = new Scene(startLayOut, 1200, 800);
    Scene quizScene = new Scene(quizLayOut, 1200, 800);

    startLayOut.setPadding(new Insets(25.0, 25.0, 40.0, 40.0));
    startLayOut.setSpacing(25);


    /* left part of the main scene */
    VBox leftVBox = new VBox();
    // 1) Top label of the left VBox
    Label questionListLabel = new Label("Question List");
    questionListLabel.setFont(Font.font(20));
    leftVBox.getChildren().add(questionListLabel);

    // 2) Mid table of the left VBox
    TableView<Question> questionListTable = new TableView<>();
    questionListTable.setEditable(true);
    // set table properties
    questionListTable.setPrefWidth(540);
    questionListTable.setPrefHeight(400);
    // add columns to the table
    TableColumn<Question, CheckBox> questionListSelectCol = new TableColumn<>("Select");
    questionListSelectCol
        .setCellValueFactory(new PropertyValueFactory<Question, CheckBox>("select"));
    questionListSelectCol.setPrefWidth(60);
    TableColumn<Question, String> questionListTopicCol = new TableColumn<>("Topic");
    questionListTopicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
    questionListTopicCol.setPrefWidth(100);
    TableColumn<Question, String> questionListContentCol = new TableColumn<>("Question");
    questionListTopicCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
    questionListContentCol.setPrefWidth(370);
    questionListTable.getColumns().addAll(questionListSelectCol, questionListTopicCol,
        questionListContentCol);
    ObservableList<Question> questions = FXCollections.observableArrayList();
    questions.add(new Question("Math", "1+1 = ?"));
    questionListTable.setItems(questions);
    leftVBox.getChildren().add(questionListTable);

    // 3) Mid Buttons of the left VBox
    HBox leftMidButtonHBox = new HBox();
    // 3a) Load Data Button
    Button lb1 = new Button("Load Data");
    lb1.setPrefWidth(135);
    lb1.setPrefHeight(40);
    lb1.setOnAction(new EventHandler<ActionEvent>() {// event for load data
      @Override
      public void handle(ActionEvent arg0) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
            new FileChooser.ExtensionFilter("JSON files (*.JSON)", "*.JSON");
        fileChooser.getExtensionFilters().add(extFilter);
        File jsonFile = fileChooser.showOpenDialog(primaryStage);
        // TODO
        try {
          questionDatabase.loadQuestions(jsonFile);
        } catch (IOException | ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    leftMidButtonHBox.getChildren().add(lb1);
    // 3b) Select All Button
    Button lb2 = new Button("Select All");
    // TODO
    lb2.setPrefWidth(135);
    lb2.setPrefHeight(40);
    leftMidButtonHBox.getChildren().add(lb2);
    // 3c) Add Selected Question Button
    Button lb3 = new Button("Add Question");
    // TODO
    lb3.setPrefWidth(135);
    lb3.setPrefHeight(40);
    leftMidButtonHBox.getChildren().add(lb3);
    // 3d) Save To File Button
    Button lb4 = new Button("Save To File");
    // TODO
    lb4.setPrefWidth(135);
    lb4.setPrefHeight(40);
    leftMidButtonHBox.getChildren().add(lb4);
    leftVBox.getChildren().add(leftMidButtonHBox);
    // 4) Total Question Label
    Label questionDatabaseCountLabel = new Label();
    questionDatabaseCountLabel.setText("Total Questions: " + questionDatabase.getQuestionNum());
    questionDatabaseCountLabel.setFont(Font.font(18));
    leftVBox.getChildren().add(questionDatabaseCountLabel);
    // 5) Filter by Topic
    VBox leftBottomVBox = new VBox();
    leftBottomVBox.setPadding(new Insets(40.0, 0.0, 0.0, 0.0));
    leftBottomVBox.setSpacing(10);
    HBox searchHBox = new HBox();
    searchHBox.setSpacing(30);
    Label searchOptionLabel = new Label("Search By Topic");
    searchOptionLabel.setFont(Font.font(20));
    leftBottomVBox.getChildren().add(searchOptionLabel);
    Label searchLabel = new Label();
    searchLabel.setText("Topic:");
    searchLabel.setFont(Font.font(18));
    searchHBox.getChildren().add(searchLabel);
    TextField topicTextField = new TextField();
    topicTextField.setPrefWidth(200);
    searchHBox.getChildren().add(topicTextField);
    leftBottomVBox.getChildren().add(searchHBox);
    Button applyTopicButton = new Button("Find Question");
    // TODO
    applyTopicButton.setPrefWidth(180);
    applyTopicButton.setPrefHeight(45);
    leftBottomVBox.getChildren().add(applyTopicButton);
    leftVBox.getChildren().add(leftBottomVBox);


    // 5) Add the left part to the scene
    startLayOut.getChildren().add(leftVBox);



    /* right part of the main scene */
    VBox rightVBox = new VBox();

    // 1) Top label of the right VBox
    Label quizListLabel = new Label("Quiz List");
    quizListLabel.setFont(Font.font(20));
    rightVBox.getChildren().add(quizListLabel);

    // 2) Mid table of the right VBox
    TableView<Question> quizListTable = new TableView<>();
    quizListTable.setEditable(true);
    // set table properties
    quizListTable.setPrefWidth(540);
    quizListTable.setPrefHeight(500);
    // add columns to the table
    TableColumn<Question, CheckBox> quizListSelectCol = new TableColumn<>("Select");
    quizListSelectCol.setCellValueFactory(new PropertyValueFactory<Question, CheckBox>("select"));
    quizListSelectCol.setPrefWidth(60);
    TableColumn<Question, String> quizListTopicCol = new TableColumn<>("Topic");
    quizListTopicCol.setCellValueFactory(new PropertyValueFactory<Question, String>("Topic"));
    quizListTopicCol.setPrefWidth(100);
    TableColumn<Question, String> quizListContentCol = new TableColumn<>("Question");
    quizListContentCol
        .setCellValueFactory(new PropertyValueFactory<Question, String>("questionText"));
    quizListContentCol.setPrefWidth(390);
    quizListTable.getColumns().addAll(quizListSelectCol, quizListTopicCol, quizListContentCol);
    ObservableList<Question> quizListLine = FXCollections.observableArrayList();
    quizListLine.add(new Question());
    quizListTable.setItems(quizListLine);
    rightVBox.getChildren().add(quizListTable);

    // 3) Mid Buttons of the left VBox
    HBox rightMidButtonHBox = new HBox();
    Button rb1 = new Button("Select All");
    // TODO
    rb1.setPrefWidth(280);
    rb1.setPrefHeight(45);
    rightMidButtonHBox.getChildren().add(rb1);
    Button rb2 = new Button("Remove Selected");
    // TODO
    rb2.setPrefWidth(280);
    rb2.setPrefHeight(45);
    rightMidButtonHBox.getChildren().add(rb2);
    rightVBox.getChildren().add(rightMidButtonHBox);

    // 4) Bottom controllers and start quiz
    HBox rightBottomHBox = new HBox();
    rightBottomHBox.setPadding(new Insets(40.0, 25.0, 40.0, 60.0));
    rightBottomHBox.setSpacing(100);
    Label quizQuestionCountLabel = new Label();
    int questionCount = 0;
    quizQuestionCountLabel.setText("Total Questions: " + questionCount);
    quizQuestionCountLabel.setFont(Font.font(18));
    rightBottomHBox.getChildren().add(quizQuestionCountLabel);
    Button startQuizButton = new Button("Start Quiz");
    startQuizButton.setOnAction(e -> primaryStage.setScene(quizScene));
    startQuizButton.setPrefWidth(180);
    startQuizButton.setPrefHeight(45);
    rightBottomHBox.getChildren().add(startQuizButton);
    rightVBox.getChildren().add(rightBottomHBox);

    // 5) Add the right part to the scene
    startLayOut.getChildren().add(rightVBox);



    // Title: Question 01/20
    // Text questionLabel = new Text("Question " + indexOfQuestion + "/" + totalNumOfQuestion);
    Text questionLabel = new Text("questionLabel");
    questionLabel.setFont(Font.font(40));
    HBox titleHBox = new HBox();
    titleHBox.setPrefHeight(100);
    titleHBox.setPrefWidth(1200);
    titleHBox.setAlignment(Pos.CENTER);
    titleHBox.getChildren().add(questionLabel);

    // get questions from the question bank.
    Text questionsText = new Text("questionsText");
    questionsText.setFont(Font.font(20));
    HBox questionHBox = new HBox();
    questionHBox.setPrefWidth(1200);
    questionHBox.setPrefHeight(200);
    questionHBox.setPadding(new Insets(25.0, 30.0, 40.0, 150.0));
    questionHBox.setSpacing(100);
    questionHBox.getChildren().add(questionsText);



    // choices: use for loop to get the choices
    VBox VBoxAnswers = new VBox();
    VBoxAnswers.setPadding(new Insets(25.0, 40.0, 40.0, 150.0));
    VBoxAnswers.setSpacing(40);
    VBoxAnswers.setPrefWidth(1200);
    VBoxAnswers.setPrefHeight(600);
    ToggleGroup group = new ToggleGroup();
    // total number of choices
    int size = 3;
    for (int i = 0; i < size; i++) {
      RadioButton button = new RadioButton("QuestionClass.getAnswers");
      button.setToggleGroup(group);
      button.setSelected(true);
      VBoxAnswers.getChildren().add(button);
    }


    // previous and next button
    Button toPreviousButton = new Button("Previous");
    Button toNextButton = new Button("Next");
    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(25, 0, 25, 300));
    buttonHBox.setPrefWidth(1200);
    buttonHBox.setPrefHeight(100);
    buttonHBox.setSpacing(300);
    buttonHBox.getChildren().add(toPreviousButton);
    buttonHBox.getChildren().add(toNextButton);


    quizLayOut.getChildren().add(titleHBox);
    quizLayOut.getChildren().add(questionHBox);
    quizLayOut.getChildren().add(VBoxAnswers);
    quizLayOut.getChildren().add(buttonHBox);


    quizGeneratorScene.getStylesheets()
        .add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(quizGeneratorScene);
    primaryStage.setTitle("Quiz Generator");
    primaryStage.show();
  }

  public void loadQuestion() {

  }
}
