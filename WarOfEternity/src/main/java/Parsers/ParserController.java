package Parsers;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the parsing process. First it reads all the parses and 
 * inserts their data into lists after it tries to find the parsing action 
 * through the lists and understand the action command the user typed.
 * 
 * @author Thomas Liakos
 */
public class ParserController {
   
    List<String> directionVerbParser;
    List<String> itemVerbParser;
    List<String> transactionVerbParser;
    List<String> battleVerbParser;
    List<String> sailVerbParser;
    
    String nounCommand;
    String verbCommand; 
    
    ParserModel pm;
    
    //Simple Constructor
    public ParserController(){
        this.directionVerbParser = new ArrayList();
        this.itemVerbParser = new ArrayList();
        this.transactionVerbParser = new ArrayList();
        this.battleVerbParser = new ArrayList();
        this.sailVerbParser = new ArrayList();
        
        pm = new ParserModel();
        
        this.nounCommand = "";
        this.verbCommand = "";
        
        this.directionVerbParser = pm.GetListOfDirectionParser();
        this.itemVerbParser = pm.GetListOfItemParser();
        this.transactionVerbParser = pm.GetListOfTransactionParser();
        this.battleVerbParser = pm.GetListOfBattleParser();
        this.sailVerbParser = pm.GetListOfSailParser();
    }
    
    /**
     * Method that based of the verb of the command that the user has 
     * entered decides which type of action to take.
     * 
     * @param action    The action command that the user has given.
     * @return Returns a word that refers to the specific parser that the verb has been found.
     */
    public String ParserControllingMethodForActionDecision(String action){
        String parsingDecision;
       
        String[] playerActionSplitted = pm.SplitPlayerCommand(action);
        //If the command that the user has entered has more words than a noun the ...
        if(playerActionSplitted.length > 1){
            
            String result = this.PlayerActionDecider(playerActionSplitted);
            if(!result.equals(""))
                parsingDecision = result;
            else
                parsingDecision = "The first word of a command must be a verb!";
        }
        else{

            String result = this.PlayerActionDecider(playerActionSplitted);
            if(result.equals("")){
                parsingDecision = "direction";
                this.nounCommand = playerActionSplitted[0].trim();
            }
            else{
                if(result.equals("battle")){
                    this.nounCommand = playerActionSplitted[0].trim();
                    parsingDecision = "battle";
                }
                else if (result.equals("item")){
                    this.nounCommand = playerActionSplitted[0].trim();
                    parsingDecision = "item";
                }
                else
                    parsingDecision = "Unclear command given!";
            }
        }
        
        return parsingDecision;
    }
    
    /**
     * This method takes as a parameter the whole action that the player given
     * separated in parts. The the specific verb and the noun part of the command
     * the application tries to figure the action command.
     * 
     * @param playerActionSplitted  The whole action that the player given separated in parts.
     * @return Returns a string message that it will be the action type command or if the command is undefined then it will return a proper message.
     */
    private String PlayerActionDecider(String[] playerActionSplitted){
        String parsingDecision = "";
        
        //pass all the directions verbs.
        for(String eachDirection : this.directionVerbParser){
                
            //if a direction verb on the parser list is equal to the one the user typed,
            //then the action that the user wants to do is move (change area)
            if(eachDirection.trim().equalsIgnoreCase(playerActionSplitted[0])){
                parsingDecision = "direction";
                this.nounCommand = this.CreateNounWithMoreThanOneWords(playerActionSplitted); 
                this.verbCommand = playerActionSplitted[0].trim();
            }
            
        }

        //Passing all the item verbs to decide the parsing decision
        for(String eachItemCommand : this.itemVerbParser){
            if(eachItemCommand.trim().equalsIgnoreCase(playerActionSplitted[0])){
                parsingDecision = "item";
                this.nounCommand = this.CreateNounWithMoreThanOneWords(playerActionSplitted);
                this.verbCommand = playerActionSplitted[0].trim();
            }
        }
        
        //Passing all the transaction verbs to decide the parsing decision
        for(String eachTransVerb : this.transactionVerbParser){
            if(eachTransVerb.trim().equalsIgnoreCase(playerActionSplitted[0])){
                parsingDecision = "transaction";
                this.nounCommand = this.CreateNounWithMoreThanOneWords(playerActionSplitted);
                this.verbCommand = playerActionSplitted[0].trim();
            }
        }

        //Passing all the battle verbs to decide the parsing decision
        for(String eachBattleVerb : this.battleVerbParser){
            if(eachBattleVerb.trim().equalsIgnoreCase(playerActionSplitted[0])){
                parsingDecision = "battle";
                this.nounCommand = this.CreateNounWithMoreThanOneWords(playerActionSplitted);
                this.verbCommand = playerActionSplitted[0].trim();
            }
        }
        
        return parsingDecision;
    }
   
    
    //Method that returns tha noun part of the user command.
    public String GetNounOnPlayerCommand(){
        return this.nounCommand;
    }
    
    //Method that returns tha noun part of the user command.
    public String GetVerbOnPlayerCommand(){
        return this.verbCommand;
    }
    
    /**
     * Method that if the action command is consisted from more than 
     * one words then it creates the noun part from the second word to the last.
     * 
     * @param playerActionSplitted  The whole action that the player given separated in parts.
     * @return  Returns a string which is the added parts of a command if that one has more than one word in the noun part of the command.
     */
    private String CreateNounWithMoreThanOneWords(String[] splittedAction){
        String nounPart = "";
        
        for(int i=1; i<splittedAction.length; i++){
            nounPart += splittedAction[i]+" ";
        }
        
        return nounPart.trim();
    }
    
}
