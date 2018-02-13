package deminer.presentation;

import deminer.domain.minefield.MinefieldConfiguration;
import deminer.domain.minefield.MinefieldCreationException;
import deminer.domain.minefield.Point;
import deminer.operation.Game;
import deminer.operation.GameState;
import javax.servlet.http.HttpServletResponse;
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
    private static final String CHECK_CELL_ACTION = "CHECK";
    private static final String SET_FLAG_TO_CELL_ACTION = "SETFLAG";
    
    private static final String ACTION_IS_NOT_SUPPORTED_ERROR_MESSAGE_FORMAT = 
        "Action '%s' is not supported.";
    
    @Autowired
    private Game game;
    
    @RequestMapping(value="/game", method=RequestMethod.GET)
    public String openGame(Model gameModel)
    {
        startGame(gameModel);
        return "game";
    }
    
    @RequestMapping(value="/game/default", method=RequestMethod.GET)
    public String openDefaultGame(Model gameModel)
    {
        game.configure(Game.DEFAULT_CONFIGURATION);
        return "redirect:/game";
    }
    
    @RequestMapping(value="/game/custom", method=RequestMethod.POST)
    public String openCustomGame(Model gameModel, @RequestParam int width, 
        @RequestParam int height, @RequestParam int numberOfMines)
    {
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(width, height, numberOfMines);
        game.configure(configuration);
        return "redirect:/game";
    }
    
    @RequestMapping(value="game/cell/{x}/{y}", method=RequestMethod.PUT)
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
    
    @RequestMapping(value="/game/state", method=RequestMethod.GET)
    public @ResponseBody GameState getGameState()
    {
        return game.getState();
    }
    
    @SuppressWarnings("InfiniteRecursion")
    private void startGame(Model gameModel)
    {
        try 
        {
            game.start(); 
            gameModel.addAttribute("game", game);
        }
        catch (MinefieldCreationException e)
        {
            gameModel.addAttribute("errorMessage", e.getMessage());
            game.configure(Game.DEFAULT_CONFIGURATION);
            startGame(gameModel);
        }
    }
}