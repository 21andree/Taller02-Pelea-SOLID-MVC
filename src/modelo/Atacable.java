
package modelo;
public interface Atacable {
    void recibirDano(int cantidad);
    boolean estaVivo();
    int getPuntosVida();
}