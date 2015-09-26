
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
    
    List<Area> areasList;
    
    List<Area> currentArea;
    List<Area> nextArea;
    List<String> directions;
    
    //Constructor with parametre the list of areas.
    public ReadConnectionFileController(List<Area> areas){
        this.areasList = areas;
        this.currentArea = new ArrayList();
        this.nextArea = new ArrayList();
        this.directions = new ArrayList();
    }
    
    /**
     * The main method of this controlling class which check the integrity of the
     * file path and sets the three data members currentArea, nextArea and directions
     * after they have been created from the model.
     */
    public void ReadConnectionFileControllingMethod(){
       
        ReadAreaConnectionFileModel rcfm = new ReadAreaConnectionFileModel(this.areasList);
        
        String[] dataOnLines = rcfm.SplitStringBufferToLines();
        this.currentArea = rcfm.GetListOfCurrentAreas(dataOnLines);
        this.nextArea = rcfm.GetListOfNextAreas(dataOnLines);
        this.directions = rcfm.GetAreasDirections(dataOnLines);
        
    }
    
}
