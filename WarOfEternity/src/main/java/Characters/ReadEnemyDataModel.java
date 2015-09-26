package Characters;

import GameFileConfiguration.TextFileProcessing;
import Map.Area;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Class ReadEnemyDataModel handles the reading of the file that contains the
 * enemy data and also creates the enemy list to be used by the application.
 * 
 * @author Vasilis Triantaris
 */
public final class ReadEnemyDataModel {

    private JSONArray jsonEnemiesArray;
    private final List<Area> gameAreas;
    
    public ReadEnemyDataModel(List<Area> areas){
        this.jsonEnemiesArray = new JSONArray();
        this.gameAreas = areas;
    }

    /**
     * Method that splits the string buffer into lines.
     * 
     * @param strBuf The string buffer that will be splitted.
     * @return Returns an array of string each string is a line fron the text file.
     */
    private String[] SplitStringBufferDataToLines(StringBuffer strBuf){
    
        String[] dataOnLines = strBuf.toString().split("\n");
        return dataOnLines;
    }
    
    /**
     * Converting the string field to integer.
     * 
     * @param data  The data to be converted.
     * @return Returns the converted data to integer format type.
     */
    private int ConvertStringToInteger(String data){
        int integerData = 0;   
        
        try{
           integerData = Integer.parseInt(data);
        }
        catch(NumberFormatException ex){
        }
        return integerData;
    }
    
    /**
     * Converting a string data type to double.
     * 
     * @param data  The data to be converted.
     * @return Returns the converted data to double data type.
     */
    private double ConvertStringToDouble(String data){
        double doubleData = 0.0;
        
        try{
           doubleData = Double.parseDouble(data);
        }
        catch(NumberFormatException ex){ 
        }
        
        return doubleData;
    }
    
    /**
     * Method that creates json object that hold the name of the area and the 
     * appropriate enemies that roam it.
     * 
     * @param eachArea Each area on the loop.
     * @param dataOnLines Array of string, each string is a line from the enemy file.
     * @return Returns a JSON object which holds the enemy data of the area.
     */
    private JSONObject SetGroupOfEnemiesOnSpecificArea(Area eachArea, String[] dataOnLines){
        
        JSONObject jObj = new JSONObject();
        List<Enemies> listOfEnemiesOnArea = new ArrayList();
        Enemies enemyObj;
        
        for(int i = 0; i < dataOnLines.length; i++){
            if(dataOnLines[i].contains(eachArea.GetAreasName())){
                
                String[] lineFields = dataOnLines[i].split("@");  
                enemyObj = new Enemies(lineFields[0].trim(), lineFields[1].trim(), eachArea, 
                    this.ConvertStringToInteger(lineFields[3].trim()), this.ConvertStringToInteger(lineFields[4].trim()),
                    this.ConvertStringToInteger(lineFields[5].trim()), this.ConvertStringToDouble(lineFields[6].trim()), 
                    this.ConvertStringToInteger(lineFields[7].trim()), lineFields[8].trim(), 
                    this.ConvertStringToInteger(lineFields[9].trim()));
                
                listOfEnemiesOnArea.add(enemyObj);
            }  
        }
 
        jObj.put("areaname", eachArea.GetAreasName());
        jObj.put("enemiesonarea", listOfEnemiesOnArea);
        
        return jObj;
    }
    
    /**
     * Method that handles the reading and the initializing of the enemies for 
     * the game.
     */
    public void SetEnemiesFromData(){
        
        Enemies enemyObj;
        String enemyFilePath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\GameEnemies.txt");
        StringBuffer enemyBuffer = TextFileProcessing.GetHelpInfoFileContent(enemyFilePath);
        
        String[] dataOnLines = SplitStringBufferDataToLines(enemyBuffer);

        for(Area eachArea : this.gameAreas){
            
            JSONObject jObj = this.SetGroupOfEnemiesOnSpecificArea(eachArea, dataOnLines);
            this.jsonEnemiesArray.add(jObj);
        }
    }
    
    //Returns the JSON enemy array.
    public JSONArray GetJSONEnemiesArray(){
        return this.jsonEnemiesArray;
    }
 
}
