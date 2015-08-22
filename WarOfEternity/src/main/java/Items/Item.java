package Items;

import Interfaces.IItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Item that implements the basic attributes from the interface IItem and 
 * extends each type of item that has extra special attributes.
 * 
 * @author Thomas Liakos
 */
public class Item extends GateItem implements IItem, Serializable {
    
    private List<ItemConnectionWithArea> itemConnectionsList;
    private String itemName;
    private String itemDescription;
    private double itemWeight;
    private double itemCost;
    //The item value of an item depends on its type. If for example the value is 12
    //and the type is weapon then that means that the weapon does 12 damage.
    private int itemValue;
    private int itemHealingPower;
    private int itemType;
    /*The type of the item can be eather :
     * 1 --> Consumables
     * 2 --> Miscenelous (keys)
     * 3 --> Weapons
     * 4 --> Doors / Gates
     * 5 --> Armor
     * 6 --> Shields
     */
    
    //Constructor that sets and item object type 1 (consumble (potion))
    public Item(String name, String descr, int type, double weight, int value, double cost, int healPower){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemCost = cost;
        this.itemHealingPower = healPower;
        itemConnectionsList = new ArrayList<ItemConnectionWithArea>();
    }
    
    //Constructor with parametres for simple item connection
    public Item(String name, String descr, int type, double weight, int value, double cost){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemCost = cost;
        this.itemHealingPower = 0;
        itemConnectionsList = new ArrayList<ItemConnectionWithArea>();
    }
    
    //COnstructor with parametres for setting gate / door object attributes.
    public Item(String name, String descr, int type, double weight, int value, double cost, String blockDir){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.blockingDirection = blockDir;
        this.itemCost = cost;
        this.itemHealingPower = 0;
        itemConnectionsList = new ArrayList<ItemConnectionWithArea>();
    }
    
    public List<ItemConnectionWithArea> GetItemConnectionsWithArea(){
        return this.itemConnectionsList;
    }
    
    public void AddItemConnectionWithAreaToList(ItemConnectionWithArea icwa){
        this.itemConnectionsList.add(icwa);
    }

    public String GetItemName() {
        return this.itemName;
    }

    public void SetItemName(String name) {
        this.itemName = name;
    }

    public String GetItemDescription() {
        return this.itemDescription;
    }

    public void SetItemDescription(String desc) {
        this.itemDescription = desc;
    }
    
    public int GetItemType() {
        return this.itemType;
    }

    public void SetItemType(int type) {
        this.itemType = type;
    }
    
    public double GetItemWeight() {
        return this.itemWeight;
    }

    public void SetItemWeight(float weight) {
        this.itemWeight = weight;
    }
    
    public int GetItemValue() {
        return this.itemValue;
    }
    
    public void SetItemValue(int value) {
        this.itemValue = value;
    }

    public double GetItemValueInGold() {
        return this.itemCost;
    }

    public void SetItemValueInGold(double cost) {
        this.itemCost = cost;
    }
    
    public int GetItemHealingPower() {
        return this.itemHealingPower;
    }

    public void SetItemValueInGold(int healPower) {
        this.itemHealingPower = healPower;
    }
}
