package application;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {
    try {
      GridPane startScene = new GridPane();
      startScene.setHgap(0);
      startScene.setVgap(0);
      ColumnConstraints leftColumnConstraints = new ColumnConstraints();
      leftColumnConstraints.setPercentWidth(50.0);
      ColumnConstraints rightColumnConstraints = new ColumnConstraints();
      rightColumnConstraints.setPercentWidth(50.0);
      startScene.getColumnConstraints().addAll(leftColumnConstraints, rightColumnConstraints);

      RowConstraints topRowConstraints = new RowConstraints();
      topRowConstraints.setPercentHeight(5);
      RowConstraints midRowConstraints = new RowConstraints();
      midRowConstraints.setPercentHeight(70);
      RowConstraints bottomRowConstraints = new RowConstraints();
      bottomRowConstraints.setPercentHeight(25.0);
      startScene.getRowConstraints().addAll(topRowConstraints, midRowConstraints,
          bottomRowConstraints);

      Text questionListLabel = new Text("Question List");
      questionListLabel.setFont(Font.font(18));
      startScene.add(questionListLabel, 0, 0);
      Text quizListLabel = new Text("Quiz List");
      quizListLabel.setFont(Font.font(20));
      startScene.add(quizListLabel, 1, 0);



      // add table
      TableView questionListTable = new TableView();
      questionListTable.setEditable(true);
      TableColumn questionListSelectCol = new TableColumn("Select");
      TableColumn questionListTopicCol = new TableColumn("Topic");
      TableColumn questionListContentCol = new TableColumn("Question");
      questionListTable.getColumns().addAll(questionListSelectCol, questionListTopicCol,
          questionListContentCol);
      // add buttons
      Button lb1 = new Button("Load Question");
      lb1.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent arg0) {
          FileChooser fileChooser = new FileChooser();
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
          fileChooser.getExtensionFilters().add(extFilter);
          File file = fileChooser.showOpenDialog(primaryStage);
          System.out.println(file);
        }
      });
      Button lb2 = new Button("Select All");
      Button lb3 = new Button("Add Selected");
      HBox leftTableButtons = new HBox(lb1, lb2, lb3);
      VBox leftMid = new VBox(questionListTable, leftTableButtons);
      startScene.add(leftMid, 0, 1);



      // add table
      TableView quizListTable = new TableView();
      quizListTable.setEditable(true);
      TableColumn quizListSelectCol = new TableColumn("Select");
      TableColumn quizTopicSelectCol = new TableColumn("Topic");
      TableColumn quizContentSelectCol = new TableColumn("Question");
      quizListTable.getColumns().addAll(quizListSelectCol, quizTopicSelectCol,
          quizContentSelectCol);
      // add buttons
      Button rb1 = new Button("Select All");
      Button rb2 = new Button("Remove Selected");
      HBox rightTableButtons = new HBox(rb1, rb2);
      VBox rightMid = new VBox(quizListTable, rightTableButtons);
      startScene.add(rightMid, 1, 1);

      GridPane.setHalignment(questionListLabel, HPos.LEFT);
      GridPane.setHalignment(quizListLabel, HPos.LEFT);
      GridPane.setHalignment(questionListTable, HPos.LEFT);
      GridPane.setHalignment(quizListTable, HPos.LEFT);
      GridPane.setValignment(questionListLabel, VPos.BOTTOM);
      GridPane.setValignment(quizListLabel, VPos.BOTTOM);
      GridPane.setValignment(questionListTable, VPos.TOP);
      GridPane.setValignment(quizListTable, VPos.TOP);

      Scene scene = new Scene(startScene, 800, 600);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setTitle("Quiz Generator");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
