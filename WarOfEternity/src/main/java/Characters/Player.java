package Characters;

import Interfaces.ICharacter;
import Items.Item;
import Items.ItemConnectionWithArea;
import Map.Area;
import Map.AreaConnectionMaker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.simple.JSONObject;

/**
 * The main class of the player. Each action that is referred to the user passes through 
 * this class. As a character object it implements the interface ICharacter and
 * also the Serializable interface so that any Player object can be written in
 * a file.
 * 
 * @author Vasilis Triantaris
 */
public class Player implements ICharacter, Serializable {
    private Area currentAreaLocation;
    private String characterName;
    private double charactersGold = 0.0;
    private String playerClass;
    
    private int damage;
    private int armor;
    private int characterHealth;
    
    private int characterStrength;
    private int characterInteligence;
    private int characterAgility;
    
    private int characterLevel;
    private int characterExperience;
    
    private List<Item> itemsSelected;
    private List<Item> equipedItems;
    
    public Player(){
        this.currentAreaLocation = null;
        this.characterName = "";
        this.charactersGold = 50000.0;
        this.characterHealth = 100;
         
        this.damage = 10;
        this.armor = 10;
        this.characterStrength = 0;
        this.characterInteligence = 0;
        this.characterAgility = 0;

        this.characterLevel = 1;
        this.characterExperience = 0;
        this.itemsSelected = new ArrayList();
        this.equipedItems = new ArrayList();
    }
    
    public Player(Area location, int playerHealth, String playerName, int exp, String plClass, int str, int intel, int agil){
        this.currentAreaLocation = location;
        this.characterName = playerName;
        this.charactersGold = 50000.0;
        this.characterHealth = playerHealth;
        this.playerClass = plClass;
        
        this.damage = 10;
        this.armor = 10;
        this.characterStrength = str;
        this.characterInteligence = intel;
        this.characterAgility = agil;
        
        this.characterLevel = 1;
        this.characterExperience = exp;
        this.itemsSelected = new ArrayList();
        this.equipedItems = new ArrayList();
    }
    
    public int GetPlayerLevel(){
        return this.characterLevel;
    }
    
    public void SetPlayerLevel(int level){
        this.characterLevel = level;
    }
    
    public int GetPlayerExperience(){
        return this.characterExperience;
    }
    
    public void SetPlayerExperience(int exp){
        this.characterExperience = exp;
    }
    
    public Area GetAreaLocation(){
        return this.currentAreaLocation;
    }
    public void SetAreaLocation(Area location){
        this.currentAreaLocation = location;
    }

    public void SetCharacterName(String name) {
        this.characterName = "player";
    }

    public String GetCharacterName() {
        return this.characterName;
    } 
    
    public void SetCharacterDamage(int damage) {
        this.damage = damage;
    }

    public int GetCharacterDamage() {
        return damage;
    }

    public List<Item> GetItemsSelectedByTheUser(){
        return this.itemsSelected;
    }

    public void SetItemsSelectedByPlayer(List<Item> itemList){
        this.itemsSelected = itemList;
    }
    
    //Add item from selected items by player
    public void AddItemToSelectedItemsByPlayer(Item item){
        this.itemsSelected.add(item);
    }

    public void SetCharacterArmor(int armorValue) {
        this.armor = armorValue;
    }

    public int GetCharacterArmor() {
        return this.armor;
    }
    
    public void SetCharacterStrenght(int str){
        this.characterStrength = str;
    }
    
    public int GetCharacterStrength(){
        return this.characterStrength;
    }
    
    public void SetCharacterInteligence(int intel){
        this.characterInteligence = intel;
    }
    
    public int GetCharacterInteligence(){
        return this.characterInteligence;
    }
    
    public void SetCharacterAgility(int agil){
        this.characterAgility = agil;
    }
    
    public int GetCharacterAgility(){
        return this.characterAgility;
    }
    
    public void SetCharacterClass(String pClass){
        this.playerClass = pClass;
    }
    
    public String GetChatacterClass(){
        return this.playerClass;
    }

    public void SetCharacterGold(double goldCoins) {
        this.charactersGold = goldCoins;
    }

    public double GetCharacterGold() {
        return this.charactersGold;
    }
    
