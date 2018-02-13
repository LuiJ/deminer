package deminer.domain.minefield;

import java.util.HashSet;
import java.util.Set;

public class Point 
{
    private final int x;
    private final int y;
    
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }  
    
    public Set<Point> getSurroundPoints()
    {
        Set<Point> surroundPoints = new HashSet<>();
        surroundPoints.add(new Point(x-1, y+1));
        surroundPoints.add(new Point(x, y+1));
        surroundPoints.add(new Point(x+1, y+1));
        surroundPoints.add(new Point(x-1, y));
        surroundPoints.add(new Point(x+1, y));
        surroundPoints.add(new Point(x-1, y-1));
        surroundPoints.add(new Point(x, y-1));
        surroundPoints.add(new Point(x+1, y-1));
        return surroundPoints;
    }
    
    @Override
    public String toString()
    {
        return String.format("[%d : %d]", x, y);
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) 
        {
            return false;
        }
        if (this.y != other.y) 
        {
            return false;
        }
        return true;
    }
}
