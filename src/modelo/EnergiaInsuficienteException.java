
package modelo;
public class EnergiaInsuficienteException extends Exception{
    public EnergiaInsuficienteException(String nombre, String accion, int requerido, int disponible) {
        super(nombre + " no tiene energia para " + accion +
              " (requiere " + requerido + ", tiene " + disponible + ").");
    }
    
}
