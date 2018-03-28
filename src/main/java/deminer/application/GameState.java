package deminer.application;

import deminer.domain.minefield.AnalysedCell;
import java.util.ArrayList;
import java.util.List;

public class GameState 
{
    private final List<AnalysedCell> analysedCells;
    private final String minefieldState;
    
    public GameState(List<AnalysedCell> analysedCells, String minefieldState)
    {
        this.analysedCells = new ArrayList<>(analysedCells);
        this.minefieldState = minefieldState;
    }

    public List<AnalysedCell> getAnalysedCells() 
    {
        return analysedCells;
    }

    public String getMinefieldState() 
    {
        return minefieldState;
    }
}
