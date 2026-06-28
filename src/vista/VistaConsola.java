package vista;

import java.util.Scanner;
import modelo.Personaje;

public class VistaConsola implements Vista {

    private Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void mostrarMensaje(String texto) {
        System.out.println(texto);
    }

    @Override
    public void mostrarPersonaje(Personaje personaje) {
        String equipo = "Ninguno";
        if (personaje.getObjetoEquipado() != null) {
            equipo = personaje.getObjetoEquipado().getNombre();
        }
        System.out.println("[" + personaje.getTipoPersonaje() + "] " + personaje.getNombre()
                + " - Nivel: " + personaje.getNivel()
                + " | PV: " + personaje.getPuntosVida() + "/" + personaje.getPuntosVidaMaximos()
                + " | Energia: " + personaje.getEnergia() + "/" + personaje.getEnergiaMaxima()
                + " | Fuerza: " + personaje.getFuerza()
                + " | Defensa: " + personaje.calcularDefensa()
                + " | EXP: " + personaje.getExperiencia() + "/100"
                + " | Equipado: " + equipo);
    }

    @Override
    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    @Override
    public int pedirOpcion(String mensaje) {
        System.out.print(mensaje);
        String linea = scanner.nextLine();
        int valor = -1;
        try {
            valor = Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            valor = -1;
        }
        return valor;
    }

    @Override
    public void esperarEnter(String mensaje) {
        System.out.println(mensaje);
        scanner.nextLine();
    }

    @Override
    public void cerrar() {
        scanner.close();
    }

}
