package GameFileConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contains methods that are used to read the outer file's of the game.
 * Those methods can be the finding and encoding of the file paths and also the
 * reading of its content.
 * 
 * @author Vasilis Triantaris
 */
public class TextFileProcessing {
    
    private TextFileProcessing(){
    }

    /**
     * Method that reads the file content and stores it into a string buffer.
     * 
     * @return Returns a string buffer which contians the content of the file.
     */
    public static StringBuffer GetHelpInfoFileContent(String filePath){
        File file = new File(filePath);
        StringBuffer stringBuffer = null;
        
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String line;
            
            //Read all the lines of the txt file and append them on string buffer variable.
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
  
        }
        catch(IOException ex){
        }
        
        return stringBuffer;
    }   
    
    /**
     * Method that connects the full path of the text file and decodes it.
     * 
     * @param subPath The partial full path (the path after the users home).
     * @return Returns the fully decoded file path.
     */
    public static String GetTextFilePathFromUserHome(String subPath){
        
        String usersHome = System.getProperty("user.home");
        String pathToEnemiesResource = "";
        
        try {
            pathToEnemiesResource = java.net.URLDecoder.decode(usersHome+subPath, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        pathToEnemiesResource = pathToEnemiesResource.replace("%20", " ");
        
        return pathToEnemiesResource;
    }
    
}
