package GameFileConfiguration;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This class configures the folder that the saves of the game will reside.
 * Its locates on the user folder of each personal computer and its compatible
 * with every operating system. If the OS has specific security permissions 
 * that forbid the creation of a folder then a exception will be made and shown
 * to the user, after that the application closes. 
 * 
 * @author Vasilis Triantarhs
 */
public class SaveFolderConfig {
    
    private final String usersHome;
    
    //Constructor that initializes the path of the home folder.
    public SaveFolderConfig(){
        this.usersHome = System.getProperty("user.home");
        this.SaveFolderConfigurationMethod();
    }
    
    /**
     * Method that configures the save folder inside the home directory.
     */
    private void SaveFolderConfigurationMethod(){
        String path = "";
        try {
            path = java.net.URLDecoder.decode(this.usersHome+"\\WarOfEternity\\Saves", "UTF-8");
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
