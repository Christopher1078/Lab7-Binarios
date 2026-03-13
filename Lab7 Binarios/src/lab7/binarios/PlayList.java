
package lab7.binarios;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PlayList {
    
    private RandomAccessFile archivo;
    
    public PlayList(){
        try{
            archivo=new RandomAccessFile("playlista.dat","rw");
        }catch(IOException e){
           JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado");
        }
    }
    
    public void agregar(Cancion cancionNueva){
        try{
            archivo.seek(archivo.length());
            archivo.writeUTF(cancionNueva.getNombre());
            archivo.writeUTF(cancionNueva.getArtista());
            archivo.writeUTF(cancionNueva.getGenero());
            archivo.writeUTF(cancionNueva.getRutaCancion());
            archivo.writeUTF(cancionNueva.getRutaImagen());
            archivo.writeDouble(cancionNueva.getDuracion());
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado");
        }
    }
    
    public ArrayList<Cancion> leer(){
        ArrayList<Cancion> lista=new ArrayList<>();
        try{
            archivo.seek(0);
            
            while(true){
                String nombre=archivo.readUTF();
                String artista=archivo.readUTF();
                String genero=archivo.readUTF();
                String ruta=archivo.readUTF();
                String imagen=archivo.readUTF();
                double duracion=archivo.readDouble();
                
                Cancion agregarCancion=new Cancion(nombre, artista, genero, ruta, imagen, duracion);
                lista.add(agregarCancion);
            }
        }catch(EOFException e){
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "A ocurrido un error inesperado");
        }
        return lista;
    }
    
}
