# Chess Game Validator

This project checks if chess games written in PGN format are valid or not. It first checks if the moves are written correctly, then checks if all moves follow chess rules.

## How It Works

The program:
1. Lets you choose a PGN file using a file dialog
2. Reads all the chess games from the file
3. Checks each game in two steps:
    - First makes sure the notation is correct (no typos or wrong formats)
    - Then replays the game to make sure all moves are legal

It can handle special chess moves like:
- Castling (O-O and O-O-O)
- En passant captures
- Pawn promotions (like e8=Q)
- Check (+) and checkmate (#) markers

## How to Run It

1. Make sure you have Java installed
2. Compile the code:
   ```
   javac *.java
   ```
3. Run the program:
   ```
   java Main
   ```
4. A window will open where you can select your PGN file
5. Results will be shown in the console

## Sample Output

When you run the program with a valid chess game file, you'll see something like this:

```
===============================================
Validating file: sample.pgn
===============================================
===============================================
VALIDATION SUMMARY
===============================================
Total games: 10
Valid games: 8
Invalid games: 2
  - Games with syntax errors: 1
  - Games with illegal moves: 1
===============================================
```

## Files Included

- `Main.java` - The main program with file selection
- `ChessBoard.java` - Represents the chess board
- `ChessPiece.java` - Contains logic for how pieces move
- `ChessGame.java` - Validates games
- `MoveParser.java` - Converts chess notation to moves
- `PgnReader.java` - Reads PGN files

![File chooser dialog](https://github.com/user-attachments/assets/3bfe85d2-beba-43c3-be4d-508f3bb73fd4)