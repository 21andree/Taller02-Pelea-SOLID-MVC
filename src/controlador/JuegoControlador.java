package controlador;

import modelo.Arma;
import modelo.Armadura;
import modelo.Arquero;
import modelo.EnergiaInsuficienteException;
import modelo.Guerrero;
import modelo.Mago;
import modelo.Personaje;
import vista.Vista;

import java.util.List;

public class JuegoControlador {

    private Vista vista;

    public JuegoControlador(Vista vista) {
        this.vista = vista;
    }

    public void iniciar() {
        vista.mostrarMensaje("====================================================");
        vista.mostrarMensaje("  SISTEMA DE COMBATE RPG - SIMULACION DE BATALLA");
        vista.mostrarMensaje("====================================================");

        vista.mostrarMensaje("");
        vista.mostrarMensaje("--- CONFIGURACION DE PERSONAJES ---");
        String nomG = vista.pedirTexto("Ingrese el nombre del Guerrero: ");
        Guerrero guerrero = new Guerrero(nomG, 100, 18, 10, 8);

        String nomM = vista.pedirTexto("Ingrese el nombre del Mago: ");
        Mago mago = new Mago(nomM, 90, 9, 6, 17);

        String nomA = vista.pedirTexto("Ingrese el nombre del Arquero: ");
        Arquero arquero = new Arquero(nomA, 100, 14, 8, 15);

        vista.mostrarMensaje("");
        vista.mostrarMensaje("--- CONFIGURACION DE EQUIPAMIENTO ---");
        Arma espada = new Arma("Espada Larga", 12);
        Armadura coraza = new Armadura("Coraza de Hierro", 7);
        Arma arco = new Arma("Arco Elfico", 9);

        guerrero.agregarObjeto(espada);
        guerrero.equipar(espada);
        mago.agregarObjeto(coraza);
        mago.equipar(coraza);
        arquero.agregarObjeto(arco);
        arquero.equipar(arco);

        vista.mostrarMensaje(guerrero.getNombre() + " equipa " + espada.getNombre() + " (+"
                + espada.getModificadorAtaque() + " ataque)");
        vista.mostrarMensaje(mago.getNombre() + " equipa " + coraza.getNombre() + " (+"
                + coraza.getModificadorDefensa() + " defensa)");
        vista.mostrarMensaje(arquero.getNombre() + " equipa " + arco.getNombre() + " (+"
                + arco.getModificadorAtaque() + " ataque)");

        vista.mostrarMensaje("");
        vista.mostrarMensaje("--- ESTADO INICIAL DE LOS HEROES ---");
        vista.mostrarPersonaje(guerrero);
        vista.mostrarPersonaje(mago);
        vista.mostrarPersonaje(arquero);

        Personaje[] personajes = {guerrero, mago, arquero};

        vista.mostrarMensaje("");
        vista.mostrarMensaje("--------------BATALLA 1: ESCOGA LOS PELEADORES--------------");
        Personaje p1 = null;
        Personaje p2 = null;
        boolean seleccionValida = false;
        while (!seleccionValida) {
            int opcion1 = vista.pedirOpcion("Seleccione el primer peleador (1: Guerrero, 2: Mago, 3: Arquero): ");
            int opcion2 = vista.pedirOpcion("Seleccione el segundo peleador (1: Guerrero, 2: Mago, 3: Arquero): ");
            if (opcion1 == opcion2) {
                vista.mostrarMensaje("No puede seleccionar el mismo personaje para ambos lados. Intente nuevamente.");
                continue;
            }
            p1 = seleccionarPersonaje(opcion1, personajes);
            p2 = seleccionarPersonaje(opcion2, personajes);
            if (p1 == null || p2 == null) {
                vista.mostrarMensaje("Opcion invalida. Intente nuevamente.");
                continue;
            }
            seleccionValida = true;
        }

        ejecutarCombate(p1, p2);

        vista.mostrarMensaje("");
        vista.mostrarMensaje("--- ESTADOS POST-COMBATE Y REVISION DE NIVELES ---");
        vista.mostrarPersonaje(guerrero);
        vista.mostrarPersonaje(mago);
        vista.mostrarPersonaje(arquero);

        vista.mostrarMensaje("");
        vista.mostrarMensaje("--------------BATALLA 2: EL SUPERVIVIENTE VS ARQUERO--------------");
        Personaje superviviente = guerrero;
        if (!guerrero.estaVivo()) {
            superviviente = mago;
        }
        if (!superviviente.estaVivo()) {
            vista.mostrarMensaje("Ambos contendientes iniciales cayeron en batalla.");
        } else {
            ejecutarCombate(superviviente, arquero);
        }

        vista.mostrarMensaje("");
        vista.mostrarMensaje("-----------------FIN DE LA SIMULACION-----------------");
    }

    private Personaje seleccionarPersonaje(int opcion, Personaje[] personajes) {
        if (opcion >= 1 && opcion <= personajes.length) {
            return personajes[opcion - 1];
        }
        return null;
    }

