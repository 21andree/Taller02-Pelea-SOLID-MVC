package modelo;

/**
 * Responsabilidad unica (SRP): administrar la experiencia y el nivel de un
 * personaje. La mejora de estadisticas al subir de nivel queda a cargo del
 * propio personaje.
 */
public class Progresion {
    private static final int EXP_PARA_SUBIR_NIVEL = 100;

    private int nivel;
    private int experiencia;

    public Progresion() {
        this.nivel = 1;
        this.experiencia = 0;
    }

    /**
     * Acumula experiencia y sube de nivel si se alcanza el umbral.
     *
     * @return true si el personaje subio de nivel
     */
    public boolean ganarExperiencia(int exp) {
        this.experiencia += exp;
        if (this.experiencia >= EXP_PARA_SUBIR_NIVEL) {
            this.nivel++;
            this.experiencia -= EXP_PARA_SUBIR_NIVEL;
            return true;
        }
        return false;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }
}
