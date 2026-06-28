
package modelo;

public class Armadura extends Objeto {

    private int defensa;

    public Armadura(String nombre, int defensa) {
        super(nombre, "Armadura");
        this.defensa = defensa;
    }

    public int getModificadorAtaque() {
        return 0;
    }

    public int getModificadorDefensa() {
        return defensa;
    }
    
    
}
