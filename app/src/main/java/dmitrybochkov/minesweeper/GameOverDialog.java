package dmitrybochkov.minesweeper;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GameOverDialog extends DialogFragment implements View.OnClickListener{

    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    ListView top10List;
    DBManager dbManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Setting title of dialog depends on how game is over
        switch (getArguments().getInt("gameResult")){
            case GameManager.GAME_WON:
                getDialog().setTitle(R.string.winning_message);
                break;
            case GameManager.GAME_LOST:
                getDialog().setTitle(R.string.loosing_message);
                break;
            default:
                break;
        }

        View v = inflater.inflate(R.layout.game_over_dialog, null);
        v.findViewById(R.id.end_game_button).setOnClickListener(this);
        v.findViewById(R.id.restart_game_button).setOnClickListener(this);
        v.findViewById(R.id.new_game_button).setOnClickListener(this);

        //Setting up Top10 list via SimpleCursorAdapter
        top10List = (ListView)v.findViewById(R.id.top10DialogList);

        dbManager = new DBManager(v.getContext());
        dbManager.openConnection();

        cursor = dbManager.getTop10Data();

        String[] from = new String[] {DBManager.KEY_ID, DBManager.KEY_NICKNAME, DBManager.KEY_SCORE};
        int[] to = new int[] {R.id.uselessId, R.id.playerNameInList, R.id.scoreInList};

        simpleCursorAdapter = new SimpleCursorAdapter(v.getContext(), R.layout.list_item, cursor, from, to);
        top10List.setAdapter(simpleCursorAdapter);

        dbManager.closeConnection();

        return v;
    }

    @Override
    public void onClick(View v) {
        GameOverDialogListener activity = (GameOverDialogListener) getActivity();
        switch (v.getId()){
            case R.id.end_game_button:
                activity.onFinishGameOverDialog(GameManager.END_GAME);
                break;
            case R.id.restart_game_button:
                activity.onFinishGameOverDialog(GameManager.RESTART_GAME);
                break;
            case R.id.new_game_button:
                activity.onFinishGameOverDialog(GameManager.NEW_GAME);
                break;
            default:
                break;
        }
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public interface GameOverDialogListener{
        void onFinishGameOverDialog(int result_code);
    }
}
