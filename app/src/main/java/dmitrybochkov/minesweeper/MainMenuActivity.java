package dmitrybochkov.minesweeper;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainMenuActivity extends Activity {

    Button newGameButton;
    Button showTop10Button;
    DialogFragment top10Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        newGameButton = (Button) findViewById(R.id.newGameButton);
        showTop10Button = (Button) findViewById(R.id.showTop10Button);
        top10Dialog = new Top10Dialog();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.newGameButton:
                newGame();
                break;
            case R.id.showTop10Button:
                showTop10Dialog();
                break;
            default:
                break;
        }
    }

    private void newGame(){
        Intent intent = new Intent(this, ChooseDifficultyActivity.class);
        /*String enteredText = enterNameField.getText().toString();
        //Protect from empty name
        if(enteredText.matches("")){
            Toast.makeText(this, R.string.enter_your_name_message, Toast.LENGTH_SHORT).show();
        }
        else{
            intent.putExtra("playerName", enterNameField.getText().toString());
            startActivity(intent);
        }*/
        startActivity(intent);
    }

    private void showTop10Dialog(){
        top10Dialog.show(getFragmentManager(), "top10Dialog");
    }
}
