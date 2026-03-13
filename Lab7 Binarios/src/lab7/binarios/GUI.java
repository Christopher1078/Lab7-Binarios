
package lab7.binarios;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI extends JFrame {
    
    PlayList archivo= new PlayList();
    Reproductor reproductor= new Reproductor();
    DefaultListModel<Cancion> modelo = new DefaultListModel<>();
    JList<Cancion> lista = new JList<>(modelo);
    JLabel imagen=new JLabel();
    JButton play=new JButton("Play");
    JButton pause=new JButton("Pause");
    JButton stop=new JButton("Stop");
    JButton agregar=new JButton("Agregar");
    JButton remover=new JButton("Remover");
    ArrayList<Cancion> canciones=new ArrayList<>();
    
    public GUI(){
        setTitle("Reproductor de Musica");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        cargarCanciones();
        JPanel controles=new JPanel();
        controles.add(play);
        controles.add(pause);
        controles.add(stop);
        controles.add(agregar);
        controles.add(remover);
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(controles, BorderLayout.SOUTH);
        add(imagen, BorderLayout.EAST);
        eventos();
    }
    
    private void cargarCanciones(){
        canciones=archivo.leer();
        for(Cancion c: canciones){
            modelo.addElement(c);
        }
    }
    
    private void eventos(){
        play.addActionListener(e-> {
           Cancion c=lista.getSelectedValue();
           if(c!=null){
               reproductor.play(c.getRutaCancion());
           }
        });
        pause.addActionListener(e-> reproductor.pause());
        stop.addActionListener(e-> reproductor.stop());
        agregar.addActionListener(e-> agregarCancion());
        remover.addActionListener(e-> eliminarCancion());
        lista.addListSelectionListener(e-> mostrarImagen());
    }
    
private void agregarCancion(){
    JTextField nombre = new JTextField();
    JTextField artista = new JTextField();
    JTextField genero = new JTextField();
    JFileChooser musica = new JFileChooser();
    int resultadoMusica = musica.showOpenDialog(this);
    if(resultadoMusica != JFileChooser.APPROVE_OPTION){
        return;
    }
    File fileMusica = musica.getSelectedFile();
    JFileChooser imagenChooser = new JFileChooser();
    int resultadoImagen = imagenChooser.showOpenDialog(this);
    if(resultadoImagen != JFileChooser.APPROVE_OPTION){
        return;
    }
    File fileImagen = imagenChooser.getSelectedFile();
    Object[] datos = {"Nombre:", nombre,"Artista:", artista,"Genero:", genero};
    int opcion = JOptionPane.showConfirmDialog(this,datos,"Datos de la Canción",JOptionPane.OK_CANCEL_OPTION);
    if(opcion != JOptionPane.OK_OPTION){
        return;
    }
    if(nombre.getText().trim().isEmpty() ||
       artista.getText().trim().isEmpty() ||
       genero.getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(this, "Debe llenar todos los campos","Error",JOptionPane.ERROR_MESSAGE);
        return;
    }
    Cancion c = new Cancion(nombre.getText(),artista.getText(),genero.getText(),fileMusica.getAbsolutePath(),fileImagen.getAbsolutePath(),0);
    canciones.add(c);
    modelo.addElement(c);
    archivo.agregar(c);
}
    
    private void eliminarCancion(){
        int indice=lista.getSelectedIndex();
        if(indice!=1){
            canciones.remove(indice);
            modelo.remove(indice);
            archivo.borrarTodo();
            for(Cancion c: canciones){
                archivo.agregar(c);
            }
            reproductor.stop();
        }
    }
    
    private void mostrarImagen(){
        Cancion c=lista.getSelectedValue();
        if(c!=null){
            ImageIcon icon=new ImageIcon(c.getRutaImagen());
            Image img=icon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
        }
    }
}
