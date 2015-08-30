
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
 * @author Vasilis Triantaris
 */
public class ReadMerchantConnections {
    
    private String filePath;
    private StringBuffer merchantFileBuffer;
    
    private List<String> stringListOfMerchantArea;
    private List<String> stringListOfMerchantName;
            
    public ReadMerchantConnections(){
        this.filePath = "";
        this.merchantFileBuffer = null;
        this.stringListOfMerchantArea = new ArrayList();
        this.stringListOfMerchantName = new ArrayList();
        
        this.SetDockFilePath();
        this.GetMerchantConnectionsFileContent();
    }
    
    /**
     * Sets the path to the merchant connection file.
     */
    private void SetDockFilePath(){
        
        String usersHome = System.getProperty("user.home");
        
        try {
            this.filePath = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\MerchantConnections.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ReadEnemyDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.filePath = this.filePath.replace("%20", " ");
    }
    
    /**
     * Reads the content of the text file and puts it into a string buffer.
     */
    private void GetMerchantConnectionsFileContent(){
        
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
        
        this.merchantFileBuffer = stringBuffer;
    }
    
    /**
     * Method that splits the string buffer into lines. Each line represents a
     * row into the table.
     * 
     * @return  Returns a table of string in which every row is a line on the text file. 
     */
    private String[] SplitTextDataOnLines(){
        String[] dataOnLines = this.merchantFileBuffer.toString().split("\n");
        return dataOnLines;
    }
    
    /**
     * Converting a string data type to int.
     * 
     * @param data  The data to be converted.
     * @return Returns the converted data to integer data format.
     */
    private double ConvertStringToInteger(String data){
        double intData = 0.0;
        
        try{
           intData = Integer.parseInt(data);
        }
        catch(NumberFormatException ex){
        }
        
        return intData;
    }
    
    /**
     * Method that splits each line from the text file into individual fields
     * and add its field into the proper list on string data type.
     */
    public void GetTextFileColumnsToList(){
        
        String[] dataOnLines = this.SplitTextDataOnLines();
        
        for(String eachLine : dataOnLines){
            String[] splitLineOnFields = eachLine.split("@");
            
            this.AddStringMerchantAreaNameIntoList(splitLineOnFields[0].trim());
            this.AddStringMerchantNameIntoList(splitLineOnFields[1].trim());
        }  
    }
    
    /**
     * Method that adds a string value into the list that holds the area name
     * that merchants are located.
     * 
     * @param data The data to be added.
     */
    private void AddStringMerchantAreaNameIntoList(String data){
        this.stringListOfMerchantArea.add(data);
    }
    
    /**
     * Method that adds a string value into the list that holds the name of 
     * the merchants.
     * 
     * @param data The data to be added.
     */
    private void AddStringMerchantNameIntoList(String data){
        this.stringListOfMerchantName.add(data);
    }
    
    /**
     * Method that finds all the areas that merchants are connected to.
     * 
     * @param areas The list of game areas.
     * @return Returns the list of areas that merchants are located in.
     */
    public List<Area> GetAreasAssociatedWithTheMerchants(List<Area> areas){
        
        List<Area> listOfMerchantAreas = new ArrayList();
        
        for(String eachAreaString : this.stringListOfMerchantArea){
            for(Area eachGameArea : areas){
          
                if(eachGameArea.GetAreasName().equals(eachAreaString))
                    listOfMerchantAreas.add(eachGameArea);
            }
        }
        
        return listOfMerchantAreas;
    }

    /**
     * Method that sets the merchant data into a merchant list.
     * 
     * @param areasOfMerchants The list of areas that the merchants are located.
     * @return Returns the list of merchants.
     */
    public List<Merchant> SetMerchantList(List<Area> areasOfMerchants){
        
        List<Merchant> listOfMerchants = new ArrayList();
        for(int i=0; i < this.stringListOfMerchantName.size(); i++){
            
            Merchant merchantObj = new Merchant(areasOfMerchants.get(i), this.stringListOfMerchantName.get(i), 0);
            listOfMerchants.add(merchantObj);
        }
        
        return listOfMerchants;
    }
    
}
