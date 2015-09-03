/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Characters;

import java.util.Random;

/**
 *
 * @author Vasilis Triantaris
 */
public class BattleActionModel {
    
    public BattleActionModel(){
    
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
