package deminer.domain.cell;

class CheckedState extends CellState
{
    private static final String CHECKED_STATE_LABEL = "CHECKED";
    
    CheckedState()
    {
        super(CHECKED_STATE_LABEL);
    }
    
    @Override
    void check(Cell cell)
    {
    }
    
    @Override
    void setFlag(Cell cell)
    {
    }
}