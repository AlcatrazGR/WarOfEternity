package Characters;

import GameFileConfiguration.SaveFolderConfig;
import Map.Area;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Class ReadEnemyDataModel handles the reading of the file that contains the
 * enemy data and also creates the enemy list to be used by the application.
 * 
 * @author Vasilis Triantaris
 */
public final class ReadEnemyDataModel {

    //
    //StringBuffer enemiesFileBuffer;
    
    private JSONArray jsonEnemiesArray;
    private final List<Area> gameAreas;
    
    public ReadEnemyDataModel(List<Area> areas){
        this.jsonEnemiesArray = new JSONArray();
        this.gameAreas = areas;
        
        
        /*
        this.encounterArea = new ArrayList();
        this.enemiesFileBuffer = null;  
        
        String filePath = this.SetEnemyFilePath();
        this.GetEnemyFileContent(filePath);
        */
    }
    
    
    private String GetEnemyFilePath(){
        
        String usersHome = System.getProperty("user.home");
        String pathToEnemiesResource = "";
        
        try {
            pathToEnemiesResource = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\GameEnemies.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        pathToEnemiesResource = pathToEnemiesResource.replace("%20", " ");
        
        return pathToEnemiesResource;
    }
    
    private StringBuffer GetEnemiesFileContent(){
        
        File file = new File(this.GetEnemyFilePath());
        StringBuffer stringBuffer = null;
        
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String line;
            
            //Read all the lines of the txt file and append them on string buffer variable.
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
  
        }
        catch(IOException ex){
        }
        
        return stringBuffer;
    }
    
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
    
    public void SetEnemiesFromData(){
        
        Enemies enemyObj;
        String[] dataOnLines = SplitStringBufferDataToLines(this.GetEnemiesFileContent());

        for(Area eachArea : this.gameAreas){
            
            JSONObject jObj = this.SetGroupOfEnemiesOnSpecificArea(eachArea, dataOnLines);
            this.jsonEnemiesArray.add(jObj);
        }
    }
    
    public JSONArray GetJSONEnemiesArray(){
        return this.jsonEnemiesArray;
    }
    
    
    
    
    
    
    
    

    /**
     * Method that sets the enemy data of the file into a list.
     * 
     * @return Returns the list of enemies after initializing it.
     */
    /*
    public List<Enemies> SetEnemyDataToList(){
        List<Enemies> enemyList = new ArrayList();
        String[] dataOnLines = SplitTextDataOnLines();

        try{
            for(int i=0; i<dataOnLines.length; i++){
                String[] dataIndex = dataOnLines[i].split("@");
                
                Enemies enemyObj = new Enemies(dataIndex[0].trim(), dataIndex[1].trim(), this.encounterArea.get(i), 
                        this.ConvertStringToIntegerDataType(dataIndex[3].trim()), this.ConvertStringToIntegerDataType(dataIndex[4].trim()),
                        this.ConvertStringToIntegerDataType(dataIndex[5].trim()), this.ConvertStringToDouble(dataIndex[6].trim()), 
                        this.ConvertStringToIntegerDataType(dataIndex[7].trim()) ,dataIndex[8].trim());
                
                enemyList.add(enemyObj);
            }
        }
        catch(Exception ex){
        }
        
        return enemyList;
    }
    */
    
    /**
     * Sets the list of encounter areas.
     * 
     * @param areas The list of game areas.
     */
    /*
    public void SetEnemyAreaEncounterList(List<Area> areas){
        String[] dataOnLines = SplitTextDataOnLines(); 
        
        for (String dataOnLine : dataOnLines) {
            String[] lineIndex = dataOnLine.split("@");
            for(Area eachGameArea : areas){
                if(lineIndex[2].trim().equals(eachGameArea.GetAreasName()))
                    this.AddAreaIntoTheEncounterAreaList(eachGameArea);
            }
        }
    }
    */
    
    
}
