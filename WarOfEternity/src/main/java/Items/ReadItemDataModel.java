package Items;

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

/**
 * Class that contains methods and data members to handle the reading 
 * of GameFile and GameFileConnections texts and insert their data to lists.
 * 
 * @author Vasilis Triantaris
 */
public class ReadItemDataModel {
    
    private final List<Area> listOfAreas;
    
    private String itemFilePath;
    private String itemConnectionsFilePath;
    private StringBuffer itemBuffer;
    private StringBuffer itemConnectionsBuffer;

    public ReadItemDataModel(List<Area> areas){
        
        this.listOfAreas = areas;
        this.itemBuffer = null;
        this.itemConnectionsBuffer = null;
        this.itemFilePath = "";
        this.itemConnectionsFilePath = "";
        
        //Setting file data
        this.SetItemFilePath();
        this.SetItemConnectionsFilePath();
    }
    
    /**
     * Method that gets the GameItems file path and set it to the data member.
     */
    private void SetItemFilePath(){
        String usersHome = System.getProperty("user.home");
        String pathToItemsResource = "";
        
        try {
            pathToItemsResource = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\GameItems.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.itemFilePath = pathToItemsResource.replace("%20", " ");
    }
    
    /**
     * Method that gets the GameItemConnections file path and set it to the data member.
     */
    private void SetItemConnectionsFilePath(){
        String usersHome = System.getProperty("user.home");
        String pathToItemConnections = "";
        
        try {
            pathToItemConnections = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\GameItemConnections.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.itemConnectionsFilePath = pathToItemConnections.replace("%20", " ");
        
    }
    
    /**
     * Method that gets the content of the text file and store it on a string buffer.
     * 
     * @return Returns a string buffer which represents the content of the item file.
     */
    private void GetItemFileContent(){
        File file = new File(this.itemFilePath);
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
        
        this.itemBuffer = stringBuffer;
    }
    
    /**
     * Method that gets the content of the text file and store it on a string buffer.
     * 
     * @return Returns a string buffer which represents the content of the item connections file.
     */
    private void GetItemConnectionsFileContent(){
        File file = new File(this.itemConnectionsFilePath);
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
        
        this.itemConnectionsBuffer = stringBuffer;
    }
    
    /**
     * Method that splits the string buffer to lines.
     * 
     * @param strBuf    The string buffer that represents the content of the file
     * @return Returns an array of strings which every cell is a line in the file.
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
     * Method that sets the item data of the file into a list.
     * 
     * @return Returns the list of game items.
     */
    public List<Item> SetItemDataList(){
        List<Item> itemList = new ArrayList();
        StringBuffer strBuf = GetItemFileContent();
        String[] dataOnLines = SplitStringBufferDataToLines(strBuf);

        try{
            for(int i=0; i<dataOnLines.length; i++){
                String[] dataIndex = dataOnLines[i].split("@");
                
                if(dataIndex.length == 6){
                    Item itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(), ConvertStringToInteger(dataIndex[2].trim()), 
                            ConvertStringToDouble(dataIndex[3].trim()), ConvertStringToInteger(dataIndex[4].trim()), ConvertStringToDouble(dataIndex[5].trim()));                         
                    itemList.add(itemObj);
                }  
                else{
                    if(dataIndex[6].trim().matches("-?\\d+(\\.\\d+)?")){
                        Item itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(), ConvertStringToInteger(dataIndex[2].trim()), 
                            ConvertStringToDouble(dataIndex[3].trim()), ConvertStringToInteger(dataIndex[4].trim()), ConvertStringToDouble(dataIndex[5].trim()), ConvertStringToInteger(dataIndex[6].trim())); 
                        itemList.add(itemObj);    
                    }
                    else{
                        Item itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(), ConvertStringToInteger(dataIndex[2].trim()), 
                            ConvertStringToDouble(dataIndex[3].trim()), ConvertStringToInteger(dataIndex[4].trim()), ConvertStringToDouble(dataIndex[5].trim()), dataIndex[6].trim()); 
                        itemList.add(itemObj);
                    }
                }
                
            }
        }
        catch(Exception ex){
        }
        
        return itemList;
    }
    
    /**
     * Method processes the text of gameItemConnections and returns a 
     * list of items from the first column of the file.
     * 
     * @param listItems     The list of game items.
     * @return Returns a list of items from the first column of the item connections file
     */
    public List<Item> SetConnectionItemList(List<Item> listItems){
        List<Item> connectionsItems = new ArrayList();
        StringBuffer strBuf = this.GetItemConnectionsFileContent();
        String[] dataOnLines = SplitStringBufferDataToLines(strBuf);
        
        for(int i=0; i<dataOnLines.length; i++){
            String[] dataIndex = dataOnLines[i].split("@");
            
            for(Item eachItem : listItems){
                if(eachItem.GetItemName().equalsIgnoreCase(dataIndex[0].trim())){
                    connectionsItems.add(eachItem);
                }
            }
        }
        
        return connectionsItems;
    }
    
    /**
     * Method processes the text of gameItemConnections and returns a 
     * list of areas from the second column of the file.
     * 
     * @param listAreas The list of game areas.
     * @return Returns a list of areas that represents the second column of the item connections file.
     */
    public List<Area> SetConnectionItemAreasList(List<Area> listAreas){
        List<Area> connectionAreas = new ArrayList();
        StringBuffer strBuf = this.GetItemConnectionsFileContent();
        String[] dataOnLines = SplitStringBufferDataToLines(strBuf);
        
        for(int i=0; i<dataOnLines.length; i++){
            String[] dataIndex = dataOnLines[i].split("@");
            
            for(Area eachArea : listAreas){
                if(eachArea.GetAreasName().equalsIgnoreCase(dataIndex[1].trim())){
                    connectionAreas.add(eachArea);
                }
            }
        }
        
        return connectionAreas;
    }
    
    /**
     * Method processes the text of gameItemConnections and returns 
     * a list of strings from the third column of the file.
     * 
     * @return Returns a list of string data which represent the third column of the item connections file.
     */
    public List<String> SetConnectionItemPurposeList(){
        List<String> connectionPurpose = new ArrayList();
        StringBuffer strBuf = this.GetItemConnectionsFileContent();
        String[] dataOnLines = SplitStringBufferDataToLines(strBuf);
        
        for(int i=0; i<dataOnLines.length; i++){
            String[] dataIndex = dataOnLines[i].split("@");
            
            String itemPurpose = dataIndex[2].trim();
            connectionPurpose.add(itemPurpose);
        }
        
        return connectionPurpose;
    }
    
}
