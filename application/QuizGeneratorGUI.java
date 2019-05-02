//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: QuizGeneratorGUI class
// Files: ChoiceGroup.java Main.java Question.java QuestionDatabase.java
// QuizGeneratorGUI.java QuizResultsGUI.java TopicRow.java
// Course: CS400
//
// Author: Zhelai Chen, Yingjie Shen, Dongxia Wu, Kerui Wang, Bojun Xu
// Email: zchen743@wisc.edu, shen92@wisc.edu, dwu93@wisc.edu,
// kwang392@wisc.edu, bxu57@wisc.edu
// Lecturer's Name: Deb Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import org.json.simple.parser.ParseException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class QuizGeneratorGUI implements IGUI {
  private Scene quizGeneratorScene;

  //
  // JavaFX Components
  private TableView<TopicRow> topicListTable;
  private Label questionDatabaseCountLabel = new Label();
  TextField numQuestionTextField;

  // Back-End Fields
  private QuestionDatabase questionList;
  private LinkedList<Question> quizQuestions;

  public QuizGeneratorGUI(Stage primaryStage, QuestionDatabase questionList) {
    this.questionList = questionList;
    this.numQuestionTextField = new TextField();
    setup(primaryStage);
  }

  /**
   * This method sets up the start scene of the program
   * 
   * @param Stage primaryStage
   */
  private void setup(Stage primaryStage) {
    VBox root = new VBox();

    // Setups for the Quiz Generator Scene
    HBox upper = new HBox();
    upper.setPadding(new Insets(25.0, 25.0, 0.0, 40.0));
    upper.setSpacing(30);
    upper.getChildren().addAll(addQuestionListComponent(primaryStage), addQuestionFormComponent());
    root.getChildren().add(upper);

    VBox lower = new VBox();
    lower.setPadding(new Insets(10, 25.0, 10.0, 40.0));
    lower.getChildren().addAll(addStartQuizComponent(primaryStage));
    root.getChildren().add(lower);

    primaryStage.setTitle("Quiz Generator");
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
        Label warning1 = new Label("Would you like to exit?");
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

        Button saveButton = addButton("Save File", 80, 20);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            event.consume();
            window.close();
            return;
          }
        });
        buttonHBox.getChildren().add(saveButton);

        Button quitButton = addButton("Exit", 80, 20);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent arg0) {
            System.exit(0);
            return;
          }
        });
        buttonHBox.getChildren().add(quitButton);


        root.getChildren().add(buttonHBox);

        Scene alert = new Scene(root, 320, 180);
        window.setScene(alert);
        window.showAndWait();
      }
    });
    this.quizGeneratorScene = new Scene(root, 1200, 800);

    for (int i = 0; i < questionList.getTopicRows().size(); i++) {
      topicListTable.getItems().add(questionList.getTopicRows().get(i));
      questionList.getTopicRows().get(i).getCheckBox().setSelected(false);
    }
    questionDatabaseCountLabel.setText("Total Questions: " + questionList.getAllQuestion().size());
  }

  /**
   * This method adds the topic list to the quizGeneratorScene
   * 
   * @param Stage primaryStage
   * 
   * @return VBox the left topic list
   * 
   */
  private VBox addQuestionListComponent(Stage primaryStage) {
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

    TableColumn<TopicRow, CheckBox> selectCol = new TableColumn<>("Select");
    selectCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    selectCol.setPrefWidth(60);
    topicListTable.getColumns().add(selectCol);

    TableColumn<TopicRow, String> topicCol = new TableColumn<>("Topic");
    topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
    topicCol.setPrefWidth(300);
    topicListTable.getColumns().add(topicCol);
    TableColumn<TopicRow, Integer> topicQuestionAmountCol = new TableColumn<>("Question Amount");
    topicQuestionAmountCol.setCellValueFactory(new PropertyValueFactory<>("numQuestions"));
    topicQuestionAmountCol.setPrefWidth(180);
    topicListTable.getColumns().add(topicQuestionAmountCol);
    root.getChildren().add(topicListTable);

    // 3) Question Count Label
    questionDatabaseCountLabel.setText("Total Questions: " + 0 /* TODO */);
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
        try {
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
        int totalQuestionNum = 0;
        for (int i = 0; i < questionList.getTopicRows().size(); i++) {
          topicListTable.getItems().add(questionList.getTopicRows().get(i));
          totalQuestionNum += questionList.getTopicRows().get(i).getNumQuestions();
        }
        questionDatabaseCountLabel.setText("Total Questions: " + totalQuestionNum);
        numQuestionTextField.setText("" + questionList.getAllQuestion().size());
      }
    });

    buttonsHBox.getChildren().add(loadDataButton);

    Button selectAllButton = addButton("Select All", 180, 40);
    selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        for (int i = 0; i < questionList.getTopicRows().size(); i++)
          questionList.getTopicRows().get(i).setSelect(true);
      }
    });
    buttonsHBox.getChildren().add(selectAllButton);

    Button unselectAllButton = addButton("Unselect All", 180, 40);
    unselectAllButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        for (int i = 0; i < questionList.getTopicRows().size(); i++)
          questionList.getTopicRows().get(i).setSelect(false);
      }
    });
    buttonsHBox.getChildren().add(unselectAllButton);
    root.getChildren().add(buttonsHBox);

    return root;
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
    imageTextField.setText("none");
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
    metaDataTextField.setText("unused");
    h4.getChildren().add(metaDataTextField);
    getQuestionVBox.getChildren().add(h4);

    Label choicePromptLabel = new Label();
    choicePromptLabel.setText("Choice: (Toggle to set answer)");
    choicePromptLabel.setFont(Font.font(18));
    getQuestionVBox.getChildren().add(choicePromptLabel);

    ToggleGroup group = new ToggleGroup();


    ArrayList<RadioButton> choiceButtons = new ArrayList<>(5);
    ArrayList<TextField> choiceTextFields = new ArrayList<>(5);
    for (int i = 0; i < 5; i++) {
      HBox choice = new HBox();
      choice.setSpacing(10);
      choice.setAlignment(Pos.CENTER_RIGHT);
      choiceButtons.add(new RadioButton());
      choiceButtons.get(i).setToggleGroup(group);
      choiceButtons.get(i).setSelected(false);
      choice.getChildren().add(choiceButtons.get(i));
      Label choiceLabel = new Label();
      choiceLabel.setText((char) ('A' + i) + ": ");
      choiceLabel.setFont(Font.font(18));
      choice.getChildren().add(choiceLabel);
      choiceTextFields.add(new TextField());
      choiceTextFields.get(i).setPrefWidth(450);
      choice.getChildren().add(choiceTextFields.get(i));
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
        // Check question text is not null
        if (questionTextArea.getText().isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Please enter the question content!");
          alert.showAndWait();
          return;
        }
        // Check meat data is not null
        if (metaDataTextField.getText().isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Please enter the meta-data!");
          alert.showAndWait();
          return;
        }
        // check topic is not null
        if (topicTextField.getText().isEmpty()) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText(null);
          alert.setContentText("Please enter the question topic!");
          alert.showAndWait();
          return;
        }
        // all required fields are filled
        newQuestion.setQuestionText(questionTextArea.getText());
        newQuestion.setMetaData(metaDataTextField.getText());
        newQuestion.setTopic(topicTextField.getText());
        if (imageTextField.getText().isEmpty())
          newQuestion.setImage("none");
        else
          newQuestion.setImage(imageTextField.getText());
        int choiceEmptyCount = 0;
        for (int i = 0; i < 5; i++) {
          if (choiceTextFields.get(i).getText().isEmpty() && choiceButtons.get(i).isSelected()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The correct answer has no content!");
            alert.showAndWait();
            return;
          }

          if (!choiceTextFields.get(i).getText().isEmpty()) {
            if (choiceButtons.get(i).isSelected())

              newQuestion.getChoiceGroup().addChoice(choiceTextFields.get(i).getText(), "T");

            else {
              newQuestion.getChoiceGroup().addChoice(choiceTextFields.get(i).getText(), "F");
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

        questionList.addQuestion(newQuestion);
        topicListTable.getItems().clear();
        for (int i = 0; i < questionList.getTopicRows().size(); i++)
          topicListTable.getItems().add(questionList.getTopicRows().get(i));
        questionDatabaseCountLabel
            .setText("Total Questions: " + questionList.getAllQuestion().size());
        numQuestionTextField.setText("" + questionList.getAllQuestion().size());

        questionTextArea.clear();
        topicTextField.clear();
        imageTextField.setText("none");
        metaDataTextField.setText("unused");

        for (int i = 0; i < 5; i++) {
          choiceTextFields.get(i).clear();
          choiceButtons.get(i).setSelected(false);
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
        imageTextField.setText("none");
        metaDataTextField.setText("unused");
        for (int i = 0; i < 5; i++) {
          choiceButtons.get(i).setSelected(false);
          choiceTextFields.get(i).clear();
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
    root.setPadding(new Insets(25, 0, 0, 0));
    root.setSpacing(55);

    HBox buttonHBox = new HBox();
    buttonHBox.setAlignment(Pos.CENTER);
    buttonHBox.setSpacing(120);

    HBox searchHBox = new HBox();// Filter Box

    searchHBox.setSpacing(20);
    searchHBox.setAlignment(Pos.TOP_LEFT);
    Label numOfQuestionLabel = new Label("Number of Questions In Quiz:");
    numOfQuestionLabel.setFont(Font.font(18));
    searchHBox.getChildren().add(numOfQuestionLabel);


    numQuestionTextField.setPrefWidth(200);
    numQuestionTextField.setText("" + questionList.getAllQuestion().size());
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
        int count = 0;
        // TODO
        for (int i = 0; i < questionList.getTopicRows().size(); i++) {
          if (questionList.getTopicRows().get(i).getSelect()) {
            for (int j = 0; j < questionList
                .filteredQuestionList(questionList.getTopicRows().get(i).getTopic()).size(); j++)
              allSelectedTopicQues.add(questionList
                  .filteredQuestionList(questionList.getTopicRows().get(i).getTopic()).get(j));
          }
        }
        int quizQuestionAmount = 0;

        try {
          quizQuestionAmount = Integer.parseInt(numQuestionTextField.getText());
          if (quizQuestionAmount <= 0)
            throw new NumberFormatException();

        } catch (NumberFormatException e) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setHeaderText("Wrong Input!");
          alert.setContentText("Please enter correct number of questions in Quiz!");
          alert.showAndWait();
          return;
        }

        for (int i = 0; i < questionList.getTopicRows().size(); i++) {
          if (questionList.getTopicRows().get(i).getSelect())
            count++;
        }
        if (count < 1) {
          Alert alert = new Alert(AlertType.WARNING);
          alert.setTitle("Warning Dialog");
          alert.setContentText("Please select at least one topic!");
          alert.showAndWait();
          return;
        }

        Random rand = new Random();
        if (quizQuestionAmount > allSelectedTopicQues.size())
          quizQuestionAmount = allSelectedTopicQues.size();
        for (int i = 0; i < quizQuestionAmount; i++) {
          int randomIndex = rand.nextInt(allSelectedTopicQues.size());
          quizQuestions.add(allSelectedTopicQues.get(randomIndex));
          allSelectedTopicQues.remove(randomIndex);
        }

        topicListTable.getItems().clear();

        QuestionDisplayGUI showQuestionGUI =
            new QuestionDisplayGUI(primaryStage, quizQuestions, questionList);
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

}
