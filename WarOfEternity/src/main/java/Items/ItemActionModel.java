package Items;

import Characters.BattleActionModel;
import Characters.Enemies;
import Characters.EnemiesController;
import Characters.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vasilis Triantaris
 */
public class ItemActionModel {
    
    private final List<Item> listOfItems;
    String verbPartOfCommand;
    String nounPartOfCommand;
    
    public ItemActionModel(List<Item> items, String verb, String noun){
        this.listOfItems = items;
        this.verbPartOfCommand = verb;
        this.nounPartOfCommand = noun;
    }
    
    /**
     * This method finds the specific item to be used by the player and returns
     * its item type.
     * 
     * @param player The player object.
     * @return Returns the integer code of the item type.
     */
    public int GetTypeOfItemForUsagePurpose(Player player){
        int itemType = 0;
        
        for(Item eachSelectedItem : player.GetItemsSelectedByTheUser()){
            if(eachSelectedItem.GetItemName().equalsIgnoreCase(this.nounPartOfCommand))
                itemType = eachSelectedItem.GetItemType();
        }
        
        return itemType;
    }
    
    /**
     * Method that handles the equip item action of the player.
     * 
     * @param player The object that holds all the player data.
     * @param equipItemCommand    The name of the item to be equipped. 
     * @return Returns a message from equipping an item action command.
     */
    public String EquipItemPlayerAction(Player player, String equipItemCommand){
        String message;
        Item itemToBeEquiped = null;
 
        for(Item eachItemOnInventory : player.GetItemsSelectedByTheUser()){
            //If the item to be equiped existes in the inventory
            if(eachItemOnInventory.GetItemName().equalsIgnoreCase(equipItemCommand))
                itemToBeEquiped = eachItemOnInventory;
        }
      
        if(itemToBeEquiped != null){
            switch(itemToBeEquiped.GetItemType()){
                case 1 :
                case 2 :
                    message = "This item cant be equiped!";
                break;
                    
                default :
                    this.RemoveItemWithSameTypeThatIsAlreadyEquipedOnPlayer(player, itemToBeEquiped);
                    player.AddItemToEquipedItemListOfPlayer(itemToBeEquiped);
                    player.CalculatePlayersAttributePoints();
                    player.CalculateGeneralPlayerDamage();
                    player.CalculatePlayersArmor();
                    message = itemToBeEquiped.GetItemName()+" is equiped!";
                break;
            }
        }
        else{
            message = "There no such item in your inventory!";
        }

        return message;
    }
    
    /**
     * Method that if there is an item with the same type already equipped then 
     * it removes it from the list of equipped items.
     * 
     * @param itemToBeEquiped   The specific item that is going to replace an existing of its same item type when going to equip.
     */
    private void RemoveItemWithSameTypeThatIsAlreadyEquipedOnPlayer(Player player, Item itemToBeEquiped){
        List<Item> newEquipedItemList = new ArrayList();
        
        for(Item eachEquipedItem : player.GetEquipedItemListOfPlayer()){
            
            //If the type there is an item with same type with new item already equiped 
            //then it must be unequip it.
            if(eachEquipedItem.GetItemType() != itemToBeEquiped.GetItemType())
                newEquipedItemList.add(eachEquipedItem); 
            //else{
            //    this.ChangePlayerStatsWhenChangingEquipment(eachEquipedItem, -eachEquipedItem.GetItemValue());   
            //}
        }
        
        player.SetEquipedItemListOfPlayer(newEquipedItemList);
    }
    
