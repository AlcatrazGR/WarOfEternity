
package Characters;

import Items.Item;
import Items.ItemConnectionWithArea;
import Map.Area;
import Map.AreaConnectionMaker;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Vasilis Triantaris
 */
public class DockYardActionModel {
    
    private final List<DockYard> listOfDocks;
    private final List<Area> listOfAreas;
    private final List<Item> listOfItems;
    private final String nounPart;
    
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
        
        JSONObject jObj = player.TalkToCaptainProcess(this.listOfDocks);
        List<DockYard> docksOnArea = (List<DockYard>) jObj.get("docklist");
        
        if((boolean) jObj.get("status")){
            JSONObject checkJSON = this.PlayerCanSailToHisDestination(player, docksOnArea);
            message = (String) checkJSON.get("message");
        }
        else{
            message = "There is no dockyard in this place!";
        }

        return message;
    }
    
    
    private JSONObject PlayerCanSailToHisDestination(Player player, List<DockYard> docksOnArea){
        
        String message = "";
        DockYard eligibleDockForArea = null;

        for(DockYard eachDockOnArea : docksOnArea){
            if(this.nounPart.contains(eachDockOnArea.GetDestinationDockLocation().GetAreasName().toLowerCase())){
                eligibleDockForArea = eachDockOnArea;
            }
        }
        
        if(this.nounPart.contains(eligibleDockForArea.GetDestinationDockLocation().GetAreasName().toLowerCase())){
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
        
        
        /*
        for(DockYard eachDockOnArea : docksOnArea){
            System.out.println("Noun Part : "+this.nounPart+"\nDestination : "+eachDockOnArea.GetDestinationDockLocation().GetAreasName().toLowerCase());
            if(this.nounPart.contains(eachDockOnArea.GetDestinationDockLocation().GetAreasName().toLowerCase()))
                System.out.print("true");
            
            if(this.nounPart.contains(eachDockOnArea.GetDestinationDockLocation().GetAreasName().toLowerCase())){
                if(player.GetCharacterGold() >= eachDockOnArea.GetSaillingFee()){
                    
                    eligibleDockForArea = eachDockOnArea;
                    if(this.SailDestinationIsBlocked(player, eligibleDockForArea)){
                        message = "The way is unreachable!";
                    }
                    else{
                        player.SetCharacterGold(player.GetCharacterGold() - eachDockOnArea.GetSaillingFee());
                        player.SetAreaLocation(eachDockOnArea.GetDestinationDockLocation());
                        message = eachDockOnArea.GetDestinationDockLocation().GetAreaDescription();
                    }
                }
                else{
                    message = "You don't have enough gold coins to travel to "+eachDockOnArea.GetDestinationDockLocation().GetAreasName();
                }
            }
            else{
                message = "There is no such destination!";
            }
        }
        */
        
        JSONObject jObj = new JSONObject();
        jObj.put("message", message);
        jObj.put("specificDock", eligibleDockForArea);
        
        return jObj;
        
    }

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
    
    
    
    
}
