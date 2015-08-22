package Interfaces;

import Map.Area;

/**
 * Interface that sets the basic characteristics of a character object. This
 * interface is being inherited by all the classes type character such as 
 * player, enemy, merchant.
 * 
 * @author Vasilis Triantaris
 */
public interface ICharacter {
    Area areaLocation = null;
    String characterName = "";
    int damage = 0;
    int armor = 0;
    int characterHealth = 0;
    double charactersGold = 0.0;
    
    public void SetAreaLocation(Area location);
    public Area GetAreaLocation();
    
    public void SetCharacterName(String name);
    public String GetCharacterName();
    
    public void SetCharacterDamage(int damage);
    public int GetCharacterDamage();
    
    public void SetCharacterGold(double goldCoins);
    public double GetCharacterGold();
    
    public void SetCharacterArmor(int armorValue);
    public int GetCharacterArmor();
    
    public void SetCharacterHealth(int health);
    public int GetCharacterHealth();
}
