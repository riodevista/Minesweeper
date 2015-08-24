package dmitrybochkov.minesweeper;

public class Cell {
    private boolean mined;
    private boolean open;
    private byte numberOfMinesNear;

    Cell(){
        mined = false;
        open = false;
        numberOfMinesNear = 0;
    }

    public boolean isMined(){
        return mined;
    }

    public void setMine(){
        mined = true;
    }

    public boolean isOpen(){
        return open;
    }

    public void open(){
        open = true;
    }

    public void close(){
        open = false;
    }

    public byte getNumberOfMinesNear(){
        return numberOfMinesNear;
    }

    public void increaseNumberOfMinesNear(){
        ++numberOfMinesNear;
    }
}
