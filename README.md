# Mastermind Game

A Java implementation of the classic code-breaking game Mastermind, built with JavaFX for the GUI.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 19 or later
- JavaFX 19 or later

### Installing

1. Clone the repository to your local machine using `git clone https://github.com/Embrejs/Mastermind.git`
2. Open the project in your preferred IDE
3. Run the `main()` method in the `Main` class to start the game

## How to Play

1. The game will randomly generate a 4-peg code for you to guess. The pegs can be one of six colors.
2. Make a guess by clicking by choosing right color for each positon
3. Click the "Check" button to submit your guess
4. The game will give you feedback on your guess in the form of "-" and "+".Minues mean that the color is in the wrong position but is in the code elseware, pluses mean that the color is in the right position.
5. Continue guessing until you correctly guess the code or until you run out of turns.

## Built With

- [Java](https://www.java.com/) - Programming language
- [JavaFX](https://openjfx.io/) - GUI framework

## Author

- [Embrejs](https://github.com/Embrejs)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Inspiration](https://en.wikipedia.org/wiki/Mastermind_(board_game))
- [JavaFX Tutorials](https://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm)

## Additional features
- Game saves the score in the files
- Show the history of previous attempts
- Reset game button


## Contribution
- Feel free to submit pull requests for bug fixes and new features
- If you find any bugs, please report them by creating an issue on the repository

## Support
If you need any help with the game or have any questions, please open an issue on the repository or contact the author directly.
