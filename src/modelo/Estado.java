
package modelo;
public class Estado {
    
    private String nombre;
    private int duracion;
    private String tipo;
    private int efecto;

    public Estado(String nombre, int duracion, String tipo, int efecto) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.tipo = tipo;
        this.efecto = efecto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getEfecto() {
        return efecto;
    }

    public void reducirDuracion() {
        if (duracion > 0) {
            duracion--;
        }
    }

    public boolean estaActivo() {
        return duracion > 0;
    }
    
}
