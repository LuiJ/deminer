package deminer.domain.cell;

public class CheckedState extends CellState
{
    private static final String CHECKED_STATE_LABEL = "CHECKED";
    
    CheckedState()
    {
        super(CHECKED_STATE_LABEL);
    }
    
    @Override
    public void check(Cell cell)
    {
    }
    
    @Override
    public void setFlag(Cell cell)
    {
    }
}