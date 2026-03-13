
package lab7.binarios;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Reproductor {
    
    private Clip clip;
    
    public void play(String ruta){
        try{
            AudioInputStream audio=AudioSystem.getAudioInputStream(new File(ruta));
            clip=AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void pause(){
        if(clip != null){
            clip.stop();
        }
    }
    public void stop(){
       if(clip!=null){
           clip.stop();
           clip.setFramePosition(0);
       } 
    }

}
