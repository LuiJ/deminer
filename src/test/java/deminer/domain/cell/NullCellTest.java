package deminer.domain.cell;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NullCellTest 
{   
    @Test
    public void checkNullCell_shouldDoNothing()
    {
        Cell cell = Cell.newNullCell();
        
        cell.check();
        
        assertTrue(cell.isUnchecked());
    }
    
    @Test
    public void setFlagToNullCell_shouldDoNothing()
    {
        Cell cell = Cell.newNullCell();
        
        cell.setFlag();
        
        assertTrue(cell.isUnchecked());
    }
}






