package Characters;

import Map.Area;
import Parsers.ParserModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class ReadEnemyDataModel handles the reading of the file that contains the
 * enemy data and also creates the enemy list to be used by the application.
 * 
 * @author Vasilis Triantaris
 */
public final class ReadEnemyDataModel {

    List<Area> encounterArea;
    StringBuffer enemiesFileBuffer;
    
    public ReadEnemyDataModel(){
        this.encounterArea = new ArrayList();
        this.enemiesFileBuffer = null;  
        
        String filePath = this.SetEnemyFilePath();
        this.GetEnemyFileContent(filePath);
    }
    
    /**
     * Method that creates the full path of the enemies file resource, and 
     * replaces any unwanted characters (happens when a sub directory has a 
     * name different than english ).
     * 
     * @return Returns the full path of the enemies file.
     */
    private String SetEnemyFilePath(){

        String usersHome = System.getProperty("user.home");
        String pathToEnemiesResource = "";
        try {
            pathToEnemiesResource = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\GameEnemies.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReadEnemyDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        pathToEnemiesResource = pathToEnemiesResource.replace("%20", " ");
        
        return pathToEnemiesResource;
    }
    
    /**
     * Method that gets the content of the text file and store it on a string buffer.
     * 
     * @param enemiesFilePath   The enemies file path.
     */
    public void GetEnemyFileContent(String enemiesFilePath){
        File file = new File(enemiesFilePath);
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
        
        this.enemiesFileBuffer = stringBuffer;
    }
    
    /**
     * Splitting the string buffer to lines.
     * 
     * @return Returns an array of string in which every cell is a line data of an enemy.
     */
    private String[] SplitTextDataOnLines(){
        String[] dataOnLines = this.enemiesFileBuffer.toString().split("\n");
        return dataOnLines;
    }
    
    /**
     * Converting a string data type to integer.
     * 
     * @param data The data to be converted.
     * @return Returns the converted data to integer data format.
     */
    private int ConvertStringToIntegerDataType(String data){
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
     * @return Returns the converted data to double data format.
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
    
    private void AddAreaIntoTheEncounterAreaList(Area area){
        this.encounterArea.add(area);
    }
    
    /**
     * Method that sets the enemy data of the file into a list.
     * 
     * @return Returns the list of enemies after initializing it.
     */
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
    
    /**
     * Sets the list of encounter areas.
     * 
     * @param areas The list of game areas.
     */
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
    
    
}
