package Map;

import GameFileConfiguration.SaveFolderConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contains methods to read the GameAreas text file.
 * 
 * @author Thomas Liakos
 */
public class ReadAreaFileModel {
    List<Area> areaList;
    String areaFilePath;
    
    //Simple Constructor
    public ReadAreaFileModel(){
        this.areaFilePath = "";
        this.areaList = new ArrayList<Area>();
    }
    
    /**
     * Method that gets the GameAreas file path and set it to the data member.
     * 
     * @return Returns a boolean variable that shows whether the finding of the file and getting its full path went fine.
     */
    public boolean SetFilePath(){

        String usersHome = System.getProperty("user.home");
        String pathToGameAreas = "";
        
        try {
            pathToGameAreas = java.net.URLDecoder.decode(usersHome+"\\WarOfEternity\\DataAccessObjects\\GameAreas.txt", "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SaveFolderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.areaFilePath = pathToGameAreas.replace("%20", " ");
        
        return true; 
    }
    
    /**
     * Method that gets the content of the text file and store it 
     * on a string buffer.
     * 
     * @return Returns a string buffer which represents the content of the GameAreas file.
     */
    public StringBuffer GetAreaFileConent(){
        File file = new File(this.areaFilePath);
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
     * Method that splits the string buffer to lines.
     * 
     * @param strBuf    This is the string buffer that contains the data of the GameAreas file.
     * @return Returns an array of string in which each line is a line in the GameAreas file.
     */
    public String[] SplitStringBufferDataToLines(StringBuffer strBuf){
    
        String[] dataOnLines = strBuf.toString().split("\n");
        
        return dataOnLines;
    }
    
    /**
     * Method that takes as a parameter the table where in each cell is 
     * contained the string line of the text file.
     * 
     * @param dataOnLines   The array of string that each string is a data line.
     * @return Returns a boolean variable that reports whether the array list data creation went fine.
     */
    public boolean SetAreaList(String[] dataOnLines){
        
        try{
            for(int i=0; i<dataOnLines.length; i++){
                //For each line of the text split it using the '@' charachter as a
                //separator.
                String[] lineIndex = dataOnLines[i].split("@");
            
                if(lineIndex.length == 3){
                    //Set tha data of each area object 
                    Area areaObj = new Area(lineIndex[0].trim(), lineIndex[1].trim(), lineIndex[2].trim());
                    this.areaList.add(areaObj);
                }
                //In case the user didnt enter any image
                else{
                    //Set tha data of each area object 
                    Area areaObj = new Area(lineIndex[0].trim(), lineIndex[1].trim(), "");
                    this.areaList.add(areaObj);
                }
            }
        }
        catch(Exception ex){
            return false;
        }
        
        return true;
    }
    
    //Method that return the list of areas of the game.
    public List<Area> GetAreaList(){
        return this.areaList;
    }
}
