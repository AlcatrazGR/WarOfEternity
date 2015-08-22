
package GameFileConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the creation of the resource folder and the copy of
 * its files from the main project directory into the new resource folder 
 * of the home directory.
 *
 * @author Vasilhs Triantaris
 */
public class ResourceFolderConfig {
    
    private final String usersHome;
    private final String projectFolder;
    
    public ResourceFolderConfig(){     
        this.usersHome = System.getProperty("user.home");
        this.projectFolder = System.getProperty("user.dir");
        this.ResourceFolderConfigMethod();
    }
    
    /**
     * Method that handles the copy - paste of the resource folder on the 
     * project directory into the resource folder of the home directory.
     */
    private void ResourceFolderConfigMethod(){

        final String sourceFold = this.SourcePathDecode();
        final String destinationFold = this.DestinationPathDecode();

        File source = new File(sourceFold);
        File dest = new File(destinationFold);
        
        if(!dest.exists()){
            try {
                FileUtilities.copyDirectory(source, dest);
            } catch (IOException e) {
                
            }
        }
        
    }
    
    /**
     * Method that handles the decode of the source file path.
     * 
     * @return Returns the full path of the source file cleaned from every unwanted character.
     */
    private String SourcePathDecode(){
        
        String path = "";
        try {
            path = java.net.URLDecoder.decode(this.projectFolder+"\\src\\main\\java\\DataAccessObjects", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        path = path.replace("%20", " ");
        return path;
        
    }
 
    /**
     * Method that handles the decode of the destination file path.
     * 
     * @return Returns the full path of the destination file cleaned from every unwanted character.
     */
    private String DestinationPathDecode(){
        
        String path = "";
        try {
            path = java.net.URLDecoder.decode(this.usersHome+"\\WarOfEternity\\DataAccessObjects", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        path = path.replace("%20", " ");
        return path;
        
    }
    
}
