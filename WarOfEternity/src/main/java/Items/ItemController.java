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
    }
    
    /**
     * Method that controlles the reading of all the item and item connecitons
     * files.
     */
    public void SetItemDataForGame(){

        ReadItemDataModel ridm = new ReadItemDataModel(this.listOfAreas);
        ridm.SetItemDataList();
        this.listOfItems = ridm.GetListOfGameItems();
        
        ridm.SetItemConnectionMainMethod();
    }

    //Returns the list of items.
    public List<Item> GetListOfItems(){
        return this.listOfItems;
    }
    
}
