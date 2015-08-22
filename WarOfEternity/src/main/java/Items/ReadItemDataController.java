package Items;

import Map.Area;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the reading of item file and item connections file.
 * 
 * @author Vasilis Triantaris
 */
public class ReadItemDataController {
    
    private List<Item> itemList;
    private final List<Area> areaList;
    
    private List<Item> connectionItems;
    private List<Area> connectionAreas;
    private List<String> connectionItemPurpose;
    
    //Constructor
    public ReadItemDataController(List<Area> areas){
        this.itemList = new ArrayList();
        this.areaList = areas;
        
        this.connectionItems = new ArrayList();
        this.connectionAreas = new ArrayList();
        this.connectionItemPurpose = new ArrayList();
    }
    
    //Method that manipulates the reading of the gameItems file and gameItemConnections file
    public void ItemDataControllingMethod(){
        
        ReadItemDataModel ridm = new ReadItemDataModel();
        
        this.itemList = ridm.SetItemDataList();
        this.connectionItems = ridm.SetConnectionItemList(this.itemList);
        this.connectionAreas = ridm.SetConnectionItemAreasList(this.areaList);
        this.connectionItemPurpose = ridm.SetConnectionItemPurposeList();        

    }
    
    public List<Item> GetListOfItems(){
        return this.itemList;
    }
    
    public List<Item> GetListOfConnectionItems(){
        return this.connectionItems;
    }
 
    public List<Area> GetListOfConnectionItemAreas(){
        return this.connectionAreas;
    }
    
    public List<String> GetListOfConnectionItemPurposes(){
        return this.connectionItemPurpose;
    }
}
