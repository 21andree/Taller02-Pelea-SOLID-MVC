package modelo;

public class Guerrero extends Personaje {
    private int fuerzaEscudo;

    public Guerrero(String nombre, int puntosVidaMaximos, int fuerza, int defensa, int fuerzaEscudo) {
        super(nombre, puntosVidaMaximos, fuerza, defensa);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    @Override
    public int obtenerDanoBase() {
        return this.fuerza + this.fuerzaEscudo;
    }

    @Override
    public int calcularDefensa() {
        return this.defensa + this.fuerzaEscudo + getBonusDefensa();
    }

    @Override
    public String obtenerHabilidadEspecial() {
        return "Carga con Escudo";
    }

    @Override
    public int getCostoEnergiaHabilidad() {
        return 25;
    }

    @Override
    public int getCooldownHabilidad() {
        return 2;
    }

    @Override
    public void aplicarEfectoEspecial(Personaje objetivo) {
        this.aplicarEstado(new Estado("Furia de Batalla", 3, "BUFF", 0));
    }

    @Override
    public String getTipoPersonaje() {
        return "Guerrero";
    }
}
