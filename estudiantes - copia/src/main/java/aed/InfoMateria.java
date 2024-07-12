package aed;

public class InfoMateria{

    private ParCarreraMateria[] paresCarreraMateria;
    public int length;

    public InfoMateria(ParCarreraMateria[] paresCarreraMateria){
        this.paresCarreraMateria = paresCarreraMateria;
    }

    public ParCarreraMateria[] getParesCarreraMateria() {
        return this.paresCarreraMateria;
    }
}
