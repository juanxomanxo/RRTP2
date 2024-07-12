package aed;
public class SistemaSIU {
    private DiccionarioDigital<DiccionarioDigital<Clase>> inscripciones;
    private DiccionarioDigital<Integer> anotacionesAlumnos;

    enum CargoDocente{
        PROF,
        JTP,
        AY1,
        AY2
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        //Esto inicializa y completa el diccionario de carreras/materias/aulas
        this.inscripciones = new DiccionarioDigital<>();
        for (InfoMateria infoMateria : infoMaterias){//O(M)
            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria();//O(1)
            int[] plantelAula = {0, 0, 0, 0};//O(1)
            String[] alumnosAula = {};//O(1)
            boolean excedeAula = false;//O(1)
            Clase aula = new Clase(plantelAula, alumnosAula, excedeAula);//O(1)
            for (ParCarreraMateria parCarreraMateria : paresCarreraMateria){//O(N_m)
                if (inscripciones.buscar(parCarreraMateria.getCarrera())){//O(|c|)
                    DiccionarioDigital<Clase> subDictionary = inscripciones.obtener(parCarreraMateria.getCarrera());//O(|c|)
                    Accesos accesos = new Accesos(subDictionary, parCarreraMateria.getNombreMateria());
                    aula.agregarAcceso(accesos);
                    subDictionary.insertar(parCarreraMateria.getNombreMateria(), aula);//O(|m|)
                    inscripciones.insertar(parCarreraMateria.getCarrera(), subDictionary);//O(|c|)
                } else {
                    DiccionarioDigital<Clase> subDictionary = new DiccionarioDigital<>();//O(1)
                    Accesos accesos = new Accesos(subDictionary, parCarreraMateria.getNombreMateria());
                    aula.agregarAcceso(accesos);
                    subDictionary.insertar(parCarreraMateria.getNombreMateria(), aula);//O(|m|)
                    inscripciones.insertar(parCarreraMateria.getCarrera(), subDictionary);//O(|c|)
                }
            }
        }
        this.anotacionesAlumnos = new DiccionarioDigital<>();//O(1)
        for (String libreta : libretasUniversitarias){//O(E)
            anotacionesAlumnos.insertar(libreta, 0);//O(1)
        }
    }

    public void inscribir(String estudiante, String carrera, String materia){
        int numero = this.anotacionesAlumnos.obtener(estudiante);//O(1)
        this.anotacionesAlumnos.insertar(estudiante, numero + 1);
        DiccionarioDigital<Clase> nombres = this.inscripciones.obtener(carrera);//O(|c|)
        Clase salon = nombres.obtener(materia);//O(|m|)
        salon.agregarAlumno(estudiante);//O(1)
    }
    
    public int inscriptos(String materia, String carrera){
        DiccionarioDigital<Clase> nombres = this.inscripciones.obtener(carrera);//O(|c|)
        Clase salon = nombres.obtener(materia);//O(|m|)
        return salon.getAlumnos().longitud();//(1)
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        DiccionarioDigital<Clase> nombres = this.inscripciones.obtener(carrera);//O(|c|)
        Clase salon = nombres.obtener(materia);//O(|m|)
        salon.agregarDocente(cargo);//O(1)    
    }

    public int[] plantelDocente(String materia, String carrera){
        DiccionarioDigital<Clase> nombres = this.inscripciones.obtener(carrera);//O(|c|) 
        Clase salon = nombres.obtener(materia);//O(|m|) 
        return salon.getPlantelDocente();//O(1)       
    }

    public boolean excedeCupo(String materia, String carrera){
        DiccionarioDigital<Clase> nombres = this.inscripciones.obtener(carrera);//O(|c|)
        Clase salon = nombres.obtener(materia);//O(|m|)
        return salon.excede();//O(1)      
    }

    public String[] carreras(){
        return this.inscripciones.claves();//O(∑|c|)
    }

    public String[] materias(String carrera){
        DiccionarioDigital<Clase> materias = this.inscripciones.obtener(carrera);//O(|c|)
        return materias.claves();//O(∑|m_c|)
    }

    public int materiasInscriptas(String estudiante){
        int numero = (int) this.anotacionesAlumnos.obtener(estudiante);//O(1)
        return numero;//O(1)
    }

    public void cerrarMateria(String materia, String carrera) {
        DiccionarioDigital<Clase> nombres = this.inscripciones.obtener(carrera);//O(|c|)
        Clase salon = nombres.obtener(materia);//O(|m|)
        ListaEnlazada<String> anotados = salon.getAlumnos();//O(1)
        for (int i = 0; i < anotados.longitud(); i++) {//O(E_m)
            String alumno = anotados.obtener(i);//O(E_m)
            int cantidad = this.anotacionesAlumnos.obtener(alumno);//O(1)
            this.anotacionesAlumnos.insertar(alumno, cantidad - 1);
        }
        DiccionarioDigital<Clase> nombreCarreras = this.inscripciones.obtener(carrera);//O(|c|)
        Clase aulaMateria = nombreCarreras.obtener(materia);//O(|m|)
        aulaMateria.cerrar();
    } 


}
