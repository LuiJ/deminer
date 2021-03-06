package deminer.domain.minefield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.VisibleForTesting;

import deminer.domain.cell.Cell;

public class Minefield 
{   
    @VisibleForTesting
    static final String MINED_MINEFIELD_STATE = "MINED";
    @VisibleForTesting
    static final String DEMINED_MINEFIELD_STATE = "DEMINED";
    @VisibleForTesting
    static final String EXPLODED_MINEFIELD_STATE = "EXPLODED";   
    
    private final Map<Point, Cell> cells;
    private String state;
    
    Minefield(Map<Point, Cell> cells)
    {
        this.cells = Collections.unmodifiableMap(cells);
        state = MINED_MINEFIELD_STATE;
    }
    
    public Map<Point, Cell> getCells()
    {
        return cells;
    }
    
    public void checkCellAt(Point point)
    {
        if (isMinefieldMined())
        {
            Cell cell = getCellAt(point);
            cell.check();            
            updateState();
            if (cell.getMinesAround() == 0)
            {
                checkSurroundings(point);
            }
        }        
    }
    
    public void setFlagForCellAt(Point point)
    {
        if (isMinefieldMined())
        {
            getCellAt(point).setFlag();
            updateState();
        }        
    }
    
    public List<AnalysedCell> getAnalysedCells()
    {
        List<AnalysedCell> analysedCells = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry : cells.entrySet())
        {
            Cell cell = entry.getValue();
            if (cell.isAnalysed())
            {
                Point point = entry.getKey();
                analysedCells.add(newAnalysedCell(point, cell));
            }
        }
        return analysedCells;
    }
    
    public synchronized String getState()
    {
        return state;
    }
    
    @VisibleForTesting
    synchronized void updateState()
    {
        if (hasExplodedCell())
        {
            state = EXPLODED_MINEFIELD_STATE;
        }
        else if (wasMinefieldDemined())
        {
            state = DEMINED_MINEFIELD_STATE;
        }        
    }
    
    @VisibleForTesting
    Cell getCellAt(Point point)
    {        
        Cell cell = cells.get(point);
        if (cell == null)
        {
            cell = Cell.newNullCell();
        }
        return cell;
    }
    
    private boolean isMinefieldMined()
    {
        return MINED_MINEFIELD_STATE.equals(state);
    }
    
    private void checkSurroundings(Point point)
    {
        point.getSurroundPoints().stream()
            .filter(p -> getCellAt(p).isUnchecked())
            .forEach(neighborPoint -> checkCellAt(neighborPoint));                
    }
    
    @VisibleForTesting
    boolean wasMinefieldDemined()
    {        
        int totalNumberOfCells = cells.entrySet().size();
        int numberOfCheckedCells = getNumberOfCheckedCells();
        int numberOfFlaggedCells = getNumberOfFlaggedCells();
        
        return numberOfCheckedCells != 0 &&
            totalNumberOfCells == numberOfCheckedCells + numberOfFlaggedCells;
    }
    
    @VisibleForTesting
    boolean hasExplodedCell()
    {
        for (Map.Entry<Point, Cell> entry : cells.entrySet())
        {
            Cell cell = entry.getValue();
            if (cell.isExploded())
            {
                return true;
            }
        }
        return false;
    }
    
    @VisibleForTesting
    int getNumberOfCheckedCells()
    {
        int numberOfCheckedCells = 0;
        for (Map.Entry<Point, Cell> entry : cells.entrySet())
        {
            Cell cell = entry.getValue();
            if (cell.isChecked())
            {
                numberOfCheckedCells++;
            }
        }
        return numberOfCheckedCells;
    }
    
    @VisibleForTesting
    int getNumberOfFlaggedCells()
    {
        int numberOfFlaggedCells = 0;
        for (Map.Entry<Point, Cell> entry : cells.entrySet())
        {
            Cell cell = entry.getValue();
            if (cell.isFlagged())
            {
                numberOfFlaggedCells++;
            }
        }
        return numberOfFlaggedCells;
    }
    
    private AnalysedCell newAnalysedCell(Point point, Cell cell)
    {
        int x = point.getX();
        int y = point.getY();
        int minesAround = cell.getMinesAround();
        String callState = cell.getState();
        
        return new AnalysedCell(x, y, minesAround, callState);
    }
}
