package modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Personaje implements Atacable {
    protected String nombre;
    protected int nivel;
    protected int puntosVida;
    protected int puntosVidaMaximos;
    protected int fuerza;
    protected int defensa;
    protected int experiencia;
    protected List<Objeto> inventario;
    protected Objeto objetoEquipado;

    protected int energia;
    protected int energiaMaxima;
    protected int cooldownActual;

    protected List<Estado> listaEstados;

    protected static final int REGENERACION_ENERGIA = 15;
    protected static final int EXP_PARA_SUBIR_NIVEL = 100;

    public Personaje(String nombre, int puntosVidaMaximos, int fuerza, int defensa) {
        this.nombre = nombre;
        this.nivel = 1;
        this.puntosVidaMaximos = puntosVidaMaximos;
        this.puntosVida = puntosVidaMaximos;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.experiencia = 0;
        this.inventario = new ArrayList<Objeto>();
        this.objetoEquipado = null;
        this.energiaMaxima = 100;
        this.energia = this.energiaMaxima;
        this.cooldownActual = 0;
        this.listaEstados = new ArrayList<Estado>();
    }

    public abstract int obtenerDanoBase();

    public abstract int calcularDefensa();

    public abstract String obtenerHabilidadEspecial();

    public abstract int getCostoEnergiaHabilidad();

    public abstract int getCooldownHabilidad();

    public abstract void aplicarEfectoEspecial(Personaje objetivo);

    public abstract String getTipoPersonaje();

    public void agregarObjeto(Objeto objeto) {
        this.inventario.add(objeto);
    }

    public void equipar(Objeto objeto) {
        if (this.inventario.contains(objeto)) {
            this.objetoEquipado = objeto;
        }
    }

    protected int getBonusAtaque() {
        if (objetoEquipado != null) {
            return objetoEquipado.getModificadorAtaque();
        }
        return 0;
    }

    protected int getBonusDefensa() {
        if (objetoEquipado != null) {
            return objetoEquipado.getModificadorDefensa();
        }
        return 0;
    }

    public void aplicarEstado(Estado nuevoEstado) {
        this.listaEstados.add(nuevoEstado);
    }

    public boolean tieneEstadoActivo(String tipo) {
        for (Estado estado : listaEstados) {
            if (estado.getTipo().equals(tipo) && estado.estaActivo()) {
                return true;
            }
        }
        return false;
    }

    public boolean estaIncapacitado() {
        return tieneEstadoActivo("INCAPACITAR");
    }

    public boolean tieneBuffAtaque() {
        return tieneEstadoActivo("BUFF");
    }

    public int aplicarDanoPorEstados() {
        int total = 0;
        for (Estado estado : listaEstados) {
            if (estado.getTipo().equals("DAN_TURNO") && estado.estaActivo()) {
                total += estado.getEfecto();
            }
        }
        this.puntosVida -= total;
        if (this.puntosVida < 0) {
            this.puntosVida = 0;
        }
        return total;
    }

    public List<String> reducirEstados() {
        List<String> expirados = new ArrayList<String>();
        List<Estado> activos = new ArrayList<Estado>();
        for (Estado estado : listaEstados) {
            estado.reducirDuracion();
            if (estado.estaActivo()) {
                activos.add(estado);
            } else {
                expirados.add(estado.getNombre());
            }
        }
        this.listaEstados = activos;
        return expirados;
    }

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
                    getCostoEnergiaHabilidad(), this.energia);
        }
        this.energia -= getCostoEnergiaHabilidad();
        this.cooldownActual = getCooldownHabilidad();
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

    public void regenerarRecursos() {
        if (this.energia < this.energiaMaxima) {
            this.energia += REGENERACION_ENERGIA;
            if (this.energia > this.energiaMaxima) {
                this.energia = this.energiaMaxima;
            }
        }
        if (this.cooldownActual > 0) {
            this.cooldownActual--;
        }
    }

    public boolean habilidadDisponible() {
        return this.cooldownActual == 0;
    }

    public boolean tieneEnergiaSuficiente() {
        return this.energia >= getCostoEnergiaHabilidad();
    }

    public void ganarExperiencia(int exp) {
        this.experiencia += exp;
        if (this.experiencia >= EXP_PARA_SUBIR_NIVEL) {
            this.nivel++;
            this.experiencia -= EXP_PARA_SUBIR_NIVEL;
            this.puntosVidaMaximos += 20;
            this.fuerza += 5;
            this.defensa += 3;
            this.puntosVida = this.puntosVidaMaximos;
        }
    }

    @Override
    public int getPuntosVida() {
        return puntosVida;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPuntosVidaMaximos() {
        return puntosVidaMaximos;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getExperiencia() {
        return experiencia;
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

    public Objeto getObjetoEquipado() {
        return objetoEquipado;
    }

    public List<Objeto> getInventario() {
        return inventario;
    }
}
