package Characters;

import Items.Item;
import org.json.simple.JSONObject;

/**
 *
 * @author Vasilhs Triantaris
 */
public class SellActionModel {
    
    String itemToSell;
    
    //Constructor
    public SellActionModel(String item){
        this.itemToSell = item;
    }
    
    /**
     * Method that handles the selling of an item to a merchant.
     * 
     * @param player The object that holds the player data.
     * @param transactionJSON A JSON object that defines whether there is a merchant in the area.
     * @return Returns a string message that will be displayed to the player.
     */
    public String SellItemToMerchantProcess(Player player, JSONObject transactionJSON){
    
        //The JSON object that holds the data of the transaction, the merchant if the are
        //(if he exists).
        if(!(boolean) transactionJSON.get("status"))
            return (String) transactionJSON.get("message");
        
        JSONObject findItemJSON = this.FindItemToSellInPlayerInventory(player);
        if(!(boolean) findItemJSON.get("status"))
            return (String) findItemJSON.get("message");
        
        JSONObject itemCheckJSON = this.ItemTypeCheck((Item) findItemJSON.get("item"));
        if(!(boolean) itemCheckJSON.get("status"))
            return (String) itemCheckJSON.get("message");
        
        String message = SellTheItemToMerchant(player, (Item) findItemJSON.get("item"));
        
        return message;
    }
    
    /**
     * Method that finds the eligible item to be sold from the inventory of the 
     * player. 
     * 
     * @param player The object that holds the player data.
     * @return Returns a JSON object which defines the status of the process.
     */
    private JSONObject FindItemToSellInPlayerInventory(Player player){
        
        JSONObject jObj = new JSONObject();
        Item itemToBeSold = null;
        String message = "There is no such item in your inventory!";
        boolean status = false;
        
        for(Item eachInvItem : player.GetItemsSelectedByTheUser()){
            if(eachInvItem.GetItemName().equalsIgnoreCase(this.itemToSell)){
                itemToBeSold = eachInvItem;
                message = "";
                status = true;
            }
        }
        
        jObj.put("message", message);
        jObj.put("status", status);
        jObj.put("item", itemToBeSold);
        
        return jObj;  
    }
    
    /**
     * Method that checks whether the item to be sold is a key. Keys are not allowed
     * to be sold to a merchant.
     * 
     * @param itemToBeSold The item to be sold.
     * @return Returns a JSON object which defines the status of the process.
     */
    private JSONObject ItemTypeCheck(Item itemToBeSold){
    
        JSONObject jObj = new JSONObject();
        String message = "";
        boolean status = true;
        
        if(itemToBeSold.GetItemType() == 2){
            message = "I can't buy this, iam sorry...";
            status = false;
        }
                
        jObj.put("message", message);
        jObj.put("status", status);
       
        return jObj;
    }
    
    /**
     * Method that changes all the appropriate fields after the item has been 
     * sold. Those are the gold of the player, the players selected items, the 
     * players equipped items and calculates the players damage and armor.
     * 
     * @param player The object that holds the player data.
     * @param itemToBeSold The item to be sold.
     * @return 
     */
    private String SellTheItemToMerchant(Player player, Item itemToBeSold){
        player.RemoveItemFromPlayerEquipedInventory(itemToBeSold);
        player.SetCharacterGold(player.GetCharacterGold() + itemToBeSold.GetItemValueInGold());
        player.RemoveItemFromSelectedItemsByPlayer(itemToBeSold);
        player.CalculatePlayersArmor();
        player.CalculateGeneralPlayerDamage();
        
        return "Can help you with anything else sir ?";  
    }
    
    
}
