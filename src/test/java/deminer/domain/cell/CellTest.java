package deminer.domain.cell;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CellTest 
{   
    @Test
    public void newClearCell()
    {
        Cell cell = Cell.newClearCell(5);
        
        assertFalse(cell.hasMine());
        assertEquals(CellState.UNCHECKED, cell.getState());
    }
    
    @Test
    public void newMinedCell()
    {
        Cell cell = Cell.newMinedCell(5);
        
        assertTrue(cell.hasMine());
        assertEquals(CellState.UNCHECKED, cell.getState());
    }
    
    @Test
    public void newNullCell()
    {
        Cell cell = Cell.newNullCell();
        
        assertFalse(cell.hasMine());
        assertEquals(CellState.UNCHECKED, cell.getState());
        assertEquals(-1, cell.getMinesAround());
    }
    
    @Test 
    public void checkUncheckedClearCell_shouldBecomeChecked()
    {
        Cell cell = getClearCell();
        
        cell.check();
        
        assertEquals(CellState.CHECKED, cell.getState());
    }
    
    @Test 
    public void checkUncheckedMinedCell_shouldExplode()
    {
        Cell cell = getMinedCell();
        
        cell.check();
        
        assertEquals(CellState.EXPLODED, cell.getState());
    }
    
    @Test 
    public void setFlagForUntouchedCell_shouldBecomeFlagged()
    {
        Cell cell = getClearCell();
        
        cell.setFlag();
        
        assertEquals(CellState.FLAGGED, cell.getState());
    }
    
    @Test
    public void checkCheckedCell_shouldNotChangeState()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.CHECKED);
        
        cell.check();
        
        assertEquals(CellState.CHECKED, cell.getState());
    }
    
    @Test
    public void setFlagForCheckedCell_shouldNotChangeState()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.CHECKED);
        
        cell.setFlag();
        
        assertEquals(CellState.CHECKED, cell.getState());
    }
    
    @Test
    public void checkFlaggedCell_shouldNotChangeState()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.FLAGGED);
        
        cell.check();
        
        assertEquals(CellState.FLAGGED, cell.getState());
    }
    
    @Test
    public void setFlagForFlaggedCell_souldBecomeUnchecked()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.FLAGGED);
        
        cell.setFlag();
        
        assertEquals(CellState.UNCHECKED, cell.getState());
    }
    
    @Test
    public void checkExplodedCell_shouldNotChangeState()
    {
        Cell cell = getMinedCell();
        cell.setState(CellState.EXPLODED);
        
        cell.check();
        
        assertEquals(CellState.EXPLODED, cell.getState());
    }
    
    @Test
    public void setFlagForExplodedCell_shouldNotChangeState()
    {
        Cell cell = getMinedCell();
        cell.setState(CellState.EXPLODED);
        
        cell.setFlag();
        
        assertEquals(CellState.EXPLODED, cell.getState());
    }
    
    private Cell getClearCell()
    {
        return Cell.newClearCell(3);
    }
    
    private Cell getMinedCell()
    {
        return Cell.newMinedCell(3);
    }
}






