# quiz-application-project

The quiz app contains the functionality to create quizzes with up to 100 questions per quiz and up to 50 options per question (that's a lot of options! ), and it saves the quizzes as serialized files. These serialized files are retrieved whenever a user wants to play one of the created quizzes, and all quizzes are displayed so the user can choose whatever they want to play. The quizzes can be played in three modes: 
1. Relaxed Mode: When the timer is off, the user can play at their own pace. 
2. Timer Mode: The quiz is timed. All questions are accessible within this timeframe. If the timer runs out, then the quiz ends automatically.
3. Timer Mode+: Each question is timed, meaning if it is not solved within that duration, the quiz will move directly to the next question and not wait for the user's response.
After the quiz ends, the score is displayed with a comment on how the player performed on the quiz.

## Directory Structure
quiz-application/ <br>
│ <br>
├── executable-jar-file/ <br>
│ ├── QuizApp.jar # A jar file that is ready to be used <br>
├── src/ <br>
│ ├── img/ # images used for app <br>
│ ├── quiz/ <br>
│ │ ├── app/ <br>
│ │ │ ├── MainMenu.java # Main class for the app <br>
│ │ │ ├── CreateQuiz.java # To create quizzes <br>
│ │ │ ├── Question.java # A frame to take input for questions in the quiz <br>
│ │ │ ├── QuestionManager.java # Manages how the questions are stored locally <br>
│ │ │ ├── QuestionData.java # A class that stores each question's data as an object <br>
│ │ │ ├── SelectQuiz.java # A class that retrieves the saved quizzes and displays them as a selection to play from <br>
│ │ │ ├── Play.java # A frame to display questions and allow the user to play the quiz <br>
│ │ │ ├── ScoreCard.java # Calculates the score of the quiz based on the user's selected choices <br>
│ └── user_saved_quizzes/ # Contains serialized quiz data (.ser files) <br>
│ <br>
├── README.md # Project documentation <br>
└── LICENSE # License information <br>

## Requirements
* NetBeans (IDE to run the Java project)
* JDK (to compile and run the program files)
* Java version 8 (or higher)

# How to use this project:
* Open NetBeans and go to the Teams tab.
* Select Git -> Clone, paste this repo's link, and proceed to clone it.
* Set up the main class to be `MainMenu.java`
* The project is ready to run now.

## Running the JAR file
* Navigate to the executable-jar-file directory
* Run the JAR file using the command: `java -jar QuizApp.jar`


## Acknowledgements
* This project uses stock images from websites such as: [Unsplash](https://unsplash.com/) and [Freepik](https://www.freepik.com/).
* If you would like to contribute to this project, please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](https://github.com/NikhilK-84/quiz-application-project/blob/master/LICENSE) file for details.
