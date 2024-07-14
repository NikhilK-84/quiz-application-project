package quiz.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class QuestionManager {
    private ArrayList<QuestionData> questionList;
    private int numOfQuestions;
    private int currentQuestionIndex;
    private int numOfOptions;
    private String quizName;
    private ArrayList<String> savedQuizzes;
    private static final String FILE_NAMES_PATH = "user_saved_quizzes/saved_quizzes.ser";

    public QuestionManager(String quizName, int numOfQuestions, int numOfOptions, ArrayList<String> savedQuizzes) {
        this.numOfQuestions = numOfQuestions;
        this.numOfOptions = numOfOptions;
        this.quizName = quizName;
        this.savedQuizzes = savedQuizzes;
        questionList = new ArrayList<>();
        currentQuestionIndex = 1;
        nextQuestion();
    }

    public void addQuestion(QuestionData q) {
        questionList.add(q);
    }

    public void nextQuestion() {
        if (currentQuestionIndex <= numOfQuestions) {
            Question currentQuestion = new Question(this, currentQuestionIndex, numOfOptions, numOfQuestions);
            currentQuestionIndex++;
        } else {
            // Save quiz to file
            saveQuizState(quizName, questionList);
            System.out.println("All Questions Created");
            JOptionPane.showMessageDialog(null, "Quiz Created Successfully!");
            new MainMenu();
        }
    }

    public void saveQuizState(String name, ArrayList<QuestionData> data) {
        String directoryPath = "user_saved_quizzes";
        String filename = directoryPath + "/" + name + ".ser";
        
        try {
            // Create the directory if it doesn't exist
            Path directory = Paths.get(directoryPath);
            Files.createDirectories(directory);
            
            // Write quiz data to file
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(data);
                savedQuizzes.add(filename); // Add the file name to the list of saved quizzes
                serializeFileNames(); // Serialize the updated list of file names
                System.out.println("Quiz saved successfully: " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
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
        nextQuestion();
    }
}
