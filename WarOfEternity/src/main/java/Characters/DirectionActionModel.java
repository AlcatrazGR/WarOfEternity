package Characters;

import Items.Item;
import Items.ItemConnectionWithArea;
import Map.AreaConnectionMaker;
import java.util.List;

/**
 *
 * @author Vasilhs Triantaris
 */
public class DirectionActionModel {
    
    private final String nounPart;
    private final List<Item> listOfGameItems;
    
    public DirectionActionModel(String noun, List<Item> items){
        this.nounPart = noun;
        this.listOfGameItems = items;
    }
    
     /**
     * Method manipulates the direction commands. It checks if the direction that
     * the player can go to exist or if it is accessible and if everything is fine 
     * then it redirects him to the next area.
     * 
     * @param player The object that holds all the player data.
     * @return Returns a message that describes the result of the player redirection action command given.
     */
    public String PlayerActionCommand(Player player){
        boolean directionToAreaIsCorrect = false;
        String directionIsBlockedMessage;
        String message = "";

        for(AreaConnectionMaker acm : player.GetAreaLocation().GetListOfAreaConnections()){
            
            if(acm.GetDirectionToOtherArea().equalsIgnoreCase(this.nounPart)){
                
                directionIsBlockedMessage = this.DirectionToNextAreaIsBlockedByItem(acm, player);
                
                if(directionIsBlockedMessage.equals("")){
                    player.SetAreaLocation(acm.GetNextArea());
                    directionToAreaIsCorrect = true;
                    message = player.GetAreaLocation().GetAreaDescription();
                }
                else{
                    message = directionIsBlockedMessage;
                    directionToAreaIsCorrect = true;
                }
            }
        }
        
        if(!directionToAreaIsCorrect)
            message = "There is no direction to : "+this.nounPart;
        
        return message;  
    }
    
    
    /**
     * This method checks whether the area that the user is in is blocked by a gate / door.
     * 
     * @param acm The object of area connections.
     * @param player The object that holds all the player data.
     * @return Returns a message if the next area that the user is trying to go is blocked by a door/gate. Else it returns an empty message.
     */
    public String DirectionToNextAreaIsBlockedByItem(AreaConnectionMaker acm, Player player){
        String checkMessage = "";
        
        for(Item eachItem : this.listOfGameItems){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){

                //If the item in there is a object door/gate in the area that the user is in and is still
                //closed then...
                if((eachItem.GetItemType() == 4) && (icwa.GetItemUsage().equals("open") && (eachItem.GetItemValue() == 0) && (eachItem.GetBlockingDirection().equalsIgnoreCase(this.nounPart)) && 
                        (player.GetAreaLocation().GetAreasName().equals(icwa.GetConnectionWithAreaReference().GetAreasName())))){
                    checkMessage = "You cannot proceed further. The gate is blocking your path!";
                }
            }
        }
        
        return checkMessage;
    }
    
    
}
