package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsabilidad unica (SRP): administrar los objetos que posee un personaje
 * y cual de ellos esta equipado, junto con los bonus que aporta.
 */
public class Inventario {
    private List<Objeto> objetos;
    private Objeto objetoEquipado;

    public Inventario() {
        this.objetos = new ArrayList<Objeto>();
        this.objetoEquipado = null;
    }

    public void agregar(Objeto objeto) {
        this.objetos.add(objeto);
    }

    public void equipar(Objeto objeto) {
        if (this.objetos.contains(objeto)) {
            this.objetoEquipado = objeto;
        }
    }

    public int getBonusAtaque() {
        if (objetoEquipado != null) {
            return objetoEquipado.getModificadorAtaque();
        }
        return 0;
    }

    public int getBonusDefensa() {
        if (objetoEquipado != null) {
            return objetoEquipado.getModificadorDefensa();
        }
        return 0;
    }

    public Objeto getObjetoEquipado() {
        return objetoEquipado;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }
}
