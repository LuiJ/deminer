package deminer.domain.minefield;

import deminer.domain.cell.Cell;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.assertj.core.util.VisibleForTesting;

public class MinefieldFactory 
{    
    private static final String MINEFIELD_SIZE_ERROR = 
        "Width or height of minefield can't be zero";
    private static final String NO_MINES_ERROR = 
        "Minefield should contain at least 1 mine";
    private static final String MORE_MINES_THAN_CELLS_ERROR = 
        "Number of mines exceeds minefield size";
    
    
    private final int width;
    private final int height;
    private final int numberOfMines;
    
    public MinefieldFactory(MinefieldConfiguration configuration)
    {
        width = configuration.getWidth();
        height = configuration.getHeight();
        numberOfMines = configuration.getNumberOfMines();
    }
    
    public Minefield create() throws MinefieldCreationException
    {
        checkConfiguration();
        
        Map<Point, Cell> cells = createCells();
        
        return new Minefield(cells);
    }
    
    private void checkConfiguration() throws MinefieldCreationException
    {
        if (width == 0 || height == 0)
        {
            throw new MinefieldCreationException(MINEFIELD_SIZE_ERROR);
        }
        if (numberOfMines < 1)
        {
            throw new MinefieldCreationException(NO_MINES_ERROR);
        }
        int numberOfCells = width * height;
        if (numberOfMines > numberOfCells)
        {
            throw new MinefieldCreationException(MORE_MINES_THAN_CELLS_ERROR);
        }
    }
    
    private Map<Point, Cell> createCells()
    {
        Map<Point, Cell> cells = createEmptyCellsMap(); 
        initialize(cells);
        return cells;
    }
    
    private Map<Point, Cell> createEmptyCellsMap()
    {
        int mapCapacity = width * height;
        return new HashMap<>(mapCapacity);
    }
    
    private void initialize(Map<Point, Cell> cells)
    {
        Set<Point> pointsForMinedCells = getPointsForMinedCells();         
        for (int y = 1; y <= height; y++)
        {
            for (int x = 1; x <= width; x++)
            {
                Point point = new Point(x, y);
                Cell cell;
                int minesAround = getMinesAround(point, pointsForMinedCells);
                if (pointsForMinedCells.contains(point))
                {
                    cell = Cell.newMinedCell(minesAround);
                }
                else 
                {
                    cell = Cell.newClearCell(minesAround);
                }
                cells.put(point, cell);
            }
        }
    }
    
    @VisibleForTesting
    Set<Point> getPointsForMinedCells()
    {
        Set<Point> pointsForMinedCells = new HashSet<>(numberOfMines);
        do
        {
            int x = new Random().nextInt(width) + 1;
            int y = new Random().nextInt(height) + 1;
            pointsForMinedCells.add(new Point(x, y));
        }
        while (pointsForMinedCells.size() < numberOfMines);
        return pointsForMinedCells;
    }  
    
    @VisibleForTesting
    int getMinesAround(Point point, Set<Point> pointsWithMinedCells)
    {
        int minesAround = 0;
        for (Point neighborPoint : point.getSurroundPoints())
        {
            if (pointsWithMinedCells.contains(neighborPoint))
            {
                minesAround++;
            }
        }
        return minesAround;
    }
}
