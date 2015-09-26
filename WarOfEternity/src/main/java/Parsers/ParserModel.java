package Parsers;

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
 * Class that deals with the reading of the parsers and adding their 
 * content inside lists.
 * 
 * @author Vasilis Triantaris
 */
public class ParserModel {
    
    //Data member of the class
    private List<String> directionParser;
    private List<String> itemParser;
    private List<String> transactionParser;
    private List<String> battleParser;
    private List<String> sailParser;

    //Simple Constructor
    public ParserModel(){
        this.directionParser = new ArrayList();
        this.itemParser = new ArrayList();
        this.transactionParser = new ArrayList();
        this.battleParser = new ArrayList();
        this.sailParser = new ArrayList();
        
        this.SetParserDataForTheGame();
    }
    
    /**
     * Method that handles the reading of all the parsers of the game.
     */
    private void SetParserDataForTheGame(){
    
        //Processes the paths of all the parsers.
        String directionParserPath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\DirectionsVerbParser.txt");
        String itemParserPath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\ItemVerbParser.txt");
        String transactionParserPath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\TransactionVerbParser.txt");
        String battleParserPath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\BattleParser.txt");
        String sailParserPath = TextFileProcessing.GetTextFilePathFromUserHome("\\WarOfEternity\\DataAccessObjects\\SailParser.txt");    
       
        //Processes the content of all the parsers.
        StringBuffer directionBuffer = TextFileProcessing.GetHelpInfoFileContent(directionParserPath);
        StringBuffer itemBuffer = TextFileProcessing.GetHelpInfoFileContent(itemParserPath);
        StringBuffer transactionBuffer = TextFileProcessing.GetHelpInfoFileContent(transactionParserPath);
        StringBuffer battleBuffer = TextFileProcessing.GetHelpInfoFileContent(battleParserPath);
        StringBuffer sailBuffer = TextFileProcessing.GetHelpInfoFileContent(sailParserPath);
        
        //Processes all the verbs in all the parsers.
        this.directionParser = this.SplitStringBufferIntoLines(directionBuffer);
        this.itemParser = this.SplitStringBufferIntoLines(itemBuffer);
        this.transactionParser = this.SplitStringBufferIntoLines(transactionBuffer);
        this.battleParser = this.SplitStringBufferIntoLines(battleBuffer);
        this.sailParser = this.SplitStringBufferIntoLines(sailBuffer);
    }

    /**
     * Method that splits each line of the parser into a single entity.
     * 
     * @param stringBuffer
     * @return 
     */
    private List<String> SplitStringBufferIntoLines(StringBuffer stringBuffer){
        List<String> parserList = new ArrayList();
        
        String[] dataOnLines = stringBuffer.toString().split("\n");
        for (String dataOnLine : dataOnLines) {
            parserList.add(dataOnLine.trim());
        }
        
        return parserList;
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
