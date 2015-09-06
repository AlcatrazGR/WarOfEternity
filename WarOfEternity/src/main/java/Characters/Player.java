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
    
    public void CalculateGeneralPlayerDamage(){
        int equippedItemDam = this.CalculatePlayersDamageFromEquippedItems();  
        int damage;
        double classPercentage;
        
        switch(this.GetChatacterClass()){
            
            case "warrior":
                classPercentage = this.GetCharacterStrength() * 0.01;
                damage = (int) ((equippedItemDam * this.GetPlayerLevel()) * classPercentage) + 10;
                this.SetCharacterDamage(damage);
            break;
                
            case "rogue":
                classPercentage =  this.GetCharacterAgility() / 100;
                damage = (int) ((equippedItemDam * this.GetPlayerLevel()) * classPercentage) + 10;
                this.SetCharacterDamage(damage);
            break;
                
            case "mage":
                classPercentage =  this.GetCharacterInteligence() / 100;
                damage = (int) ((equippedItemDam * this.GetPlayerLevel()) * classPercentage) + 10;
                this.SetCharacterDamage(damage);
            break;
                
        }
        
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
     * Method manipulates the direction commands. It checks if the direction that
     * the player can go to exist or if it is accessible and if everything is fine 
     * then it redirects him to the next area.
     * 
     * @param playerAction  The action command given by the user.
     * @param itemList  The list of game items. It is used to redirect the player to areas that maybe blocked by doors / gates.
     * @return Returns a message that describes the result of the player redirection action command given.
     */
    public String PlayerActionCommand(String playerAction, List<Item> itemList){
        boolean directionToAreaIsCorrect = false;
        String directionIsBlockedMessage;
        String message = "";

        for(AreaConnectionMaker acm : this.currentAreaLocation.GetListOfAreaConnections()){
            
            if(acm.GetDirectionToOtherArea().equalsIgnoreCase(playerAction)){
                
                directionIsBlockedMessage = this.DirectionToNextAreaIsBlockedByItem(itemList, acm, playerAction);
                if(directionIsBlockedMessage.equals("")){
                    this.currentAreaLocation = acm.GetNextArea();
                    directionToAreaIsCorrect = true;
                    message = this.currentAreaLocation.GetAreaDescription();
                }
                else{
                    message = directionIsBlockedMessage;
                    directionToAreaIsCorrect = true;
                }
            }
        }
        
        if(!directionToAreaIsCorrect)
            message = "There is no direction to : "+playerAction;
        
        return message;  
    }
    
    /**
     * This method checks whether the area that the user is in is blocked by a gate / door.
     * 
     * @param itemList  The list of game items.
     * @param acm   The object of area connections.
     * @param nounPart  The noun part of a action command.
     * @return Returns a message if the next area that the user is trying to go is blocked by a door/gate. Else it returns an empty message.
     */
    public String DirectionToNextAreaIsBlockedByItem(List<Item> itemList, AreaConnectionMaker acm, String nounPart){
        String checkMessage = "";
        
        for(Item eachItem : itemList){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){

                //If the item in there is a object door/gate in the area that the user is in and is still
                //closed then...
                if((eachItem.GetItemType() == 4) && (icwa.GetItemUsage().equals("open") && (eachItem.GetItemValue() == 0) && (eachItem.GetBlockingDirection().equalsIgnoreCase(nounPart)) && 
                        (this.currentAreaLocation.GetAreasName().equals(icwa.GetConnectionWithAreaReference().GetAreasName())))){
                    checkMessage = "You cannot proceed further. The gate is blocking your path!";
                }
            }
        }
        
        return checkMessage;
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
     * Method that changes the state of the gate (Sets is to 1 which means unlocked).
     * 
     * @param itemList  The list of items of the game.
     * @param doorName  The name of the door that is blocking a specific path.
     */
    private void ChangeStateOfGate(List<Item> itemList, String doorName){
        int i = 0;
        for(Item eachItem : itemList){
            if(eachItem.GetItemName().equals(doorName))
                itemList.get(i).SetItemValue(1);

            i++;
        }
    }

    /**
     * This method sets the data needed for battle. Generally if the player is
     * about to encounter an enemy is sets a list of enemies that dwell in the 
     * area that the user is about to be transported and then it randomly pick
     * one from this list to face the player.
     * 
     * @param enemyController   The object of the enemy controller class.
     * @param playerCommand     The command given by the player.
     * @param areasList  The list of game areas. It is used to find all the enemies that dwell in the specific area.
     * @return Returns the specific enemy object that the player is going to battle.
     */
    public Enemies SetBattleData(EnemiesController enemyController, String playerCommand, List<Area> areasList){
        List<Enemies> enemiesInPlayerArea = new ArrayList();
        
        for(Enemies eachGameEnemy : enemyController.GetGameEnemyList()){
               for(AreaConnectionMaker acm : this.GetAreaLocation().GetListOfAreaConnections()){
                   if((eachGameEnemy.GetAreaLocation().GetAreasName().equals(acm.GetNextArea().GetAreasName())) && 
                            (acm.GetDirectionToOtherArea().equalsIgnoreCase(playerCommand))){
                        enemiesInPlayerArea.add(eachGameEnemy);
                 }
            }  
        }
        
        //Generates a random number with scale from 0 to the size of areas enemies 
        Random randomNumb = new Random();
        int numb = randomNumb.nextInt(enemiesInPlayerArea.size());

        return enemiesInPlayerArea.get(numb);
    }
    
    /**
     * Method that handles the attack action from the player to the enemy.
     * 
     * @param enemyToCombat The enemy object that the user is battling.
     * @return Returns a message from the attack made by the user to the enemy which displays the damage being done.
     */
    public String AttackEnemyProcess(Enemies enemyToCombat){
        String message;

        Random rand = new Random();
        int randomDamage = rand.nextInt(this.GetCharacterDamage());
        
        //The damage reduction is calculated as : dr = armor(enemy) / (armor(enemy) + damage(player))
        double damageReduction = enemyToCombat.GetCharacterArmor() / (enemyToCombat.GetCharacterArmor() + randomDamage);
        int finalDamage = (int) (randomDamage - ((randomDamage * damageReduction)/2));
        
        enemyToCombat.SetCharacterHealth(enemyToCombat.GetCharacterHealth() - finalDamage);
        message = "You damaged the enemy for "+finalDamage+" attack damage!";
        
        //if the enemy died, then process a random number for gold set the apropriate message
        if(enemyToCombat.GetCharacterHealth() <= 0){
            message = "The enemy is dead!";  
            double randomGold = 0.0 + (enemyToCombat.GetCharacterGold() - 0.0) * rand.nextDouble();
            this.SetCharacterGold(this.GetCharacterGold() + randomGold);
        }
        
        return message;
    } 
    
    /**
     * Method that handles the attack that the enemy does to the player.
     * 
     * @param enemyToCombat The enemy object that the user is battling.
     * @return Returns a message from the attack made by the enemy to the user which displays the damage being done.
     */
    public String AttackFromEnemyToPlayerProcess(Enemies enemyToCombat){
        String message;
            
        Random rand = new Random();
        int randomDamage = rand.nextInt(enemyToCombat.GetCharacterDamage());
        
        //The damage reduction is calculated as : dr = armor(player) / (armor(player) + damage(enemy))
        double damageReduction = this.GetCharacterArmor() / (this.GetCharacterArmor() + enemyToCombat.GetCharacterDamage());
        int finalDamage = (int) (randomDamage - ((randomDamage * damageReduction)/2));
        
        this.SetCharacterHealth(this.GetCharacterHealth() - finalDamage);
        message = enemyToCombat.GetCharacterName()+" damaged you for "+finalDamage+" damage!";
        
        if(this.GetCharacterHealth() <= 0)
            message = "You died!";

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
   
    /**
     * Method that increase the level of the player by one and also increases
     * the armor status and damage status by 2 points.
     */
    private void LevelUp(){
        this.SetPlayerLevel(this.GetPlayerLevel() + 1);
        this.SetCharacterArmor(this.GetCharacterArmor() + 2);
        this.CalculateGeneralPlayerDamage();
        
    }
    
}
