package deminer.domain.cell;

public abstract class CellState
{
    public static final CellState UNCHECKED = new UncheckedState();
    public static final CellState CHECKED = new CheckedState();
    public static final CellState FLAGGED = new FlaggedState();
    public static final CellState EXPLODED = new ExplodedState();
    
    private final String label;
    
    CellState(String label)
    {
        this.label = label;
    }
    
    abstract public void check(Cell cell);
    
    abstract public void setFlag(Cell cell);
    
    @Override
    public String toString()
    {
        return label;
    }
}
