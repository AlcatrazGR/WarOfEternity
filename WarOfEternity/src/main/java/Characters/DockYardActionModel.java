
package Characters;

import Items.Item;
import Items.ItemConnectionWithArea;
import Map.Area;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Model class that handles all the sailling action such as sail to commands and
 * checking whether the destination is blocked.
 * 
 * @author Vasilis Triantaris
 */
public class DockYardActionModel {
    
    private final List<DockYard> listOfDocks;
    private final List<Area> listOfAreas;
    private final List<Item> listOfItems;
    private final String nounPart;
    
    //Constructor with parameters
    public DockYardActionModel(List<DockYard> docks, String noun, List<Item> items, List<Area> areas){
        this.listOfDocks = docks;
        this.listOfAreas = areas;
        this.listOfItems = items;
        this.nounPart = noun;  
    }

    /**
     * Method that handles the sailling from one area to another. Firstly it
     * checks whether there is a captain on that particullar area, then if 
     * the command given contains the destination area and lastly if the
     * player has the amount of gold needed for the travel. At the end is 
     * sends a string message which indicates the status of the transaction.
     * 
     * @param player The object that contains the data of the player.
     * @return Returns a string message that indicates the status of the transaction.
     */
    public String ChangeAreaOnSailAction(Player player){
        String message;
        CaptainActionModel cam = new CaptainActionModel(this.listOfDocks);
        
        JSONObject jObj = cam.TalkToCaptainProcess(player);
        List<DockYard> docksOnArea = (List<DockYard>) jObj.get("docklist");
        
        if((boolean) jObj.get("status")){
            message = this.PlayerCanSailToHisDestination(player, docksOnArea);
        }
        else{
            message = "There is no dockyard in this place!";
        }

        return message;
    }
    
    /**
     * Method that checks if the desired sailling destination is reachable by
     * the plater. It checks if the destination given exists on the current are,
     * it checks if the player has the gold to make the trip and if the 
     * destination is blocked.
     * 
     * @param player The object that contains the data of the player.
     * @param docksOnArea The docks connected on the current player area.
     * @return Returns a message indicating the status of the process.
     */
    private String PlayerCanSailToHisDestination(Player player, List<DockYard> docksOnArea){
        
        String message;
        DockYard eligibleDockForArea = this.GetEligibleDockForCurrentArea(docksOnArea);
        String noun = this.nounPart.replace("to", "");
        
        if((eligibleDockForArea != null) && (noun.trim().equalsIgnoreCase(eligibleDockForArea.GetDestinationDockLocation().GetAreasName()))){
        //if(this.nounPart.contains(eligibleDockForArea.GetDestinationDockLocation().GetAreasName().toLowerCase())){
            if(player.GetCharacterGold() >= eligibleDockForArea.GetSaillingFee()){

                if(this.SailDestinationIsBlocked(player, eligibleDockForArea)){
                    message = "The way is unreachable!";
                }
                else{
                    player.SetCharacterGold(player.GetCharacterGold() - eligibleDockForArea.GetSaillingFee());
                    player.SetAreaLocation(eligibleDockForArea.GetDestinationDockLocation());
                    message = eligibleDockForArea.GetDestinationDockLocation().GetAreaDescription();
                }
            }
            else{
                message = "You don't have enough gold coins to travel to "+eligibleDockForArea.GetDestinationDockLocation().GetAreasName();
            }
        }
        else{
            message = "There is no such destination!";
        }

        return message;
    }
    
    /**
     * Method that finds the eligible dock for the specific destination and the
     * current player area.
     * 
     * @param docksOnArea The docks connected on the current player area.
     * @return Returns the eligible dock yard.
     */
    private DockYard GetEligibleDockForCurrentArea(List<DockYard> docksOnArea){
        
        DockYard eligibleDockForArea = null;
        for(DockYard eachDockOnArea : docksOnArea){
            if(this.nounPart.contains(eachDockOnArea.GetDestinationDockLocation().GetAreasName().toLowerCase())){
                eligibleDockForArea = eachDockOnArea;
            }
        }
        
        return eligibleDockForArea;
    }

    /**
     * Method that checks if the sailling destination is blocked.
     * 
     * @param player The object that contains the data of the player.
     * @param eligibleDockForArea The eligible dock for the specific current area.
     * @return Returns a boolean variable that determiines whether the destination is blocked.
     */
    private boolean SailDestinationIsBlocked(Player player, DockYard eligibleDockForArea){
        
        boolean check = false;
        List<Item> areaItems = this.GetListOfItemsAssociatedWithTheArea(eligibleDockForArea);
        
        for(Item eachItem : areaItems){
            for(ItemConnectionWithArea eachConnection : eachItem.GetItemConnectionsWithArea()){
                
                if(eligibleDockForArea.GetStartingDockLocation().GetAreasName().equals(eachConnection.GetConnectionWithAreaReference().GetAreasName())
                        && eachConnection.GetItemUsage().equals("open") && eachItem.GetItemValue() == 0 
                        && this.nounPart.contains(eachItem.GetBlockingDirection().toLowerCase())){
                    check = true;
                }  
            } 
        }
        
        return check;
    }
    
    /**
     * Method that finds all the dock yards connected on the cirrent player area.
     * 
     * @param dy The eligible dock for the specific current area.
     * @return Returns the list of dock yards connected to the current area.
     */
    private List<Item> GetListOfItemsAssociatedWithTheArea(DockYard dy){
        
        List<Item> itemsConnectedToArea = new ArrayList();
        
        for(Item eachItem : this.listOfItems){
            for(ItemConnectionWithArea eachConnection : eachItem.GetItemConnectionsWithArea()){
            
                if(eachConnection.GetConnectionWithAreaReference().GetAreasName().equals(dy.GetStartingDockLocation().GetAreasName()))
                    itemsConnectedToArea.add(eachItem);

            } 
        }
        
        return itemsConnectedToArea;
    }
    
    /**
     * Method that handles the sink ship action command by the user. Firstly 
     * it checks whether the action is blocked by an item and the it implements
     * the battle. 
     * 
     * @param player The object that contains the data of the player.
     * @return Retuens a message indicating the status of the process.
     */
    public String SinkActionCommandProcess(Player player){

        String message = "To implement the battle ... ";
        Item blockItem = null;

        for(Item eachItem : this.listOfItems){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){
                
                if((eachItem.GetItemType() == 4) && (icwa.GetItemUsage().equals("open") && (eachItem.GetItemValue() == 0) 
                        && (eachItem.GetBlockingDirection().equalsIgnoreCase("sink")))){
                    message = "You cannot procced further, the beam is blocking the ship!";
                }
            }
        }
        
        //TODO : Implement the battle and form the code of the method.
       

        return message;
    }
    
    
    
}
