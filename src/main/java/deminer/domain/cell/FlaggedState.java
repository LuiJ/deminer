package deminer.domain.cell;

public class FlaggedState extends CellState
{
    private static final String FLAGGED_STATE_LABEL = "FLAGGED";
    
    FlaggedState()
    {
        super(FLAGGED_STATE_LABEL);
    }    
    
    @Override
    public void check(Cell cell)
    {
    }
    
    @Override
    public void setFlag(Cell cell)
    {
        cell.setState(UNCHECKED);
    }
}
