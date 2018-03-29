package deminer.domain.cell;

class FlaggedState extends CellState
{
    private static final String FLAGGED_STATE_LABEL = "FLAGGED";
    
    FlaggedState()
    {
        super(FLAGGED_STATE_LABEL);
    }    
    
    @Override
    void check(Cell cell)
    {
    }
    
    @Override
    void setFlag(Cell cell)
    {
        cell.setState(UNCHECKED);
    }
}
