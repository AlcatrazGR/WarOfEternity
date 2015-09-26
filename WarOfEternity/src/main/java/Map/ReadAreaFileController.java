package Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the reading of the area file text.
 * 
 * @author Triantaris Vasilis
 */
public class ReadAreaFileController {
    
    private StringBuffer strBufData;
    
    //Simple Constructor
    public ReadAreaFileController(){
        this.strBufData = null;
    }
    
    /**
     * Method that calls the read file model to get all the data from the 
     * GameAreas file and store them into a list of Area objects.
     * 
     * @return Returns the list of game areas.
     */
    public List<Area> AreaFileControllingMethod(){
        List<Area> areaList = new ArrayList();
        
        ReadAreaFileModel rafm = new ReadAreaFileModel();

        String[] dataOnLines = rafm.SplitStringBufferDataToLines();
        boolean setListCheck = rafm.SetAreaList(dataOnLines);
        
        //If the processes of makeing the area objects and adding them on the list.
        if(setListCheck)
            areaList = rafm.GetAreaList();

        return areaList;
    }
    
    
}
