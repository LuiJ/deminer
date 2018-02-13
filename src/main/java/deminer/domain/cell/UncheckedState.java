package deminer.domain.cell;

public class UncheckedState extends CellState
{
    private static final String UNCHECKED_STATE_LABEL = "UNCHECKED";
    
    UncheckedState()
    {
        super(UNCHECKED_STATE_LABEL);
    }
    
    @Override
    public void check(Cell cell)
    {
        if (cell.hasMine())
        {
            cell.setState(EXPLODED);
        }
        else 
        {
            cell.setState(CHECKED);
        }
    }
    
    @Override
    public void setFlag(Cell cell)
    {
        cell.setState(FLAGGED);
    }
}