    public void SetEquipedItemListOfPlayer(List<Item> items){
        this.equipedItems = items;
    }
    
    public List<Item> GetEquipedItemListOfPlayer(){
        return this.equipedItems;
    }
    
    public void AddItemToEquipedItemListOfPlayer(Item item){
        this.equipedItems.add(item);
    }

    public void SetCharacterHealth(int health) {
        this.characterHealth = health;
    }

    public int GetCharacterHealth() {
        return this.characterHealth;
    }
    
    
    /**
     * This method removes an item from the list of selected player items (players
     * inventory). Basically this method is used whenever an item is sold.
     * 
     * @param item  The item to be removed from the inventory.
     */
    public void RemoveItemFromSelectedItemsByPlayer(Item item){
        List<Item> listOfNewSelectedItems = new ArrayList();
        
        for(Item eachItem : this.itemsSelected){
            if(!eachItem.GetItemName().equals(item.GetItemName()))
                listOfNewSelectedItems.add(eachItem);
        }
        
        this.SetItemsSelectedByPlayer(listOfNewSelectedItems);
    }
    
     /**
     * This method is removes an item from the equipped item list of the player
     * whenever this item is about to be sold to the merchant.
     * 
     * @param itemToBeSold  The item to be sold to the merchant
     */
    public void RemoveItemFromPlayerEquipedInventory(Item itemToBeSold){
        List<Item> newListOfEquipedItems = new ArrayList();
        
        for(Item eachEquipedItem : this.GetEquipedItemListOfPlayer()){
           
            if(!eachEquipedItem.GetItemName().equals(itemToBeSold.GetItemName()))
                newListOfEquipedItems.add(eachEquipedItem); 
        }
        
        this.SetEquipedItemListOfPlayer(newListOfEquipedItems);
    }
    
    
    
    /**
     * Calculates the whole armor value of the player from all the equipped
     * armor sets.
     */
    public void CalculatePlayersArmor(){
        int sum=0;
        
        for(Item eachEquipedItem : this.equipedItems){
            if(eachEquipedItem.GetItemType() == 5 || eachEquipedItem.GetItemType() == 6)
                sum += eachEquipedItem.GetItemValue();
        }
        sum += 10;
        
        this.SetCharacterArmor(sum);
    }
    
    /**
     * Calculates players damage from all the equipped weapon sets of the player.
     * 
     * @return Returns the summary of the damage from the equipped items.
     */
    public int CalculatePlayersDamageFromEquippedItems(){
        int sum=10;
        
        for(Item eachEquipedItem : this.equipedItems){
            if(eachEquipedItem.GetItemType() == 3)
                sum += eachEquipedItem.GetItemValue();
        }
        
        return sum;
    }
    
    /**
     * Method that calculates the final player damage. The attribute of the character
     * makes the 60% of the final damage while the item damage the 30% and the
     * players level the 10%.
     */
    public void CalculateGeneralPlayerDamage(){
        int equippedItemDam = this.CalculatePlayersDamageFromEquippedItems();  
        int damage;
        
        System.out.println("Item Damage : "+equippedItemDam);
        
        switch(this.GetChatacterClass()){
            
            case "warrior":
                damage = (int) ((equippedItemDam * 0.3) + (this.GetCharacterStrength() * 0.6) + (this.GetPlayerLevel() * 0.1));
                this.SetCharacterDamage(damage);
            break;
                
            case "rogue":
                damage = (int) ((equippedItemDam * 0.3) + (this.GetCharacterAgility() * 0.6) + (this.GetPlayerLevel() * 0.1));
                this.SetCharacterDamage(damage);
            break;
                
            case "mage":
                damage = (int) ((equippedItemDam * 0.3) + (this.GetCharacterInteligence() * 0.6) + (this.GetPlayerLevel() * 0.1));
                this.SetCharacterDamage(damage);
            break;
                
        }
        
    }
    
