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
     * @param player The player object.
     * @param itemList The list of game items.
     * @param enemyController The object of the class EnemyController.
     * @param areasList The list of game areas.
     * @param docksList The list of game dock yards.
     * @param listOfMerchants The list of game merchants.
     * @return Returns a string message that describes the result of the action.
     */
    public String PlayerMainControllingMethodForActionDecision(Player player, List<Item> itemList, 
            EnemiesController enemyController, List<Area> areasList, List<DockYard> docksList, 
            List<Merchant> listOfMerchants){
        
        String resultMessage = "";

        //Calls the method which checks the integrity of the battle (the type of commands to be used).
        JSONObject integrJSON = enemyController.BattleIntegrityActionCheck(this.parsingDecision, this.verbPartOfCommand);
        if(!(boolean) integrJSON.get("status"))
            return (String) integrJSON.get("message");
        
        
        //Calls the method which triggers a battle on direction command.
        JSONObject battleTrigJSON = enemyController.TriggerBattleOnAreaChangeController(player, this.nounPartOfCommand, this.parsingDecision);
        if(battleTrigJSON != null && (boolean) battleTrigJSON.get("status")){
            
            enemyController.SetBattleState(true);
            this.playerCommandBeforeBattle = (String) battleTrigJSON.get("actionbeforebattle");
            Enemies eligibleEnemy = (Enemies) battleTrigJSON.get("enemy");
            this.SetEnemyToBattle(eligibleEnemy);
            return eligibleEnemy.GetEnemyDescription();
        }

        //Switch-case that calls the right action method for the specific verb that the
        //user has typed.
        switch(this.parsingDecision){
            
            case "direction" :
                DirectionActionModel dam = new DirectionActionModel(this.nounPartOfCommand, itemList);
                resultMessage = dam.PlayerActionCommand(player);
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
                
                resultMessage = enemyController.BattleActionProcessController(player, this.GetEnemyToBattle(), this.playerCommandBeforeBattle, itemList);
                
                /*
                resultMessage = player.AttackEnemyProcess(this.GetEnemyToBattle());
                if(!resultMessage.equals("The enemy is dead!"))
                    resultMessage += "\n"+player.AttackFromEnemyToPlayerProcess(this.GetEnemyToBattle());
                else{
                    player.BattleExperienceEarned(this.GetEnemyToBattle());
                    enemyController.SetBattleProgressState(false); 
                    resultMessage += "\n"+player.PlayerActionCommand(this.playerCommandBeforeBattle, itemList);
                }
                */
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
