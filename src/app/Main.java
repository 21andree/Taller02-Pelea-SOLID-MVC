package app;

import controlador.JuegoControlador;
import vista.Vista;
import vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        Vista vista = new VistaConsola();
        JuegoControlador controlador = new JuegoControlador(vista);
        controlador.iniciar();
        vista.cerrar();
    }
}
