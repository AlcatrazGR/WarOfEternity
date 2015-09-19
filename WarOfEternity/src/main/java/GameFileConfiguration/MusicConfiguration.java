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
    
    //Constructor
    public MusicConfiguration(){
        this.SetSoundFilePath();
    }
    
    //Method that sets the status of the music file.
    public void SetMusicStatus(boolean status){
        this.musicStatus = status;
    }
    
    //Method that returns the music file status.
    public boolean GetMusicStatus(){
        return this.musicStatus;
    }
    
    
    private void SetSoundFilePath(){
    
        String projectDir = System.getProperty("user.dir");
        String filePath = "";
        
        try {
            filePath = java.net.URLDecoder.decode(projectDir+"\\src\\main\\MusicAssets\\outdoor1.wav", "UTF-8");
        } catch (UnsupportedEncodingException ex) {   
        }
        this.soundFilePath = filePath.replace("%20", " ");    
    }
    
    public void PlayOutDoorSoundFile(){

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
    
    public void StopSoundFile(){
        this.clip.stop();
    }
    
    
    
}
