package deminer.domain.cell;

public class Cell 
{   
    private static final Cell NULL_CELL = new NullCell();
    
    private final boolean hasMine;
    private final int minesAround;
    private CellState state;
    
    public static Cell newClearCell(int minesAround)
    {
        return new Cell(false, minesAround);
    }
    
    public static Cell newMinedCell(int minesAround)
    {
        return new Cell(true, minesAround);
    }
    
    public static Cell newNullCell()
    {
        return NULL_CELL;
    }
    
    Cell(boolean hasMine, int minesAround)
    {
        this.hasMine = hasMine;
        this.minesAround = minesAround;
        state = CellState.UNCHECKED;
    }
    
    public boolean hasMine()
    {
        return hasMine;
    }
    
    public int getMinesAround()
    {
        return minesAround;
    }
    
    void setState(CellState state)
    {
        this.state = state;
    }
    
    public String getState()
    {
        return state.toString();
    }
    
    public void check()
    {
        state.check(this);
    }
    
    public void setFlag()
    {
        state.setFlag(this);
    }
    
    public boolean isUnchecked()
    {
        return state == CellState.UNCHECKED;
    }
    
    public boolean isChecked()
    {
        return state == CellState.CHECKED;
    }
    
    public boolean isFlagged()
    {
        return state == CellState.FLAGGED;
    }
    
    public boolean isExploded()
    {
        return state == CellState.EXPLODED;
    }
    
    public boolean isAnalysed()
    {
        return isChecked() || isFlagged() || isExploded();
    }
}