package application;
//

import javafx.scene.control.CheckBox;

public class TopicRow {
  private CheckBox checkBox;
  private String topic;
  private int numQuestions;

  public TopicRow() {
    this.checkBox = new CheckBox();
    this.checkBox.setSelected(false);
    this.topic = null;
    this.numQuestions = 0;
  }

  public CheckBox getCheckBox() {
    return this.checkBox;
  }

  public boolean getSelect() {
    return this.checkBox.isSelected();
  }

  public String getTopic() {
    return this.topic;
  }

  public int getNumQuestions() {
    return this.numQuestions;
  }

  public void setSelect(boolean select) {
    this.checkBox.setSelected(select);
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setNumQuestions(int numQuestions) {
    this.numQuestions = numQuestions;
  }
  
  public boolean equals(Object o) {
      if (!(o instanceof TopicRow)) {
          return false;
      }
      TopicRow other = (TopicRow) o;
      return this.topic.equals(other.getTopic());
  }

  public int hashCode() {
      return topic.hashCode() * numQuestions;
  }
}
