package application;

public class QuestionNode {
	Question question = null;
	QuestionNode next;
	QuestionNode pre;
	
     public QuestionNode(Question question) {
    	 this.question = question;
    	 this.next = null;
    	 
     }
     
     
     public QuestionNode getNext() {
    	 return next;
     }
     
     public QuestionNode getPrev() {
    	 return pre;
     }
     
     public Question getQuestion() {
    	 return question;
     }
     
     public boolean hasNext() {
    	 if(next != null) {
    		 return true;
    	 }else return false;
     }
     
     public  boolean hasPrev() {
    	 if(pre != null)
    		 return true;
    	 return false;
     }
}


