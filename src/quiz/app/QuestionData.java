package quiz.app;
import java.io.*;
import java.util.ArrayList;

public class QuestionData implements Serializable {
    private String questionText; // The text of the question
    private ArrayList<String> options; // List of options for the question
    private int ans; // Index of the correct answer
    
    // Constructor to initialize the question text, options, and correct answer index
    public QuestionData(String questionText, ArrayList<String> options, int ans) {
        this.questionText = questionText;
        this.options = options;
        this.ans = ans;
    }

    // Getter for the question text
    public String getQuestionText() {
        return questionText;
    }

    // Getter for the options list
    public ArrayList<String> getOptions() {
        return options;
    }
    
    // Getter for the index of the correct answer
    public int getAns() {
        return ans;
    }
}
