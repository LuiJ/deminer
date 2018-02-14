package deminer.presentation;

import deminer.domain.minefield.AnalysedCell;
import deminer.domain.minefield.MinefieldConfiguration;
import deminer.domain.minefield.MinefieldCreationException;
import deminer.domain.minefield.Point;
import deminer.operation.Game;
import deminer.operation.GameState;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest 
{
    @Mock
    private Model mockGameModel;
    @Mock
    private Game game;
    @InjectMocks
    private GameController gameController;
    
    HttpServletResponse mockResponse = new MockHttpServletResponse();
    
    @Test
    public void openGame() throws MinefieldCreationException
    {
        String expectedViewName = "game";
        
        String actualViewName = gameController.openGame(mockGameModel);
        
        assertEquals(expectedViewName, actualViewName);
        verify(game, times(1)).start();
    }
    
    @Test
    public void openDefaultGame_shouldConfigureGameWithDefaultConfiguration()
    {
        String expectedRedirectLine = "redirect:/game";
        
        String actualRedirectLine = gameController.openDefaultGame();
        
        assertEquals(expectedRedirectLine, actualRedirectLine);
        verify(game, times(1)).configure(Game.DEFAULT_CONFIGURATION);       
    }
    
    @Test
    public void openCustomGame_shouldConfigureGameWithCustomConfiguration()
    {
        int width = 8;
        int height = 8;
        int numberOfMines = 10;
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(width, height, numberOfMines);
        String expectedRedirectLine = "redirect:/game";
        
        String actualRedirectLine = 
            gameController.openCustomGame(width, height, numberOfMines);
        
        assertEquals(expectedRedirectLine, actualRedirectLine);
        verify(game, times(1)).configure(configuration);       
    }
    
    @Test
    public void updateCell_checkCellAction()
    {
        int x = 1;
        int y = 1;
        Point point = new Point(x, y);
        String checkCellAction = "CHECK";
        UpdateCellStateRequest request = 
            new UpdateCellStateRequest(checkCellAction);
        
        gameController.updateCell(x, y, request, mockResponse);
        
        verify(game, times(1)).checkCellAt(point);
        assertEquals(HttpServletResponse.SC_OK, mockResponse.getStatus());
    }
    
    @Test
    public void updateCell_setFlagForCellAction()
    {
        int x = 1;
        int y = 1;
        Point point = new Point(x, y);
        String checkCellAction = "SETFLAG";
        UpdateCellStateRequest request = 
            new UpdateCellStateRequest(checkCellAction);
        
        gameController.updateCell(x, y, request, mockResponse);
        
        verify(game, times(1)).setFlagForCellAt(point);
        assertEquals(HttpServletResponse.SC_OK, mockResponse.getStatus());
    }   
    
    @Test(expected = ActionNotSupportedException.class)
    public void updateCell_shouldFailForRequestWithIncorrectAction()
    {
        int x = 1;
        int y = 1;
        String checkCellAction = "INCORRECTACTION";
        UpdateCellStateRequest request = 
            new UpdateCellStateRequest(checkCellAction);
        
        gameController.updateCell(x, y, request, mockResponse);
    }
    
    @Test
    public void getGameState()
    {
        String minefieldState = "MINED";
        List<AnalysedCell> analysedCells = 
            Arrays.asList(new AnalysedCell(1, 1, 0, "CHECKED"));
        GameState gameState = new GameState(analysedCells, minefieldState);
        
        when(game.getState()).thenReturn(gameState);
        
        assertEquals(gameState, gameController.getGameState());
    }
    
    @Test
    public void startGame_shouldStartGameWithCorrectConfiguration() 
        throws MinefieldCreationException
    {
        gameController.startGame(mockGameModel);
        verify(game, times(1)).start();
    }
    
    @Test
    public void startGame_shouldReplaceIncorrectCofigurationWithDefaultAndStart() 
        throws MinefieldCreationException
    {        
        doThrow(MinefieldCreationException.class).doNothing().when(game).start();
        
        gameController.startGame(mockGameModel);
        verify(game, times(2)).start();
    }
}
