package quiz.app;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Question extends JFrame implements ActionListener {
    private JTextField questionF, optionF; // Text fields for question and option inputs
    private JButton nextOptionButton, nextQuestionButton; // Buttons for navigating options and questions
    private JPanel optionsPanel; // Panel to hold option input fields
    private JLabel optionNum, heading; // Label to display current option number
    private ArrayList<String> options = new ArrayList<>(); // List to store options
    private QuestionManager m; // QuestionManager instance to handle question logic
    private JSpinner answerSpinner; // Spinner to select the correct answer
    
    int numOfOptions, currentQuestionNum, totalQs; // Variables for options, question number, and total questions
    int currentOption; // To keep track of the current option number
    int panelWidth = 420; // Width of the options panel
    
    // Constructor for the Question class
    Question(QuestionManager m, int currentQuestionNum, int numOfOptions, int totalQs) {
        this.totalQs = totalQs;
        currentOption = 1;
        this.numOfOptions = numOfOptions;
        this.currentQuestionNum = currentQuestionNum;
        this.m = m;

        setTitle("Question Creation");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        int fH = 600;
        int fW = 900;
        setLayout(null);
        setResizable(false);

        // Background image label
        int left = fW / 2 + fW / 8; // Size of left section
        JLabel background = new JLabel();
        background.setIcon(new ImageIcon(ClassLoader.getSystemResource("img/QuestionMark1.jpeg")));
        background.setBounds(0, 0, fW, fH);
        add(background);

        // Heading label
        heading = new JLabel("Question No. " + currentQuestionNum + "/" + totalQs);
        heading.setBounds(left / 2 - 110, 60, 340, 60);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
        heading.setForeground(Color.BLUE);
        background.add(heading);

        // Frame icon
        ImageIcon frameIcon = new ImageIcon(ClassLoader.getSystemResource("img/quiz.png"));
        Image frameImage = frameIcon.getImage();
        setIconImage(frameImage);

        // Question label
        JLabel questionL = new JLabel("Insert Question Below");
        questionL.setBounds(left / 2 - (210 / 2), 60 + 60 + 10, 210, 50);
        questionL.setFont(new Font("Tahoma", Font.BOLD, 18));
        questionL.setForeground(Color.BLACK);
        background.add(questionL);

        // Question text field
        questionF = new JTextField();
        questionF.setBounds(left / 2 - (420 / 2), 60 + 60 + 10 + 50 + 5, 420, 50);
        questionF.setFont(new Font("Tahoma", Font.BOLD, 18));
        questionF.setForeground(Color.BLACK);
        background.add(questionF);

        // Panel for options
        optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        optionsPanel.setBounds(left / 2 - (panelWidth / 2), 60 + 60 + 10 + 50 + 5 + 80, panelWidth, 250);
        background.add(optionsPanel);
        
        // Option number label
        optionNum = new JLabel("Option " + currentOption);
        optionNum.setFont(new Font("Tahoma", Font.BOLD, 18));
        optionNum.setBounds((panelWidth / 2) - (340 / 2), 20, 340, 50);
        optionNum.setHorizontalAlignment(JLabel.CENTER);
        optionsPanel.add(optionNum);
        
        // Option text field
        optionF = new JTextField();
        optionF.setBounds((panelWidth / 2) - (340 / 2), 20 + 50 + 20, 340, 50);
        optionF.setFont(new Font("Tahoma", Font.BOLD, 18));
        optionF.setForeground(Color.BLACK);
        optionsPanel.add(optionF);
        
        // Spinner for correct answer selection
        answerSpinner = new JSpinner(new SpinnerNumberModel(1, 1, numOfOptions, 1));
        answerSpinner.setBounds((panelWidth / 2) - (340 / 2), 20 + 50 + 20, 340, 50);
        answerSpinner.setVisible(false); // Initially invisible
        optionsPanel.add(answerSpinner);
        JComponent editor = answerSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            JTextField textField = spinnerEditor.getTextField();
            textField.setForeground(Color.WHITE);
            textField.setFont(new Font("Arial", Font.BOLD, 16)); 
            textField.setBackground(new Color(248, 177, 150)); 
            textField.setHorizontalAlignment(JTextField.CENTER);
        }
        
        // Button dimensions and positions
        int buttonX = fW / 2 + fW / 8 - 20;
        int buttonHeight = 40;
        int buttonWidth = 120;
        int gapX = 30;
        int gapY = 30;
        
        // Next option button
        if(numOfOptions == 1)
            nextOptionButton = new JButton("Save Option");
        else
            nextOptionButton = new JButton("Next Option");
        nextOptionButton.setBounds(buttonX, fH - 180, buttonWidth, buttonHeight);
        nextOptionButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        nextOptionButton.addActionListener(this);
        nextOptionButton.setFocusPainted(false);
        background.add(nextOptionButton);
        
        // Next question button
        if(currentQuestionNum == totalQs)
            nextQuestionButton = new JButton("Save Question");
        else
            nextQuestionButton = new JButton("Next Question");
        nextQuestionButton.setBounds(buttonX + buttonWidth + gapX, fH - 180, buttonWidth + 18, buttonHeight);
        nextQuestionButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        nextQuestionButton.addActionListener(this);
        nextQuestionButton.setFocusPainted(false);
        nextQuestionButton.setEnabled(false);
        background.add(nextQuestionButton);
        
        // Add initial option label and text field
        emptyOptionField(currentOption, "option");
        setBounds(width / 2 - fW / 2, height / 2 - fH / 2, fW, fH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Action listener for button clicks
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == nextOptionButton) {
            if (currentOption <= numOfOptions) {
                String opt = optionF.getText();
                if (opt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid option.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                options.add(opt);
                System.out.println(options);
                if (currentOption == numOfOptions) {
                    // Switch to spinner for correct answer
                    emptyOptionField(currentOption + 1, "ans");
                    nextOptionButton.setText("Save Option");
                    optionF.setVisible(false);
                    answerSpinner.setVisible(true); // Make the spinner visible
                    optionsPanel.revalidate();
                    optionsPanel.repaint();
                } else {
                    emptyOptionField(currentOption + 1, "option");
                }
            } else {
                nextOptionButton.setEnabled(false);
                nextQuestionButton.setEnabled(true);
            }
            currentOption++;
        } else if (ae.getSource() == nextQuestionButton) {
            String questionText = questionF.getText();
            int ans = (int) answerSpinner.getValue(); // Retrieve selected answer from spinner
            if (questionText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a valid question.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            QuestionData data = new QuestionData(questionText, options, ans);
            m.questionCompleted(data);
        }
    }

    // Method to reset the option field for the next input
    private void emptyOptionField(int currentOption, String type) {
        // Update the option number label based on the type
        if (type.equals("option")) {
            optionNum.setText("Option " + currentOption + "/" + numOfOptions);
        } else if (type.equals("ans")) {
            optionNum.setText("Select the correct option below");
        }
        // Clear option text field
        optionF.setText("");
        // Revalidate and repaint the options panel to reflect changes
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    
    public void updateForNextQuestion(int currentQuestionIndex) {
        heading.setText("Question No. " + currentQuestionIndex + "/" + totalQs);
        this.currentQuestionNum = currentQuestionIndex;
        this.currentOption = 1;
        this.options.clear(); // Clear options from previous question
        questionF.setText(""); // Clear question text field
        optionF.setVisible(true); // Make option text field visible
        answerSpinner.setVisible(false); // Hide spinner
        emptyOptionField(currentOption, "option"); // Reset option field
        nextOptionButton.setText("Next Option"); // Reset button text
        nextOptionButton.setEnabled(true); // Enable next option button
        nextQuestionButton.setEnabled(false); // Disable next question button (until all options are entered)
      }
}
