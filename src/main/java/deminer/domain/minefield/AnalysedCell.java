package deminer.domain.minefield;

import java.util.Objects;

public class AnalysedCell 
{
    private final int x;
    private final int y;
    private final int minesAround;
    private final String state;
    
    public AnalysedCell(int x, int y, int minesAround, String state)
    {
        this.x = x;
        this.y = y;
        this.minesAround = minesAround;
        this.state = state;
    }

    public int getX() 
    {
        return x;
    }

    public int getY() 
    {
        return y;
    }

    public int getMinesAround() 
    {
        return minesAround;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        hash = 47 * hash + this.minesAround;
        hash = 47 * hash + Objects.hashCode(this.state);
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
        final AnalysedCell other = (AnalysedCell) obj;
        if (this.x != other.x) 
        {
            return false;
        }
        if (this.y != other.y) 
        {
            return false;
        }
        if (this.minesAround != other.minesAround) 
        {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) 
        {
            return false;
        }
        return true;
    }
}
