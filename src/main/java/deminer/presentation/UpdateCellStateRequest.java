package deminer.presentation;

public class UpdateCellStateRequest 
{
    private String action;
    
    public UpdateCellStateRequest()
    {
    }
    
    public UpdateCellStateRequest(String action) 
    {
        this.action = action;
    }    

    public String getAction() 
    {
        return action;
    }

    public void setAction(String action) 
    {
        this.action = action;
    }    
}
