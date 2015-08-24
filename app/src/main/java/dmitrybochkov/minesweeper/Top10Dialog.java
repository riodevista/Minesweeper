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

public class Top10Dialog extends DialogFragment implements View.OnClickListener {

    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    ListView top10List;
    DBManager dbManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.top10_dialog_title);
        View v = inflater.inflate(R.layout.top10_dialog, null);
        v.findViewById(R.id.okay_button).setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.okay_button:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
