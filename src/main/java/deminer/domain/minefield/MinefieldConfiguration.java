package deminer.domain.minefield; 

public class MinefieldConfiguration 
{
    private final int width;
    private final int height;
    private final int numberOfMines;
    
    public MinefieldConfiguration(
        int width, int height, int numberOfMines)
    {
        this.width = width;
        this.height = height;
        this.numberOfMines = numberOfMines;
    }

    public int getWidth() 
    {
        return width;
    }

    public int getHeight() 
    {
        return height;
    }

    public int getNumberOfMines() 
    {
        return numberOfMines;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 29 * hash + this.width;
        hash = 29 * hash + this.height;
        hash = 29 * hash + this.numberOfMines;
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
        final MinefieldConfiguration other = (MinefieldConfiguration) obj;
        if (this.width != other.width) 
        {
            return false;
        }
        if (this.height != other.height) 
        {
            return false;
        }
        if (this.numberOfMines != other.numberOfMines) 
        {
            return false;
        }
        return true;
    }
}
