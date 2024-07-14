package quiz.app;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class SelectQuiz extends JFrame implements ActionListener {
    JButton viewQuizzesButton, playButton, exitButton; // Buttons for view, play, and exit actions
    JComboBox<String> dropdown; // Dropdown for selecting quizzes

    // Constructor to set up the SelectQuiz frame
    SelectQuiz() {
        setTitle("Select Quiz"); // Set the frame title
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screen.getWidth();
        int height = (int) screen.getHeight();
        int fH = 600; // Frame height
        int fW = 534 * 2; // Frame width
        setLayout(null);
        setResizable(false);

        // Load and add background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("img/selectQuiz.jpg"));
        JLabel i11 = new JLabel(i1);
        i11.setBounds(0, 0, 534, fH); // Image dimensions
        add(i11);

        // Set frame icon
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("img/quiz.png"));
        Image i22 = i2.getImage();
        setIconImage(i22);

        // Add heading label
        JLabel heading = new JLabel("It's Play Time!");
        heading.setBounds(fW - fW / 4 - 120, 80, 240, 60);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
        heading.setForeground(Color.BLUE);
        add(heading);

        // Create and configure panel for buttons and dropdown
        JPanel p1 = new JPanel();
        p1.setBounds(fW / 2, 0, fW / 2, fH);
        p1.setBackground(new Color(255, 251, 200));
        p1.setLayout(null);

        int buttonWidth = 240;
        int buttonHeight = 60;
        int buttonX = fW / 4 - buttonWidth / 2; // Position buttons in the center of the panel
        int initialY = 180;
        int gap = 70;

        dropdown = new JComboBox<>(); // Dropdown for quiz selection
        playButton = new JButton("Play"); // Button to start the quiz
        exitButton = new JButton("Exit"); // Button to exit

        // Set bounds and styles for dropdown and buttons
        dropdown.setBounds(buttonX, initialY, buttonWidth, buttonHeight);
        CenteredComboRenderer renderer = new CenteredComboRenderer();
        dropdown.setRenderer(renderer);

        playButton.setBounds(buttonX, initialY + gap, buttonWidth, buttonHeight);
        exitButton.setBounds(buttonX, initialY + 2 * gap, buttonWidth, buttonHeight);

        dropdown.setBackground(new Color(53, 92, 195));
        playButton.setBackground(new Color(53, 92, 195));
        exitButton.setBackground(new Color(108, 91, 123));
        
        dropdown.setForeground(Color.WHITE);
        playButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);

        dropdown.setFont(new Font("Tahoma", Font.BOLD, 20));
        playButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 20));

        // Add action listeners
        dropdown.addActionListener(this);
        playButton.addActionListener(this);
        exitButton.addActionListener(this);

        playButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        // Add initial item to dropdown
        dropdown.addItem("Select");
        refreshDropdown(); // Load and refresh the dropdown with saved quizzes

        // Add components to the panel
        p1.add(dropdown);
        p1.add(playButton);
        p1.add(exitButton);

        // Add panel to the frame
        add(p1);
        getContentPane().setBackground(Color.WHITE);
        setBounds(width / 2 - fW / 2, height / 2 - fH / 2, fW, fH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == playButton) {
            String selectedQuiz = (String) dropdown.getSelectedItem();
            if (!selectedQuiz.equals("Select")) {
                String FilePath = "user_saved_quizzes/" + selectedQuiz + ".ser";
                ArrayList<QuestionData> list = new ArrayList<>();
                // Deserialize the selected quiz file
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FilePath))) {
                    list = (ArrayList<QuestionData>) in.readObject();
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(SelectQuiz.this, "The save file might have been deleted/misplaced. Create New Quiz.", "Save File Not Found", JOptionPane.WARNING_MESSAGE);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                new Play(list, selectedQuiz); // Start playing the selected quiz
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(SelectQuiz.this, "Please select a quiz.");
            }
        } else if (ae.getSource() == exitButton) {
            new MainMenu(); // Go back to main menu
            this.dispose();
        }
    }

    // Method to refresh the dropdown with the list of saved quizzes
    private void refreshDropdown() {
        dropdown.removeAllItems();
        dropdown.addItem("Select");
        ArrayList<String> list = updateSavedQuizzes(); // Load updated list of saved quizzes
        for (String filename : list) {
            int dotIndex = filename.indexOf('.');
            if (dotIndex != -1) {
                String s = filename.substring(0, dotIndex);
                dropdown.addItem(s);
            }
        }
    }

    // Method to update the list of saved quizzes and refresh the saved_quizzes.ser file
    private ArrayList<String> updateSavedQuizzes() {
        ArrayList<String> list = new ArrayList<>();
        File dir = new File("user_saved_quizzes");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".ser") && !name.equals("saved_quizzes.ser"));

        if (files != null) {
            for (File file : files) {
                list.add(file.getName());
            }
        }

        // Serialize the updated list to saved_quizzes.ser
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user_saved_quizzes/saved_quizzes.ser"))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new SelectQuiz(); // Start the SelectQuiz frame
        });
    }

    // Custom renderer to center-align items in the dropdown
    class CenteredComboRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(CENTER); // Set horizontal alignment to center
            return this;
        }
    }
}
