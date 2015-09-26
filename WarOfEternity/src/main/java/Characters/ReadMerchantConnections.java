package Characters;

import GameFileConfiguration.TextFileProcessing;
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
    
    private StringBuffer merchantFileBuffer;
    
    private List<String> stringListOfMerchantArea;
    private List<String> stringListOfMerchantName;
         
    //Constructor
    public ReadMerchantConnections(){
        this.merchantFileBuffer = null;
        this.stringListOfMerchantArea = new ArrayList();
        this.stringListOfMerchantName = new ArrayList();
        
        String merchantFilePath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\MerchantConnections.txt");
        this.merchantFileBuffer = TextFileProcessing.GetHelpInfoFileContent(merchantFilePath);
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
