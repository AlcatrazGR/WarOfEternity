package Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Area sets the basic characteristics for an area object. It implements
 * the Serializable interface in order to write object of this class into an
 * external file.
 * 
 * @author Thomas Liakos
 */
public class Area implements Serializable{
    
    //Data members of the area object
    private String areaName;
    private String areaDescription;
    private String areaImage;
    private List<AreaConnectionMaker> areaConnections; 
    
    //Simple Constructor
    Area(){
        this.areaName = "";
        this.areaDescription = "";
        this.areaImage = "";
        this.areaConnections = new ArrayList<AreaConnectionMaker>();
    }
    
    //Constructor with parametres
    Area(String title, String description, String image){
        this.areaName = title;
        this.areaDescription = description;
        this.areaImage = image;
        this.areaConnections = new ArrayList<AreaConnectionMaker>();
    }
    
    //Adds a object connection the the list of area connections
    public void addAreaConnection(AreaConnectionMaker acm){
        areaConnections.add(acm);
    }
    
    public String GetAreasName(){
        return this.areaName;
    }
    
    public void SetAreaName(String name){
        this.areaName = name;
    }
    
    public String GetAreaDescription(){
        return this.areaDescription;
    }
    
    public void SetAreaDescription(String discription){
        this.areaDescription = discription;
    }
    
    public String GetAreaImage(){
        return this.areaImage;
    }
    
    public void SetAreadImage(String image){
        this.areaImage = image;
    }
    
    public List<AreaConnectionMaker> GetListOfAreaConnections(){
        return this.areaConnections;
    }

}
