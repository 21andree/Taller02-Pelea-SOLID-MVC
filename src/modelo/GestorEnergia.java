package modelo;

/**
 * Responsabilidad unica (SRP): administrar la energia y el tiempo de recarga
 * (cooldown) de la habilidad especial de un personaje.
 */
public class GestorEnergia {
    private static final int REGENERACION_POR_TURNO = 15;

    private int energia;
    private int energiaMaxima;
    private int cooldownActual;

    public GestorEnergia(int energiaMaxima) {
        this.energiaMaxima = energiaMaxima;
        this.energia = energiaMaxima;
        this.cooldownActual = 0;
    }

    public boolean tieneEnergia(int costo) {
        return this.energia >= costo;
    }

    public void consumir(int costo) {
        this.energia -= costo;
        if (this.energia < 0) {
            this.energia = 0;
        }
    }

    public void iniciarCooldown(int turnos) {
        this.cooldownActual = turnos;
    }

    public boolean habilidadDisponible() {
        return this.cooldownActual == 0;
    }

    public void regenerar() {
        if (this.energia < this.energiaMaxima) {
            this.energia += REGENERACION_POR_TURNO;
            if (this.energia > this.energiaMaxima) {
                this.energia = this.energiaMaxima;
            }
        }
        if (this.cooldownActual > 0) {
            this.cooldownActual--;
        }
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public int getCooldownActual() {
        return cooldownActual;
    }
}
