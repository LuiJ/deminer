package deminer.presentation;

import deminer.domain.minefield.MinefieldConfiguration;
import deminer.domain.minefield.MinefieldCreationException;
import deminer.domain.minefield.Point;
import deminer.operation.Game;
import deminer.operation.GameState;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController 
{
    private static final String GAME_URI = "/game";
    private static final String DEFAULT_GAME_URI = "/game/default";
    private static final String CUSTOM_GAME_URI = "/game/custom";
    private static final String UPDATE_CELL_STATE_URI = "game/cell/{x}/{y}";
    private static final String GAME_STATE_URI = "/game/state";
    
    private static final String GAME_VIEW_NAME = "game";
    private static final String REDIRECT_LINE = "redirect:" + GAME_URI;
    
    private static final String GAME_MODEL_ATTRIBUTE = "game";
    private static final String ERROR_MESSAGE_MODEL_ATTRIBUTE = "errorMessage";
    
    private static final String CHECK_CELL_ACTION = "CHECK";
    private static final String SET_FLAG_TO_CELL_ACTION = "SETFLAG";
    
    private static final String ACTION_IS_NOT_SUPPORTED_ERROR_MESSAGE_FORMAT = 
        "Action '%s' is not supported.";   
    
    @Autowired
    private Game game;
    
    @RequestMapping(value=GAME_URI, method=RequestMethod.GET)
    public String openGame(Model gameModel)
    {
        startGame(gameModel);
        return GAME_VIEW_NAME;
    }
    
    @RequestMapping(value=DEFAULT_GAME_URI, method=RequestMethod.GET)
    public String openDefaultGame()
    {
        game.configure(Game.DEFAULT_CONFIGURATION);
        return REDIRECT_LINE;
    }
    
    @RequestMapping(value=CUSTOM_GAME_URI, method=RequestMethod.POST)
    public String openCustomGame(@RequestParam int width, 
        @RequestParam int height, @RequestParam int numberOfMines)
    {
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(width, height, numberOfMines);
        game.configure(configuration);
        return REDIRECT_LINE;
    }
    
    @RequestMapping(value=UPDATE_CELL_STATE_URI, method=RequestMethod.PUT)
    public void updateCell(@PathVariable int x, @PathVariable int y, 
        @RequestBody UpdateCellStateRequest request, HttpServletResponse response)
    {
        Point point = new Point(x, y);
        String cellAction = request.getAction();        
        switch (cellAction)
        {
            case CHECK_CELL_ACTION: 
                game.checkCellAt(point);
                break;
            case SET_FLAG_TO_CELL_ACTION:
                game.setFlagForCellAt(point);
                break;
            default: 
                throw new ActionNotSupportedException(
                    String.format(ACTION_IS_NOT_SUPPORTED_ERROR_MESSAGE_FORMAT, 
                        cellAction));            
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    @RequestMapping(value=GAME_STATE_URI, method=RequestMethod.GET)
    public @ResponseBody GameState getGameState()
    {
        return game.getState();
    }
    
    @VisibleForTesting
    @SuppressWarnings("InfiniteRecursion")
    void startGame(Model gameModel)
    {
        try 
        {
            game.start(); 
            gameModel.addAttribute(GAME_MODEL_ATTRIBUTE, game);
        }
        catch (MinefieldCreationException e)
        {
            gameModel.addAttribute(ERROR_MESSAGE_MODEL_ATTRIBUTE, e.getMessage());
            game.configure(Game.DEFAULT_CONFIGURATION);
            startGame(gameModel);
        }
    }
}
