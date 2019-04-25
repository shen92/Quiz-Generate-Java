package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Question {
  private String meta_data;
  private String text;
  private HashMap<String, String> answer;
  private String imageFile;

  /**
   * Default no-arg constructor
   */
  public Question() {

  }

  public void setMetaData(String metaData) {
    meta_data = metaData;
  }
  public void setContent(String text) {
    this.text = text;
  }
  
  public void setChoice(HashMap<String, String> answer) {
    this.answer = answer;
  }
  
  public void setImage(String imageFile) {
    this.imageFile = imageFile;
  }
}
