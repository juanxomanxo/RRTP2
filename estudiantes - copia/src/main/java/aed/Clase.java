package aed;
import aed.SistemaSIU.CargoDocente;

public class Clase {
    private int[] plantelDocente;
    private ListaEnlazada<String> alumnos;
    private ListaEnlazada<Accesos> accesos;
/*Invariante de Reprecentación:
 *Para todo i, 0 <= i < |plantelDocente|, plantelDocente[i] >= 0 y plantel_docente.length = 4;
 *La longitud de la lista enlazada "alumnos" es mayor a 0 si y solo si la longitud de la lista enlazada "accesos" es mayor a 0.
 |alumnos| >= |accesos|
 */
    public Clase(int[] plantelDocente, String[] alumnosArray, boolean excede) {
        this.plantelDocente = plantelDocente;
        this.alumnos = new ListaEnlazada<>();//O(1)
        for (String alumno : alumnosArray) {
            this.alumnos.agregarAtras(alumno);//O(1)
        }
        this.accesos = new ListaEnlazada<>();//O(1)
    }

    public void cerrar() {
        Iterador<Accesos> iterador = this.accesos.iterador();
        while(iterador.haySiguiente()){
            Accesos actual = iterador.siguiente();
            DiccionarioDigital<Clase> subAcceso = actual.getDiccionario();
            subAcceso.borrar(actual.getNombreMateria());
        }
    }

    public int[] getPlantelDocente() {
        return plantelDocente; //O(1)
    }

    public ListaEnlazada<String> getAlumnos() {
        return this.alumnos; //O(1)
    }

    public boolean excede() {
        return this.alumnos.longitud() > 250 * this.plantelDocente[0] ||
               this.alumnos.longitud() > 100 * this.plantelDocente[1] ||
               this.alumnos.longitud() > 20 * this.plantelDocente[2] ||
               this.alumnos.longitud() > 30 * this.plantelDocente[3];
    } //O(1)

    public void agregarAlumno(String nuevoAlumno) {
        this.alumnos.agregarAtras(nuevoAlumno); //O(1)
    }

    public void agregarDocente(CargoDocente cargo) {
        int[] plantel = this.plantelDocente; //O(1)
        plantel[cargo.ordinal()]++; //O(1)
    }

    public void agregarAcceso(Accesos acceso) {
        this.accesos.agregarAtras(acceso); //O(1)
    }
}
