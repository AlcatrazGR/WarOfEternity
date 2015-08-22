package Interfaces;

/**
 * Interface IItem sets the basic characteristics for an object type item. This
 * interface is being inherited by the item class.
 * 
 * @author Vasilis Triantaris
 */
public interface IItem {
    
    String itemName = "";
    String itemDescription = "";
    double itemWeight = 0;
    double itemCost=0.0;
    int itemValue = 0;
    int itemType = 0; 
    
    public String GetItemName();
    public void SetItemName(String name);
    
    public String GetItemDescription();
    public void SetItemDescription(String desc);
    
    public int GetItemType();
    public void SetItemType(int type);
    
    public double GetItemWeight();
    public void SetItemWeight(float weight);
    
    public int GetItemValue();
    public void SetItemValue(int value);
    
    public double GetItemValueInGold();
    public void SetItemValueInGold(double cost);
    
}
