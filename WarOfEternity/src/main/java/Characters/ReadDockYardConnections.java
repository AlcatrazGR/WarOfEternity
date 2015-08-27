
package Characters;

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
 *
 * @author Vasilhs Triantarhs
 */
public class ReadDockYardConnections {
    
    private List<DockYard> listOfDocks;
    private StringBuffer docksFileBuffer;
    private String filePath;
    
    private List<String> startingDocksList;
    private List<String> destinationDocksList;
    private List<String> shipFeeList;
    
    //Constructor
    public ReadDockYardConnections(){
        this.listOfDocks = new ArrayList();
        this.docksFileBuffer = null;
        
        this.startingDocksList = new ArrayList();
        this.destinationDocksList = new ArrayList();
        this.shipFeeList = new ArrayList();
        
        this.SetDockFilePath();
        this.GetDockYardConnectionsFileContent();
    }
    
    /**
     * Sets the path to the dockyard connection file.
     */
    private void SetDockFilePath(){
        
        String usersHome = System.getProperty("user.home");
        
        try {
            this.filePath = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\DockYardConnections.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReadEnemyDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.filePath = this.filePath.replace("%20", " ");
    }
    
    /**
     * Reads the content of the text file and puts it into a string buffer.
     */
    private void GetDockYardConnectionsFileContent(){
        
        File file = new File(this.filePath);
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
        
        this.docksFileBuffer = stringBuffer;
    }
    
    /**
     * Method that splits the string buffer into lines. Each line represents a
     * row into the table.
     * 
     * @return  Returns a table of string in which every row is a line on the text file. 
     */
    private String[] SplitTextDataOnLines(){
        String[] dataOnLines = this.docksFileBuffer.toString().split("\n");
        return dataOnLines;
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
    
    /**
     * Method that splits each line from the text file into individual fields
     * and add its field into the proper list on string data type.
     */
    public void GetTextFileColumnsToList(){
        
        String[] dataOnLines = this.SplitTextDataOnLines();
        
        for(String eachLine : dataOnLines){
            String[] splitLineOnFields = eachLine.split("@");
            
            this.AddStartingDockAreaIntoList(splitLineOnFields[0].trim());
            this.AddDestinationDockAreaIntoList(splitLineOnFields[1].trim());
            this.AddShipingFeeIntoList(splitLineOnFields[2].trim());
        }
        
    }
    
    /**
     * Adds a string into the startingDockArea list that represents the name of
     * the starting area.
     * 
     * @param data  The name of the starting area trimmed.
     */
    private void AddStartingDockAreaIntoList(String data){
        this.startingDocksList.add(data);
    }
    
    /**
     * Adds a string into the destinationDockArea list that represents the name of
     * the destination area.
     * 
     * @param data  The name of the destination area trimmed.
     */
    private void AddDestinationDockAreaIntoList(String data){
        this.destinationDocksList.add(data);
    }
    
    /**
     * Adds a string into the shipingFee list that represents the amount of
     * fee the player has to pay in order to use the ship.
     * 
     * @param data  The amount of the shipping fee  trimmed.
     */
    private void AddShipingFeeIntoList(String data){
        this.shipFeeList.add(data);
    }
    
    public List<String> GetStartAreaStringList(){
        return this.startingDocksList;
    }
    
    public List<String> GetDestinationStringList(){
        return this.destinationDocksList;
    }

    /**
     * Method that determines which area objects are used in every specific string
     * area list. It takes two parameters the string list of dock areas that can
     * either be the starting dock areas or the destination dock areas and the 
     * list of areas.
     * 
     * @param stringAreaList The string list of dock areas. It can either be starting dock areas or destination dock areas.
     * @param areasList The list of game areas.
     * @return Returns a list of areas that are either the starting dock areas or the destination dock areas.
     */
    public List<Area> GetDockAreaList(List<String> stringAreaList, List<Area> areasList){
        List<Area> dockAreas = new ArrayList();
        
        for(String eachStringArea : stringAreaList){
            for(Area eachArea : areasList){
                
                if(eachArea.GetAreasName().equals(eachStringArea.trim()))
                    dockAreas.add(eachArea);    
            }
        }

        return dockAreas;
    }
    
    /**
     * Method that creates the connection of dock yards from the two seperated 
     * area list and the shipping fee list. At the end it ends all the dockyard
     * objects into a list.
     * 
     * @param start
     * @param dest 
     */
    public void SetDockYardConnectionsToList(List<Area> start, List<Area> dest){
        
        for(int i=0; i < this.shipFeeList.size(); i++){
            DockYard dy = new DockYard(start.get(i), dest.get(i), this.ConvertStringToDouble(this.shipFeeList.get(i)));
            this.AddDockYardObjectToTheList(dy);
        }  
    }
    
    /**
     * Method that adds a dock yard object into the dock yard list.
     * 
     * @param obj The dock yard object.
     */
    private void AddDockYardObjectToTheList(DockYard obj){
        this.listOfDocks.add(obj);
    }
    
    public List<DockYard> GetDockYardList(){
        return this.listOfDocks;
    }
    
     
    
    
}
