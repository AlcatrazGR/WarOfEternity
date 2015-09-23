package Characters;

import GameFileConfiguration.MusicConfiguration;
import Items.Item;
import Map.Area;
import java.io.Serializable;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class EnemiesController is the controlling class (since the application 
 * follows the MVC architecture) for actions based for enemies / battle.
 * 
 * @author Triantaris Vasilis
 */
public class EnemiesController implements Serializable{

    private final List<Area> areas;
    private final List<Item> items;
    private JSONArray jsonEnemiesArray;
    private boolean battleState;
    
    private BattleActionModel bam;
    
    //Constructor
    public EnemiesController(List<Area> areas, List<Item> listOfItems){
        
        this.areas = areas;
        this.items = listOfItems;
        this.jsonEnemiesArray = new JSONArray();
        this.battleState = false;
    }
    
    /**
     * Method that calls the methods which read the enemy data and implement them
     * on JSONArray data type.
     */
    public void SetEnemiesForGame(){
        ReadEnemyDataModel redm = new ReadEnemyDataModel(this.areas);
        redm.SetEnemiesFromData();
        this.jsonEnemiesArray = redm.GetJSONEnemiesArray();

    }
    
    /**
     * Control method that calls the method which renders a random integer number
     * which is used in order to trigger or not a battle.
     * 
     * @return Returns the random integer.
     */
    public int GetEncounterNumber(){
        int randomEncNum = this.bam.GetRandomEncounterNumber();
        return randomEncNum;
    }
    
    /**
     * Method that checks the integrity of the battle commands. Basically it checks
     * whether the player is in battle mode and can use item and if the player
     * is in battle mode and try to re direct to another area.
     * 
     * @param parsingDecision The parsing decision of the command.
     * @param verb The verb part of the command.
     * @return Retuens a JSON object which contains the status of the check and the eligible message for it.
     */
    public JSONObject BattleIntegrityActionCheck(String parsingDecision, String verb){
        
        JSONObject jObj = new JSONObject();
        String message = "";
        boolean status = true;
        
        //if a player action is not battle or using a potion then cant excecute it while in battle
        if(this.battleState){
           if(!parsingDecision.equals("battle") && !verb.equals("use")){
                message = "You can't do this action while in battle!";
                status = false;
           }
        }

        if(!this.battleState && parsingDecision.equals("battle")){
            message = "There is no enemy to attack!";
            status = false;
        }
        
        jObj.put("message", message);
        jObj.put("status", status);
        
        return jObj;
    }
    
    /**
     * Control method that calls the methods which triggers a battle. Thoose methods
     * are the rendering of an integer number to trigger or not a battle, getting
     * the list of enemies from the specific area and finding the eligible enemy 
     * that the player will face.
     * 
     * @param player The object that holds the player data.
     * @param noun The noun part of the command.
     * @param parsingDecision The parsing decision of the command.
     * 
     * @return Returns a JSON object which holds the eligible enemy, and the action before the battle.
     */
    public JSONObject TriggerBattleOnAreaChangeController(Player player, String noun, String parsingDecision){
        
        this.bam = new BattleActionModel(this.jsonEnemiesArray);
        int encounterPer = this.GetEncounterNumber();
        JSONObject jObj = bam.TriggerBattleOnAreaChange(player, encounterPer, this.items, noun, parsingDecision, this.battleState);
        
        return jObj;
    }
    
    
    public String BattleActionProcessController(Player player, Enemies eligibleEnemy, 
            String actionBeforeBattle, List<Item> listOfItems, MusicConfiguration mcf){
        
        String resultMessage = this.bam.AttackEnemyProcess(eligibleEnemy, player);
        
        if(!resultMessage.equals("The enemy is dead!")){
            resultMessage += "\n"+this.bam.AttackFromEnemyToPlayerProcess(eligibleEnemy, player);
            mcf.SetChangeMusicStatus(false);
        }
        else{
            player.BattleExperienceEarned(eligibleEnemy);
            this.SetBattleState(false); 
            mcf.SetChangeMusicStatus(true);
            DirectionActionModel dam = new DirectionActionModel(actionBeforeBattle, listOfItems);
            resultMessage += "\n"+dam.PlayerActionCommand(player);
        }
        
        return resultMessage;
    }
    
    public void SetBattleState(boolean state){
        this.battleState = state;
    }
    
    public boolean GetBattleState(){
        return this.battleState;
    }
    
    public JSONArray GetJSONEnemiesArray(){
        return this.jsonEnemiesArray;
    }
    

}
