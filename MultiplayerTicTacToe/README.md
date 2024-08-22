# Multiplayer Configurable Tic-Tac-Toe

## Project Overview

This project was a semester-long implementation of tic-tac-toe, allowing the user to select the number of players, size of the board, and number of tokens in a row to win. In addition to the java program, I've included `MultiplayerTicTacToe/TicTacToeGui/report.pdf` which outlines the theoretical planning for the project. Included in it are the list of requirements I created, class and activity diagrams, and planned test cases. Everything in this project is my own work aside from the rudimentary JavaSwing GUI which was provided by my professor. 

## Technical Overview

- Written in Java
- Utilizes **Model View Controller** design pattern
- Test cases written in JUnit
- GUI in JavaSwing (provided by professor, I did not produce this)
- Class and activity diagrams, and test cases are provided in `report.pdf`
- Functional and non-functional requirements are also listed in `report.pdf`

## Execution Instructions

To start, you'll need to clone this repository into your current directory:

```console
git clone https://github.com/marksheldon2/marksheldonrepo.git
```

Once the repository is cloned, you'll need an IDE capable of supporting a Java project (I used [IntelliJ](https://www.jetbrains.com/idea/download/?section=mac)). Create a new project using existing files, then select the `MultiplayerTicTacToe/TicTacToeGui` directory. Import the files, select JDK version 11, and create the project. 

When the project is created, simply navigate to the path `src/cpsc2150/extendedTicTacToe`, open the `TicTacToeGame.java` file, and hit the run icon in the top right of the screen.