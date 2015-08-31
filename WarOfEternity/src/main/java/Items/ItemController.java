package Items;

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
    private List<Item> listOfItems;
    
    //Constructor of item controller class
    public ItemController(List<Area> areaList){
        
        this.listOfAreas = areaList;
        this.listOfItems = new ArrayList();
        
        /*
        //Initiallize list arrays
        this.itemList = new ArrayList();
        List<Item> connectionItems = new ArrayList();
        List<Area> connectionAreas = new ArrayList();
        List<String> connectionItemPurpose = new ArrayList();
        
        //Create object for the class that will set all the list data needed
        ReadItemDataController ridc = new ReadItemDataController(areaList);
        ridc.ItemDataControllingMethod();
        
        this.itemList = ridc.GetListOfItems();
        connectionItems = ridc.GetListOfConnectionItems();
        connectionAreas = ridc.GetListOfConnectionItemAreas();
        connectionItemPurpose = ridc.GetListOfConnectionItemPurposes();
        
        //Take the specific items in i place from each of the list and set the
        //connections of each item.
        for(int i=0; i<connectionItems.size(); i++){
            SetItemConnectionWithAreas(connectionItems.get(i), connectionAreas.get(i), connectionItemPurpose.get(i));
        }
        */
    }
    
    public void SetItemDataForGame(){
        
        ReadItemDataModel ridm = new ReadItemDataModel(this.listOfAreas);
    
    }
    
    
    
    /**
     * Method that sets the connections of the items.
     *
     * @param itemRef   The specific item with which a connection is going to be made.
     * @param areaConnection    The area that the item is going to be connected.
     * @param itemPuropose  The purpose of the connection (for example pick).
     */
    private void SetItemConnectionWithAreas(Item itemRef, Area areaConnection, String itemPurpose){
       
        ItemConnectionWithArea icwa = new ItemConnectionWithArea(areaConnection, itemRef, itemPurpose);
       
        for(Item eachItem : this.listOfItems){
            if(eachItem.GetItemName().equalsIgnoreCase(itemRef.GetItemName())){
                eachItem.AddItemConnectionWithAreaToList(icwa);
            }
        }    
    }
    
    //Returns the list of items.
    public List<Item> GetListOfItems(){
        return this.listOfItems;
    }
    
}
