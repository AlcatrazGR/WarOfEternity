
package Characters;

import Map.Area;

/**
 * This class initializes object of dockyard in order to be use by the main game.
 *
 * @author Vasilhs Triantarhs
 */
public class DockYard {
    
    private Area startDock;
    private Area destinationDock;
    private double sailingFee;
    
    //Constructor with no parameters
    public DockYard(){
        this.startDock = null;
        this.destinationDock = null;
        this.sailingFee = 0.0;
    }
    
    //Constructor with parameters
    public DockYard(Area start, Area end, double fee){
        this.startDock = start;
        this.destinationDock = end;
        this.sailingFee = fee;
    }
    
    public Area GetStartingDockLocation(){
        return this.startDock;
    }
    
    public void SetStartingDockLocation(Area start){
        this.startDock = start;
    }
    
    public Area GetDestinationDockLocation(){
        return this.destinationDock;
    }
    
    public void SetDestinationDockLocation(Area dest){
        this.destinationDock = dest;
    }
    
    public double GetSalingFee(){
        return this.sailingFee;
    }
    
    public void SetSailingFee(double fee){
        this.sailingFee = fee;
    }
    
    
}
