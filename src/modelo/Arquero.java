package modelo;

public class Arquero extends Personaje {
    private int precision;

    public Arquero(String nombre, int puntosVidaMaximos, int fuerza, int defensa, int precision) {
        super(nombre, puntosVidaMaximos, fuerza, defensa);
        this.precision = precision;
    }

    @Override
    public int obtenerDanoBase() {
        return this.fuerza + (this.precision / 2);
    }

    @Override
    public int calcularDefensa() {
        return this.defensa + (this.precision / 4) + getBonusDefensa();
    }

    @Override
    public String obtenerHabilidadEspecial() {
        return "Lluvia de Flechas";
    }

    @Override
    public int getCostoEnergiaHabilidad() {
        return 30;
    }

    @Override
    public int getCooldownHabilidad() {
        return 2;
    }

    @Override
    public void aplicarEfectoEspecial(Personaje objetivo) {
        objetivo.aplicarEstado(new Estado("Aturdimiento", 2, "INCAPACITAR", 0));
    }

    @Override
    public String getTipoPersonaje() {
        return "Arquero";
    }
}
