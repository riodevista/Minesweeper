package dmitrybochkov.minesweeper;

import android.content.Context;
import android.widget.ImageView;

//To make cells square

public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
