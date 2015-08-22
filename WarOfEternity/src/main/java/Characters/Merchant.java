package Characters;

import Interfaces.ICharacter;
import Items.Item;
import Map.Area;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Class merchant initializes merchant objects. This class extends the ICharacter
 * interface to inherit the data members / methods of a character object and also 
 * extends the serializable interface so that a merchant object can be written 
 * in a file.
 * 
 * @author  Thomas Liakos
 */
public class Merchant implements ICharacter, Serializable {

    private Area areaLocation;
    private String characterName;
    private int damage;
    private int armor;
    private int characterHealth;
    private List<Item> merchantGoods; 
    private double charactersGold;
            
    public Merchant(Area merchantsLoc, String merchName, int merchDam){
        this.areaLocation = merchantsLoc;
        this.characterName = merchName;
        this.damage = merchDam;
        this.armor = 0;
        this.characterHealth = 1000;
        this.merchantGoods = new ArrayList();
    }
    
    public void SetAreaLocation(Area location) {
        this.areaLocation = location;
    }
    
    public Area GetAreaLocation() { 
        return this.areaLocation;
    }

    public void SetCharacterName(String name) { 
        this.characterName = name;
    }

    public String GetCharacterName() {
        return this.characterName;
    }

    public void SetCharacterDamage(int damage) {
        this.damage = damage;
    }

    public int GetCharacterDamage() {
        return this.damage;
    }
    
    public void AddItemToMerchantGoods(Item item){
        this.merchantGoods.add(item);
    }
    
    public List<Item> GetMerchantGoodsList(){
        return this.merchantGoods;
    }
    
    public void SetCharacterGold(double goldCoins) {
        this.charactersGold = goldCoins;
    }

    public double GetCharacterGold() {
        return this.charactersGold;
    }
    
    public void SetCharacterArmor(int armorValue) {
        this.armor = armorValue;
    }

    public int GetCharacterArmor() {
        return this.armor;
    }
    
    public void SetCharacterHealth(int health) {
        this.characterHealth = health;
    }

    public int GetCharacterHealth() {
        return this.characterHealth;
    }

    /**
     * This method handles the buy process of a transaction.
     * 
     * @param player    The player object.
     * @param merchantGood  The object that the player wants to buy in string format.
     * @return Returns a string message that will be displayed to the user which describes the result of the action.
     */
    public String MerchantBuyItemProcess(Player player, String merchantGood){
        boolean itemExistance = false;
        Item itemToBuy = null;
        String message;
        
        //Finds the specific item that the user wants to buy
        for(Item merchGood : this.merchantGoods){
            if(merchGood.GetItemName().equalsIgnoreCase(merchantGood.trim())){
                itemToBuy = merchGood;
                itemExistance = true;
            }
        }
        
        if(itemExistance){

            if(player.CalculatingPlayerInventoryItemWeight() + itemToBuy.GetItemWeight() > 100.0)
                return "Exceeding weight limit, can't buy this item!";

            //checks if the player can aford to buy the specific item.
            if(player.GetCharacterGold() >= itemToBuy.GetItemValueInGold()){
                
                player.SetCharacterGold(player.GetCharacterGold() - itemToBuy.GetItemValueInGold());

                for(Item eachItemOnInventory : player.GetItemsSelectedByTheUser()){
                    if(eachItemOnInventory.GetItemName().equals(itemToBuy.GetItemName()) && itemToBuy.GetItemType() == 1){
                        eachItemOnInventory.SetItemValue(eachItemOnInventory.GetItemValue() + 8);
                        return "Thank you, can i do anything else for you sir ?";
                     }
                }
                
                player.AddItemToSelectedItemsByPlayer(itemToBuy);
                message = "Thank you, can i do anything else for you sir ?";
                
            }
            else{
                message = "Sorry sir, you cant afford this item...";
            }
        }
        else{
            message = "There is no item by the name "+merchantGood+" into my goods, sorry...";  
        }
        
        return message;
    }
    
    /**
     * This method handles the sell process of an item transaction.
     * 
     * @param player    The player object.
     * @param playerGood    The object that the user wants to sell in string format.
     * @return Returns a string message that will be displayed to the user which describes the result of the action.
     */
    public String MerchantSellItemProcess(Player player, String playerGood){
        String message = "";
 
        int i=0;
        for(Item eachSelectedItem : player.GetItemsSelectedByTheUser()){
   
            if(eachSelectedItem.GetItemName().equalsIgnoreCase(playerGood)){
                //The sell transaction type depends on the type of items to be sold if an
                //items is a key it cannot be sold.
                switch(eachSelectedItem.GetItemType()){
                    case 2:
                        message = "I can't buy those, iam sorry...";
                    break;
                
                    default:
                        this.RemoveItemFromPlayerEquipedInventory(eachSelectedItem, player);
                        player.SetCharacterGold(player.GetCharacterGold() + eachSelectedItem.GetItemValueInGold());
                        player.RemoveItemFromSelectedItemsByPlayer(eachSelectedItem);
                        player.CalculatePlayersArmor();
                        player.CalculateGeneralPlayerDamage();
                        message = "Can i help you with anything else sir ?";
                    break;     
                }
                //If the 
                break;
            }
            else{
                message = "You can't sell me something that you don't have!";
            }
            i++;
        }
        
        return message;
    }
    
    /**
     * This method is removes an item from the equipped item list of the player
     * whenever this item is about to be sold to the merchant.
     * 
     * @param item  The item to be sold to the merchant and its already equipped.
     * @param player The player object.
     */
    public void RemoveItemFromPlayerEquipedInventory(Item item, Player player){
        List<Item> newListOfEquipedItems = new ArrayList();
        
        for(Item eachEquipedItem : player.GetEquipedItemListOfPlayer()){
           
            if(!eachEquipedItem.GetItemName().equals(item.GetItemName()))
                newListOfEquipedItems.add(eachEquipedItem); 
        }
        
        player.SetEquipedItemListOfPlayer(newListOfEquipedItems);
    }
    
}
