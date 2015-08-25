package Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class MapController is the controlling class for setting the area  and 
 * area connection objects. It implements the Serializable interface so that
 * we can write objects of its type in a external file.
 * 
 * @author Vasilis Triantaris
 */
public class MapController implements Serializable{
    
    //List data member that contains all the objects for each area
    private List<Area> areasList;
    
    /**
     * Constructor of MapCotroller class when a object of this class is made
     * then this constructor automatically sets all the data that is 
     * referred for the map.
     */
    public MapController(){
        this.areasList = new ArrayList();
        
        ReadAreaFileController rafc = new ReadAreaFileController();
        //Get the list of areas.
        this.areasList = rafc.AreaFileControllingMethod();
       
        ReadConnectionFileController rcfcm = new ReadConnectionFileController(this.areasList);
        rcfcm.ReadConnectionFileControllingMethod();
        
        List<Area> currentAreas = rcfcm.currentArea;
        List<Area> nextArea = rcfcm.nextArea;
        List<String> directions = rcfcm.directions;
        
        //For every object in the above list call the SetAreaConnection method to
        //create the connection of areas.
        for(int i=0; i<currentAreas.size(); i++){
            System.out.println(currentAreas.get(i).GetAreasName() + " @ " + nextArea.get(i).GetAreasName() + " @ " + directions.get(i));
            SetAreaConnection(currentAreas.get(i), nextArea.get(i), directions.get(i));
        }
        
    }

    /**
     * Method that creates the connections of each area. After getting the three
     * columns of the are connection file and storing them in lists with the 
     * ReadAreaConnectionFileModel class we get each list item from those lists 
     * and create the connection.
     * 
     * @param currentArea   The starting area or just the area that the player might be located.
     * @param connectionsWithOtherAreas     The next area that the previous is connected with.
     * @param direction     The direction that the player must follow in order to travel from the currentArea to the NextArea.
     */
    private void SetAreaConnection(Area currentArea, Area connectionsWithOtherAreas, String directions){
    
        //Set a area connection with data the next area and the direction to go there. 
        AreaConnectionMaker acm = new AreaConnectionMaker(directions, connectionsWithOtherAreas);
    
        //Check all the areas on the area list. If the area on the loop is equal
        //to the one current area given in the parametres then set this area as the
        //current - starting area of the connection.
        for(Area currentAreaOnTheLoop : this.areasList){
            
            if(currentAreaOnTheLoop.GetAreasName().equals(currentArea.GetAreasName())){
                currentAreaOnTheLoop.addAreaConnection(acm);
            }
            
        }
    }
    
    //Method that return the list of areas.
    public List<Area> GetAreasList(){
        return this.areasList;
    }
    
    
}
