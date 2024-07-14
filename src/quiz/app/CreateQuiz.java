package quiz.app;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CreateQuiz extends JFrame implements ActionListener {
    // GUI components
    JTextField quizNameF;
    JSpinner numOfQuestionsSpin, numOfOptionsSpin;
    JButton submit, back;

    // Constructor to set up the Create Quiz frame
    CreateQuiz(ArrayList<String> savedQuizzes) {
        setResizable(false);
        // Set title and dimensions
        setTitle("Quiz Creation");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        int fH = 600;
        int fW = 483 * 2;

        // Set layout
        setLayout(null);

        // Add background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/QuestionMark2.jpg"));
        JLabel i11 = new JLabel(i1);
        i11.setBounds(0, 0, 483, fH); // image dimensions
        add(i11);

        // Set frame icon
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("img/quiz.png"));
        Image i22 = i2.getImage();
        setIconImage(i22);

        // Add heading
        JLabel heading = new JLabel("Quiz Details");
        heading.setBounds(fW - fW / 4 - 100, 80, 200, 60);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
        heading.setForeground(Color.BLUE);
        add(heading);

        // Create and customize panel
        JPanel p1 = new JPanel();
        p1.setBounds(fW / 2, 0, fW / 2, fH);
        p1.setBackground(new Color(246, 114, 128));
        p1.setLayout(null);

        // Define positions and dimensions for labels and fields
        int buttonWidth = 240;
        int buttonHeight = 60;
        int buttonX = fW / 4 - buttonWidth / 2; // Changed to be relative to the JPanel
        int initialY = 80 + 60 + 20;
        int gapY = 70;

        int labelWidth = 170;
        int labelHeight = 50;
        int labelX = fW / 8 - labelWidth / 2;
        int gapX = 5;

        int fieldWidth = 200;
        int fieldHeight = labelHeight;

        // Add Quiz Name label and field
        JLabel quizNameL = new JLabel("Quiz Name:");
        quizNameL.setFont(new Font("Tahoma", Font.BOLD, 18));
        quizNameL.setForeground(Color.WHITE);
        quizNameL.setBounds(labelX, initialY + 0 * gapY, labelWidth, labelHeight);
        quizNameF = new JTextField();
        quizNameF.setBackground(new Color(248, 177, 150));
        quizNameF.setHorizontalAlignment(JTextField.CENTER);
        quizNameF.setFont(new Font("Arial", Font.BOLD, 16));
        quizNameF.setBounds(labelX + labelWidth + gapX, initialY + 0 * gapY, fieldWidth, fieldHeight);
        p1.add(quizNameL);
        p1.add(quizNameF);

        // Add Number of Questions label and spinner
        JLabel numOfQuestionL = new JLabel("Total Questions:");
        numOfQuestionL.setFont(new Font("Tahoma", Font.BOLD, 18));
        numOfQuestionL.setForeground(Color.WHITE);
        numOfQuestionL.setBounds(labelX, initialY + 1 * gapY, labelWidth, labelHeight);
        numOfQuestionsSpin = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        numOfQuestionsSpin.setBounds(labelX + labelWidth + gapX, initialY + 1 * gapY, fieldWidth, fieldHeight);
        customizeSpinner(numOfQuestionsSpin);
        p1.add(numOfQuestionL);
        p1.add(numOfQuestionsSpin);

        // Add Number of Options per Question label and spinner
        JLabel numOfOptionsL = new JLabel("Opts/Question:");
        numOfOptionsL.setFont(new Font("Tahoma", Font.BOLD, 18));
        numOfOptionsL.setForeground(Color.WHITE);
        numOfOptionsL.setBounds(labelX, initialY + 2 * gapY, labelWidth, labelHeight);
        numOfOptionsSpin = new JSpinner(new SpinnerNumberModel(2, 1, 50, 1));
        numOfOptionsSpin.setBounds(labelX + labelWidth + gapX, initialY + 2 * gapY, fieldWidth, fieldHeight);
        customizeSpinner(numOfOptionsSpin);
        p1.add(numOfOptionsL);
        p1.add(numOfOptionsSpin);

        // Add submit button
        submit = new JButton("Create");
        submit.setForeground(Color.WHITE);
        submit.setBounds(buttonX, initialY + 3 * gapY, buttonWidth, buttonHeight);
        submit.setBackground(new Color(108, 91, 123));
        submit.setFont(new Font("Tahoma", Font.BOLD, 20));
        submit.addActionListener(this);
        p1.add(submit);

        // Add back button
        back = new JButton("Cancel");
        back.setForeground(Color.WHITE);
        back.setBounds(buttonX, initialY + 4 * gapY, buttonWidth, buttonHeight);
        back.setBackground(new Color(108, 91, 123));
        back.setFont(new Font("Tahoma", Font.BOLD, 20));
        back.addActionListener(this);
        p1.add(back);

        // Add panel to frame
        add(p1);

        // Set frame background and dimensions
        getContentPane().setBackground(Color.WHITE);
        setBounds(width / 2 - fW / 2, height / 2 - fH / 2, fW, fH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Customize spinner appearance
    private void customizeSpinner(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            JTextField textField = spinnerEditor.getTextField();
            textField.setForeground(Color.BLUE); // Change text color
            textField.setFont(new Font("Arial", Font.BOLD, 16)); // Change font and size
            textField.setBackground(new Color(248, 177, 150)); // Change background color
            textField.setHorizontalAlignment(JTextField.CENTER); // Center-align text
        }
    }

    // Handle button actions
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            // Get input values
            String quizName = quizNameF.getText().trim(); // Trim to remove leading/trailing spaces
            int numOfQuestions = (int) numOfQuestionsSpin.getValue();
            int numOfOptions = (int) numOfOptionsSpin.getValue();

            // Validate quiz name
            if (quizName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quiz name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String regexPattern = "^[a-zA-Z0-9_]+$";
            if (!quizName.matches(regexPattern) || Character.isDigit(quizName.charAt(0))) {
                JOptionPane.showMessageDialog(this, "Quiz name '" + quizName + "' should not start with numbers and contain any symbols.\nPlease choose an alphanumeric name.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Deserialize saved_quizzes to check for existing quiz names
            ArrayList<String> existingQuizzes = deserializeSavedQuizzes();

            // Check if quizName already exists
            for (String name : existingQuizzes) {
                if (name.startsWith("user_saved_quizzes/" + quizName)) {
                    JOptionPane.showMessageDialog(this, "Quiz name '" + quizName + "' already exists. Please choose a different name.", "Duplicate Quiz Name", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // If quiz name is unique, proceed to create the quiz
            new QuestionManager(quizName, numOfQuestions, numOfOptions, existingQuizzes);
            dispose();
        } else if (ae.getSource() == back) {
            dispose();
            new MainMenu();
        }
    }

    // Deserialize saved quizzes from file
    private ArrayList<String> deserializeSavedQuizzes() {
        ArrayList<String> existingQuizzes = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_saved_quizzes/saved_quizzes.ser"))) {
            existingQuizzes = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, ignore and return empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }

        return existingQuizzes;
    }
}
