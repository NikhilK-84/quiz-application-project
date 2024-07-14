package quiz.app;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;
public class QuestionManager {
    private ArrayList<QuestionData> questionList;
    private int numOfQuestions;
    private int currentQuestionIndex;
    private int numOfOptions;
    private String quizName; 
    private ArrayList<String> savedQuizzes;
    private String FILE_NAMES_PATH = "user_saved_quizzes/saved_quizzes.ser";
    private Question currentQuestion;
    
    public QuestionManager(String quizName, int numOfQuestions, int numOfOptions, ArrayList<String> savedQuizzes){
        this.numOfQuestions = numOfQuestions;
        this.numOfOptions = numOfOptions;
        this.quizName = quizName;
        this.savedQuizzes = savedQuizzes;
        questionList = new ArrayList<>();
        currentQuestionIndex = 1;
        nextQuestion();
    }
    
    public void addQuestion(QuestionData q){
        questionList.add(q);
    }
    
    public void nextQuestion() {
        if (currentQuestionIndex <= numOfQuestions) {
            if (currentQuestion == null) {
                currentQuestion = new Question(this, currentQuestionIndex, numOfOptions, numOfQuestions);
            } else {
                currentQuestion.updateForNextQuestion(currentQuestionIndex);
            }
        } else {
          // save to file
            saveQuizState(quizName, questionList);
            System.out.println("All Questions Created");
            JOptionPane.showMessageDialog(currentQuestion, "Quiz Created Sucessfully!");
            currentQuestion.dispose();
            new MainMenu();
        }
      }
    
    public void saveQuizState(String name, ArrayList<QuestionData> data){
        String filename = "user_saved_quizzes/"+ name + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))){
            out.writeObject(data);
            savedQuizzes.add(filename); // Add the file name to the list of saved quizzes
            serializeFileNames(); // Serialize the updated list of file names
            System.out.println("Quiz saved successfully: " + filename);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private void serializeFileNames() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAMES_PATH))) {
            out.writeObject(savedQuizzes);
            System.out.println("File names saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void questionCompleted(QuestionData questionData) {
        addQuestion(questionData);
        currentQuestionIndex++;
        nextQuestion();
    }
}
