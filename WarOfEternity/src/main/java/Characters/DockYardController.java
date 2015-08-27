
package Characters;

import Map.Area;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vasilis Triantaris
 */
public class DockYardController {
    
    private final List<Area> listOfAreas;
    private List<DockYard> listOfDockYards;
    
    //Constructor
    public DockYardController(List<Area> areas){
        this.listOfAreas = areas;
        this.listOfDockYards = new ArrayList();
        this.DockYardMainControllingMethod();
    }
    
    private void DockYardMainControllingMethod(){
        
        ReadDockYardConnections rdyc = new ReadDockYardConnections();
        
        rdyc.GetTextFileColumnsToList();
        List<String> startStringDocks = rdyc.GetStartAreaStringList();
        List<String> destStringDocks = rdyc.GetDestinationStringList();
        
        List<Area> startAreaDocks = rdyc.GetDockAreaList(startStringDocks, this.listOfAreas);
        List<Area> destAreaDocks = rdyc.GetDockAreaList(destStringDocks, this.listOfAreas);
        
        rdyc.SetDockYardConnectionsToList(startAreaDocks, destAreaDocks);
        this.listOfDockYards = rdyc.GetDockYardList();
    }
    
    public List<DockYard> GetDockYardList(){
        return this.listOfDockYards;
    }
    
}