    private void ejecutarCombate(Personaje p1, Personaje p2) {
        vista.mostrarMensaje("");
        vista.mostrarMensaje("COMIENZA EL DUELO ENTRE " + p1.getNombre().toUpperCase()
                + " Y " + p2.getNombre().toUpperCase() + "!");
        vista.mostrarMensaje("Habilidad Especial de " + p1.getNombre() + ": " + p1.obtenerHabilidadEspecial());
        vista.mostrarMensaje("Habilidad Especial de " + p2.getNombre() + ": " + p2.obtenerHabilidadEspecial());
        vista.esperarEnter("Presione ENTER para procesar los turnos de combate...");

        int turno = 1;
        while (p1.estaVivo() && p2.estaVivo()) {
            vista.mostrarMensaje("--- Turno " + turno + " ---");

            realizarTurno(p1, p2);
            if (!p2.estaVivo()) {
                vista.mostrarMensaje(" " + p2.getNombre() + " ha sido derrotado!");
                break;
            }
            if (!p1.estaVivo()) {
                break;
            }

            realizarTurno(p2, p1);
            if (!p1.estaVivo()) {
                vista.mostrarMensaje(" " + p1.getNombre() + " ha sido derrotado!");
                break;
            }
            if (!p2.estaVivo()) {
                break;
            }

            vista.mostrarMensaje("Estado actual: " + p1.getNombre() + " (" + p1.getPuntosVida()
                    + " PV, " + p1.getEnergia() + " EN) | " + p2.getNombre() + " (" + p2.getPuntosVida()
                    + " PV, " + p2.getEnergia() + " EN)");
            turno++;
            vista.mostrarMensaje("");
        }

        if (p1.estaVivo()) {
            vista.mostrarMensaje("");
            vista.mostrarMensaje("GANADOR: " + p1.getNombre() + "!");
            vista.mostrarMensaje(p1.getNombre() + " recibe 100 puntos de experiencia y sube de nivel.");
            p1.ganarExperiencia(100);
        } else if (p2.estaVivo()) {
            vista.mostrarMensaje("");
            vista.mostrarMensaje("GANADOR: " + p2.getNombre() + "!");
            vista.mostrarMensaje(p2.getNombre() + " recibe 100 puntos de experiencia y sube de nivel.");
            p2.ganarExperiencia(100);
        } else {
            vista.mostrarMensaje("");
            vista.mostrarMensaje("EMPATE! Ambos personajes cayeron en combate.");
        }
    }

    private void realizarTurno(Personaje atacante, Personaje objetivo) {
        int danoVeneno = atacante.aplicarDanoPorEstados();
        if (danoVeneno > 0) {
            vista.mostrarMensaje(" -> " + atacante.getNombre() + " sufre " + danoVeneno
                    + " de dano por sus estados. (PV restantes: " + atacante.getPuntosVida() + ")");
        }

        boolean incapacitado = atacante.estaIncapacitado();

        if (atacante.tieneBuffAtaque()) {
            vista.mostrarMensaje(" -> " + atacante.getNombre() + " mantiene su ataque potenciado.");
        }

        List<String> expirados = atacante.reducirEstados();
        for (String nombreEstado : expirados) {
            vista.mostrarMensaje(" -> El estado " + nombreEstado + " ha expirado en " + atacante.getNombre());
        }

        if (!atacante.estaVivo()) {
            vista.mostrarMensaje(" " + atacante.getNombre() + " ha caido por efecto de sus estados!");
            return;
        }

        if (incapacitado) {
            vista.mostrarMensaje(" -> " + atacante.getNombre() + " esta incapacitado y pierde su turno.");
            return;
        }

        atacante.regenerarRecursos();

        if (atacante.habilidadDisponible()) {
            try {
                int danoEspecial = atacante.usarHabilidadEspecial(objetivo);
                vista.mostrarMensaje(" -> " + atacante.getNombre() + " ejecuta " + atacante.obtenerHabilidadEspecial()
                        + " causando " + danoEspecial + " de dano. (Energia: " + atacante.getEnergia()
                        + "/" + atacante.getEnergiaMaxima() + " | Recarga: " + atacante.getCooldownActual() + " turnos)");
                atacante.aplicarEfectoEspecial(objetivo);
                return;
            } catch (EnergiaInsuficienteException e) {
                vista.mostrarMensaje(" -> [SIN ENERGIA] " + e.getMessage());
                vista.mostrarMensaje("    " + atacante.getNombre() + " recurre a un ataque basico.");
            }
        } else {
            vista.mostrarMensaje(" -> [EN RECARGA] " + atacante.obtenerHabilidadEspecial() + " no esta disponible ("
                    + atacante.getCooldownActual() + " turnos restantes). Realiza un ataque basico.");
        }

        int ataque = atacante.calcularAtaque();
        int vidaAntes = objetivo.getPuntosVida();
        objetivo.recibirDano(ataque);
        int danoReal = vidaAntes - objetivo.getPuntosVida();
        vista.mostrarMensaje(" -> " + atacante.getNombre() + " realiza un ataque basico causando "
                + danoReal + " de dano efectivo.");
    }

}
