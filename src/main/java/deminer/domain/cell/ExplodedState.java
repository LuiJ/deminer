package deminer.domain.cell;

public class ExplodedState extends CellState
{
    private static final String EXPLODED_STATE_LABEL = "EXPLODED";
    
    ExplodedState()
    {
        super(EXPLODED_STATE_LABEL);
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
