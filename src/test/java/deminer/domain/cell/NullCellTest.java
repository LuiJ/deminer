package deminer.domain.cell;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullCellTest 
{   
    @Test
    public void checkNullCell_shouldDoNothing()
    {
        Cell cell = Cell.newNullCell();
        
        cell.check();
        
        assertEquals(CellState.UNCHECKED, cell.getState());
    }
    
    @Test
    public void setFlagToNullCell_shouldDoNothing()
    {
        Cell cell = Cell.newNullCell();
        
        cell.setFlag();
        
        assertEquals(CellState.UNCHECKED, cell.getState());
    }
}