    /**
     * Method that calculates the attribute points (strength, agility, intelligence) 
     * of the player.
     */
    public void CalculatePlayersAttributePoints(){
        
        int strengthAttribute = 0;
        int agilityAttribute = 0;
        int intelligenceAttribute = 0;
        JSONObject jObj = this.GetPlayerClassStartingStats();
        
        for(Item eachEquippedItem : this.equipedItems){

            if(eachEquippedItem.GetItemType() == 3 && eachEquippedItem.GetAttributeType().equals("str"))
                strengthAttribute += eachEquippedItem.GetAttributeValue();

            if(eachEquippedItem.GetItemType() == 3 && eachEquippedItem.GetAttributeType().equals("agi"))
                agilityAttribute += eachEquippedItem.GetAttributeValue();
            
            if(eachEquippedItem.GetItemType() == 3 && eachEquippedItem.GetAttributeType().equals("int"))
                intelligenceAttribute += eachEquippedItem.GetAttributeValue();

        }
        
        this.SetCharacterStrenght(strengthAttribute + (int) jObj.get("strength"));
        this.SetCharacterAgility(agilityAttribute + (int) jObj.get("agility"));
        this.SetCharacterInteligence(intelligenceAttribute + (int) jObj.get("intelligence"));
       
    }
    
    /**
     * Method that returns the starting attribute points for each class.
     * @return Returns a JSON object which contains the starting attribute points for each class.
     */
    private JSONObject GetPlayerClassStartingStats(){
        
        JSONObject jObj = new JSONObject();
        int strength = 0;
        int agility = 0;
        int intelligence = 0;
        
        switch(this.playerClass){
            
            case "warrior":
                strength = 14;
                agility = 8;
                intelligence = 6;
            break;
                
            case "rogue":
                strength = 6;
                agility = 14;
                intelligence = 8;
            break;
                
            case "mage":
                strength = 5;
                agility = 9;
                intelligence = 14;
            break;
        }
        
        jObj.put("strength", strength);
        jObj.put("agility", agility);
        jObj.put("intelligence", intelligence);
     
        return jObj;
    }
    
    /**
     * Calculating the whole wight of item in the inventory of player.
     * 
     * @return Returns the whole weight of items that the player is carrying in a double data format.
     */
    public double CalculatingPlayerInventoryItemWeight(){
        double weightSum = 0.0;
        
        for(Item eachItemInInventory : this.itemsSelected)
            weightSum += eachItemInInventory.GetItemWeight();
 
        return weightSum;
    }
    
    /**
     * Method that returns the name of the gate in the specific area.
     * 
     * @param playerAction  The action command of the player.
     * @param itemList  The list of items of the game.
     * @return Returns the name of the door/gate that is blocking a specific path.
     */
    private String GetGateNameFromItemList(String playerAction, List<Item> itemList){
        String doorItemNameOnArea = "";
        
        for(Item eachItem : itemList){       
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){
                
                //If the item connection area is the same one with the players and its a door / gate 
                if((icwa.GetConnectionWithAreaReference().GetAreasName().equals(this.currentAreaLocation.GetAreasName())) && (eachItem.GetItemType() == 4))
                    doorItemNameOnArea = eachItem.GetItemName();
            }
        }
        
