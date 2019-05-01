package application;

import java.util.Hashtable;
import javafx.scene.control.RadioButton;

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
  private Object choiceBox;

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

  public void addChoice(String choiceText, String correctness) {
    Status initStatus = new Status();
    if (correctness.equals("T")) {
      initStatus.setCorrectness(true);
    }
    this.choiceGroup.put(choiceText, initStatus);
  }

  /** IF AND ONLY IF status is isSelected and is Correct can return true */
  public boolean isCorrect(String choiceText) {
    return this.choiceGroup.get(choiceText).isSelected()
        && this.choiceGroup.get(choiceText).isCorrect();

  }
}
