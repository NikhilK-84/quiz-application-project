package quiz.app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class Play extends JFrame implements ActionListener {
    JButton saveAndNext, exitButton, startButton;
    JTextArea questArea;
    ArrayList<QuestionData> questionList;
    ArrayList<JRadioButton> optionButtons;
    ButtonGroup optionsGroup;
    JPanel optionsPanel, p1;
    JScrollPane optionsScrollPane;
    Timer timer;
    String quizName;
    int questPanelWidth, questPanelHeight, loopIdx;
    JLabel timeL, minL, secL, displayQuestion;
    JSpinner minuteS, secondS, modeChoice;
    public static String selectedMode;
    Integer time = null;
    Integer[] maxTimeMinutes = {12, 45}; // mode 3, mode 2
    Integer maxMinutes = 45;
    ArrayList<Integer> selectedAns = new ArrayList<>();
    Play(ArrayList<QuestionData> questionList, String quizName) {
        this.quizName = quizName;
        this.questionList = questionList;
        setTitle("Playing");
        loopIdx = 0;
        this.questionList = questionList;
        setTitle("Quiz Time");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        int fH = 600;
        int fW = 418 * 2 + 418 / 2;
        setLayout(null);
        setResizable(false);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/Hourglass.jpg"));
        JLabel i11 = new JLabel(i1);
        i11.setBounds(0, 0, 418, fH); // image dimensions
        add(i11);

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("img/quiz.png"));
        Image i22 = i2.getImage();
        setIconImage(i22);

        int p1Width = 418 + 418 / 2;
        JLabel heading = new JLabel("Quiz Time: Put Your Knowledge to the Test!");
        heading.setBounds(p1Width / 2 - (520 / 2), 25, 520, 25);
        heading.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);

        JLabel quizTitle = new JLabel("Playing: " + quizName);
        quizTitle.setBounds(p1Width - 40 - 200, 50 + 15, 200, 25);
        quizTitle.setHorizontalAlignment(JLabel.CENTER);
        quizTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        quizTitle.setForeground(new Color(89, 38, 147));

        p1 = new JPanel();
        p1.setBounds(418, 0, p1Width, fH);
        p1.setBackground(new Color(179, 139, 109));
        p1.setLayout(null); // Ensure layout is set for nested components
        p1.add(heading);
        p1.add(quizTitle);

        questPanelWidth = p1Width - 100;
        questPanelHeight = 120;
        int initialQuestPanelHeight = 240 + 30 + 15;
        questArea = new JTextArea(); // Create JTextArea
        questArea.setBackground(new Color(46, 21, 3));
        questArea.setForeground(Color.WHITE);
        questArea.setFont(new Font("Tahoma", Font.BOLD, 20));
        questArea.setEditable(false); // Make it non-editable
        questArea.setLineWrap(true); // Enable line wrapping
        questArea.setWrapStyleWord(true); // Wrap on word boundaries
        questArea.setBounds(40, 105, questPanelWidth, initialQuestPanelHeight);
        questArea.setText("""
                          Before you begin the quiz, you can choose the mode of play: 
                          1. Relaxed Mode  (Timer is Off.)
                          2. Timed Mode (The quiz is timed. Spend your time however you want.)
                          3. Timed Mode+ (The questions are timed.)
                          
                          Note: You cannot change the mode after starting the quiz.""");
        Insets insets = new Insets(10, 15, 10, 15);
        questArea.setMargin(insets);

        // Options Panel
        optionsPanel = new JPanel();
        optionsPanel.setOpaque(false); // Ensures the panel can be translucent
        optionsPanel.setBackground(new Color(0, 0, 0, 128)); // alpha = translucent
        optionsPanel.setLayout(new GridLayout(0, 1)); // Flexible number of rows

        // Scroll Pane for Options Panel
        optionsScrollPane = new JScrollPane(optionsPanel);
        optionsScrollPane.setBounds(40, 240, questPanelWidth, 150);
        optionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        optionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        optionsScrollPane.setOpaque(false); // Ensures the scroll pane can be translucent
        optionsScrollPane.getViewport().setOpaque(false); // Ensures the viewport can be translucent
        optionsScrollPane.getViewport().setBackground(new Color(0, 0, 0, 128));
        optionsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(46, 21, 3), 2));
        JScrollBar verticalScrollBar = optionsScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(15); // mouse scroll speed
        optionsScrollPane.setVisible(false);
        p1.add(optionsScrollPane);

        optionButtons = new ArrayList<>();
        optionsGroup = new ButtonGroup();
        int numOfOptions = questionList.get(0).getOptions().size();
        for (int i = 0; i < numOfOptions; i++) {
            JRadioButton optionButton = new JRadioButton();
            optionButton.setBackground(new Color(46, 21, 3));
            optionButton.setForeground(Color.WHITE);
            optionButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
            optionButton.setOpaque(false);
            optionButton.setBackground(new Color(0, 0, 0, 128));
            optionsGroup.add(optionButton);
            optionsPanel.add(optionButton);
            optionButtons.add(optionButton);
        }

        startButton = new JButton("Start");
        saveAndNext = new JButton("Save & Next");
        exitButton = new JButton("Exit Quiz");

        int buttonWidth = 158;
        int buttonHeight = 40;
        int rightBorderGap = 62;
        int downBorderGap = 40;
        int verticalGap = 70;
        int buttonX = p1Width - buttonWidth - rightBorderGap; // Changed to be relative to the JPanel
        int initialY = fH - buttonHeight * 2 - downBorderGap - verticalGap;

        saveAndNext.setBounds(buttonX, initialY, buttonWidth, buttonHeight);
        saveAndNext.setBackground(new Color(165, 11, 94));
        saveAndNext.setFont(new Font("Tahoma", Font.BOLD, 20));
        saveAndNext.setForeground(Color.WHITE);
        saveAndNext.addActionListener(this);

        exitButton.setBounds(buttonX, initialY + verticalGap, buttonWidth, buttonHeight);
        exitButton.setBackground(new Color(0, 0, 0));
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        exitButton.setFocusPainted(false);

        startButton.setBounds(buttonX, initialY, buttonWidth, buttonHeight);
        startButton.setBackground(new Color(0, 0, 0));
        startButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
        startButton.setFocusPainted(false);

        p1.add(startButton);
        saveAndNext.setVisible(false);
        p1.add(saveAndNext);
        p1.add(exitButton);
        
        setupTimeSpinners();
        initializeTimers(40, initialY + verticalGap, buttonWidth+90, buttonHeight);
        
        displayQuestion = new JLabel();
        displayQuestion.setBounds(40, 50 + 15, 200, 25);
        displayQuestion.setHorizontalAlignment(JLabel.CENTER);
        displayQuestion.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        displayQuestion.setForeground(new Color(89, 38, 147));
        displayQuestion.setOpaque(false);
        displayQuestion.setVisible(false);
        
        p1.add(displayQuestion); 
        p1.add(questArea);
        p1.add(timeL);
        add(p1);
        getContentPane().setBackground(Color.WHITE);
        setBounds(width / 2 - fW / 2, height / 2 - fH / 2, fW, fH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == startButton) {
            questArea.setBounds(40, 105, questPanelWidth, questPanelHeight);
            startButton.setVisible(false);
            saveAndNext.setVisible(true);
            optionsPanel.setVisible(true);
            displayQuestion.setVisible(true);
            optionsScrollPane.setVisible(true);
            int min = (int) minuteS.getValue();
            int sec = (int) secondS.getValue();
            time = min * 60 + sec;
            if(selectedMode.equals("Timer On+")) {                
                timeL.setVisible(true);
                updatePanelLoopPlus(loopIdx, false);
            } else if (selectedMode.equals("Timer On")){
                timeL.setVisible(true);
                startTimer(this, time);
                updatePanelLoop(loopIdx);
            } else {
                updatePanelLoopPlus(loopIdx, false);
            }
            
        } else if (ae.getSource() == saveAndNext) {
            if(selectedMode.equals("Timer On+") && timer != null && timer.isRunning()){
                timer.stop(); // Stop the timer if running
            }
            int ans = saveResponse(optionButtons);
            if(ans == -1){
                JOptionPane.showMessageDialog(this, "You must select an option before proceeding.", "No Option Selected.", JOptionPane.WARNING_MESSAGE);
                return;
            }
            selectedAns.add(ans);
            updatePanelLoopPlus(loopIdx, false);
        } else if (ae.getSource() == exitButton) {
            if (timer != null && timer.isRunning()) {
                timeL.setText("");
                timer.stop(); // Stop the timer if running
            }
            dispose(); // Close the quiz window
            new MainMenu(); // Return to main menu
        }
    }

    public void startTimerPlus(int time) {
        int delay = 1000; // 1 second delay
        final int[] timeRemaining = {time}; // effectively-final variable
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeRemaining[0]--;
                int min = timeRemaining[0] / 60;
                int sec = timeRemaining[0] % 60;
                if(min == 0){
                    timeL.setText("Time Left: " +  sec + " sec.");
                } else {
                    timeL.setText("Time Left: " + min + " min. " + sec + " sec.");
                }
                if(timeRemaining[0] <= 0){
                    timer.stop();
                    updatePanelLoopPlus(loopIdx, true);
                    loopIdx++;
                }
                
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.start();
    }
    
    public void startTimer(Play t, int time){
        int delay = 1000; // 1 second delay
        final int[] timeRemaining = {time}; // effectively-final variable
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeRemaining[0]--;
                int min = timeRemaining[0] / 60;
                int sec = timeRemaining[0] % 60;
                if (min == 0) {
                    timeL.setText("Time Left: " + sec + " sec.");
                } else {
                    timeL.setText("Time Left: " + min + " min. " + sec + " sec.");
                }
                if (timeRemaining[0] <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(Play.this, "Time out. The quiz has ended!", "Quiz Timer", JOptionPane.INFORMATION_MESSAGE);
                    t.dispose();
                    new ScoreCard(quizName, selectedAns, questionList, selectedMode);
                }
            }
        };

        timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    public int saveResponse(ArrayList<JRadioButton> buttons) {
        int selectedIndex = -1;
        for (JRadioButton button : buttons) {
            if (button.isSelected()) {
                selectedIndex = buttons.indexOf(button) + 1; // Get index directly
                break; // Exit loop after finding the first selected button
            }
        }
        return selectedIndex;
    }
    
    private void setupTimeSpinners(){
        int spinW = 40;
        int gap = 12;
        int maxSeconds = 60;
        minuteS = new JSpinner(new SpinnerNumberModel(0, 0, (int) maxMinutes, 1));
        minuteS.setBounds(15 + 145 + gap, 240, spinW, 30);
        minuteS.setVisible(false);
        customizeSpinners(minuteS);
        questArea.add(minuteS);
        
        minL = new JLabel("MIN.");
        minL.setBounds(15 + 145 + 2*gap + spinW, 240, spinW, 30);
        minL.setVisible(false);
        minL.setFont(new Font("Tahoma", Font.BOLD, 16));
        minL.setForeground(Color.WHITE);
        questArea.add(minL);
                
        secondS = new JSpinner(new SpinnerNumberModel(45, 0, maxSeconds, 1));
        secondS.setBounds(15 + 145 + 3*gap + 2*spinW, 240, spinW, 30);
        secondS.setVisible(false);
        customizeSpinners(secondS);
        questArea.add(secondS);
        
        secL = new JLabel("SEC.");
        secL.setBounds(15 + 145 + 4*gap + 3*spinW, 240, spinW, 30);
        secL.setVisible(false);
        secL.setFont(new Font("Tahoma", Font.BOLD, 16));
        secL.setForeground(Color.WHITE);
        questArea.add(secL);
        
        secondS.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int seconds = (int) secondS.getValue();
                if (seconds == maxSeconds) {
                    secondS.setValue(0);
                    int minutes = (int) minuteS.getValue();
                    if(minutes != maxMinutes){
                        minuteS.setValue(minutes + 1);
                    } else {
                        secondS.setValue(0);
                        secondS.setEnabled(false);
                        secondS.repaint();
                    }
                }
            }
        });
        minuteS.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int minutes = (int) minuteS.getValue();
                if (minutes == maxMinutes) {
                    secondS.setValue(0);
                    secondS.setEnabled(false);
                    secondS.repaint();
                } else {
                    secondS.setEnabled(true);
                    secondS.repaint();
                }
            }
        });
    }
    
    private void initializeTimers(int initialX, int initialY, int buttonWidth, int buttonHeight){
        timeL = new JLabel("Time Left: ");
        timeL.setBounds(initialX, initialY, buttonWidth, buttonHeight);
        timeL.setForeground(Color.BLACK);
        timeL.setFont(new Font("Tahoma", Font.BOLD, 20));
        timeL.setOpaque(false); // Non-opaque to enable transparency
        timeL.setBackground(new Color(0, 0, 0, 0));
        timeL.setVisible(false);
        
        String[] modes = {"Timer Off", "Timer On", "Timer On+"};
        selectedMode = modes[0];
        modeChoice = new JSpinner(new CustomSpinnerModel(modes));
        modeChoice.setBounds(15, 240, 145, 30);
        customizeSpinners(modeChoice);
        modeChoice.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(selectedMode.equals("Timer On+")){
                    SpinnerNumberModel model = (SpinnerNumberModel) minuteS.getModel();
                    maxMinutes = maxTimeMinutes[0];
                    model.setMaximum(maxMinutes);
                    secondS.setValue(45);
                    minuteS.setValue(0);
                    secondS.setVisible(true);
                    minuteS.setVisible(true);
                    minL.setVisible(true);
                    secL.setVisible(true);
                } else if (selectedMode.equals("Timer On")) {
                    SpinnerNumberModel model = (SpinnerNumberModel) minuteS.getModel();
                    maxMinutes = maxTimeMinutes[1];
                    model.setMaximum(maxMinutes);
                    secondS.setValue(0);
                    minuteS.setValue(15);
                    secondS.setVisible(true);
                    minuteS.setVisible(true);
                    minL.setVisible(true);
                    secL.setVisible(true);
                } else {
                    secondS.setVisible(false);
                    minuteS.setVisible(false);
                    minL.setVisible(false);
                    secL.setVisible(false);
                }
            }
        });
        modeChoice.repaint();
        questArea.add(modeChoice);
    }
    
    public void customizeSpinners(JSpinner spinner){
        JComponent editor = spinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
        JTextField textField = spinnerEditor.getTextField();
        textField.setForeground(Color.BLUE); // Change text color
        textField.setFont(new Font("Arial", Font.BOLD, 16)); // Change font and size
        textField.setBackground(new Color(248, 177, 150)); // Change background color
        textField.setHorizontalAlignment(JTextField.CENTER);
    }

    public void updatePanelLoopPlus(int idx, boolean timeOut) {        
        if (idx < questionList.size()) {
            optionsGroup.clearSelection();
            QuestionData data = questionList.get(idx);
            questArea.setText(data.getQuestionText());
            String formattedQuestionNumber = String.format("%02d/%d", idx + 1, questionList.size());
            displayQuestion.setText("Question No. " + formattedQuestionNumber);
            ArrayList<String> options = data.getOptions();
            for (int i = 0; i < options.size(); i++) {
                optionButtons.get(i).setText(options.get(i));
            }
            if (selectedMode.equals("Timer On+")) {                
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                }
                startTimerPlus(time);
            }
            loopIdx++;
        } else {
            // Handle quiz completion
            if(timeOut){
                JOptionPane.showMessageDialog(this, "Time out. The quiz has ended!");
            } else {
                JOptionPane.showMessageDialog(this, "Quiz completed!");
            }
            System.out.println("Options selected: " + selectedAns);
            this.dispose();
            new ScoreCard(quizName, selectedAns, questionList, selectedMode);
        }
    }
    
    public void updatePanelLoop(int idx){
        if (idx < questionList.size()) {
            optionsGroup.clearSelection();
            QuestionData data = questionList.get(idx);
            questArea.setText(data.getQuestionText());
            String formattedQuestionNumber = String.format("%02d/%d", idx + 1, questionList.size());
            displayQuestion.setText("Question No. " + formattedQuestionNumber);
            ArrayList<String> options = data.getOptions();
            for (int i = 0; i < options.size(); i++) {
                optionButtons.get(i).setText(options.get(i));
            }
            loopIdx++;
        } else {
            if(timer != null && timer.isRunning()){
                timer.stop();
            }
            JOptionPane.showMessageDialog(this, "Quiz completed!");
            System.out.println("Options selected: " + selectedAns);
            this.dispose(); // Close the quiz window
            new ScoreCard(quizName, selectedAns, questionList, selectedMode);
        }
    }
    
    static class CustomSpinnerModel extends AbstractSpinnerModel {
        private String[] options;
        private int currentIndex;

        public CustomSpinnerModel(String[] options) {
            this.options = options;
            this.currentIndex = 0;
        }

        @Override
        public Object getValue() {
            return options[currentIndex];
        }

        @Override
        public void setValue(Object value) {
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(value)) {
                    currentIndex = i;
                    fireStateChanged();
                    return;
                }
            }
            throw new IllegalArgumentException("Invalid value");
        }

        @Override
        public Object getNextValue() {
            selectedMode = options[(currentIndex + 1) % options.length];
            return selectedMode;
        }

        @Override
        public Object getPreviousValue() {
            selectedMode = options[(currentIndex - 1 + options.length) % options.length];
            return selectedMode;
        }
    }

    class CustomSpinnerEditor extends JSpinner.DefaultEditor {
        public CustomSpinnerEditor(JSpinner spinner, String[] options) {
            super(spinner);
            JTextField textField = getTextField();
            textField.setEditable(false);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner = getSpinner();
            String value = spinner.getValue().toString();
            getTextField().setText(value);
        }
    }
}