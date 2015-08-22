package Serialization;

import Characters.Player;
import GameFileConfiguration.SaveFolderConfig;
import Items.ItemController;
import Map.MapController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles the whole process of loading the data written in the file
 * with the serialization interface and restoring them back into the game.
 * 
 * @author Vasilis Triantaris
 */
public class LoadGameData {
    
    private Player player;
    private MapController mc;    
    private ItemController ic;

    //Constructor
    public LoadGameData(){
        this.player = null;
        this.mc = null;    
        this.ic = null;
    }
    
    /**
     * Method that loads the data that have been saved in the .sav file.
     * 
     * @param fileSelected  The name of the file selected in the list of the loadGame form.
     * @exception FileNotFoundException Catches any type of error that happens if the file isn't been found.
     * @exception IOException Catches the errors that have to do with data stream between the application and external files.
     * @exception ClassNotFoundException Catches the errors that have to do with objects that handle the data streaming. 
     */
    public void LoadGameFileData(String fileSelected) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        FileInputStream fis;
        ObjectInputStream in;
        String usersHome = System.getProperty("user.home");
        
        String pathToSaveFile = "";
        try {
            pathToSaveFile = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\Saves\\"+fileSelected, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        pathToSaveFile = pathToSaveFile.replace("%20", " ");
        
        fis = new FileInputStream(pathToSaveFile);
        in = new ObjectInputStream(fis);
           
        this.player = (Player) in.readObject();
        this.mc = (MapController) in.readObject();
        this.ic = (ItemController) in.readObject();
        
        in.close();   
        
    }
    
    /**
     * Method that reads all the files inside the saves folder and return 
     * the names of each file in a list of string.
     * 
     * @return Returns a list of string that contains all the names of the saved files.
     */
    public List<String> GetSavedFileNames(){

        List<String> savedGameFiles = new ArrayList();
        String usersHome = System.getProperty("user.home");
        
        String pathToSavesFolder = "";
        try {
            pathToSavesFolder = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\Saves", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pathToSavesFolder = pathToSavesFolder.replace("%20", " ");
        
        File fileFolder = new File(pathToSavesFolder);
        File[] listOfFiles = fileFolder.listFiles();
        
        for(File file : listOfFiles){
            if(file.getName().contains(".sav"))
                savedGameFiles.add(file.getName());
        }
            
        
        return savedGameFiles;
    }
    
    /**
     * Method that checks whether the player name already registered.
     * 
     * @param playerName    The name of the player which is basically also the name of the save file.
     * @return Returns a boolean that reports whether the specific save file exists.
     */
    public boolean PlayerNameExistsAsASaveFile(String playerName){
        boolean check = false;
        List<String> savedGameFiles;
        
        savedGameFiles = this.GetSavedFileNames();
        for(String eachFileName : savedGameFiles){
        
            if(eachFileName.equalsIgnoreCase(playerName+".sav"))
                return true;  
        }
        
        return check;
    }
    
    /**
     * Method that deletes a selected saved game and return the process message.
     * 
     * @param selectedFile  The name of the save file to be deleted.
     * @return Returns a report message that indicates the delete process.
     */
    public String DeleteSavedGame(String selectedFile){

        String message = null;
        String usersHome = System.getProperty("user.home");
        String pathToSavesFile = "";
        try {
            pathToSavesFile = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\Saves\\"+selectedFile, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pathToSavesFile = pathToSavesFile.replace("%20", " ");
        
        File file = new File(pathToSavesFile);
        
        try {
            if(file.delete())
                message = "File has been successfully deleted!";
            else
                message = "Error occurred!, Could not delete file!";
   
        } catch (Exception ex) {
        }
        
        return message;
    }

    public Player GetPlayerObject(){
        return this.player;
    } 
    
    public MapController GetMapController(){
        return this.mc;
    }
    
    public ItemController GetItemController(){
        return this.ic;
    }
  
}
