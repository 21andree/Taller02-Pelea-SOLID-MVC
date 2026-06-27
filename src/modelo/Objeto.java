package modelo;

public abstract class Objeto {
    protected String nombre;
    protected String tipo;

    public Objeto(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public abstract int getModificadorAtaque();

    public abstract int getModificadorDefensa();

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }
}
