package Items;

import Map.Area;
import java.io.Serializable;

/**
 * Class Item Connections that from which objects of item connections are made 
 * It is necessary to create connections in order to know which item is 
 * connected with which area.
 * 
 * @author Thomas Liakos
 */
public class ItemConnectionWithArea implements Serializable{
    
    Area itemArea;
    Item itemRef;
    String itemUsage;
    
    public ItemConnectionWithArea(){
        this.itemRef = null;
        this.itemArea = null;
        this.itemUsage = "";
    }
    
    public ItemConnectionWithArea(Area area, Item item, String purpose){
        this.itemArea = area;
        this.itemRef = item;
        this.itemUsage = purpose;
    }
    
    public void SetItemConnectedToAreaReference(Item item){
        this.itemRef = item;
    }
    
    public Item GetItemConnectedToAreaReference(){
        return this.itemRef;
    }
    
    public void SetConnectionWithAreaReference(Area area){
        this.itemArea = area;
    }
    
    public Area GetConnectionWithAreaReference(){
        return this.itemArea;
    }
    
    public void SetItemUsage(String purpose){
        this.itemUsage = purpose;
    }
    
    public String GetItemUsage(){
        return this.itemUsage;
    }

}
