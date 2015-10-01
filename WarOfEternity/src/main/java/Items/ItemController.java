package Items;

import Characters.Enemies;
import Characters.EnemiesController;
import Characters.Player;
import Map.Area;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Item Controller that controls the item objects and the 
 * item connection objects.
 * 
 * @author Vasilis Triantaris
 */
public class ItemController implements Serializable{
    
    private final List<Area> listOfAreas;
    String verbPartOfCommand;
    String nounPartOfCommand;

    private List<Item> listOfItems;
    
    //Constructor of item controller class
    public ItemController(List<Area> areaList){
        
        this.listOfAreas = areaList;
        
        this.listOfItems = new ArrayList();
        this.verbPartOfCommand = "";
        this.nounPartOfCommand = "";
    }
    
    //Constructor for item action command.
    public ItemController(String verb, String noun, List<Item> items){
        
        this.verbPartOfCommand = verb;
        this.nounPartOfCommand = noun;
        this.listOfItems = items;
        
        this.listOfAreas = new ArrayList();
    }
    
    /**
     * Method that controls the reading of all the item and item connections
     * files.
     */
    public void SetItemDataForGame(){

        ReadItemDataModel ridm = new ReadItemDataModel(this.listOfAreas);
        ridm.SetItemDataList();
        this.listOfItems = ridm.GetListOfGameItems();
        
        ridm.SetItemConnectionMainMethod();
    }
    
    public String ItemActionCommandProcessController(Player player, EnemiesController enemyController, Enemies enemyToCombat){
    
        String resultMessage = null;
        ItemActionModel iam = new ItemActionModel(this.listOfItems, this.verbPartOfCommand, this.nounPartOfCommand);

        if(this.verbPartOfCommand.equals("equip")){
            resultMessage = iam.EquipItemPlayerAction(player, this.nounPartOfCommand);
        }
        else if(this.verbPartOfCommand.equals("use")){
            switch(iam.GetTypeOfItemForUsagePurpose(player)){
                case 0 :
                    resultMessage = "There is no such item in your inventory!";     
                break;
                        
                case 1 :
                    resultMessage = iam.PlayerUseItemPotionCommand(player, enemyController, enemyToCombat);
                break;
                            
                case 2 :
                    resultMessage = iam.PlayerUseItemKeyActionCommand(player);
                break;
            }  
        }
        else if(this.verbPartOfCommand.equals("search")){
            resultMessage = iam.PlayerSearchItemProcess(player);
        }
        else{
            resultMessage = iam.PlayerItemActionCommand(player);
        }
        
        return resultMessage;
    }

    //Returns the list of items.
    public List<Item> GetListOfItems(){
        return this.listOfItems;
    }
    
}
