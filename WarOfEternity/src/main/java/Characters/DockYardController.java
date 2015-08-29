
package Characters;

import Items.Item;
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
    private final List<Item> listOfItems;
    
    //Constructor
    public DockYardController(List<Area> areas, List<Item> items){
        this.listOfAreas = areas;
        this.listOfDockYards = new ArrayList();
        this.listOfItems = items;
    }
    
    /**
     * Method that controlls the reading of the dock yard model.
     */
    public void DockYardMainControllingMethod(){
        
        ReadDockYardConnections rdyc = new ReadDockYardConnections();
        
        rdyc.GetTextFileColumnsToList();
        List<String> startStringDocks = rdyc.GetStartAreaStringList();
        List<String> destStringDocks = rdyc.GetDestinationStringList();
        
        List<Area> startAreaDocks = rdyc.GetDockAreaList(startStringDocks, this.listOfAreas);
        List<Area> destAreaDocks = rdyc.GetDockAreaList(destStringDocks, this.listOfAreas);
        
        rdyc.SetDockYardConnectionsToList(startAreaDocks, destAreaDocks);
        this.listOfDockYards = rdyc.GetDockYardList();
    }
    
    /**
     * Method that handles the command actions that have to do with sail.
     * 
     * @param player The object that refers to the player.
     * @param docks The list of docks in the game.
     * @param noun The noun part of the command.
     * @return Returns a string message that will be displayed to the user.
     */
    public String DockYardCommandActionProcess(Player player, List<DockYard> docks, String noun){
        
        DockYardActionModel dyam = new DockYardActionModel(docks, noun, this.listOfItems, this.listOfAreas);
        String message = dyam.ChangeAreaOnSailAction(player);
        
        return message;
    }
    
    
    public List<DockYard> GetDockYardList(){
        return this.listOfDockYards;
    }
    
}
