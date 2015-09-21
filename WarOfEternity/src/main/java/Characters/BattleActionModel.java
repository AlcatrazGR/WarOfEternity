/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Characters;

import Items.Item;
import Items.ItemConnectionWithArea;
import Map.AreaConnectionMaker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class that handles the battle actions of the game. Battle actions happen 
 * whenever the player is involved into a battle with an enemy.
 * 
 * @author Vasilis Triantaris
 */
public class BattleActionModel {
    
    private JSONArray jsonEnemiesArray;
    
    //Constructor
    public BattleActionModel(JSONArray jsonArray){
        
        this.jsonEnemiesArray = jsonArray;
    }
    
    /**
     * Returns a random integer number from which it will be decided if 
     * there will be a enemy encounter.
     * 
     * @return Returns an integer number which specifies whether the player is going into battle.
     */
    public int GetRandomEncounterNumber(){
        
        Random randomNumb = new Random();
        int numb = randomNumb.nextInt(100) + 1;
        
        System.out.println("Random Num : "+numb);
        
        return numb;
    }
    
    /**
     * Method that triggers the battle mode.
     * 
     * @param player The player object.
     * @param encPercentage The random integer encounter number.
     * @param items The list of game items.
     * @param noun The noun part of the command.
     * @param parseDecision The parsing decision from the command.
     * @param battleState A boolean which represents the state of the battle.
     * 
     * @return Returns JSON object which holds the action before the battle and the enemy to combat.
     */
    public JSONObject TriggerBattleOnAreaChange(Player player, int encPercentage, List<Item> items, 
            String noun, String parseDecision, boolean battleState){

        JSONObject jObj = new JSONObject();
        boolean status = false;
        List<Enemies> enemiesOnArea = this.EnemyEncounterAreaIntegrity(player, noun, items);
        Enemies eligibleEnemy = this.GetRandomEnemyFromListOfEnemiesOnArea(enemiesOnArea, encPercentage);
        
        if(eligibleEnemy != null && parseDecision.equals("direction") && (!battleState)){
            status = true;  
        }
        
        jObj.put("status", status);
        jObj.put("actionbeforebattle", noun);
        jObj.put("enemy", eligibleEnemy);
        
        return jObj;
      
    }
    
    /**
     * Method that finds an enemy to combat from tha list of area enemies.
     * 
     * @param enemiesOnArea The list of enemies on the specific area.
     * @param encPercentage The random integer encounter number.
     * @return Returns the eligible enemy which the player will combat.
     */
    private Enemies GetRandomEnemyFromListOfEnemiesOnArea(List<Enemies> enemiesOnArea, int encPercentage){
        
        Enemies eligibleEnemy = null;
        List<Enemies> enemiesOnPercent = new ArrayList();
        for(Enemies eachEnemyOnArea : enemiesOnArea){
            
            if(encPercentage <= eachEnemyOnArea.GetEncounterPercentageRate()){
                enemiesOnPercent.add(eachEnemyOnArea);
            }
        }
        
        if(enemiesOnPercent.size() > 1 && enemiesOnPercent != null){
            Random ran = new Random();
            int ranNum = ran.nextInt(enemiesOnPercent.size());
            eligibleEnemy = enemiesOnPercent.get(ranNum);
        }
        else if(enemiesOnPercent.size() == 1 && enemiesOnPercent != null){
            eligibleEnemy = enemiesOnPercent.get(0);
        }
        
        return eligibleEnemy;
    }
    
    /**
     * Method that checks the integrity for the encounter area. Baiscaly what it
     * does is to checks whether the next area that the player wants to go is 
     * blocked in order to start a battle.
     * 
     * @param player The player object.
     * @param noun The noun part of command.
     * @param itemList The list of items.
     * @return Returns the list of enemies on the specific area.
     */
    public List<Enemies> EnemyEncounterAreaIntegrity(Player player, String noun, List<Item> itemList){
        
        JSONObject jObj = new JSONObject();
        boolean check = false;
        String directionIsBlockedMessage = "";
        List<Enemies> enemiesOnArea = new ArrayList();
        
        //For every area conneciton, if in a connection the direction of the player exists and there isn't
        //any doors / gates blocking the way then ...
        for(AreaConnectionMaker acm :  player.GetAreaLocation().GetListOfAreaConnections()){
            
            directionIsBlockedMessage = this.DirectionToNextAreaIsBlockedByItem(itemList, player, noun);
           
            if(acm.GetDirectionToOtherArea().equalsIgnoreCase(noun) && (directionIsBlockedMessage.equals(""))){
                
                enemiesOnArea = this.GetListOfEnemiesThatRoamTheNextArea(acm);
                //check = true;  
            }
                
        }  
        
        //jObj.put("status", check);
        //jObj.put("enemiesonarea", enemiesOnArea);
        
        return enemiesOnArea;
    }
    
