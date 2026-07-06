package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsabilidad unica (SRP): administrar los estados alterados de un
 * personaje (buffs, incapacitaciones, dano por turno) y su duracion.
 */
public class GestorEstados {
    private List<Estado> estados;

    public GestorEstados() {
        this.estados = new ArrayList<Estado>();
    }

    public void agregar(Estado estado) {
        this.estados.add(estado);
    }

    public boolean tieneEstadoActivo(String tipo) {
        for (Estado estado : estados) {
            if (estado.getTipo().equals(tipo) && estado.estaActivo()) {
                return true;
            }
        }
        return false;
    }

    public int calcularDanoPorTurno() {
        int total = 0;
        for (Estado estado : estados) {
            if (estado.getTipo().equals("DAN_TURNO") && estado.estaActivo()) {
                total += estado.getEfecto();
            }
        }
        return total;
    }

    public List<String> reducirDuraciones() {
        List<String> expirados = new ArrayList<String>();
        List<Estado> activos = new ArrayList<Estado>();
        for (Estado estado : estados) {
            estado.reducirDuracion();
            if (estado.estaActivo()) {
                activos.add(estado);
            } else {
                expirados.add(estado.getNombre());
            }
        }
        this.estados = activos;
        return expirados;
    }
}
