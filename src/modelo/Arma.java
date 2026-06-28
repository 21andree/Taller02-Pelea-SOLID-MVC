
package modelo;

public class Arma extends Objeto {

    private int dano;

    public Arma(String nombre, int dano) {
        super(nombre, "Arma");
        this.dano = dano;
    }

    public int getModificadorAtaque() {
        return dano;
    }

    public int getModificadorDefensa() {
        return 0;
    }
    
}
