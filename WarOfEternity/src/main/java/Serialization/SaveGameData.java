package Serialization;

import Characters.Player;
import GameFileConfiguration.SaveFolderConfig;
import Items.ItemController;
import Map.MapController;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles the whole process of saving the data of the classes that
 * have inherited the Serialization interface. 
 * 
 * @author Thomas Liakos
 */
public class SaveGameData {
  
    private Player player;
    private MapController mc;    
    private ItemController ic;

    //Constructor
    public SaveGameData(Player playerObj, MapController mcObj, ItemController icObj){
        this.player = playerObj;
        this.mc = mcObj;    
        this.ic = icObj;
    }
    
    /**
     * Method that saves the data of the specific game.
     */
    public void SavePlayerData(){

        String usersHome = System.getProperty("user.home");
        
        String saveFilePath = "";
        try {
            saveFilePath = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\Saves\\"+this.player.GetCharacterName()+".sav", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        saveFilePath = saveFilePath.replace("%20", " ");
        
        try{
            FileOutputStream fileOut = new FileOutputStream(saveFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(this.player);
            out.writeObject(this.mc);
            out.writeObject(this.ic);

            out.close();
            fileOut.close();
        }
        catch(IOException i){
        }
      
    }  
    
}
