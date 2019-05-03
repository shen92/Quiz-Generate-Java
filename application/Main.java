//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
// Title: Quiz Generator
//
// Files: ChoiceGroup.java, IChoiceGroup.java, IGUI.java, IQuestion.java,
// IQuestionDatabase.java, ITopicR.java, Main.java, Question.java,
// QuestionDatabase.java, QuestionDisplayGUI.java, QuizGeneratorGUI.java,
// QuizResultsGUI.java, TopicRow.java, application.css
//
// Course: CS400 Spring 2019 Sec 001 & 002
//
// Author, E-mail:
// Bojun Xu, bxu57@wisc.edu
// Yingjie Shen, shen92@wisc.edu
// Kerui Wang, kwang392@wisc.edu
// Dongxia Wu, dwu93@wisc.edu
// Zhelai Chen, zchen743@wisc.edu
//
// Lecturer's Name: Deb Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class tests the scene of the program
 * 
 * @author Yingjie Shen
 */

public class Main extends Application {
  private QuizGeneratorGUI quizGeneratorGUI;
  private QuestionDatabase questionList;

  @Override
  public void start(Stage primaryStage) {
    try {
      setup(primaryStage);
      primaryStage.setScene(quizGeneratorGUI.getScene());
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (String to : questionList.getAllTopic()) {
      System.out.print(to);
    }

  }

  private void setup(Stage primaryStage) {
    this.questionList = new QuestionDatabase();
    this.quizGeneratorGUI = new QuizGeneratorGUI(primaryStage, questionList);
  }

  //
  public static void main(String[] args) {
    launch(args);
  }
}
