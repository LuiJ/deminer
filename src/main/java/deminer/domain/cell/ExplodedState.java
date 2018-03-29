package deminer.domain.cell;

class ExplodedState extends CellState
{
    private static final String EXPLODED_STATE_LABEL = "EXPLODED";
    
    ExplodedState()
    {
        super(EXPLODED_STATE_LABEL);
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
