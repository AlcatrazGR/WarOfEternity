package GameFileConfiguration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Vasilis Triantaris
 */
public class HelpFormInfoConfig {
    
    private final String filePath;
    private String[] sectionTitles;
       
    //Constructor
    public HelpFormInfoConfig(String path){
        this.filePath = path+"\\src\\main\\java\\View\\helpInfo.txt";
        this.sectionTitles = new String[7];
        
        this.sectionTitles[0] = "Commands";
        this.sectionTitles[1] = "Recognition";
        this.sectionTitles[2] = "NPC's";
        this.sectionTitles[3] = "Experience Mechanism";
        this.sectionTitles[4] = "Attribute Mechanism";
        this.sectionTitles[5] = "Game Map";
        this.sectionTitles[6] = "Music";
    }
    
    /**
     * Method that splits the string buffer into contents.
     * 
     * @param strBuff The string buffer that contains all the data of the text.
     * @return Returs a string array, each row is a section of the file.
     */
    private String[] SplitDataInSections(StringBuffer strBuff){
        String[] splitInSections = strBuff.toString().split("<break>");
        return splitInSections;
    }
    
    /**
     * Method that creates JSON object that hold the title of the section and the
     * section itself and then adds the object into a JSON array.
     * 
     * @return Returns a JSON array.
     */
    public JSONArray GetHelpInfoListContent(){
        JSONArray jsonHelpInfoArray = new JSONArray();
        StringBuffer fileContent = TextFileProcessing.GetHelpInfoFileContent(this.filePath);

        String[] fileSplittedInSection = this.SplitDataInSections(fileContent);

        for(int i=0; i < fileSplittedInSection.length; i++){
            JSONObject jObj = new JSONObject();
            jObj.put("title", this.sectionTitles[i].trim());
            jObj.put("content", fileSplittedInSection[i].trim());
            jsonHelpInfoArray.add(jObj);
        }

        return jsonHelpInfoArray;
    }
    
    
}
