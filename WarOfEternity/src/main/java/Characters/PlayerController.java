package Characters;

import Items.Item;
import Items.ItemController;
import Map.Area;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Class PlayeController is the controlling class for every player action command
 * that occurs. Through this class every command passes and with the help of the
 * verb parsers the application can easily determine which class to be called 
 * from the specific command given by the player.
 * 
 * @author Vasilis Triantaris
 */
public class PlayerController {
    
    private String parsingDecision;
    private String nounPartOfCommand;
    private String verbPartOfCommand;
    private Enemies enemyToCombat;
    private String playerCommandBeforeBattle;
    
    //Constructor with parametres
    public PlayerController(String parsingDec, String nounPart, String verbPart){
        this.parsingDecision = parsingDec;
        this.nounPartOfCommand = nounPart;
        this.verbPartOfCommand = verbPart;
        this.enemyToCombat = new Enemies();
        this.playerCommandBeforeBattle = "";
    }

    /**
     * Method that decides which action to call by the parsing decision. Most of 
     * the parameters in this method are just send to the wright class / method
     * for the specific action command.
     * 
     * @param player    The player object.
     * @param itemList  The list of game items.
     * @param enemyController   The object of the class EnemyController.
     * @param areasList  The list of game areas.
     * @param enemyList  The list of game enemies.
     * @param docksList  The list of game dockyards.
     * @param listOfMerchants The list of game merchants.
     * @return Returns a string message that describes the result of the action.
     */
    public String PlayerMainControllingMethodForActionDecision(Player player, List<Item> itemList, 
            EnemiesController enemyController, List<Area> areasList, List<Enemies> enemyList, 
            List<DockYard> docksList, List<Merchant> listOfMerchants){
        
        String resultMessage = "";

        //if a player action is not battle or using a potion then cant excecute it while in battle
        if(enemyController.GetBattleProgressState()){
           if(!this.parsingDecision.equals("battle") && !this.verbPartOfCommand.equals("use"))
                return "You can't do this action while in battle!";
        }

        if(!enemyController.GetBattleProgressState() && this.parsingDecision.equals("battle"))
            return "There is no enemy to attack!";
        
        //if at the next area that the player wants to go has enemies and the random number is
        //inside the scale for battle process...
        int encounterChance = enemyController.GetRandomEncounterNumber();
        if((encounterChance >= 60) && (enemyController.EnemyEncounterAreaIntegrity(player, this.nounPartOfCommand, itemList)) && (this.parsingDecision.equals("direction")) && (!enemyController.GetBattleProgressState())){
                
            this.enemyToCombat = player.SetBattleData(enemyController, this.nounPartOfCommand, areasList);
            enemyController.SetBattleProgressState(true);
            this.playerCommandBeforeBattle = this.nounPartOfCommand;
            return this.enemyToCombat.GetEnemyDescription();
        }    

        //Switch-case that calls the right action method for the specific verb that the
        //user has typed.
        switch(this.parsingDecision){
            
            case "direction" :
                resultMessage = player.PlayerActionCommand(this.nounPartOfCommand, itemList);
            break;
        
            case "item" :
                ItemController ic = new ItemController(this.verbPartOfCommand, this.nounPartOfCommand, itemList);
                resultMessage = ic.ItemActionCommandProcessController(player, enemyController, this.enemyToCombat);
            break;
                
            case "transaction" :
                TransactionController tc = new TransactionController(areasList, listOfMerchants, this.nounPartOfCommand, this.verbPartOfCommand);
                resultMessage = tc.TransactionCommandProcessControll(player, docksList, itemList);
            break;
                
            case "battle" :
                resultMessage = player.AttackEnemyProcess(this.GetEnemyToBattle());
                if(!resultMessage.equals("The enemy is dead!"))
                    resultMessage += "\n"+player.AttackFromEnemyToPlayerProcess(this.GetEnemyToBattle());
                else{
                    player.BattleExperienceEarned(this.GetEnemyToBattle());
                    enemyController.SetBattleProgressState(false); 
                    resultMessage += "\n"+player.PlayerActionCommand(this.playerCommandBeforeBattle, itemList);
                }
            break;
                
            case "sail" :
                DockYardController dyc = new DockYardController(areasList, itemList);
                resultMessage = dyc.DockYardCommandActionProcess(player, docksList, this.nounPartOfCommand, this.verbPartOfCommand);
            break;
        }

        return resultMessage;
    }

    
    
    public Enemies GetEnemyToBattle(){
        return this.enemyToCombat;
    }
    
    public void SetEnemyToBattle(Enemies enemy){
        this.enemyToCombat = enemy;  
    }
    
    public void SetParsingDecision(String decision){
        this.parsingDecision = decision;
    }
    
    public void SetNounPartOfCommand(String noun){
        this.nounPartOfCommand = noun;
    }
    
    public void SetVerbPartOfCommand(String verb){
        this.verbPartOfCommand = verb;
    }
}
