package dmitrybochkov.minesweeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CellsGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private Cell[][] cellsArray;
    private int fieldSize;

    CellsGridViewAdapter(Context context, Cell[][] cellsArray){
        mContext = context;
        this.cellsArray = cellsArray;
        fieldSize = cellsArray.length;
    }

    @Override
    public int getCount() {
        return fieldSize * fieldSize;
    }

    @Override
    public Object getItem(int position) {
        return cellsArray[position / fieldSize][position % fieldSize];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cell cell = (Cell)getItem(position); //The semantic content of a cell
        SquareImageView imageView; //The graphic content of a cell

        if (convertView == null){
            imageView = new SquareImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setAdjustViewBounds(false);
            imageView.setPadding(0, 0, 0, 0);
        }
        else{
            imageView = (SquareImageView)convertView;
        }

        //Set the correct image depending on the state of the cell.
        if(cell.isOpen()){
            if(cell.isMined())
                imageView.setImageResource(R.drawable.mined_cell);
            else
                switch (cell.getNumberOfMinesNear()){
                    case 0:
                        imageView.setImageResource(R.drawable.mines_near_0);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.mines_near_1);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.mines_near_2);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.mines_near_3);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.mines_near_4);
                        break;
                    case 5:
                        imageView.setImageResource(R.drawable.mines_near_5);
                        break;
                    case 6:
                        imageView.setImageResource(R.drawable.mines_near_6);
                        break;
                    case 7:
                        imageView.setImageResource(R.drawable.mines_near_7);
                        break;
                    case 8:
                        imageView.setImageResource(R.drawable.mines_near_8);
                        break;
                    default:
                        break;
                }
        }
        else
            imageView.setImageResource(R.drawable.closed_cell);
        return imageView;
    }
}
