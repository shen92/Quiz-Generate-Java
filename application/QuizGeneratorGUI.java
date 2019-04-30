package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;
import org.json.simple.parser.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class QuizGeneratorGUI {
  private Scene quizGeneratorScene;

  // JavaFX Components
  private TableView<Question> topicListTable;
  private Label questionDatabaseCountLabel = new Label();
  private LinkedList<Question> quizQuestions;

  // Back-End Fields
  private QuestionDatabase questionList;

  public QuizGeneratorGUI(Stage primaryStage) {
    setup(primaryStage);
  }

  /**
   * This method sets up the start scene of the program
   * 
   * @param Stage primaryStage
   */
  private void setup(Stage primaryStage) {
    VBox root = new VBox();
    questionList = new QuestionDatabase();

    // Setups for the Quiz Generator Scene
    HBox upper = new HBox();
    upper.setPadding(new Insets(25.0, 25.0, 40.0, 40.0));
    upper.setSpacing(30);
    upper.getChildren().addAll(addQuestionListComponet(primaryStage), addQuestionFormComponent());
    root.getChildren().add(upper);

    VBox lower = new VBox();
    lower.setPadding(new Insets(25.0, 25.0, 40.0, 40.0));
    lower.getChildren().addAll(addStartQuizComponent(primaryStage));
    root.getChildren().add(lower);

    primaryStage.setTitle("Quiz Generator");

    this.quizGeneratorScene = new Scene(root, 1200, 800);
  }

  /**
   * This method adds the topic list to the quizGeneratorScene
   * 
   * @param Stage primaryStage
   * 
   * @return VBox the left topic list
   * 
   */
  private VBox addQuestionListComponet(Stage primaryStage) {
    VBox root = new VBox();


    // 1) Question List Label
    Label questionListLabel = new Label("Topic List");
    questionListLabel.setFont(Font.font(20));
    questionListLabel.setPadding(new Insets(0, 0, 10, 0));
    root.getChildren().add(questionListLabel);


    // 2) Question Database TableView
    topicListTable = new TableView<>();
    topicListTable.setPrefWidth(540);
    topicListTable.setPrefHeight(400);
    topicListTable.setEditable(true);

    TableColumn<Question, CheckBox> selectCol = new TableColumn<>("Select");
    selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    selectCol.setPrefWidth(60);
    topicListTable.getColumns().add(selectCol);

    TableColumn<Question, String> topicCol = new TableColumn<>("Topic");
    topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
    topicCol.setPrefWidth(300);
    topicListTable.getColumns().add(topicCol);

    TableColumn<Question, Integer> topicQuestionAmountCol = new TableColumn<>("Question Amount");
    topicQuestionAmountCol.setCellValueFactory(new PropertyValueFactory<>(/* TODO NUM FIELD */
        ""));
    topicQuestionAmountCol.setPrefWidth(180);
    topicListTable.getColumns().add(topicQuestionAmountCol);
    root.getChildren().add(topicListTable);

    // 3) Question Count Label
    questionDatabaseCountLabel.setText("Total Questions: " + topicListTable.getItems().size());
    questionDatabaseCountLabel.setPadding(new Insets(25, 0, 10, 0));
    questionDatabaseCountLabel.setFont(Font.font(18));
    root.getChildren().add(questionDatabaseCountLabel);

    // 4) Buttons HBox
    HBox buttonsHBox = new HBox();
    buttonsHBox.setPadding(new Insets(25, 0, 0, 0));

    Button loadDataButton = addButton("Load Data", 180, 40);
    loadDataButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        try {// TODO: DUPLICATE QUESTIONS
          FileChooser fileChooser = new FileChooser();
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
          fileChooser.getExtensionFilters().add(extFilter);
          File jsonFile = fileChooser.showOpenDialog(primaryStage);
          questionList.loadQuestions(jsonFile);
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }
        // Add topics to topic list
        topicListTable.getItems().clear();
        ArrayList<String> currentTopicInList = new ArrayList<String>();
        for (int i = 0; i < questionList.getAllQuestion().size(); i++) {
          if (currentTopicInList.contains(questionList.getAllQuestion().get(i).getTopic()))
            continue;
          addQuestionToQuestionList(questionList.getAllQuestion().get(i));
          currentTopicInList.add(questionList.getAllQuestion().get(i).getTopic());
        }
        questionDatabaseCountLabel
            .setText("Total Questions: " + questionList.getAllQuestion().size());
      }
    });
    buttonsHBox.getChildren().add(loadDataButton);

    Button selectAllButton = addButton("Select All", 180, 40);
    selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        for (Question question : questionList.getAllQuestion())
          question.getCheckBox().setSelected(true);
      }
    });
    buttonsHBox.getChildren().add(selectAllButton);

    Button unselectAllButton = addButton("Unselect All", 180, 40);
    unselectAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        for (Question question : questionList.getAllQuestion())
          question.getCheckBox().setSelected(false);
      }
    });
    buttonsHBox.getChildren().add(unselectAllButton);
    root.getChildren().add(buttonsHBox);

    return root;
  }

  private void addQuestionToQuestionList(Question question) {
    topicListTable.getItems().add(question);

  }

  /**
   * This method adds add the question from to the quizGeneratorScene
   * 
   * @param Stage primaryStage
   * 
   * @return VBox the right add question form
   * 
   */
  private VBox addQuestionFormComponent() {
    VBox root = new VBox();
    // 1) Question List Label
    Label questionListLabel = new Label("Add Question");
    questionListLabel.setFont(Font.font(20));
    root.getChildren().add(questionListLabel);

    // 2) Get Question VBox
    VBox getQuestionVBox = new VBox();
    getQuestionVBox.setPadding(new Insets(26.0, 0.0, 62, 0.0));
    getQuestionVBox.setSpacing(10);

    HBox h1 = new HBox();
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
    h3.setAlignment(Pos.CENTER_RIGHT);
    Label imageLabel = new Label();
    imageLabel.setText("Image:");
    imageLabel.setFont(Font.font(18));
    h3.getChildren().add(imageLabel);
    TextField imageTextField = new TextField();
    imageTextField.setPrefWidth(450);
    h3.getChildren().add(imageTextField);
    getQuestionVBox.getChildren().add(h3);

    HBox h4 = new HBox();
    h4.setSpacing(10);
    h4.setAlignment(Pos.CENTER_RIGHT);
    Label metaDataLabel = new Label();
    metaDataLabel.setText("Meta-data:");
    metaDataLabel.setFont(Font.font(18));
    h4.getChildren().add(metaDataLabel);
    TextField metaDataTextField = new TextField();
    metaDataTextField.setPrefWidth(450);
    h4.getChildren().add(metaDataTextField);
    getQuestionVBox.getChildren().add(h4);

    Label choicePromptLabel = new Label();
    choicePromptLabel.setText("Choice: (Toggle to set answer)");
    choicePromptLabel.setFont(Font.font(18));
    getQuestionVBox.getChildren().add(choicePromptLabel);

    ToggleGroup group = new ToggleGroup();

    TextField[] choiceTextFields = new TextField[5];
    RadioButton[] choiceButtons = new RadioButton[5];
    for (int i = 0; i < 5; i++) {
      HBox choice = new HBox();
      choice.setSpacing(10);
      choice.setAlignment(Pos.CENTER_RIGHT);
      choiceButtons[i] = new RadioButton();
      choiceButtons[i].setToggleGroup(group);
      choiceButtons[i].setSelected(false);
      choice.getChildren().add(choiceButtons[i]);
      Label choiceLabel = new Label();
      choiceLabel.setText((char) ('A' + i) + ": ");
      choiceLabel.setFont(Font.font(18));
      choice.getChildren().add(choiceLabel);
      choiceTextFields[i] = new TextField();
      choiceTextFields[i].setPrefWidth(450);
      choice.getChildren().add(choiceTextFields[i]);
      getQuestionVBox.getChildren().add(choice);
    }

    root.getChildren().add(getQuestionVBox);

    HBox buttonHBox = new HBox();
    buttonHBox.setPadding(new Insets(1.4, 0.0, 0.0, 0.0));
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(80);

    Button confirmButton = addButton("Confirm", 180, 40);
    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO
        Question newQuestion = new Question();
        if (questionTextArea.getText().isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Please enter the question content!");
          alert.showAndWait();
          return;
        }
        if (metaDataTextField.getText().isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Please enter the meta-data!");
          alert.showAndWait();
          return;
        }
        if (topicTextField.getText().isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Please enter the question topic!");
          alert.showAndWait();
          return;
        }
        newQuestion.setQuestionText(questionTextArea.getText());
        newQuestion.setMetaData(metaDataTextField.getText());
        newQuestion.setTopic(topicTextField.getText());
        if (imageTextField.getText().isEmpty())
          newQuestion.setImage("none");
        else
          newQuestion.setImage(imageTextField.getText());
        LinkedHashMap<String, String> choices = new LinkedHashMap<String, String>();
        int choiceEmptyCount = 0;
        for (int i = 0; i < 5; i++) {
          if (!choiceTextFields[i].getText().isEmpty()) {
            if (choiceButtons[i].hasProperties() == true)
            	
              choices.put(choiceTextFields[i].getText(), "T");
            
            else {
              choices.put(choiceTextFields[i].getText(), "F");
            }
          } else {
            choiceEmptyCount++;
          }
          if (choiceEmptyCount > 3) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You must enter at least two choices!");
            alert.showAndWait();
            return;
          }
          if (group.getSelectedToggle() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You must select one correct answer!");
            alert.showAndWait();
            return;
          }
        }
        newQuestion.setChoice(choices);
        if (questionList == null) {
          questionList = new QuestionDatabase();
          questionList.addQuestion(newQuestion);
          addQuestionToQuestionList(newQuestion);
        } else {
          questionList.addQuestion(newQuestion);
          addQuestionToQuestionList(newQuestion);
        }
      }
    });
    buttonHBox.getChildren().add(confirmButton);

    Button resetButton = addButton("Reset", 180, 40);
    resetButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        questionTextArea.clear();
        topicTextField.clear();
        imageTextField.clear();
        for (int i = 0; i < 5; i++) {
          choiceButtons[i].setSelected(false);
          choiceTextFields[i].clear();
        }
      }
    });
    buttonHBox.getChildren().add(resetButton);

    root.getChildren().add(buttonHBox);

    return root;
  }

  /**
   * This method adds save to file and start quiz to the quizGeneratorScene
   * 
   * @param Stage primaryStage
   * 
   * @return VBox
   * 
   */
  private VBox addStartQuizComponent(Stage primaryStage) {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(20, 0, 0, 0));

    HBox buttonHBox = new HBox();
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(120);

    HBox searchHBox = new HBox();// Filter Box

    searchHBox.setSpacing(20);
    searchHBox.setAlignment(Pos.TOP_LEFT);
    Label numOfQuestionLabel = new Label("Number of Questions In Quiz:");
    numOfQuestionLabel.setFont(Font.font(18));
    searchHBox.getChildren().add(numOfQuestionLabel);

    TextField numQuestionTextField = new TextField();
    numQuestionTextField.setPrefWidth(200);
    searchHBox.getChildren().add(numQuestionTextField);
    root.getChildren().add(searchHBox);

    Button saveToFileButton = addButton("Save to File", 180, 40);
    Tooltip saveToFileTooltip = new Tooltip();
    saveToFileTooltip.setText("Save all questions in topic list to file");
    saveToFileButton.setTooltip(saveToFileTooltip);
    saveToFileButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO
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

    buttonHBox.getChildren().add(saveToFileButton);

    Button startQuizButton = addButton("Start Quiz", 180, 40);
    Tooltip startQuizTooltip = new Tooltip();
    startQuizTooltip.setText("Add selected topic questions and start quiz");
    startQuizButton.setTooltip(startQuizTooltip);
    startQuizButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        quizQuestions = new LinkedList<Question>();
        ArrayList<Question> allSelectedTopicQues = new ArrayList<Question>();
        // TODO
        for (int i = 0; i < questionList.getAllQuestion().size(); i++) {
          if (questionList.getAllQuestion().get(i).getSelected()) {
            questionList.checkUnselected(questionList.getAllQuestion().get(i).getTopic());
            allSelectedTopicQues.add(questionList.getAllQuestion().get(i));
          }
        }
        int quizQuestionAmount = Integer.parseInt(numQuestionTextField.getText());
        Random rand = new Random();
        if (quizQuestionAmount > allSelectedTopicQues.size())
          quizQuestionAmount = allSelectedTopicQues.size();
        for (int i = 0; i < quizQuestionAmount; i++) {
          int randomIndex = rand.nextInt(allSelectedTopicQues.size());
          quizQuestions.add(allSelectedTopicQues.get(randomIndex));
          allSelectedTopicQues.remove(randomIndex);
        }

        ShowQuestionGUI showQuestionGUI = new ShowQuestionGUI(primaryStage, quizQuestions);
        primaryStage.setScene(showQuestionGUI.getScene());
        primaryStage.setTitle("Quiz");
      }
    });
    buttonHBox.getChildren().add(startQuizButton);

    root.getChildren().add(buttonHBox);

    return root;
  }


  public Scene getScene() {
    return this.quizGeneratorScene;
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
