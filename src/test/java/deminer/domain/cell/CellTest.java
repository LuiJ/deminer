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
        assertTrue(cell.isUnchecked());
    }
    
    @Test
    public void newMinedCell()
    {
        Cell cell = Cell.newMinedCell(5);
        
        assertTrue(cell.hasMine());
        assertTrue(cell.isUnchecked());
    }
    
    @Test
    public void newNullCell()
    {
        Cell cell = Cell.newNullCell();
        
        assertFalse(cell.hasMine());
        assertTrue(cell.isUnchecked());
        assertEquals(-1, cell.getMinesAround());
    }
    
    @Test 
    public void checkUncheckedClearCell_shouldBecomeChecked()
    {
        Cell cell = getClearCell();
        
        cell.check();
        
        assertTrue(cell.isChecked());
    }
    
    @Test 
    public void checkUncheckedMinedCell_shouldExplode()
    {
        Cell cell = getMinedCell();
        
        cell.check();
        
        assertTrue(cell.isExploded());
    }
    
    @Test 
    public void setFlagForUntouchedCell_shouldBecomeFlagged()
    {
        Cell cell = getClearCell();
        
        cell.setFlag();
        
        assertTrue(cell.isFlagged());
    }
    
    @Test
    public void checkCheckedCell_shouldNotChangeState()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.CHECKED);
        
        cell.check();
        
        assertTrue(cell.isChecked());
    }
    
    @Test
    public void setFlagForCheckedCell_shouldNotChangeState()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.CHECKED);
        
        cell.setFlag();
        
        assertTrue(cell.isChecked());
    }
    
    @Test
    public void checkFlaggedCell_shouldNotChangeState()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.FLAGGED);
        
        cell.check();
        
        assertTrue(cell.isFlagged());
    }
    
    @Test
    public void setFlagForFlaggedCell_souldBecomeUnchecked()
    {
        Cell cell = getClearCell();
        cell.setState(CellState.FLAGGED);
        
        cell.setFlag();
        
        assertTrue(cell.isUnchecked());
    }
    
    @Test
    public void checkExplodedCell_shouldNotChangeState()
    {
        Cell cell = getMinedCell();
        cell.setState(CellState.EXPLODED);
        
        cell.check();
        
        assertTrue(cell.isExploded());
    }
    
    @Test
    public void setFlagForExplodedCell_shouldNotChangeState()
    {
        Cell cell = getMinedCell();
        cell.setState(CellState.EXPLODED);
        
        cell.setFlag();
        
        assertTrue(cell.isExploded());
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






