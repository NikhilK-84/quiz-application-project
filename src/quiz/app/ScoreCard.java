package quiz.app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScoreCard extends JFrame implements ActionListener {
    JButton exitButton, playAnother, mainMenu, playAgain;
    ArrayList<QuestionData> questionList;
    String quizName;
    ScoreCard(String quizName, ArrayList<Integer> ans, ArrayList<QuestionData> questionList, String mode) {
        this.quizName = quizName;
        this.questionList = questionList;
        
        setTitle("Score");
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        int fH = 600;
        int fW = 900;
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/score.jpg"));
        JLabel i11 = new JLabel();
        i11.setIcon(i1);
        i11.setBounds(0, 0, fW, fH); // image dimensions
        add(i11);

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("img/quiz.png"));
        Image i22 = i2.getImage();
        setIconImage(i22);
        
        int total = questionList.size();
        int score = calculateScore(ans, questionList);
        
        String imgPath = null;
        if(score == 0)
           imgPath = new String("img/zero.png");
        else if (score < total/2)
           imgPath = new String("img/one.png");
        else if (score == total/2)
            imgPath = new String("img/one_half.png");
        else if (score == total/2)
            imgPath = new String("img/two.png");
        else 
            imgPath = new String("img/three.png");
        
        
        ImageIcon wheel = new ImageIcon(ClassLoader.getSystemResource(imgPath));
        JLabel wheeL = new JLabel(wheel);
        wheeL.setBounds(fW / 4 - 115, 180, 325, 200); // image dimensions
        i11.add(wheeL);
        
        ArrayList<String> compliment = new ArrayList();
        compliment.add("Congrates you have beat the quiz.");
        compliment.add("Good try.");
        compliment.add("You might have learnt something today :)");
        String selectCompliment = (score == total) ? compliment.get(0) : (score > (total / 2) ? compliment.get(1) : compliment.get(2));

        int labelgap = 45;
        int offsetY = 40;
        int offsetX = 15;
        JLabel heading = new JLabel(selectCompliment);
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setBounds(fW / 4 - (420 / 2) + 40 + offsetX, offsetY, 420, 35);
        heading.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        i11.add(heading); // Add to background label

        JLabel displayScore = new JLabel("Your Score: " + score + "/" + total);
        displayScore.setHorizontalAlignment(JLabel.CENTER);
        displayScore.setBounds(fW / 4 - (190 / 2) + 40 + offsetX, offsetY + labelgap*2, 190, 30);
        displayScore.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        displayScore.setForeground(Color.WHITE);
        i11.add(displayScore); // Add to background label
        
        JLabel quizTitle = new JLabel("Quiz: " + quizName);
        quizTitle.setHorizontalAlignment(JLabel.CENTER);
        quizTitle.setBounds(fW / 4 - (190 / 2) + 40 + offsetX, offsetY + labelgap*1, 190, 30);
        quizTitle.setHorizontalAlignment(JLabel.CENTER);
        quizTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        quizTitle.setForeground(Color.WHITE);
        i11.add(quizTitle); // Add to background label

        int buttonWidth = 180;
        int buttonHeight = 40;
        int verticalGap = 70;
        int horizontalGap = 200;
        int buttonX = 80;
        int initialY = fH - 200;
        
        playAnother = createButton("Play Another", buttonX, initialY, buttonWidth, buttonHeight, new Color(165, 11, 94));
        i11.add(playAnother);

        playAgain = createButton("Play Again", buttonX + horizontalGap, initialY, buttonWidth, buttonHeight, new Color(165, 11, 94));
        i11.add(playAgain);

        exitButton = createButton("Exit App", buttonX, initialY + verticalGap, buttonWidth, buttonHeight, Color.BLACK);
        i11.add(exitButton);

        mainMenu = createButton("Go to Menu", buttonX + horizontalGap, initialY + verticalGap, buttonWidth, buttonHeight, Color.BLACK);
        i11.add(mainMenu);

        getContentPane().setBackground(Color.WHITE);
        setBounds(width / 2 - fW / 2, height / 2 - fH / 2, fW, fH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, int width, int height, Color bgColor) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(bgColor);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        button.setFocusPainted(false);
        return button;
    }
    
    public int calculateScore(ArrayList<Integer> ans, ArrayList<QuestionData> list){
        int countCorrect = 0;
        for(int i = 0; i < ans.size(); i++){
            int correctOpt = list.get(i).getAns();
            int selectedOpt = ans.get(i);
            if(selectedOpt == correctOpt){
                countCorrect++;
            }
        }
        return countCorrect;
    }

    public void actionPerformed(ActionEvent ae) {
        this.dispose();
        if(ae.getSource() == mainMenu){
            new MainMenu();
        } else if(ae.getSource() == playAnother){
            new SelectQuiz();
        } else if(ae.getSource() == playAgain){
            new Play(questionList, quizName);
        }
    }

    public static void main(String[] args) {
        new ScoreCard("Name", new ArrayList<>(), new ArrayList<>(), "Mode");
    }
}
