package lab7.binarios;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Reproductor {
    private Clip clip;
    private long posicionPausa = 0;
    private boolean pausado = false;
    private String rutaActual = null;
    public void play(String ruta) {
        try {
            if (pausado && ruta.equals(rutaActual)) {
                clip.setMicrosecondPosition(posicionPausa);
                clip.start();
                pausado = false;
                return;
            }
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            posicionPausa = 0;
            pausado = false;
            rutaActual = ruta;
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(ruta));
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void pause() {
        if (clip != null && clip.isRunning()) {
            posicionPausa = clip.getMicrosecondPosition();
            clip.stop();
            pausado = true;
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            posicionPausa = 0;
            pausado = false;
        }
    }
}
