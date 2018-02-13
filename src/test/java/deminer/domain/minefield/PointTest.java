package deminer.domain.minefield;

import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class PointTest 
{
    @Test
    public void getSurroundPoints()
    {
        Set<Point> surroundPoints = new Point(1, 1).getSurroundPoints();
        
        assertEquals(8, surroundPoints.size());
        
        // Top left [x-1, y+1]
        assertTrue(surroundPoints.contains(new Point(0, 2)));
        // Top center [x, y+1]
        assertTrue(surroundPoints.contains(new Point(1, 2)));
        // Top right [x+1, y+1]
        assertTrue(surroundPoints.contains(new Point(2, 2)));
        // Left [x-1, y]
        assertTrue(surroundPoints.contains(new Point(0, 1)));
        // Right [x+1, y]
        assertTrue(surroundPoints.contains(new Point(2, 1)));
        // Bottom left [x-1, y-1]
        assertTrue(surroundPoints.contains(new Point(0, 0)));
        // Bottom center [x, y-1]
        assertTrue(surroundPoints.contains(new Point(1, 0)));
        // Bottom Right [x+1, y-1]
        assertTrue(surroundPoints.contains(new Point(2, 0)));
    }
}
