package Map;

import java.io.Serializable;


/**
 * Class AreaConnectionMaker sets the characteristics for a area connection. 
 * An area connection is consisted of the area object which refers to the next
 * area and the direction with witch the two areas are connected. It also 
 * implements the Serializable interface so that the storing of area connection
 * objects can be made.
 * 
 * @author Vasilis Triantaris
 */
public class AreaConnectionMaker implements Serializable{
    
    //Data memebr of the area connection maker class
    private Area nextArea;
    private String directionsOnCurrentArea;
    
    //Simple Constructor
    public AreaConnectionMaker(){
        nextArea = null;
        directionsOnCurrentArea = "";
    }
    
    //Constructor with parametres
    public AreaConnectionMaker(String directions, Area connection){
        this.nextArea = connection;
        this.directionsOnCurrentArea = directions;
    }
    
    public Area GetNextArea(){
        return this.nextArea;
    }
    
    public void SetNextArea(Area changeArea){
        this.nextArea = changeArea;
    }
    
    public String GetDirectionToOtherArea(){
        return this.directionsOnCurrentArea;
    }
    
    public void SetDirectionToOtherArea(String direction){
        this.directionsOnCurrentArea = direction;
    }
    
    
}
