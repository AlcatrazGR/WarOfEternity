
package Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the reading of the area connection file and sets 
 * lists of the text content.
 * 
 * @author Vasilis Triantaris
 */
public class ReadConnectionFileController {
    
    //Class data members.
    StringBuffer strBufData;
    List<Area> areasList;
    
    List<Area> currentArea;
    List<Area> nextArea;
    List<String> directions;
    
    //Constructor with parametre the list of areas.
    public ReadConnectionFileController(List<Area> areas){
        this.areasList = areas;
                
        this.strBufData = null;
        this.currentArea = new ArrayList<Area>();
        this.nextArea = new ArrayList<Area>();
        this.directions = new ArrayList<String>();
    }
    
    /**
     * The main method of this controlling class which check the integrity of the
     * file path and sets the three data members currentArea, nextArea and directions
     * after they have been created from the model.
     */
    public void ReadConnectionFileControllingMethod(){
       
        ReadAreaConnectionFileModel rcfm = new ReadAreaConnectionFileModel(this.areasList);
        boolean filePathCheck = rcfm.SetFilePath();
        
        //If the path of the file is correct and nothing went wrong then...
        if(filePathCheck){
            
            this.strBufData = rcfm.ReadAreaConnectionFile();
            String[] dataOnLines = rcfm.SplitStringBufferToLines(this.strBufData);
            this.currentArea = rcfm.GetListOfCurrentAreas(dataOnLines);
            this.nextArea = rcfm.GetListOfNextAreas(dataOnLines);
            this.directions = rcfm.GetAreasDirections(dataOnLines);
        }
            
    }
    
}
