package lab7.binarios;
import java.io.File;
import javax.sound.sampled.AudioFormat;
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
            AudioInputStream audioOriginal = AudioSystem.getAudioInputStream(new File(ruta));
            AudioInputStream audioFinal;
            AudioFormat formatoOriginal = audioOriginal.getFormat();
            if (formatoOriginal.getEncoding().toString().startsWith("MPEG")) {
                AudioFormat formatoPCM = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    formatoOriginal.getSampleRate(),
                    16,
                    formatoOriginal.getChannels(),
                    formatoOriginal.getChannels() * 2,
                    formatoOriginal.getSampleRate(),
                    false
                );
                audioFinal = AudioSystem.getAudioInputStream(formatoPCM, audioOriginal);
            } else {
                audioFinal = audioOriginal;
            }
            clip = AudioSystem.getClip();
            clip.open(audioFinal);
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
        } else if (clip != null && pausado) {
            clip.setMicrosecondPosition(posicionPausa);
            clip.start();
            pausado = false;
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

    public long getMicrosegundosTranscurridos() {
        if (clip != null) {
            return clip.getMicrosecondPosition();
        }
        return 0;
    }

    public long getMicrosegundosTotales() {
        if (clip != null) {
            return clip.getMicrosecondLength();
        }
        return 0;
    }

    public boolean estaPausado() {
        return pausado;
    }

    public void cerrar() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
