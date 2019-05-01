package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * Every ChoiceGroup of a question can be presented as a HashTable.
 * 
 * In the HashTable, the Key is the choice text, and the value is the Choice Status.
 * 
 * The choice status is represented as an inner class with two fields, correctness and is checked
 * 
 * @author Yingjie Shen
 */
public class ChoiceGroup {

  private Hashtable<String, Status> choiceGroup;
  private ToggleGroup toggleGroup;
  private ArrayList<String> keys;// ArrayList for iterating the HashTaable
  private ArrayList<Status> values;

  /**
   * This class represent of the value object of a choice
   */
  private class Status {
    private boolean correctness;// This field represent the correctness of the choice
    private RadioButton radioButton;

    private Status() {
      this.correctness = false;
      this.radioButton = new RadioButton();
      this.radioButton.setSelected(false);
    }

    /**
     * This method updates the correctness of a choice status
     */
    private void setCorrectness(boolean correctness) {
      this.correctness = correctness;
    }

    /**
     * This method updates the select status of a choice status
     */
    private void setSelected(boolean select) {
      this.radioButton.setSelected(select);
    }


    private RadioButton getRadioButton() {
      return this.radioButton;
    }

    private boolean isSelected() {
      return this.radioButton.isSelected();
    }

    private boolean isCorrect() {
      return this.correctness;
    }
  }

  /**
   * Constructor of the ChoiceGroup object
   */
  public ChoiceGroup() {
    this.choiceGroup = new Hashtable<>();
    this.toggleGroup = new ToggleGroup();
    this.keys = new ArrayList<>();
    this.values = new ArrayList<>();
  }

  /*
   * * This method adds a new choice to the choiceGroup
   * 
   * @param choiceText key of a node in HashTable
   * 
   * @param correctness used for updating the correctness field of the value in a node
   */
  public void addChoice(String choiceText, String correctness) {
    Status status = new Status();
    status.getRadioButton().setToggleGroup(toggleGroup);
    if (correctness.equals("T")) {
      status.setCorrectness(true);
    }
    this.choiceGroup.put(choiceText, status);
    this.keys.add(choiceText);
    this.values.add(status);
  }

  /**
   * IF AND ONLY IF status is isSelected and is Correct can return true.
   * 
   * This method is used for count number of correct in the quiz section
   */
  public boolean isCorrect(String choiceText) {
    return this.choiceGroup.get(choiceText).isSelected()
        && this.choiceGroup.get(choiceText).isCorrect();
  }

  /**
   * This method returns if the question is answered(user selected the answer)
   * 
   * @return true if the question is answered
   */
  public boolean isAnswered() {
    for (String key : this.keys) {
      if (this.choiceGroup.get(key).isSelected()) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method returns the number of choices in the choice group
   * 
   * @return int size of the choiceGroup
   */
  public int size() {
    return this.choiceGroup.size();
  }

  /**
   * This method returns the whole choice group of a question.
   * 
   * This method can be called to in the ShowQuestionGUI class
   * 
   * @return HashTabel<String,Status> of a question
   */
  public Hashtable<String, Status> getChoiceGroup() {
    return this.choiceGroup;
  }

  /*
   * This method gets the RadioButton of a choice in a choiceGroup
   * 
   * @param String choiceText key of the choice
   * 
   * @return RadioBox radioBox of the choice
   */
  public RadioButton getRadioButton(String choiceText) {
    return this.choiceGroup.get(choiceText).getRadioButton();
  }

  /**
   * This method clears the select status when the quiz is submitted
   */
  public void reset() {
    for (String key : this.keys) {
      this.choiceGroup.get(key).setSelected(false);
    }
  }

  /**
   * This class is used for external iteration for quiz
   * 
   * @return ArrayList<String> keys of the hastabel
   */
  public ArrayList<String> getChoiceGroupKeys() {
    return this.keys;
  }

}
