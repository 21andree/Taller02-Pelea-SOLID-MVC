package modelo;

public class Mago extends Personaje {
    private int poderMagico;

    public Mago(String nombre, int puntosVidaMaximos, int fuerza, int defensa, int poderMagico) {
        super(nombre, puntosVidaMaximos, fuerza, defensa);
        this.poderMagico = poderMagico;
    }

    @Override
    public int obtenerDanoBase() {
        return this.fuerza + this.poderMagico;
    }

    @Override
    public int calcularDefensa() {
        return this.defensa + (this.poderMagico / 2) + getBonusDefensa();
    }

    @Override
    public String obtenerHabilidadEspecial() {
        return "Bola de Fuego";
    }

    @Override
    public int getCostoEnergiaHabilidad() {
        return 45;
    }

    @Override
    public int getCooldownHabilidad() {
        return 3;
    }

    @Override
    public void aplicarEfectoEspecial(Personaje objetivo) {
        objetivo.aplicarEstado(new Estado("Quemadura", 3, "DAN_TURNO", 5));
    }

    @Override
    public String getTipoPersonaje() {
        return "Mago";
    }
}
