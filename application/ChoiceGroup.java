package application;

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
  // Fields of a Choice in the Choice Group

  private String choiceText;// key of a choice
  private boolean isCorrect;


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
  }

  /*
   * * This method adds a new choice to the choiceGroup
   * 
   * @param choiceText key of a node in HashTable
   * 
   * @param correctness used for updating the correctness field of the value in a node
   */
  public void addChoice(String choiceText, String correctness) {
    Status initStatus = new Status();
    if (correctness.equals("T")) {
      initStatus.setCorrectness(true);
    }
    this.choiceGroup.put(choiceText, initStatus);
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
   */
  public boolean isAnswered() {
    Enumeration<Status> choiceStatus = this.choiceGroup.elements();
    while (choiceStatus.hasMoreElements()) {
      if (choiceStatus.nextElement().isSelected()) {
        return true;
      }
    }
    return false;
  }
}
