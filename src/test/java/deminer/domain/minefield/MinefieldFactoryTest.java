package deminer.domain.minefield;

import deminer.domain.cell.Cell;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinefieldFactoryTest 
{    
    @Test
    public void create() throws MinefieldCreationException
    {
        int minefieldWidth = 8;
        int minefieldHeight = 8;
        int numberOfMines = 10;
        int expectedNumberOfCells = minefieldWidth * minefieldHeight;
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(minefieldWidth, minefieldHeight, numberOfMines);
        
        Minefield minefield = new MinefieldFactory(configuration).create();
        
        assertEquals(expectedNumberOfCells, minefield.getCells().size());
        assertEquals(numberOfMines, getNumberOfMines(minefield));
    }
    
    @Test(expected = MinefieldCreationException.class)
    public void create_shouldFailWhenWidthOrHeightIsZero()
        throws MinefieldCreationException
    {
        int width = 0;
        int height = 0;
        int numberOfMines = 10;        
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(width, height, numberOfMines);
        
        new MinefieldFactory(configuration).create();
    }
    
    @Test(expected = MinefieldCreationException.class)
    public void create_shouldFailWhenNumberOfMinesExceedsMinefieldSize()
        throws MinefieldCreationException
    {
        int width = 3;
        int height = 3;
        int numberOfMines = 10;        
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(width, height, numberOfMines);
        
        new MinefieldFactory(configuration).create();
    }
    
    @Test
    public void getPointsForMinedCells()
    {
        int width = 8;
        int height = 10;
        int numberOfMines = 15;
        MinefieldConfiguration configuration = 
            new MinefieldConfiguration(width, height, numberOfMines);
        
        Set<Point> pointsForMinedCells = 
            new MinefieldFactory(configuration).getPointsForMinedCells();
        
        assertEquals(numberOfMines, pointsForMinedCells.size());
    }
    
    @Test
    public void getMinesAround()
    {
        Point point = new Point(1, 1);
        Set<Point> pointsForMinedCells = new HashSet<>();
        pointsForMinedCells.add(new Point(0, 2));       
        pointsForMinedCells.add(new Point(10, 3));
        pointsForMinedCells.add(new Point(1, 0));       
        pointsForMinedCells.add(new Point(4, 5));        
        pointsForMinedCells.add(new Point(0, 0));              
        pointsForMinedCells.add(new Point(1, 8));                      
        pointsForMinedCells.add(new Point(3, 8));         
        MinefieldConfiguration configuration = new MinefieldConfiguration(8, 8, 10);
        
        int minesAround = new MinefieldFactory(configuration)
            .getMinesAround(point, pointsForMinedCells);
        
        assertEquals(3, minesAround);        
    }
    
    private int getNumberOfMines(Minefield minefield)
    {
        int numberOfMines = 0;
        for (Map.Entry<Point, Cell> entry : minefield.getCells().entrySet())
        {
            if (entry.getValue().hasMine())
            {
                numberOfMines++;
            }
        }
        return numberOfMines;
    }
}
