package Map;

import GameFileConfiguration.SaveFolderConfig;
import GameFileConfiguration.TextFileProcessing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contain methods that read the AreaConnections text file.
 * 
 * @author Vasilis Triantaris
 */
public class ReadAreaConnectionFileModel {
    
    String filePath;
    List<Area> areasList;
    StringBuffer strBufData;
    
    public ReadAreaConnectionFileModel(List<Area> areas){
        this.filePath = "";
        this.areasList = areas;
        this.strBufData = null;
        
        this.filePath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\GameAreaConnections.txt");
        this.strBufData = TextFileProcessing.GetHelpInfoFileContent(filePath);
    }
 
    
    /**
     * Splits the string buffer variable into lines.
     * 
     * @return Returns an array of strings in which every line is a line on the area connection file.
     */
    public String[] SplitStringBufferToLines(){
        
        String[] dataOnLines = this.strBufData.toString().split("\n");
        return dataOnLines;
    }
    
    /**
     * The first column of the text file is referred to the current areas 
     * (the area that the player is at a specific time), so it gets all 
     * of those and sets them into a list of areas.
     * 
     * @param dataOnLines   This is the array of string in which every line represent each line on the area connection file.
     * @return Returns the list of current game areas which is the first column on the file.
     */
    public List<Area> GetListOfCurrentAreas(String dataOnLines[]){
       
        List<Area> curAreasList = new ArrayList();
        
        for(int i=0; i<dataOnLines.length; i++){
            String[] dataIndex = dataOnLines[i].split("@");
            
            //Because a specific area can be used more than once in the text file
            //of connections then we need a loop that will read all the areas for
            //each line of the text. So a integer variable n is used to get the 
            //place of the area in the areasList.
            int n=0;
            for(Area eachArea : this.areasList){
                
                if(eachArea.GetAreasName().equalsIgnoreCase(dataIndex[0].trim())){
                
                    curAreasList.add(this.areasList.get(n));
                }
                
                n++;
            }
            
        }
        
        return curAreasList;
    }
    
    
    /**
     * The second column of the text file is consisted of the next areas (
     * the areas that the player can go to when he is a specific current area).
     * 
     * @param dataOnLines   This is the array of string in which every line represent each line on the area connection file.
     * @return Returns the list of Next game areas which is the second column on the file.
     */
    public List<Area> GetListOfNextAreas(String dataOnLines[]){
        
        List<Area> nextAreaList = new ArrayList();
        
        for(int i=0; i<dataOnLines.length; i++){
            String[] dataIndex = dataOnLines[i].split("@");
            
            int n=0;
            for(Area eachArea : this.areasList){
                
                if(eachArea.GetAreasName().equalsIgnoreCase(dataIndex[1].trim())){
                
                    nextAreaList.add(this.areasList.get(n));
                }
                
                n++;
            }
            
        }
        
        return nextAreaList;
    }
    
    
    /**
     * The third column of the text file is consisted of the directions 
     * for the specific current area that the user is in.
     * 
     * @param dataOnLine   This is the array of string in which every line represent each line on the area connection file.
     * @return Returns the list are directions which represents the third column in the file.
     */
    public List<String> GetAreasDirections(String[] dataOnLine){
        List<String> directions = new ArrayList();
        
        for (String line : dataOnLine) {
            String[] dataIndex = line.split("@");
            directions.add(dataIndex[2].trim());
        }
        
        return directions;
    }
    
    
}
