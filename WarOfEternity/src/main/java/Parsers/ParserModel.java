package Parsers;

import GameFileConfiguration.SaveFolderConfig;
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
 * Class that deals with the reading of the parsers and adding their 
 * content inside lists.
 * 
 * @author Vasilis Triantaris
 */
public class ParserModel {
    
    //Data member of the class
    private final List<String> directionParser;
    private final List<String> itemParser;
    private final List<String> transactionParser;
    private final List<String> battleParser;
    private final List<String> sailParser;
    
    private String directionParserPath;
    private String itemParserPath;
    private String transParsePath; 
    private String battleParserPath;
    private String sailParserPath;  
    
    //Simple Constructor
    public ParserModel(){
        this.directionParser = new ArrayList();
        this.itemParser = new ArrayList();
        this.transactionParser = new ArrayList();
        this.battleParser = new ArrayList();
        this.sailParser = new ArrayList();
        
        this.SetPathOfTheTextDirectionsParserFile();
        this.SetPathOfTheTextItemParserFile();
        this.SetPathOfTheTextTransactionParserFile();
        this.SetPathOfTheTextBattleParserFile();
        this.SetPathOfSailParserText();
        
        this.SetDirectionParserFromDAOContent();
        this.SetItemParserFromDAOContent();
        this.SetTransactionParserFromDAOContent();
        this.SetBattleParserFromDAOContent();
        this.SetSailParserFromDAOContent();
    }
    
    /**
     * Sets the path of the battle parser
     */
    private void SetPathOfTheTextBattleParserFile(){
        
        String usersHome = System.getProperty("user.home");
        
        String pathToBattleParser = "";
        try {
            pathToBattleParser = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\BattleParser.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.battleParserPath = pathToBattleParser.replace("%20", " ");    
    }
    
    /**
     * Sets the path of the item parser.
     */
    private void SetPathOfTheTextItemParserFile(){
       
        String usersHome = System.getProperty("user.home");
        
        String pathToItemParser = "";
        try {
            pathToItemParser = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\ItemVerbParser.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.itemParserPath = pathToItemParser.replace("%20", " ");   
    }

    /**
     * Sets the path of the directions parser to the 
     * directionParserPath member.
     */
    private void SetPathOfTheTextDirectionsParserFile(){
        
        String usersHome = System.getProperty("user.home");
        
        String pathToDirectionParser = "";
        try {
            pathToDirectionParser = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\DirectionsVerbParser.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.directionParserPath = pathToDirectionParser.replace("%20", " ");
    }
    
    /**
     * Sets the path of the transaction parser.
     */
    private void SetPathOfTheTextTransactionParserFile(){

        String usersHome = System.getProperty("user.home");
        
        String pathToTransactionParser = "";
        try {
            pathToTransactionParser = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\TransactionVerbParser.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.transParsePath = pathToTransactionParser.replace("%20", " ");
    }
    
     /**
     * Sets the path of the sail parser.
     */
    private void SetPathOfSailParserText(){
        String usersHome = System.getProperty("user.home");
        
        String pathToTransactionParser = "";
        try {
            pathToTransactionParser = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\SailParser.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.sailParserPath = pathToTransactionParser.replace("%20", " ");
    }

    /**
     * Reads the BattleParser file and add its content inside a list.
     */
    private void SetBattleParserFromDAOContent(){
        File file = new File(this.battleParserPath);
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
        
        String[] dataOnLines = stringBuffer.toString().split("\n");
        for (String dataOnLine : dataOnLines) {
            this.battleParser.add(dataOnLine.trim());
        }
        
    }
    
    /**
     * Reads the directionParser file and add its content inside a list.
     */
    private void SetDirectionParserFromDAOContent(){
        File file = new File(this.directionParserPath);
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
        
        String[] dataOnLines = stringBuffer.toString().split("\n");
        for (String dataOnLine : dataOnLines) {
            this.directionParser.add(dataOnLine.trim());
        }
        
    }
    
    /**
     * Reads the Item Parser file and add its content inside a list.
     */
    private void SetItemParserFromDAOContent(){
        File file = new File(this.itemParserPath);
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
        
        String[] dataOnLines = stringBuffer.toString().split("\n");
        for (String dataOnLine : dataOnLines) {
            this.itemParser.add(dataOnLine.trim());
        }
    
    }
    
    /**
     * Sets the verbs of the transaction data access object to a string list.
     */
    private void SetTransactionParserFromDAOContent(){
        File file = new File(this.transParsePath);
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
        
        String[] dataOnLines = stringBuffer.toString().split("\n");
        for (String dataOnLine : dataOnLines) {
            this.transactionParser.add(dataOnLine.trim());
        }
    
    }
    
     /**
     * Sets the verbs of the sail data access object to a string list.
     */
    private void SetSailParserFromDAOContent(){
        File file = new File(this.sailParserPath);
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
        
        String[] dataOnLines = stringBuffer.toString().split("\n");
        for (String dataOnLine : dataOnLines) {
            this.sailParser.add(dataOnLine.trim());
        }
    }
    

    /**
     * Split the command of the player.
     *
     * @param command   The action command that the user has given.
     * @return Returns an array in which there are two cell the first for the verb part and the second for the noun part.
     */
    public String[] SplitPlayerCommand(String command){
        String[] commandIndex = command.split(" ");
        return commandIndex;
    }
    
    //Returns the list of the verbs of the direction parser text file.
    public List<String> GetListOfDirectionParser(){
        return this.directionParser;
    }
    
    //Returns the list of the verbs of the item parser text file.
    public List<String> GetListOfItemParser(){
        return this.itemParser;
    }
    
    //Returns the list of the verbs of the transaction parser text file.
    public List<String> GetListOfTransactionParser(){
        return this.transactionParser;
    }
    
    //Returns the list of the verbs of the transaction parser text file.
    public List<String> GetListOfBattleParser(){
        return this.battleParser;
    }
    
    //Returns the list of the verbs of the sail parser text file.
    public List<String> GetListOfSailParser(){
        return this.sailParser;
    }
}