    /**
     * Method that finds the specific list of enemies from the area that the 
     * player wants to go from the JSON array.
     * 
     * @param acm The connection object from the current area to the next.
     * @return Returns the eligible list of enemies of the area.
     */
    private List<Enemies> GetListOfEnemiesThatRoamTheNextArea(AreaConnectionMaker acm){
        
        JSONObject arrayJSONObj;
        List<Enemies> enemiesOnArea = new ArrayList();
        
        for(int i = 0; i < this.jsonEnemiesArray.size(); i++){
            
            arrayJSONObj = (JSONObject) this.jsonEnemiesArray.get(i);
            String enemyAreaName = (String) arrayJSONObj.get("areaname");
            
            if(enemyAreaName.equals(acm.GetNextArea().GetAreasName()))
                enemiesOnArea = (List<Enemies>) arrayJSONObj.get("enemiesonarea");  
        }
        
        return enemiesOnArea;
    }
    
    /**
     * This method checks whether the area that the user is in is blocked by a gate / door.
     * 
     * @param player The object that holds all the player data.
     * @return Returns a message if the next area that the user is trying to go is blocked by a door/gate. Else it returns an empty message.
     */
    public String DirectionToNextAreaIsBlockedByItem(List<Item> itemList, Player player, String noun){
        String checkMessage = "";
        
        for(Item eachItem : itemList){
            for(ItemConnectionWithArea icwa : eachItem.GetItemConnectionsWithArea()){

                //If the item in there is a object door/gate in the area that the user is in and is still
                //closed then...
                if((eachItem.GetItemType() == 4) && (icwa.GetItemUsage().equals("open") 
                        && (eachItem.GetItemValue() == 0) && (eachItem.GetBlockingDirection().equalsIgnoreCase(noun)) 
                        && (player.GetAreaLocation().GetAreasName().equals(icwa.GetConnectionWithAreaReference().GetAreasName())))){
                    checkMessage = "You cannot proceed further. The gate is blocking your path!";
                }
            }
        }
        
        return checkMessage;
    }
    
    
    
    
    
    
    
    
    
    /**
     * Method that handles the attack action from the player to the enemy.
     * 
     * @param enemyToCombat The enemy object that the user is battling.
     * @param player The object that holds the player data.
     * @return Returns a message from the attack made by the user to the enemy which displays the damage being done.
     */
    public String AttackEnemyProcess(Enemies enemyToCombat, Player player){
        String message;

        Random rand = new Random();
        int randomDamage = rand.nextInt(player.GetCharacterDamage());
        
        //The damage reduction is calculated as : dr = armor(enemy) / (armor(enemy) + damage(player))
        double damageReduction = enemyToCombat.GetCharacterArmor() / (enemyToCombat.GetCharacterArmor() + randomDamage);
        int finalDamage = (int) (randomDamage - ((randomDamage * damageReduction)/2));
        
        enemyToCombat.SetCharacterHealth(enemyToCombat.GetCharacterHealth() - finalDamage);
        message = "You damaged the enemy for " + finalDamage + " attack damage!";
        
        //if the enemy died, then process a random number for gold set the apropriate message
        if(enemyToCombat.GetCharacterHealth() <= 0){
            message = "The enemy is dead!";  
            double randomGold = 0.0 + (enemyToCombat.GetCharacterGold() - 0.0) * rand.nextDouble();
            player.SetCharacterGold(player.GetCharacterGold() + randomGold);
        }
        
        return message;
    } 
    
    /**
     * Method that handles the attack that the enemy does to the player.
     * 
     * @param enemyToCombat The enemy object that the user is battling.
     * @param player The object that holds the player data.
     * @return Returns a message from the attack made by the enemy to the user which displays the damage being done.
     */
    public String AttackFromEnemyToPlayerProcess(Enemies enemyToCombat, Player player){
        String message;
            
        Random rand = new Random();
        int randomDamage = rand.nextInt(enemyToCombat.GetCharacterDamage());
        
        //The damage reduction is calculated as : dr = armor(player) / (armor(player) + damage(enemy))
        double damageReduction = player.GetCharacterArmor() / (player.GetCharacterArmor() + enemyToCombat.GetCharacterDamage());
        int finalDamage = (int) (randomDamage - ((randomDamage * damageReduction)/2));
        
        player.SetCharacterHealth(player.GetCharacterHealth() - finalDamage);
        message = enemyToCombat.GetCharacterName()+" damaged you for "+finalDamage+" damage!";
        
        if(player.GetCharacterHealth() <= 0)
            message = "You died!";

        return message;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Method that handles the attack that the enemy does to the player.
     * 
     * @param enemyToCombat The enemy object that the user is battling.
     * @return Returns a message from the attack made by the enemy to the user which displays the damage being done.
     */
    public String AttackFromEnemyToPlayerProcess(Player player, Enemies enemyToCombat){
        String message;
            
        Random rand = new Random();
        int randomDamage = rand.nextInt(enemyToCombat.GetCharacterDamage());
        
        //The damage reduction is calculated as : dr = armor(player) / (armor(player) + damage(enemy))
        double damageReduction = player.GetCharacterArmor() / (player.GetCharacterArmor() + enemyToCombat.GetCharacterDamage());
        int finalDamage = (int) (randomDamage - ((randomDamage * damageReduction)/2));
        
        player.SetCharacterHealth(player.GetCharacterHealth() - finalDamage);
        message = enemyToCombat.GetCharacterName()+" damaged you for "+finalDamage+" damage!";
        
        if(player.GetCharacterHealth() <= 0)
            message = "You died!";

        return message;
    }
    
}
