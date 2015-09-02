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
    private final List<Item> listOfGameItems;
    
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
        this.listOfGameItems = new ArrayList();
        
        //Setting file data
        this.SetItemFilePath();
        this.SetItemConnectionsFilePath();
        this.GetItemFileContent();
        this.GetItemConnectionsFileContent();
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
     * Method that sets all the game items, for their specific item type. Each
     * items has each own constructor on the Item class that initializes the 
     * object. 
     */
    public void SetItemDataList(){
        Item itemObj;
        String[] dataOnLines = SplitStringBufferDataToLines(this.itemBuffer);
        
        for(int i=0; i<dataOnLines.length; i++){
           
            String[] dataIndex = dataOnLines[i].split("@");

            switch(dataIndex[2].trim()){
           
                //Case the item is consumable
                case "1" :
                    itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(),
                        this.ConvertStringToInteger(dataIndex[2].trim()), this.ConvertStringToDouble(dataIndex[3].trim()),
                        this.ConvertStringToInteger(dataIndex[4].trim()), this.ConvertStringToDouble(dataIndex[5].trim()),
                        this.ConvertStringToInteger(dataIndex[6].trim()));
                    this.AddItemIntoTheItemList(itemObj);
                break;
                   
                //Case the item is miscenelous
                case "2" :
                    itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(),
                        this.ConvertStringToInteger(dataIndex[2].trim()), this.ConvertStringToDouble(dataIndex[3].trim()),
                        this.ConvertStringToInteger(dataIndex[4].trim()), this.ConvertStringToDouble(dataIndex[5].trim()));
                    this.AddItemIntoTheItemList(itemObj);
                break;
                  
                //Case the item is a weapon
                case "3" :
                    itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(),
                        this.ConvertStringToInteger(dataIndex[2].trim()), this.ConvertStringToDouble(dataIndex[3].trim()),
                        this.ConvertStringToInteger(dataIndex[4].trim()), dataIndex[5].trim(),
                        this.ConvertStringToInteger(dataIndex[6].trim()), this.GetEligibleAreaForItem(dataIndex[7].trim()),
                        this.ConvertStringToDouble(dataIndex[8].trim()));
                    this.AddItemIntoTheItemList(itemObj);
                break;
                  
                //Case the item is a gate
                case "4" :
                    itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(),
                        this.ConvertStringToInteger(dataIndex[2].trim()), this.ConvertStringToDouble(dataIndex[3].trim()),
                        this.ConvertStringToInteger(dataIndex[4].trim()), this.ConvertStringToDouble(dataIndex[5].trim()),
                        dataIndex[6].trim());
                    this.AddItemIntoTheItemList(itemObj);
                break;
                 
                //Case the item is armor / shield
                case "5" :
                case "6" :
                    itemObj = new Item(dataIndex[0].trim(), dataIndex[1].trim(),
                        this.ConvertStringToInteger(dataIndex[2].trim()), this.ConvertStringToDouble(dataIndex[3].trim()),
                        this.ConvertStringToInteger(dataIndex[4].trim()), this.GetEligibleAreaForItem(dataIndex[5].trim()),
                        this.ConvertStringToDouble(dataIndex[6].trim()));
                    this.AddItemIntoTheItemList(itemObj);
                break;
                    
            }
            itemObj = null;
        }
  
    }
    
    /**
     * Method that finds the eligible game are that the item can be found. The
     * are of each item means the area that the merchant is located.
     * 
     * @param itemAreaName The name of the area in string form.
     * @return Returns an area object that is the eligible item area.
     */
    private Area GetEligibleAreaForItem(String itemAreaName){
        
        Area eligibleItemArea = null;
        
        for(Area eachGameArea : this.listOfAreas){
            if(eachGameArea.GetAreasName().equals(itemAreaName))
                eligibleItemArea = eachGameArea;
        }
        
        return eligibleItemArea;
    }

    //Adds a items object into the item list.
    private void AddItemIntoTheItemList(Item item){
        this.listOfGameItems.add(item);
    }
    
    public List<Item> GetListOfGameItems(){
        return this.listOfGameItems;
    }
    
    /**
     * Method that finds the eligible game item that will be used on the item
     * connections. 
     * 
     * @param itemName The name of the item.
     * @return Returns the item that is eligible on the specific item connection.
     */
    private Item GetEligibleGameItem(String itemName){
        
        Item eligibleItem = null;
        
        for(Item eachItem : this.listOfGameItems){
            if(eachItem.GetItemName().equals(itemName))
                eligibleItem = eachItem;
        }
        
        return eligibleItem;
    }
    
    /**
     * Method that sets in each item on connection text file its eligible connection
     * with the specific area.
     */
    public void SetItemConnectionMainMethod(){
        
        List<Item> connectionItems = new ArrayList();
        List<Area> connectionAreas = new ArrayList();
        List<String> connectionItemPurpose = new ArrayList();
        
        String[] dataOnLines = this.SplitStringBufferDataToLines(this.itemConnectionsBuffer);
        for(int i=0; i < dataOnLines.length; i++){
            
            String[] dataIndex = dataOnLines[i].split("@");
            
            connectionItems.add(this.GetEligibleGameItem(dataIndex[0].trim()));
            connectionAreas.add(this.GetEligibleAreaForItem(dataIndex[1].trim()));
            connectionItemPurpose.add(dataIndex[2].trim());
        }
        
        //For each item on connection, item area and connection purpose a connection
        //is made and it is added on the eligible item.
        for(int i=0; i<connectionItems.size(); i++){
            SetItemConnectionWithAreas(connectionItems.get(i), connectionAreas.get(i), connectionItemPurpose.get(i));
        }

    }
    
    /**
     * Method that sets the connections of the items.
     *
     * @param itemRef   The specific item with which a connection is going to be made.
     * @param areaConnection    The area that the item is going to be connected.
     * @param itemPuropose  The purpose of the connection (for example pick).
     */
    private void SetItemConnectionWithAreas(Item itemRef, Area areaConnection, String itemPurpose){
       
        ItemConnectionWithArea icwa = new ItemConnectionWithArea(areaConnection, itemRef, itemPurpose);
       
        for(Item eachItem : this.listOfGameItems){
            if(eachItem.GetItemName().equalsIgnoreCase(itemRef.GetItemName())){
                eachItem.AddItemConnectionWithAreaToList(icwa);
            }
        }    
    }

  
}
