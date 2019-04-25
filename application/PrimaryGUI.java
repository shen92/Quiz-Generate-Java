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
  private int questionDatabaseSize;

  @SuppressWarnings("unchecked")
  public PrimaryGUI(Stage primaryStage) {
    this.questionDatabase = new QuestionDatabase();
    this.questionDatabaseSize = questionDatabase.getQuestionNum();

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
    questionListContentCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
    questionListContentCol.setPrefWidth(370);
    questionListTable.getColumns().addAll(questionListSelectCol, questionListTopicCol,
        questionListContentCol);
    leftVBox.getChildren().add(questionListTable);
    questionListTable.getItems().add(new Question("Math", "1+1 = ?"));
    

    // 3) Mid Buttons of the left VBox
    HBox leftMidButtonHBox = new HBox();
    // 3a) Load Data Button
    Button lb1 = new Button("Load Data");
    lb1.setPrefWidth(135);
    lb1.setPrefHeight(40);
    lb1.setOnAction(new EventHandler<ActionEvent>() {// event for load data
      @Override
      public void handle(ActionEvent arg0) {
        try {
          FileChooser fileChooser = new FileChooser();
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("JSON files (*.JSON)", "*.JSON");
          fileChooser.getExtensionFilters().add(extFilter);
          File jsonFile = fileChooser.showOpenDialog(primaryStage);
          // TODO
          questionDatabase.loadQuestions(jsonFile);  

          for(int i = 0; i < questionDatabase.getAllQuestion().size(); i++) {
            questionListTable.getItems().add(questionDatabase.getAllQuestion().get(i));
          }
  
          questionDatabaseSize = questionDatabase.getQuestionNum();
          System.out.println(questionDatabase.getQuestionNum());
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
    questionDatabaseCountLabel.setText("Total Questions: " + questionDatabaseSize);
    questionDatabaseCountLabel.setFont(Font.font(18));
    leftVBox.getChildren().add(questionDatabaseCountLabel);
    // 5) Filter by Topic
    VBox leftBottomVBox = new VBox();
    leftBottomVBox.setPadding(new Insets(50.0, 0.0, 0.0, 0.0));
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
    quizListTable.getItems().remove(0);
    quizListTable.getItems().add(new Question("Math", "1+1 = ?"));

    // 3) Mid Buttons of the left VBox
    HBox rightMidButtonHBox = new HBox();
    Button rb1 = new Button("Select All");
    // TODO
    rb1.setPrefWidth(280);
    rb1.setPrefHeight(40);
    rightMidButtonHBox.getChildren().add(rb1);
    Button rb2 = new Button("Remove Selected");
    // TODO
    rb2.setPrefWidth(280);
    rb2.setPrefHeight(40);
    rightMidButtonHBox.getChildren().add(rb2);
    rightVBox.getChildren().add(rightMidButtonHBox);

    // 4) Bottom controllers and start quiz
    HBox rightBottomHBox = new HBox();
    rightBottomHBox.setPadding(new Insets(80.0, 25.0, 40.0, 60.0));
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



    int indexOfQuestion = 1;
    int totalNumOfQuestion = 20;
    // Title: Question 01/20
    HBox titleHBox = new HBox();
    titleHBox.setPadding(new Insets(40.0, 25.0, 40.0, 60.0));
    titleHBox.setPrefHeight(80);
    titleHBox.setAlignment(Pos.CENTER);
    Text questionLabel = new Text("Question " + indexOfQuestion + "/" + totalNumOfQuestion);
    questionLabel.setFont(Font.font(40));
    titleHBox.getChildren().add(questionLabel);

    // get questions from the question bank.
    HBox questionHBox = new HBox();
    questionHBox.setPadding(new Insets(25.0, 40.0, 40.0, 150.0));
    questionHBox.setPrefHeight(150);
    questionHBox.setAlignment(Pos.TOP_LEFT);
    questionHBox.setSpacing(100);
    Text questionsText = new Text("1 + 1 = ?");
    questionsText.setFont(Font.font(20));
    questionHBox.getChildren().add(questionsText);

    // choices: use for loop to get the choices
    VBox answersVBox = new VBox();
    answersVBox.setPadding(new Insets(25.0, 40.0, 40.0, 150.0));
    answersVBox.setPrefHeight(400);
    answersVBox.setAlignment(Pos.TOP_LEFT);
    answersVBox.setSpacing(40);
    ToggleGroup group = new ToggleGroup();
    // total number of choices
    int size = 3;
    for (int i = 0; i < size; i++) {
      RadioButton button = new RadioButton("" + (i + 1));
      button.setToggleGroup(group);
      button.setFont(Font.font(18));
      button.setSelected(true);
      answersVBox.getChildren().add(button);
    }


    // previous and next button
    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(25.0, 40.0, 40.0, 80.0));
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(300);
    Button toPreviousButton = new Button("Previous");
    toPreviousButton.setPrefWidth(135);
    toPreviousButton.setPrefHeight(45);
    buttonHBox.getChildren().add(toPreviousButton);
    Button toNextButton = new Button("Next");
    toNextButton.setPrefWidth(135);
    toNextButton.setPrefHeight(45);
    buttonHBox.getChildren().add(toNextButton);



    quizLayOut.getChildren().add(titleHBox);
    quizLayOut.getChildren().add(questionHBox);
    quizLayOut.getChildren().add(answersVBox);
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
