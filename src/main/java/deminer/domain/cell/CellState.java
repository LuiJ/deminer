package deminer.domain.cell;

abstract class CellState
{
    static final CellState UNCHECKED = new UncheckedState();
    static final CellState CHECKED = new CheckedState();
    static final CellState FLAGGED = new FlaggedState();
    static final CellState EXPLODED = new ExplodedState();
    
    private final String label;
    
    CellState(String label)
    {
        this.label = label;
    }
    
    abstract void check(Cell cell);
    
    abstract void setFlag(Cell cell);
    
    @Override
    public String toString()
    {
        return label;
    }
}
