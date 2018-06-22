package deminer.application;

import java.util.ArrayList;
import java.util.List;

import deminer.domain.minefield.AnalysedCell;

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
