
package GameFileConfiguration;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This class handles the creation of the main folder (wrapper folder) inside
 * the home directory of the computer.
 * 
 * @author Vasilhs Triantaris
 */
public class MainFolderConfig {
    
    private final String usersHome;
    
    //Constructor
    public MainFolderConfig(){
        this.usersHome = System.getProperty("user.home");
        this.MainDataFolderConfig();
    }
    
    /**
     * Method that checks whether the main folder of the game exists in the 
     * home directory of the computer. If it does then the operation stops 
     * if it doesn't it creates it. There is a possibility to throw security 
     * exception, that happens if the OS of the computer denies the application
     * to create an folders.
     */
    private void MainDataFolderConfig(){
        
        String path = "";
        try {
            path = java.net.URLDecoder.decode(this.usersHome+"\\WarOfEternity", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        path = path.replace("%20", " ");
        
        File theDir = new File(path);
        
         // if the directory does not exist, create it
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            } 
            catch(SecurityException se){
                JOptionPane.showMessageDialog(null, "Error Occurred!", 
                        "Save folder could not be created due to OS permitions,\n"
                        +"Real message : "+se, JOptionPane.OK_OPTION);
                System.exit(0);
            }  
        }
        
    }
    
    
    
}
