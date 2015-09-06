package Characters;

import Items.Item;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Vasilhs Triantaris
 */
public class BuyActionModel {

    String merchandise;
    
    //Constructor
    public BuyActionModel(String noun){
        this.merchandise = noun;
    }
    
    /**
     * Main method that handles the buy item from merchant process.
     * 
     * @param player The object that holds the player data.
     * @param jObj A JSON object which holds data to start a transaction.
     * @return Returns a string message that will be displayed to the user.
     */
    public String BuyItemFromMerchantProcess(Player player, JSONObject jObj){
        
        if(!(boolean) jObj.get("status"))
            return (String) jObj.get("message");
        
        List<Item> inventoryOfMerchant = this.GetMerchantsGoods((Merchant) jObj.get("merchant"));
        
        JSONObject merchandiseJSON = this.GetEligibleMerchandise(inventoryOfMerchant);
        if(!(boolean) merchandiseJSON.get("status"))
            return (String) merchandiseJSON.get("message");
        
        JSONObject buyIntegrity = this.ItemBuyProcessIntegrity(player, (Item) merchandiseJSON.get("item"));
        if(!(boolean) buyIntegrity.get("status"))
            return (String) buyIntegrity.get("message");
        
        String message = this.BuyItemFromMerchant(player, (Item) merchandiseJSON.get("item"));
        
        return message;
    }
    
    /**
     * Method that gets the list of merchandise from a specific merchant.
     * 
     * @param merchant The object that contains data for a specific merchant.
     * @return Returns a list of merchant items.
     */
    private List<Item> GetMerchantsGoods(Merchant merchant){
        return merchant.GetMerchantGoodsList();
    }
    
    /**
     * Method that finds the item that the player wants to buy in merchants 
     * inventory. 
     * 
     * @param inventoryOfMerchant The list of merchant items.
     * @return Returns the eligible item from merchants inventory.
     */
    private JSONObject GetEligibleMerchandise(List<Item> inventoryOfMerchant){
        
        JSONObject jObj = new JSONObject();
        Item eligibleItem = null;
        String message = "There is no such item!";
        boolean status = false;
        
        for(Item eachMerchItem : inventoryOfMerchant){
            if(eachMerchItem.GetItemName().equalsIgnoreCase(this.merchandise.trim())){
                eligibleItem = eachMerchItem;
                message = "";
                status = true;
            }
        }
        
        jObj.put("message", message);
        jObj.put("status", status);
        jObj.put("item", eligibleItem);
        
        return jObj;
    }
    
    /**
     * Method that checks the integrity of the transaction. That means that it
     * checks if the player has the required amount of gold and the required
     * weight space to buy the item. 
     * 
     * @param player The object that holds the player data.
     * @param eligibleItem The item that the player wants to buy.
     * @return Returns a JSON object that holds the results from the checks.
     */
    private JSONObject ItemBuyProcessIntegrity(Player player, Item eligibleItem){
        
        JSONObject jObj = new JSONObject();
        boolean status = true;
        boolean goldIntegrity = true;
        boolean weightIntegrity = true;
        String message = "";
        
        if(player.GetCharacterGold() < eligibleItem.GetItemValueInGold()){
            message = "Sorry sir, you cant afford this item...";
            goldIntegrity = false;
        }
        
        if(player.CalculatingPlayerInventoryItemWeight() + eligibleItem.GetItemWeight() > 100.0){
            message = "Exceeding weight limit, can't buy this item!";
            weightIntegrity = false;
        }
        
        if(!goldIntegrity || !weightIntegrity)
            status = false;
     
        jObj.put("message", message);
        jObj.put("status", status);
        
        return jObj;
    }
    
    /**
     * Method that handles the buy of the item. It subtracts the amount of gold 
     * from the gold of the player, adds the item into the players inventory and
     * if the item to be bought is a potion and already exists in the inventory
     * it just changes its quantity.
     * 
     * @param player The object that holds the player data.
     * @param itemToBuy The item that the player wants to buy.
     * @return Returns a string that will be displayed to the player.
     */
    private String BuyItemFromMerchant(Player player, Item itemToBuy){
        
        String message = "Thank you, can i do anything else for you sir ?";
        player.SetCharacterGold(player.GetCharacterGold() - itemToBuy.GetItemValueInGold());

        for(Item eachItemOnInventory : player.GetItemsSelectedByTheUser()){
            
            //If the item the player wants to buy is a potion and it alrady exists on his inventory then
            //don't add a new item row in the inventory just add 8 potion into the quantity of the object.
            if(eachItemOnInventory.GetItemName().equals(itemToBuy.GetItemName()) && itemToBuy.GetItemType() == 1){
                
                eachItemOnInventory.SetItemValue(eachItemOnInventory.GetItemValue() + 8);
                return message;
            }
        }
                
        player.AddItemToSelectedItemsByPlayer(itemToBuy);

        return message;
    }
    
 
}
