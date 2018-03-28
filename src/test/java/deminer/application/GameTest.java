package deminer.application;

import deminer.application.Game;
import deminer.application.GameState;
import deminer.domain.minefield.AnalysedCell;
import deminer.domain.minefield.Minefield;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameTest 
{
    @Mock
    Minefield mockMinefield;
    @InjectMocks
    Game game = new Game();
    
    @Test
    public void newGame_shouldHaveDefaultConfiguration()
    {
        assertEquals(Game.DEFAULT_CONFIGURATION, game.getConfiguration());
        assertEquals(8, game.getConfiguration().getWidth());
        assertEquals(8, game.getConfiguration().getHeight());
        assertEquals(10, game.getConfiguration().getNumberOfMines());
    }
    
    @Test
    public void getState()
    {
        List<AnalysedCell> analysedCells = 
                Arrays.asList(new AnalysedCell(1, 1, 1, "CHECKED"));
        String minefieldState = "MINED";
        
        when(mockMinefield.getAnalysedCells()).thenReturn(analysedCells);
        when(mockMinefield.getState()).thenReturn(minefieldState);
        
        GameState gameState = game.getState();
        
        assertEquals(analysedCells, gameState.getAnalysedCells());
        assertEquals(minefieldState, gameState.getMinefieldState());
    }
}
