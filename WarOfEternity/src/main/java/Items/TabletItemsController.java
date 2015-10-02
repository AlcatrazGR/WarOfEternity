
package Items;

import Characters.Player;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Class that handles the inspect action commands of the game. 
 * 
 * @author Vasilis Triantaris
 */
public class TabletItemsController {
    
    private final List<Item> listOfItems;
    private final String verbPart;
    private final String nounPart;
    
    
    //Constructor
    public TabletItemsController(String verb, String noun, List<Item> gameItems){
        this.listOfItems = gameItems;
        this.verbPart = verb;
        this.nounPart = noun;
    }
    
    /**
     * Main control method of the inspect commands.
     * 
     * @param player The object of the player.
     * @return Returns a string that represents the result of the process.
     */
    public String TabletInspectActionCommand(Player player){
        
        String message;
        TableItemActionModel tiam = new TableItemActionModel(this.verbPart, this.nounPart, this.listOfItems);
        
        JSONObject integrityJSON = tiam.GetStoneTabletOnAreaIfExists(player);
        if(!(boolean) integrityJSON.get("status"))
            return (String) integrityJSON.get("message");
        
        Item stoneTablet = (Item) integrityJSON.get("item");
        message = "Tablet Description :\n"+stoneTablet.GetItemDescription();
        
        return message;
    }
    
}
