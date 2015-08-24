package dmitrybochkov.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class PlayingScreenActivity extends Activity implements GameOverDialog.GameOverDialogListener{

    String playerName;
    TextView playerScoreTextView;
    GridView gridView; //Good way to store mines
    GameOverDialog gameOverDialog;

    GameManager gameManager;
    CellsGridViewAdapter cellsGridViewAdapter;
    int fieldSize;
    DBManager dbManager;
    int difficulty;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_screen);

        gridView = (GridView) findViewById(R.id.gridForMines);
        playerScoreTextView = (TextView) findViewById(R.id.playerScore);

        gameOverDialog = new GameOverDialog(); //Will alert in the end of the game

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("difficulty", GameManager.BEGINNER);  //Get player's name from MainMenuActivity

        dbManager = new DBManager(this);

        initializeGame(); //Creating objects of GameManager and CellGridViewAdapter is here

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int result = gameManager.handleItemClick(position);
                cellsGridViewAdapter.notifyDataSetChanged(); //Update gridView
                playerScoreTextView.setText(String.valueOf(gameManager.getScore()));

                if(result == GameManager.GAME_LOST || result == GameManager.GAME_WON)
                    endOfTheGame(result);
            }
        });
    }

    private void initializeGame(){
        gameManager = new GameManager(difficulty);

        fieldSize = gameManager.getFieldSize();
        playerScoreTextView.setText(String.valueOf(gameManager.getScore()));

        cellsGridViewAdapter = new CellsGridViewAdapter(this, gameManager.getDataForAdapter());
        gridView.setAdapter(cellsGridViewAdapter);
        adjustGridView();
    }

    private void adjustGridView(){
        gridView.setNumColumns(fieldSize);
        gridView.setVerticalSpacing(0);
        gridView.setHorizontalSpacing(0);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    private void endOfTheGame(final int result_code){
        saveScoreToDB();
        showGameOverDialog(result_code);
    }

    private void showGameOverDialog(final int result_code){
        Bundle extra = new Bundle();
        extra.putInt("gameResult", result_code);
        gameOverDialog.setArguments(extra); //Sending result code of game to GameOverDialog
        gameOverDialog.setCancelable(false); //To turn off back button
        gameOverDialog.show(getFragmentManager(), "gameOverDialog");
    }

    public void onClick(View v){

    }

    @Override
    public void onFinishGameOverDialog(int result_code) {
        switch (result_code){
            case GameManager.END_GAME:
                endGame();
                break;
            case GameManager.RESTART_GAME:
                restartGame();
                break;
            case GameManager.NEW_GAME:
                newGame();
                break;
            default:
                break;
        }
    }

    private void endGame(){
        finish();
    }

    private void restartGame(){
        gameManager.restartGame();
        cellsGridViewAdapter.notifyDataSetChanged();
        playerScoreTextView.setText(String.valueOf(gameManager.getScore()));
    }

    private void newGame(){
        gameManager = null; //For garbage collector
        cellsGridViewAdapter = null;
        initializeGame();
    }

    private void saveScoreToDB(){
        dbManager.openConnection();
        dbManager.insertDataIntoDB(gameManager.getPlayerName(), gameManager.getScore());
        dbManager.closeConnection();
    }
}
