package GameFileConfiguration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class configures the background music of the game. It contains three 
 * class attributes first is the path of the sound file, the second is the 
 * sound file it self and the third is a boolean which represents the status
 * of the sound file (whether sound is played or is stopped).
 * 
 * @author Vasilis Triantaris
 */
public class MusicConfiguration {
    
    private String soundFilePath;
    private Clip clip;
    private boolean musicStatus;
    private boolean changeMusic;
    
    //Constructor
    public MusicConfiguration(){
    }

    public void SetMusicStatus(boolean status){
        this.musicStatus = status;
    }

    public boolean GetMusicStatus(){
        return this.musicStatus;
    }
    
    public void SetChangeMusicStatus(boolean status){
        this.changeMusic = status;
    }

    public boolean GetChangeMusicStatus(){
        return this.changeMusic;
    }
    
    /**
     * Method that sets the path of the audio file.
     * 
     * @param soundFileName The name of the audio file that will be attached to the static path.
     */
    public void SetSoundFilePath(String soundFileName){
    
        String projectDir = System.getProperty("user.dir");
        String filePath = "";
        
        try {
            filePath = java.net.URLDecoder.decode(projectDir+"\\src\\main\\MusicAssets\\"+soundFileName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {   
        }
        this.soundFilePath = filePath.replace("%20", " ");    
    }
    
    /**
     * Method that plays the audio file.
     */
    public void PlaySoundFile(){

        try {
            // Open an audio input stream.
            URL url = new URL("file:\\"+this.soundFilePath);
        
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            this.clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            this.clip.open(audioIn);
            this.clip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        }
        
    }
    
    /**
     * Method that stops the music tha is played and also clears the memory of
     * the space that the file is allocating.
     */
    public void StopMusic(){
        this.clip.close();
        this.clip = null;
    }
    
    
}
