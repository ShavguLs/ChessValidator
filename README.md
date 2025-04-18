# Chess Game Validator

This project is a Java application that reads and validates chess games in PGN format. The validator checks both the syntax of the chess notation and the legality of each move according to standard chess rules.

## Project Description

The Chess Game Validator parses PGN files containing one or more chess games and checks each game for validity. The validation happens in two phases:

1. **Parsing Phase**: Checks for syntax errors in the move notation.
2. **Game Replay Phase**: Simulates the game move by move to ensure all moves are legal according to chess rules.

The program handles special chess moves including:
- Castling (both kingside and queenside)
- En passant captures
- Pawn promotions
- Check and checkmate notation

## How to Run the Program

### Prerequisites
- A PGN file containing chess games

### Compilation
Compile all Java files:
```
javac *.java
```

### Execution
Run the program with a PGN file as argument:
```
java Main path/to/your/file.pgn
```


![image](https://github.com/user-attachments/assets/3bfe85d2-beba-43c3-be4d-508f3bb73fd4)
