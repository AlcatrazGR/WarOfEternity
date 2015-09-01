package Items;

import Interfaces.IItem;
import Map.Area;
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
    
    private Area itemArea;
    private String attributeType;
    private int attributeValue;
    
    //Constructor that sets and item object type 1 (consumble (potion))
    public Item(String name, String descr, int type, double weight, int value, double cost, int healPower){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemCost = cost;
        this.itemHealingPower = healPower;
        
        itemConnectionsList = new ArrayList();
        this.itemArea = null;
        this.attributeType = "";
        this.attributeValue = 0;
    }
    
    //Constructor that sets items type 2 (keys)
    public Item(String name, String descr, int type, double weight, int value, double cost){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemCost = cost;
        
        itemConnectionsList = new ArrayList();
        this.itemArea = null;
        this.attributeType = "";
        this.attributeValue = 0;
        this.itemHealingPower = 0;
    }
    
    //Constructor that sets items type 3 (weapon)
    public Item(String name, String descr, int type, double weight, int value, String atr, int atrVal, Area area, double cost){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.attributeType = atr;
        this.attributeValue = atrVal;
        this.itemArea = area;
        this.itemCost = cost;
        
        itemConnectionsList = new ArrayList();
        this.itemHealingPower = 0;
    }
    
    //Constructor that sets items type 4 (Gates)
    public Item(String name, String descr, int type, double weight, int value, double cost, String blockDir){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemCost = cost;
        this.blockingDirection = blockDir;
        
        itemConnectionsList = new ArrayList();
        this.itemHealingPower = 0;
        this.itemArea = null;
        this.attributeType = "";
        this.attributeValue = 0;
    }
    
    //Constructor that sets items type 5-6 (armor / shield)
    public Item(String name, String descr, int type, double weight, int value, Area area, double cost){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemArea = area;
        this.itemCost = cost;
        
        itemConnectionsList = new ArrayList();
        this.attributeType = "";
        this.attributeValue = 0;
        this.itemHealingPower = 0;
    }
    
   
    
    /*
    //Constructor with parametres for simple item connection
    public Item(String name, String descr, int type, double weight, int value, String atribute, int atrValue, Area merchArea, double cost){
        this.itemName = name;
        this.itemDescription = descr;
        this.itemType = type;
        this.itemWeight = weight;
        this.itemValue = value;
        this.itemCost = cost;
        this.itemHealingPower = 0;
        itemConnectionsList = new ArrayList();
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
        itemConnectionsList = new ArrayList();
    }
    */
    
    public List<ItemConnectionWithArea> GetItemConnectionsWithArea(){
        return this.itemConnectionsList;
    }
    
    public void AddItemConnectionWithAreaToList(ItemConnectionWithArea icwa){
        this.itemConnectionsList.add(icwa);
    }

    public void SetItemArea(Area area){
        this.itemArea = area;
    }
    
    public Area GetItemArea(){
        return this.itemArea;
    }
    
    public void SetAttributeType(String atr){
        this.attributeType = atr;
    }
    
    public String GetAttributeType(){
        return this.attributeType;
    }
    
    public void SetAttributeValue(int atrVal){
        this.attributeValue = atrVal;
    }
    
    public int GetAttributeValue(){
        return this.attributeValue;
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
