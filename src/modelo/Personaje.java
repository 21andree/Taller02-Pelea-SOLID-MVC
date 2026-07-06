package modelo;

import java.util.List;

/**
 * Clase base de los personajes del juego.
 *
 * Aplicacion del SRP (Principio de Responsabilidad Unica): esta clase se
 * concentra en las estadisticas y acciones de combate del personaje, y delega
 * las demas responsabilidades en clases colaboradoras:
 * - {@link Inventario}: objetos y equipamiento.
 * - {@link GestorEstados}: estados alterados y su duracion.
 * - {@link GestorEnergia}: energia y recarga de la habilidad especial.
 * - {@link Progresion}: experiencia y nivel.
 */
public abstract class Personaje implements Atacable {
    protected String nombre;
    protected int puntosVida;
    protected int puntosVidaMaximos;
    protected int fuerza;
    protected int defensa;

    private Inventario inventario;
    private GestorEstados gestorEstados;
    private GestorEnergia gestorEnergia;
    private Progresion progresion;

    private static final int ENERGIA_MAXIMA_INICIAL = 100;
    private static final int VIDA_POR_NIVEL = 20;
    private static final int FUERZA_POR_NIVEL = 5;
    private static final int DEFENSA_POR_NIVEL = 3;

    public Personaje(String nombre, int puntosVidaMaximos, int fuerza, int defensa) {
        this.nombre = nombre;
        this.puntosVidaMaximos = puntosVidaMaximos;
        this.puntosVida = puntosVidaMaximos;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.inventario = new Inventario();
        this.gestorEstados = new GestorEstados();
        this.gestorEnergia = new GestorEnergia(ENERGIA_MAXIMA_INICIAL);
        this.progresion = new Progresion();
    }

    public abstract int obtenerDanoBase();

    public abstract int calcularDefensa();

    public abstract String obtenerHabilidadEspecial();

    public abstract int getCostoEnergiaHabilidad();

    public abstract int getCooldownHabilidad();

    public abstract void aplicarEfectoEspecial(Personaje objetivo);

    public abstract String getTipoPersonaje();

    // ------------------- Inventario (delegacion) -------------------

    public void agregarObjeto(Objeto objeto) {
        this.inventario.agregar(objeto);
    }

    public void equipar(Objeto objeto) {
        this.inventario.equipar(objeto);
    }

    protected int getBonusAtaque() {
        return this.inventario.getBonusAtaque();
    }

    protected int getBonusDefensa() {
        return this.inventario.getBonusDefensa();
    }

    // ------------------- Estados (delegacion) -------------------

    public void aplicarEstado(Estado nuevoEstado) {
        this.gestorEstados.agregar(nuevoEstado);
    }

    public boolean tieneEstadoActivo(String tipo) {
        return this.gestorEstados.tieneEstadoActivo(tipo);
    }

    public boolean estaIncapacitado() {
        return tieneEstadoActivo("INCAPACITAR");
    }

    public boolean tieneBuffAtaque() {
        return tieneEstadoActivo("BUFF");
    }

    public int aplicarDanoPorEstados() {
        int total = this.gestorEstados.calcularDanoPorTurno();
        this.puntosVida -= total;
        if (this.puntosVida < 0) {
            this.puntosVida = 0;
        }
        return total;
    }

    public List<String> reducirEstados() {
        return this.gestorEstados.reducirDuraciones();
    }

    // ------------------- Combate -------------------

    public int calcularAtaque() {
        int danoFinal = obtenerDanoBase() + getBonusAtaque();
        if (tieneBuffAtaque()) {
            danoFinal = (int) (danoFinal * 1.25);
        }
        return danoFinal;
    }

    public int usarHabilidadEspecial(Personaje objetivo) throws EnergiaInsuficienteException {
        if (!tieneEnergiaSuficiente()) {
            throw new EnergiaInsuficienteException(this.nombre, obtenerHabilidadEspecial(),
                    getCostoEnergiaHabilidad(), getEnergia());
        }
        this.gestorEnergia.consumir(getCostoEnergiaHabilidad());
        this.gestorEnergia.iniciarCooldown(getCooldownHabilidad());
        int danoEspecial = (int) (calcularAtaque() * 1.5);
        int vidaAntes = objetivo.getPuntosVida();
        objetivo.recibirDano(danoEspecial);
        return vidaAntes - objetivo.getPuntosVida();
    }

    @Override
    public void recibirDano(int danoRecibido) {
        int danoEfectivo = danoRecibido - calcularDefensa();
        if (danoEfectivo < 1) {
            danoEfectivo = 1;
        }
        this.puntosVida -= danoEfectivo;
        if (this.puntosVida < 0) {
            this.puntosVida = 0;
        }
    }

    @Override
    public boolean estaVivo() {
        return this.puntosVida > 0;
    }

    // ------------------- Energia (delegacion) -------------------

    public void regenerarRecursos() {
        this.gestorEnergia.regenerar();
    }

    public boolean habilidadDisponible() {
        return this.gestorEnergia.habilidadDisponible();
    }

    public boolean tieneEnergiaSuficiente() {
        return this.gestorEnergia.tieneEnergia(getCostoEnergiaHabilidad());
    }

    // ------------------- Progresion (delegacion) -------------------

    public void ganarExperiencia(int exp) {
        boolean subioNivel = this.progresion.ganarExperiencia(exp);
        if (subioNivel) {
            mejorarEstadisticasPorNivel();
        }
    }

    private void mejorarEstadisticasPorNivel() {
        this.puntosVidaMaximos += VIDA_POR_NIVEL;
        this.fuerza += FUERZA_POR_NIVEL;
        this.defensa += DEFENSA_POR_NIVEL;
        this.puntosVida = this.puntosVidaMaximos;
    }

    // ------------------- Getters -------------------

    @Override
    public int getPuntosVida() {
        return puntosVida;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return progresion.getNivel();
    }

    public int getPuntosVidaMaximos() {
        return puntosVidaMaximos;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getExperiencia() {
        return progresion.getExperiencia();
    }

    public int getEnergia() {
        return gestorEnergia.getEnergia();
    }

    public int getEnergiaMaxima() {
        return gestorEnergia.getEnergiaMaxima();
    }

    public int getCooldownActual() {
        return gestorEnergia.getCooldownActual();
    }

    public Objeto getObjetoEquipado() {
        return inventario.getObjetoEquipado();
    }

    public List<Objeto> getInventario() {
        return inventario.getObjetos();
    }
}
