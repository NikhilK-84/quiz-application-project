package quiz.app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainMenu extends JFrame implements ActionListener {
    // Declare buttons
    JButton createQuizButton, editQuizzesButton, playQuizButton, exitButton;


    // Constructor to set up the main menu
    MainMenu() {
        setTitle("Quiz Time!"); // Set window title
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen dimensions
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        int fH = 600;
        int fW = 479 * 2;
        setLayout(null); // Use null layout for custom positioning

        // Load background image and set its bounds
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/name.jpg"));
        JLabel i11 = new JLabel(i1);
        i11.setBounds(0, 0, 479, fH);
        add(i11);

        // Load and set window icon
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("img/quiz.png"));
        Image i22 = i2.getImage();
        setIconImage(i22);

        // Add heading label
        JLabel heading = new JLabel("It's Quiz Time!");
        heading.setBounds(fW - fW / 4 - 120, 80, 240, 60);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
        heading.setForeground(Color.BLUE);
        add(heading);

        // Create a panel for the buttons
        JPanel p1 = new JPanel();
        p1.setBounds(fW / 2, 0, fW / 2, fH);
        p1.setBackground(new Color(248, 177, 149));
        p1.setLayout(null);

        // Initialize buttons
        createQuizButton = new JButton("Create a New Quiz");
        editQuizzesButton = new JButton("Edit Existing Quiz");
        playQuizButton = new JButton("Play a Quiz");
        exitButton = new JButton("Exit");

        // Set button bounds and layout
        int buttonWidth = 240;
        int buttonHeight = 60;
        int buttonX = fW / 4 - buttonWidth / 2; // Changed to be relative to the JPanel
        int initialY = 180;
        int gap = 70;

        createQuizButton.setBounds(buttonX, initialY, buttonWidth, buttonHeight);
        editQuizzesButton.setBounds(buttonX, initialY + gap, buttonWidth, buttonHeight);
        playQuizButton.setBounds(buttonX, initialY + 2 * gap, buttonWidth, buttonHeight);
        exitButton.setBounds(buttonX, initialY + 3 * gap, buttonWidth, buttonHeight);

        // Set button colors and fonts
        createQuizButton.setBackground(new Color(53, 92, 195));
        editQuizzesButton.setBackground(new Color(53, 92, 195));
        playQuizButton.setBackground(new Color(53, 92, 195));
        exitButton.setBackground(new Color(108, 91, 123));
        
        createQuizButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        editQuizzesButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        playQuizButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 20));

        // Add action listeners to buttons
        createQuizButton.addActionListener(this);
        editQuizzesButton.addActionListener(this);
        playQuizButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        createQuizButton.setFocusPainted(false);
        editQuizzesButton.setFocusPainted(false);
        playQuizButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        // Add buttons to the panel
        p1.add(createQuizButton);
        p1.add(editQuizzesButton);
        p1.add(playQuizButton);
        p1.add(exitButton);

        // Add panel to the frame
        add(p1);

        // Set frame properties
        getContentPane().setBackground(Color.WHITE);
        setBounds(width / 2 - fW / 2, height / 2 - fH / 2, fW, fH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createQuizButton) {
            dispose(); // Close current frame
            ArrayList<String> existingQuizzes = new ArrayList<>();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user_saved_quizzes/saved_quizzes.ser"))) {
                existingQuizzes = (ArrayList<String>) ois.readObject();
            } catch (FileNotFoundException e) {
                // File not found, ignore and return empty list
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
            new CreateQuiz(existingQuizzes);
        } else if (ae.getSource() == editQuizzesButton) {
            // Handle "Edit Existing Quiz" button click
            // The functionality is yet to be developed.
        } else if (ae.getSource() == playQuizButton) {
            new SelectQuiz();
            dispose(); // Close current frame
        } else {
            setVisible(false); // Hide current frame
            dispose(); // Close current frame
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu(); // Create and display the main menu
        });
    }
}
