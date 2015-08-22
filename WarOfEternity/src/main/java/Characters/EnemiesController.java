package Characters;

import Items.Item;
import Map.Area;
import Map.AreaConnectionMaker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class EnemiesController is the controlling class (since the application 
 * follows the MVC architecture) for actions based for enemies / battle.
 * 
 * @author Thomas Liakos
 */
public class EnemiesController implements Serializable{
    
    private List<Enemies> enemiesList;
    private final List<Area> areas;
    private boolean battleInProgress;
    
    public EnemiesController(List<Area> areas){
        this.enemiesList = new ArrayList();
        this.areas = areas;
        this.battleInProgress = false;
    }
    
    /**
     * Sets the data for the enemies. Those are the list of all the game enemies
     * and the encounter areas that the enemies can be found in.
     */
    public void SetGameEnemiesData(){
        ReadEnemyDataModel redm = new ReadEnemyDataModel();
        redm.SetEnemyAreaEncounterList(this.areas);
        this.enemiesList = redm.SetEnemyDataToList();
    }
    
    /**
     * Returns a random integer number from which it will be decided if 
     * there will be a enemy encounter.
     * @return Returns an integer number which specifies whether the player is going into battle.
     */
    public int GetRandomEncounterNumber(){
        
        Random randomNumb = new Random();
        int numb = randomNumb.nextInt(100) + 1;
        
        return numb;
    }
    
    /**
     * Checks whether the area that the user is about to travel has enemies to
     * encounter.
     * 
     * @param player    The object of the player.
     * @param playerCommand     The command given by the user.
     * @param itemList  The list of all the game items.
     * @return  Returns a boolean that defines whether the next area has enemies.
     */
    public boolean EnemyEncounterAreaIntegrity(Player player, String playerCommand, List<Item> itemList){
        boolean check = false;
        String directionIsBlockedMessage;
        
        //For every area conneciton, if in a connection the direction of the player exists and there isn't
        //any doors / gates blocking the way then ...
        for(AreaConnectionMaker acm :  player.GetAreaLocation().GetListOfAreaConnections()){
            directionIsBlockedMessage = player.DirectionToNextAreaIsBlockedByItem(itemList, acm, playerCommand);
            if(acm.GetDirectionToOtherArea().equalsIgnoreCase(playerCommand) && (directionIsBlockedMessage.equals(""))){
                
                //If the area that the user is about to travel has enemies
                for(Enemies eachGameEnemy : this.enemiesList){
                    if(eachGameEnemy.GetAreaLocation().GetAreasName().equals(acm.GetNextArea().GetAreasName()))
                        check = true;
                }
            }
        }
        
        return check;
    }
    
    public void SetBattleProgressState(boolean state){
        this.battleInProgress = state;
    }
    
    public boolean GetBattleProgressState(){
        return this.battleInProgress;
    }

    public List<Enemies> GetGameEnemyList(){
        return this.enemiesList;
    }
    
}
