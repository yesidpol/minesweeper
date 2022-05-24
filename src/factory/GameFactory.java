package factory;

import enums.DifficultyLevel;
import mines.Board;
import mines.Game;

public class GameFactory {
    public static Game createBoard(DifficultyLevel level) {
        int mines = 0;
        int width = 0;
        int height = 0;

        switch (level) {
        case BEGINNER:
            width = 10;
            height = 10;
            mines = 10;
            break;
        case INTERMEDIATE:
            width = 16;
            height = 16;
            mines = 40;
            break;
        case EXPERT:
            width = 30;
            height = 16;
            mines = 99;
            break;
        }

        return createBoard(width, height, mines);
    }

    public static Game createBoard(int width, int height, int mines) {
        Board board = new Board(width, height);

        return new Game(board, mines);
    }
}
