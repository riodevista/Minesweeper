package dmitrybochkov.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseDifficultyActivity extends Activity {

    Button startSimpleGameButton;
    Button startNormalGameButton;
    Button startHardGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);

        startSimpleGameButton = (Button) findViewById(R.id.startSimpleGameButton);
        startNormalGameButton = (Button) findViewById(R.id.startNormalGameButton);
        startHardGameButton = (Button) findViewById(R.id.startHardGameButton);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.startSimpleGameButton:
                startGame(GameManager.BEGINNER);
                break;
            case R.id.startNormalGameButton:
                startGame(GameManager.INTERMEDIATE);
                break;
            case R.id.startHardGameButton:
                startGame(GameManager.EXPERT);
                break;
            default:
                break;
        }
    }

    private void startGame(final int difficulty){
        Intent intent = new Intent(this, PlayingScreenActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}
