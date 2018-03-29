package deminer.domain.minefield;

import deminer.domain.cell.Cell;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;        
import org.junit.Before;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinefieldTest 
{  
    private Minefield minefield;
    
    @Before
    public void setUp()
    {
        MinefieldConfiguration configuration = new MinefieldConfiguration(8, 8, 10);
        minefield = new MinefieldFactory(configuration).create();
    }
    
    @Test
    public void newMinefield_shouldHaveMinedState()
    {
        assertEquals(Minefield.MINED_MINEFIELD_STATE, minefield.getState());
    }
    
    @Test
    public void getCellAtPoint()
    {
        Point pointWithinMinefield = new Point(4, 7);
        
        Cell cell = minefield.getCellAt(pointWithinMinefield);
        
        assertNotNull(cell);
    }
    
    @Test
    public void getCellAtPoint_shouldReturnNullCellForPointOutOfMinefield() 
    {
        Point pointOutOfMinefield = new Point(9, 10);
        Cell nullCell = Cell.newNullCell();
        
        Cell cell = minefield.getCellAt(pointOutOfMinefield);
        
        assertEquals(nullCell, cell);
    }
    
    @Test
    public void checkCellAtPoint_shouldCheckSurroundingsForCellWithoutMinesAround()
    {
        int width = 8; 
        int height = 8;
        Minefield clearMinefield = createMinefieldWithoutMines(width, height);
        Point pointWithinMinefield = new Point(4, 5);  
        
        clearMinefield.checkCellAt(pointWithinMinefield);
        
        clearMinefield.getCells().entrySet().forEach(entry -> 
            {
                assertTrue(entry.getValue().isChecked());
            });
    }
    
    @Test
    public void getAnalysedCells()
    {
        Map<Point, Cell> cells = new HashMap<>();
        
        String state1 = "CHECKED";
        int x1 = 1;
        int y1 = 1;
        int minesAround1 = 0;
        AnalysedCell analysedCell1 = new AnalysedCell(x1, y1, minesAround1, state1);
        Point point1 = new Point(x1, y1);
        Cell cell1 = Cell.newClearCell(0);
        cell1.check();
        cells.put(point1, cell1);
        
        String state2 = "FLAGGED";
        int x2 = 2;
        int y2 = 1;
        int minesAround2 = 0;
        AnalysedCell analysedCell2 = new AnalysedCell(x2, y2, minesAround2, state2);
        Point point2 = new Point(x2, y2);
        Cell cell2 = Cell.newClearCell(0);
        cell2.setFlag();
        cells.put(point2, cell2);
        
        Minefield minefieldWithTwoAnalysedCells = new Minefield(cells);
        List<AnalysedCell> analysedCells = 
            minefieldWithTwoAnalysedCells.getAnalysedCells();
        
        assertEquals(2, analysedCells.size());
        assertTrue(analysedCells.contains(analysedCell1));
        assertTrue(analysedCells.contains(analysedCell2));
    }
    
    @Test
    public void updateState_shouldSetExplodedStateForMinefieldWithExplodedCell()
    {
        Minefield deminedMinefield = createDeminedMinefield();
        
        assertEquals(Minefield.MINED_MINEFIELD_STATE, 
            deminedMinefield.getState());
        
        deminedMinefield.updateState();
        
        assertEquals(Minefield.DEMINED_MINEFIELD_STATE, 
            deminedMinefield.getState());
    }
    
    @Test
    public void updateState_shouldSetDeminedStateForDeminedMinefield()
    {
        Minefield minefieldWithExplodedCell = createMinefieldWithExplodedCell();
        
        assertEquals(Minefield.MINED_MINEFIELD_STATE, 
            minefieldWithExplodedCell.getState());
        
        minefieldWithExplodedCell.updateState();
        
        assertEquals(Minefield.EXPLODED_MINEFIELD_STATE, 
            minefieldWithExplodedCell.getState());
    }
    
    @Test
    public void wasMinefieldDemined()
    {
        Minefield deminedMinefield = createDeminedMinefield();
        
        assertTrue(deminedMinefield.wasMinefieldDemined());
    }
    
    @Test
    public void hasExplodedCell()
    {
        Minefield minefieldWithExplodedCell = createMinefieldWithExplodedCell();
        
        assertTrue(minefieldWithExplodedCell.hasExplodedCell());
    }
    
    @Test
    public void getNumberOfCheckedCells()
    {
        Minefield minefieldWithThreeCheckedCells = createDeminedMinefield();
        
        assertEquals(3, minefieldWithThreeCheckedCells.getNumberOfCheckedCells());
    }
    
    @Test
    public void getNumberOfFlaggedCells()
    {
        Minefield minefieldWithOneFlaggedCells = createDeminedMinefield();
        
        assertEquals(1, minefieldWithOneFlaggedCells.getNumberOfFlaggedCells());
    }
    
    private Minefield createMinefieldWithoutMines(int width, int height)
    {
        Map<Point, Cell> cells = new HashMap<>(width * height);
        for (int y = 1; y <= height; y++)
        {
            for (int x = 1; x <= width; x++)
            {
                Point point = new Point(x, y);
                Cell cell = Cell.newClearCell(0);
                cells.put(point, cell);
            }
        }
        return new Minefield(cells);
    }
    
    private Minefield createDeminedMinefield()
    {
        Map<Point, Cell> cells = new HashMap<>();
        
        Point point1 = new Point(1, 1);
        Cell cell1 = Cell.newMinedCell(0);
        cell1.setFlag();
        cells.put(point1, cell1);
        
        Point point2 = new Point(2, 1);
        Cell cell2 = Cell.newClearCell(1);
        cell2.check();
        cells.put(point2, cell2);
        
        Point point3 = new Point(1, 2);
        Cell cell3 = Cell.newClearCell(1);
        cell3.check();
        cells.put(point3, cell3);
        
        Point point4 = new Point(2, 2);
        Cell cell4 = Cell.newClearCell(1);
        cell4.check();
        cells.put(point4, cell4);       
        
        return new Minefield(cells);
    }
    
    private Minefield createMinefieldWithExplodedCell()
    {
        Map<Point, Cell> cells = new HashMap<>();
        
        Point point = new Point(1, 1);
        Cell minedCell = Cell.newMinedCell(0);
        minedCell.check();
        cells.put(point, minedCell);
        
        return new Minefield(cells);
    }
}
