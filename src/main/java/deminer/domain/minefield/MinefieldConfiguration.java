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
}
