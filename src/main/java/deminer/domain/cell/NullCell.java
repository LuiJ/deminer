package deminer.domain.cell;

public class NullCell extends Cell
{
    private static final boolean HAS_MINE = false;
    private static final int MINES_AROUND_NULL_CELL = -1;
    
    NullCell()
    {
        super(HAS_MINE, MINES_AROUND_NULL_CELL);
    }
    
    @Override
    public void check()
    {
    }
    
    @Override
    public void setFlag()
    {
    }
}