    /**
     * Method that processes the use of potion items command actions.
     * 
     * @param enemyController   The object of the enemy controller class. It is used to decide whether the player is in a battle phase.
     * @param enemyToCombat     The enemy object that the user is battling. It is used in order to maintain the battle cycle.
     * @param player The object that holds all the player data.
     * @return Returns a message that describes the result of a use potion item action command. This message will be shown to the user.
     */
    public String PlayerUseItemPotionCommand(Player player, EnemiesController enemyController, Enemies enemyToCombat){
        String message = "";
        List<Item> newInventory = new ArrayList();
        
        int i=0;
        for(Item eachItemOnInventory : player.GetItemsSelectedByTheUser()){
            if((eachItemOnInventory.GetItemName().equalsIgnoreCase(this.nounPartOfCommand)) && (eachItemOnInventory.GetItemValue() > 0)){
                player.SetCharacterHealth(player.GetCharacterHealth() + eachItemOnInventory.GetItemHealingPower());
                player.GetItemsSelectedByTheUser().get(i).SetItemValue(player.GetItemsSelectedByTheUser().get(i).GetItemValue() - 1);
                
                //If the player runs out of potion then the item must be removed from inventory
                if(player.GetItemsSelectedByTheUser().get(i).GetItemValue() == 0){
                    for(Item eachItem: player.GetItemsSelectedByTheUser()){
                        if(!eachItem.GetItemName().equals(eachItemOnInventory.GetItemName()))
                            newInventory.add(eachItem); 
                    }
                    
                    player.SetItemsSelectedByPlayer(newInventory);
                }
                
                message = eachItemOnInventory.GetItemName()+" has been used!";
            }
            i++;
        }
        
        if(enemyController.GetBattleProgressState()){
            BattleActionModel bam = new BattleActionModel();
            message += "\n"+bam.AttackFromEnemyToPlayerProcess(player, enemyToCombat);
        }

        return message;
    }
    
    /**
     * Method that processes the use of keys command actions.
     * @param player The object that holds all the player data.
     * @return Returns a message that describes the result of a use item action command.
     */
    public String PlayerUseItemKeyActionCommand(Player player){
        boolean itemCanBeUsedOnSpecificArea = false;
        String doorItemNameOnArea;
        String message = "";

        int i = 0;
        for(Item eachItem : this.listOfItems){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()) { 
                
                //If the item item to be used its purposed for usage and its already picked by the player and there 
                //is a usage connection of the item with the speciific are that the player is on then ...
                if((icwa.GetItemUsage().equals("use")) && (icwa.GetConnectionWithAreaReference().GetAreasName().equals(player.GetAreaLocation().GetAreasName()))){
                    if(this.listOfItems.get(i).GetItemValue() != 0){

                        switch(this.listOfItems.get(i).GetItemType()){

                            //case item is misceleneous
                            case 2 :
                                doorItemNameOnArea = this.GetGateNameFromItemList(player);
                                String gateIntegrityMessage = this.UsageIntegrityOfTheGate(player, doorItemNameOnArea);
                                if(!gateIntegrityMessage.equals(""))
                                    return gateIntegrityMessage;

                                this.ChangeStateOfGate(doorItemNameOnArea);
                                itemCanBeUsedOnSpecificArea = true;
                                message = "Gate has been open!";
                            break;
                       }
                    }
                    else{
                        message = "Item : " + this.nounPartOfCommand + " has already been used!";
                    }
                }
            }
            i++;   
        } 
        
        if(!itemCanBeUsedOnSpecificArea)
            return "This item can't be used here!";

