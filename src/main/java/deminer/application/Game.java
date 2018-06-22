package deminer.application;

import java.util.List;

import deminer.domain.minefield.AnalysedCell;
import deminer.domain.minefield.Minefield;
import deminer.domain.minefield.MinefieldConfiguration;
import deminer.domain.minefield.MinefieldFactory;
import deminer.domain.minefield.Point;

public class Game 
{    
    private static final int DEFAULT_MINEFIELD_WIDTH = 8;
    private static final int DEFAULT_MINEFIELD_HEIGHT = 8;
    private static final int DEFAULT_NUMBER_OF_MINES = 10;
    
    public static final MinefieldConfiguration DEFAULT_CONFIGURATION = 
        new MinefieldConfiguration(DEFAULT_MINEFIELD_WIDTH, 
            DEFAULT_MINEFIELD_HEIGHT, DEFAULT_NUMBER_OF_MINES);
    
    private MinefieldConfiguration configuration;
    private Minefield minefield;
    
    public Game()
    {
        configuration = DEFAULT_CONFIGURATION;
    }
    
    public void configure(MinefieldConfiguration configuration)
    {
        this.configuration = configuration;
    }
    
    public MinefieldConfiguration getConfiguration()
    {
        return configuration;
    }
    
    public void start()
    {
        MinefieldFactory factory = new MinefieldFactory(configuration);
        minefield = factory.create();
    }
    
    public void checkCellAt(Point point)
    {
        minefield.checkCellAt(point);
    }
    
    public void setFlagForCellAt(Point point)
    {
        minefield.setFlagForCellAt(point);
    }
    
    public GameState getState()
    {
        List<AnalysedCell> analysedCells = minefield.getAnalysedCells();
        String minefieldState = minefield.getState();
        
        return new GameState(analysedCells, minefieldState);
    }       
}
