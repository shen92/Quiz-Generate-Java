package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class DisplayQuestion {
  private static int indexOfQuestion = 0;
  private static int totalNumOfQuestion = 0;



  public DisplayQuestion(Stage primaryStage) {
    try {
      // Title: Question 01/20
      // Text questionLabel = new Text("Question " + indexOfQuestion + "/" + totalNumOfQuestion);
      Text questionLabel = new Text("纪念比利海林顿");
      questionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 40));
      HBox titleHBox = new HBox();
      titleHBox.setPrefHeight(100);
      titleHBox.setPrefWidth(1200);
      titleHBox.setAlignment(Pos.CENTER);
      titleHBox.getChildren().add(questionLabel);

      // get questions from the question bank.
      Text questionsText = new Text("纪念敬爱的比利-海林顿同志，感谢他给我们带来了无尽的欢乐");
      questionsText.setFont(Font.font("Arial", 20));
      HBox questionHBox = new HBox();
      questionHBox.setPrefWidth(1200);
      questionHBox.setPrefHeight(200);
      questionHBox.setPadding(new Insets(25.0, 30.0, 40.0, 150.0));
      questionHBox.setSpacing(100);
      questionHBox.getChildren().add(questionsText);


      Image image = new Image("file:download.jpg");
      ImageView imageView = new ImageView(image);
      // imageView.setFitHeight(200);//set Height and Width to 200
      // imageView.setFitWidth(200);



      questionHBox.getChildren().add(imageView);


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

      VBox root = new VBox();
      root.getChildren().add(titleHBox);
      root.getChildren().add(questionHBox);
      root.getChildren().add(VBoxAnswers);
      root.getChildren().add(buttonHBox);
      Scene scene = new Scene(root, 1200, 800);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Quiz");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
