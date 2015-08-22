package Characters;

import Interfaces.ICharacter;
import Map.Area;
import java.io.Serializable;

/**
 * Class Enemies initializes enemy objects in order to be used by the game 
 * battle system. This class extends the interface ICharacter to inherit its 
 * data members / methods and also extends the Serializable interface so that
 * its objects can be written in a file.
 * 
 * @author Liakos Thomas
 */
public class Enemies implements ICharacter, Serializable{

    private Area areaLocation;
    private String characterName;
    private String enemyDescription;
    private String image;
    private int damage;
    private int armor;
    
    private int enemyExperience;
    
    private int characterHealth;
    private double charactersGold;
    
     //Constructor with parametres.
    public Enemies(){
        this.characterName = "";
        this.enemyDescription = "";
        this.areaLocation = null;
        this.damage = 0;
        this.armor = 0;
        this.characterHealth = 0;
        this.charactersGold = 0.0; 
        this.enemyExperience = 0;
        this.image = "";
    }
    
    //Constructor with parametres.
    public Enemies(String name, String description, Area encounterArea, int enemyDamage, 
            int enemyArmor, int enemyHealth, double maxGoldCarrying, int exp, String enemyImage){
        
        this.characterName = name;
        this.enemyDescription = description;
        this.areaLocation = encounterArea;
        this.damage = enemyDamage;
        this.armor = enemyArmor;
        this.enemyExperience = exp;
        this.characterHealth = enemyHealth;
        this.charactersGold = maxGoldCarrying;  
        this.image = enemyImage;
    }
    
    public void SetEnemyExperience(int exp){
        this.enemyExperience = exp;
    }
    
    public int GetEnemyExperience(){
        return this.enemyExperience;
    }
    
    public void SetEnemyDescription(String description) {
        this.enemyDescription = description;
    }

    public String GetEnemyDescription() {
        return this.enemyDescription;
    }
    
    public void SetAreaLocation(Area location) {
        this.areaLocation = location;
    }

    public Area GetAreaLocation() {
        return this.areaLocation;
    }

    public void SetCharacterName(String name) {
        this.characterName = name;
    }

    public String GetCharacterName() {
        return this.characterName;
    }

    public void SetCharacterDamage(int damage) {
        this.damage = damage;
    }

    public int GetCharacterDamage() {
        return this.damage;
    }

    public void SetCharacterGold(double goldCoins) {
        this.charactersGold = goldCoins;
    }

    public double GetCharacterGold() {
        return this.charactersGold;
    }

    public void SetCharacterArmor(int armorValue) {
        this.armor = armorValue;
    }

    public int GetCharacterArmor() {
        return this.armor;
    }

    public void SetCharacterHealth(int health) {
        this.characterHealth = health;
    }

    public int GetCharacterHealth() {
        return this.characterHealth;
    }
    
    public void SetEnemyImage(String enemyImage){
        this.image = enemyImage;
    }
    
    public String GetEnemyImage(){
        return this.image;
    }
    
}
