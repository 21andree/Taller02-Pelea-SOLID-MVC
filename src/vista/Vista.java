
package vista;
import modelo.Personaje;
public interface Vista {
    void mostrarMensaje(String texto);
    void mostrarPersonaje(Personaje personaje);
    String pedirTexto(String mensaje);
    int pedirOpcion(String mensaje);
    void esperarEnter(String mensaje);
    void cerrar();
}
