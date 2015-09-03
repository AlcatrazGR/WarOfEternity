package Characters;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Vasilis Triantaris
 */
public class CaptainActionModel {
    
    List<DockYard> listOfDocks;
    
    public CaptainActionModel(List<DockYard> docks){
        this.listOfDocks = docks;
    }
 
    /**
     * Method that checks whether there is a person to sail on a different location.
     * If there is then a apropriate message is created which shows the destionation
     * and the fee required. If it doesnt exist then also a message is created.
     * 
     * @param player The player object.
     * @return Returns the message created.
     */
    public JSONObject TalkToCaptainProcess(Player player){

        String message = "There is no dockyard in this place!";
        boolean status = false;
        
        List<DockYard> listOfDocksThatAreConnectedToArea = new ArrayList();

        for(DockYard eachDock : this.listOfDocks){
            if(eachDock.GetStartingDockLocation().GetAreasName().equals(player.GetAreaLocation().GetAreasName())){
                if(eachDock.GetSaillingFee() != 0.0)
                    message = "I can get you to "+ eachDock.GetDestinationDockLocation().GetAreasName() +
                        " for "+ eachDock.GetSaillingFee() +" gold coins.";
                
                status = true;
                listOfDocksThatAreConnectedToArea.add(eachDock);
            }     
        }

        JSONObject jObj = new JSONObject();
        jObj.put("message", message);
        jObj.put("status", status);
        jObj.put("docklist", listOfDocksThatAreConnectedToArea);
        
        return jObj;
    }
    
    
}
