package dmitrybochkov.minesweeper;

import java.util.Random;

public class GameManager {

    //Different result codes
    public static final int GAME_LOST = 111;
    public static final int GAME_WON = 112;
    public static final int GAME_CONTINUES = 222;
    public static final int END_GAME = 333;
    public static final int RESTART_GAME = 444;
    public static final int NEW_GAME = 555;

    //Difficulties
    public static final int BEGINNER = 1;
    public static final int INTERMEDIATE = 2;
    public static final int EXPERT = 3;

    private static final int BEGINNER_FIELD_SIZE = 3;
    private static final int INTERMEDIATE_FIELD_SIZE = 15;
    private static final int EXPERT_FIELD_SIZE = 20;

    //Some variables
    private int fieldSize;
    private int numberOfMines;
    private Cell[][] cellsArray;
    private boolean isFieldGenerated;
    private int numberOfOpenCells;
    private int playerScore;
    private String playerName;

    private final int[][] neighbor = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0},           {1, 0},
            {-1, 1},  {0, 1},  {1, 1}
    };

    GameManager(final int difficulty){
        setFieldSize(difficulty);
        cellsArray = new Cell[fieldSize][fieldSize];
        for(int i = 0; i < fieldSize; ++i) //Creating empty field without mines
            for(int j = 0; j < fieldSize; ++j) {
                cellsArray[i][j] = new Cell();
            }
        isFieldGenerated = false;
        playerScore = 0;
        numberOfMines = 0;
        numberOfOpenCells = 0;
    }

    public void generateField(final int firstPressedCell){
        while(numberOfMines < fieldSize){ //Number of mines should be equal to size of field.
            int newMine = randInt(0, fieldSize * fieldSize);
            int newMineRow = newMine / fieldSize;
            int newMineColumn = newMine % fieldSize;

            //If random cell is empty and is not pressed than place a new mine here.
            if(newMine != firstPressedCell && !cellsArray[newMineRow][newMineColumn].isMined()){
                cellsArray[newMineRow][newMineColumn].setMine();
                ++numberOfMines;

                //Increase cell's counter of mines near by 1 if needed
                for(int i = 0; i < 8; ++i){
                    int neighboringMineRow = newMineRow + neighbor[i][0];
                    int neighboringMineColumn = newMineColumn + neighbor[i][1];
                    if(neighboringMineColumn >= 0 && neighboringMineColumn < fieldSize){
                        if(neighboringMineRow >= 0 && neighboringMineRow < fieldSize &&
                                !cellsArray[neighboringMineRow][neighboringMineColumn].isMined()){
                            cellsArray[neighboringMineRow][neighboringMineColumn].increaseNumberOfMinesNear();
                        }
                    }
                    else{
                        i += 2; //Small optimization.
                    }
                }
            }
        }
        isFieldGenerated = true;
    }

    public int handleItemClick(final int position){
        if(isFieldGenerated){
            Cell selectedCell = cellsArray[position / fieldSize][position % fieldSize];
            if(!selectedCell.isOpen()){
                if(!selectedCell.isMined()){
                    cellsArray[position / fieldSize][position % fieldSize].open();
                    ++numberOfOpenCells;
                    increaseScore();
                    if(selectedCell.getNumberOfMinesNear() == 0){
                        openEmptyCellsNear(position / fieldSize, position % fieldSize);
                    }
                    if(isGameWon())
                        return GAME_WON;
                }
                else{
                    cellsArray[position / fieldSize][position % fieldSize].open();
                    return GAME_LOST;
                }
            }
        }
        else{
            cellsArray[position / fieldSize][position % fieldSize].open();
            increaseScore();
            ++numberOfOpenCells;
            generateField(position);
        }
        return GAME_CONTINUES;
    }

    private void openEmptyCellsNear(final int cellRow, final int cellColumn){
        for(int i = 0; i < 8; ++i){
            int neighboringCellRow = cellRow + neighbor[i][0];
            int neighboringCellColumn = cellColumn + neighbor[i][1];
            if(neighboringCellColumn >= 0 && neighboringCellColumn < fieldSize){
                if(neighboringCellRow >= 0 && neighboringCellRow < fieldSize &&
                        !cellsArray[neighboringCellRow][neighboringCellColumn].isMined() &&
                        !cellsArray[neighboringCellRow][neighboringCellColumn].isOpen()){
                    cellsArray[neighboringCellRow][neighboringCellColumn].open();
                    increaseScore();
                    ++numberOfOpenCells;
                    //If cell is empty than continue open neighbors
                    if(cellsArray[neighboringCellRow][neighboringCellColumn].getNumberOfMinesNear() == 0){
                        openEmptyCellsNear(neighboringCellRow, neighboringCellColumn);
                    }
                }
            }
            else{
                i += 2; //Small optimization.
            }
        }
    }

    private boolean isGameWon(){
        return numberOfOpenCells == (fieldSize * fieldSize - numberOfMines);
    }

    public int getFieldSize(){
        return fieldSize;
    }

    public Cell[][] getDataForAdapter(){
        return cellsArray;
    }

    private void increaseScore(){
        ++playerScore;
    }

    public int getScore(){
        return playerScore;
    }

    public void setPlayerName(final String name){
        playerName = name;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void restartGame(){
        playerScore = 0;
        numberOfOpenCells = 0;
        for(int i = 0; i < fieldSize; ++i)
            for(int j = 0; j < fieldSize; ++j)
                cellsArray[i][j].close();
    }

    private void setFieldSize(final int difficulty){
        switch(difficulty){
            case BEGINNER:
                fieldSize = BEGINNER_FIELD_SIZE;
                break;
            case INTERMEDIATE:
                fieldSize = INTERMEDIATE_FIELD_SIZE;
                break;
            case EXPERT:
                fieldSize = EXPERT_FIELD_SIZE;
                break;
            default:
                break;
        }
    }

    private int randInt(final int min, final int max){
        Random random = new Random();
        return random.nextInt((max - min)) + min;
    }
}
