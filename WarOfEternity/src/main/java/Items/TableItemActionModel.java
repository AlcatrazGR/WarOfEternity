
package Items;

import Characters.Player;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Class model that implements the inspect action command on the game.
 * 
 * @author Vasilis Triantarhs
 */
public class TableItemActionModel {
        
    private final List<Item> listOfItems;
    private final String verbPart;
    private final String nounPart;
    
    //Constructor
    public TableItemActionModel(String verb, String noun, List<Item> gameItems){
        this.listOfItems = gameItems;
        this.verbPart = verb;
        this.nounPart = noun;
    }
    
    /**
     * Method that handles the stone tablet inspection command.
     * 
     * @param player The object of the player.
     * @return Returns a json object that contains the results of the process.
     */
    public JSONObject GetStoneTabletOnAreaIfExists(Player player){
        
        JSONObject jObj = new JSONObject();
        boolean status = false;
        Item stoneTablet = null;
        String message = "There is no stone tablet here to inspect !";

        Item eligibleTablet = this.GetEligibleItemFromArea(player);    
        if(eligibleTablet != null){
                
            stoneTablet = eligibleTablet;
            status = true;
            message = "";
        }

        jObj.put("status", status);
        jObj.put("message", message);
        jObj.put("item", stoneTablet);
        
        return jObj;
    }
    
    /**
     * Method that finds all the stone tablet items from the list of items and
     * creates a list of them.
     * 
     * @return Returns the list of stone tablets. 
     */
    private List<Item> GetListOfStoneTablets(){
    
        List<Item> listOfStoneTablets = new ArrayList();
        
        for(Item eachItem : this.listOfItems){
            if(eachItem.GetItemType() == 7)
                listOfStoneTablets.add(eachItem);
        }
        
        return listOfStoneTablets;
    }
    
    /**
     * Method that finds the eligible stone tablet connected to the area that
     * the player is located.
     * 
     * @param player The object of the player.
     * @return Returns the eligible stone tablet.
     */
    public Item GetEligibleItemFromArea(Player player){
        
        List<Item> listOfStoneTablets = this.GetListOfStoneTablets();
        Item eligibleTablet = null;
        
        for(Item eachTablet : listOfStoneTablets){

            if(eachTablet.GetItemArea().GetAreasName().equals(player.GetAreaLocation().GetAreasName()))
                eligibleTablet = eachTablet;
        }

        return eligibleTablet;
    }
    
    
}