        return message;
    }
    
    /**
     * Method that returns the name of the gate in the specific area.
     * 
     * @param playerAction  The action command of the player.
     * @param itemList  The list of items of the game.
     * @return Returns the name of the door/gate that is blocking a specific path.
     */
    private String GetGateNameFromItemList(Player player){
        String doorItemNameOnArea = "";
        
        for(Item eachItem : this.listOfItems){       
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){
                
                //If the item connection area is the same one with the players and its a door / gate 
                if((icwa.GetConnectionWithAreaReference().GetAreasName().equals(player.GetAreaLocation().GetAreasName())) && (eachItem.GetItemType() == 4))
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
    private String UsageIntegrityOfTheGate(Player player, String doorItemNameOnArea){
        String message = "";
        String gateLocation = "";
        String keyLocation = "";
        
        for(Item eachItem : this.listOfItems){       
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){
                
                //If the item connection area is the same one with the players and its a door / gate 
                if((icwa.GetConnectionWithAreaReference().GetAreasName().equals(player.GetAreaLocation().GetAreasName())) && (eachItem.GetItemType() == 4))
                    gateLocation = icwa.GetConnectionWithAreaReference().GetAreasName();

                if((icwa.GetItemConnectedToAreaReference().GetItemName().equalsIgnoreCase(this.nounPartOfCommand)) && (icwa.GetItemUsage().equalsIgnoreCase("use")))
                    keyLocation = icwa.GetConnectionWithAreaReference().GetAreasName();
            }
        }

        if(!keyLocation.equals(gateLocation))
            message = "The item : " + this.nounPartOfCommand + " can't be used here!";
            
        return message;
    }

    /**
     * Method that changes the state of the gate (Sets is to 1 which means unlocked).
     * 
     * @param itemList  The list of items of the game.
     * @param doorName  The name of the door that is blocking a specific path.
     */
    private void ChangeStateOfGate(String doorName){
        int i = 0;
        for(Item eachItem : this.listOfItems){
            if(eachItem.GetItemName().equals(doorName))
                listOfItems.get(i).SetItemValue(1);

            i++;
        }
    }
    
    /**
     * Method that handles the search for items action.
     * 
     * @param player The player object.
     * @return Returns a message as a result of the search command and it is displayed to the user.
     */
    public String PlayerSearchItemProcess(Player player){
        String message = "";
            
        for(Item eachItem : this.listOfItems){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){
                if((icwa.GetItemUsage().equals("pick")) && (icwa.GetConnectionWithAreaReference().GetAreasName().equals(player.GetAreaLocation().GetAreasName())) && (eachItem.GetItemValue() == 0))
                    message += "--> "+eachItem.GetItemDescription()+"\n";
                
                if((icwa.GetItemUsage().equals("open")) && (icwa.GetConnectionWithAreaReference().GetAreasName().equals(player.GetAreaLocation().GetAreasName())) && (eachItem.GetItemValue() == 0))
                    message += "--> "+eachItem.GetItemDescription()+"\n";
            }
        }
        
        if(message.equals(""))
            message = "Nothing found while searching!";
        else
            message = "While searching you found : \n"+message;
        
        return message;
    }
    
    /**
     * Method that processes the pick / take item command action.
     * 
     * @param player The object that holds all the player data.
     * @return Returns a message that describes the result of a pick / take action command. This message will be shown to the user.
     */
    public String PlayerItemActionCommand(Player player){
        boolean itemExistsOnTheAreaCheck = false;
        String message = "";

        int i=0;
        for(Item eachItem : this.listOfItems){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()) {

                //If the the item is the same with the one that the player is trying to pick and there
                //is a connection of this specific item with the area that the player is on then ...
                if(((eachItem.GetItemName().equalsIgnoreCase(this.nounPartOfCommand)) || (eachItem.GetItemDescription().contains(this.nounPartOfCommand))) && 
                        (icwa.GetItemUsage().equals("pick")) && (icwa.GetConnectionWithAreaReference().GetAreasName().equals(player.GetAreaLocation().GetAreasName()))) {
     
                    //if the summary of weight plus the items is more than the limit then..
                    if(this.CalculatingPlayerInventoryItemWeight(player) + eachItem.GetItemWeight() > 100.0)
                        return "Exceeding weight limit, can't pick that up!";
                    
                    switch(this.listOfItems.get(i).GetItemType()){
                        
                        //case it is misceleneous
                        case 2 :
                           if(this.listOfItems.get(i).GetItemValue() == 0){
                               itemExistsOnTheAreaCheck = true;
                               message = "Item " + this.nounPartOfCommand + " is picked.\n--> Description : " + eachItem.GetItemDescription();
                               this.listOfItems.get(i).SetItemValue(1);
                               player.AddItemToSelectedItemsByPlayer(this.listOfItems.get(i));
                           }
                           else {
                               itemExistsOnTheAreaCheck = true;
                               message = "You have already picked : " + this.nounPartOfCommand;
                           } 
                        break;
                    
                        case 1:
                            if(this.listOfItems.get(i).GetItemValue() > 0){
                                itemExistsOnTheAreaCheck = true;
                                message = "Item " + this.nounPartOfCommand + " is picked\n--> Decription : " + eachItem.GetItemDescription();
                                this.listOfItems.get(i).SetItemValue(0);
                                player.AddItemToSelectedItemsByPlayer(this.listOfItems.get(i));
                            }
                            else{
                                itemExistsOnTheAreaCheck = true;
                                message = "You have already picked : " + this.nounPartOfCommand;
                            }
                        break;
                    }
                }
            }
            i++;
        }
   
        if(!itemExistsOnTheAreaCheck)
            message = "There is no item : " + this.nounPartOfCommand;
        
        return message;
    }
    
    /**
     * Calculating the whole wight of item in the inventory of player.
     * 
     * @param player The object that holds all the player data.
     * @return Returns the whole weight of items that the player is carrying in a double data format.
     */
    public double CalculatingPlayerInventoryItemWeight(Player player){
        double weightSum = 0.0;
        
        for(Item eachItemInInventory : player.GetItemsSelectedByTheUser())
            weightSum += eachItemInInventory.GetItemWeight();
 
        return weightSum;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
