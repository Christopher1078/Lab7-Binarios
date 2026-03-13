
package lab7.binarios;

public class Cancion{
    
    private String nombre, artista, genero, rutaCancion, rutaImagen;
    private double duracion;
    
    public Cancion(String nombre, String artista, String genero, String rutaCancion, String rutaImagen, double duracion){
        this.nombre=nombre;
        this.artista=artista;
        this.genero=genero;
        this.rutaCancion=rutaCancion;
        this.duracion=duracion;
        this.rutaImagen=rutaImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public String getRutaCancion() {
        return rutaCancion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public double getDuracion() {
        return duracion;
    }
    
    @Override
    public String toString(){
        return "Cancion: "+nombre + " Artista: "+ artista+ " Genero: "+genero;
    }
}
