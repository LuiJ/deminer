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
    
    public CellState getState()
    {
        return state;
    }
    
    public void check()
    {
        state.check(this);
    }
    
    public void setFlag()
    {
        state.setFlag(this);
    }
}