        return doorItemNameOnArea;
    }
    
    /**
     * Checks the integrity of the gate. That means if the gate is referred to open by  the key that
     * the player is trying to use.
     * 
     * @param playerAction  The action command of the player.
     * @param itemList  The list of items of the game.
     * @param doorItemNameOnArea The name of the gate the is blocking a specific path on the area.
     * @return Returns a message if the key that is gong to be used on a specific door/gate doesn't match.
     */
    private String UsageIntegrityOfTheGate(String playerAction, List<Item> itemList, String doorItemNameOnArea){
        String message = "";
        String gateLocation = "";
        String keyLocation = "";
        
        for(Item eachItem : itemList){       
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){
                
                //If the item connection area is the same one with the players and its a door / gate 
                if((icwa.GetConnectionWithAreaReference().GetAreasName().equals(this.currentAreaLocation.GetAreasName())) && (eachItem.GetItemType() == 4))
                    gateLocation = icwa.GetConnectionWithAreaReference().GetAreasName();

                if((icwa.GetItemConnectedToAreaReference().GetItemName().equalsIgnoreCase(playerAction)) && (icwa.GetItemUsage().equalsIgnoreCase("use")))
                    keyLocation = icwa.GetConnectionWithAreaReference().GetAreasName();
            }
        }

        if(!keyLocation.equals(gateLocation))
            message = "The item : "+playerAction+" can't be used here!";
            
        return message;
    }

    /**
     * Method that handles the experience earned by a battle
     *
     * @param enemyThatBattled The enemy object that the user has battled.
     */
    public void BattleExperienceEarned(Enemies enemyThatBattled){
        
        int expEarned = (int) (enemyThatBattled.GetEnemyExperience() - (this.GetPlayerLevel() * 0.3));
        
        if(expEarned < 0)
            expEarned = 1;
        
        //if the previous amount of experience plus the experience earned from
        //the enemy excedes the limit of 100 then the player levels up.
        if((this.characterExperience + expEarned) >= 100){
            this.LevelUp();
            int newExperience = (this.characterExperience + expEarned) - 100;
            this.SetPlayerExperience(newExperience);
        }
        else{
            int newExperience = this.characterExperience + expEarned;
            this.SetPlayerExperience(newExperience);
        }
    }
   
    private JSONObject GetAttributePointsFromEquippedItems(){
        
        JSONObject jObj = new JSONObject();
        int strFromItems = 0;
        int agiFromItems = 0;
        int intelFromItems = 0;
        
        for(Item eachEquippedItem : this.equipedItems){
            if(eachEquippedItem.GetItemType() == 3){
                switch(eachEquippedItem.GetAttributeType()){
                    
                    case "str":
                        strFromItems += eachEquippedItem.GetAttributeValue();
                    break;
                    
                    case "agi":
                        agiFromItems += eachEquippedItem.GetAttributeValue();
                    break;
                    
                    case "int":
                        intelFromItems += eachEquippedItem.GetAttributeValue();
                    break;
                    
                }
            }
        }
        
        jObj.put("itemstr", strFromItems);
        jObj.put("itemagi", agiFromItems);
        jObj.put("itemint", intelFromItems);
        
        return jObj;
    }
    
    /**
     * Method that increase the level of the player by one and also increases
     * the armor status and damage status by 2 points.
     */
    private void LevelUp(){
        this.SetPlayerLevel(this.GetPlayerLevel() + 1);
        this.SetCharacterArmor(this.GetCharacterArmor() + 2);
        
        JSONObject JSONBasicAttr = this.GetPlayerClassStartingStats();
        JSONObject JSONItemAttr = GetAttributePointsFromEquippedItems();
        
        switch(this.playerClass){
            
            case "warrior":
                this.SetCharacterStrenght(((int) JSONBasicAttr.get("strength")) + ((int) JSONItemAttr.get("itemstr")) + ((this.GetPlayerLevel() * 2) - 2));
                this.SetCharacterAgility(((int) JSONBasicAttr.get("agility")) + ((int) JSONItemAttr.get("itemagi")) + (this.GetPlayerLevel() - 1));
                this.SetCharacterInteligence(((int) JSONBasicAttr.get("intelligence")) + ((int) JSONItemAttr.get("itemint")) + (this.GetPlayerLevel() - 1));
            break;
                
            case "rogue":
                this.SetCharacterStrenght(((int) JSONBasicAttr.get("strength")) + ((int) JSONItemAttr.get("itemstr")) + (this.GetPlayerLevel() - 1));
                this.SetCharacterAgility(((int) JSONBasicAttr.get("agility")) + ((int) JSONItemAttr.get("itemagi")) + ((this.GetPlayerLevel() * 2) - 2));
                this.SetCharacterInteligence(((int) JSONBasicAttr.get("intelligence")) + ((int) JSONItemAttr.get("itemint")) + (this.GetPlayerLevel() - 1));
            break;
                
            case "mage":
                this.SetCharacterStrenght(((int) JSONBasicAttr.get("strength")) + ((int) JSONItemAttr.get("itemstr")) + (this.GetPlayerLevel() - 1));
                this.SetCharacterAgility(((int) JSONBasicAttr.get("agility")) + ((int) JSONItemAttr.get("itemagi")) + (this.GetPlayerLevel() - 1));
                this.SetCharacterInteligence(((int) JSONBasicAttr.get("intelligence")) + ((int) JSONItemAttr.get("itemint")) + ((this.GetPlayerLevel() * 2) - 2));
            break;
        }
        
        this.CalculateGeneralPlayerDamage();
        
    }
    
}
