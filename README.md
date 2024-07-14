# quiz-application-project

The quiz app contains the functionalities to create quizes with upto 100 questions per quiz and upto 50 options per question (That's a lot of options!!), and it saves the quizzes as serialized files. These serialized files are retrived whenever a user wants to play one of the created quiz and all quizzes are displayed so user can choose whatever they want to play. The quizzes can be played in three modes: 
1. Relaxed Mode: The timer is off, user can play at their pace. 
2. Timer Mode: The quiz is timed. All questions are accessible throught this timeframe; If timer runs out, then the quiz ends automatically.
3. Timer Mode+: Each question is timed, meaning if it is not solved within that duration, the quiz will move directly for the next question and not wait for user's response.
After the quiz ends, the score is displayed with a remark of how the playerr performed on the quiz.

## Directory Structure
quiz-application/
│
├── executable-jar-file/
│ ├── QuizApp.jar # A jar file which is ready to be used
├── src/
│ ├── img/ # images used for app
│ ├── quiz/
│ │ ├── app/
│ │ │ ├── MainMenu.java # Main class for the app
│ │ │ ├── CreateQuiz.java # To create quizzes
│ │ │ ├── Question.java # A frame to take input for questions in the quiz
│ │ │ ├── QuestionManager.java # Manages how the questions are stored locally
│ │ │ ├── QuestionData.java # A class that stores each question's data as an object 
│ │ │ ├── SelectQuiz.java # A class that retrives the saved quizzes and displays it as a selection to play from
│ │ │ ├── Play.java # A frame to display questions and allow user to play the qui
│ │ │ ├── ScoreCard.java # Calculates the score of quiz based on the user's selected choices
│ └── user_saved_quizzes/ # Contains serialized quiz data (.ser files)
│
├── README.md # Project documentation
└── LICENSE # License information

## Requirements
* NetBeans (IDE to run the java project)
* JDK (To compile and run the program files)
* Java version 8 (or higher)

# How to use this project:
* Open NetBeans, go to Teams tab
* Select Git -> Clone, and paste this repo's link and proceed to clone it
* Set up the main class to be `MainMenu.java`
* The project is ready to run now

# Running the JAR file
* Navigate to the executable-jar-file directory
* Run the JAR file using the command: `java -jar QuizApp.jar`


## Acknowledgements
* This project uses stock images from websites such as: [Unsplash](https://unsplash.com/) and [Freepik](https://www.freepik.com/).
* If you would like to contribute to this project, please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE]() file for details.
