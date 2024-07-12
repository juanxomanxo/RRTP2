package aed;

public class Accesos {
    DiccionarioDigital<Clase> diccionario;
    String nombreMateria;

    public Accesos(DiccionarioDigital<Clase> diccionario, String nombreMateria) {
        this.diccionario = diccionario;
        this.nombreMateria = nombreMateria;
    }

    public String getNombreMateria() {
        return this.nombreMateria;
    }

    public DiccionarioDigital<Clase> getDiccionario() {
        return this.diccionario;
    }
}
