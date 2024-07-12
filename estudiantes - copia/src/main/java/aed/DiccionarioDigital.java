package aed;
import java.util.ArrayList;

public class DiccionarioDigital<T> {
    private Nodo raiz;

    private class Nodo{
        T valor;
        ArrayList<Nodo> hijos;

        public Nodo() {
            hijos = new ArrayList<>(256); 
            for (int i = 0; i < 256; i++) {
                hijos.add(null);
            }
        }
    }
    public DiccionarioDigital(){
        this.raiz = new Nodo();
    }

    public void insertar(String palabra, T valor) {
        Nodo actual = raiz;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            int indice = letra;
            Nodo siguiente = actual.hijos.get(indice);
            if (siguiente == null) {
                siguiente = new Nodo();
                actual.hijos.set(indice, siguiente);
            }
            actual = siguiente;
        }
        actual.valor = valor;
    }

    public boolean buscar(String palabra) {
        Nodo actual = raiz;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            int indice = letra;
            Nodo siguiente = actual.hijos.get(indice);
            if (siguiente == null) {
                return false; 
            }
            actual = siguiente;
        }
        return actual.valor != null; 
    }

    public T obtener(String palabra) {
        Nodo actual = raiz;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            int indice = letra;
            Nodo siguiente = actual.hijos.get(indice);
            if (siguiente == null) {
                return null; 
            }
            actual = siguiente;
        }
        return actual.valor; 
    }
    public void borrar(String palabra) {
        if (raiz == null) return;

        Nodo actual = raiz;
        ArrayList<Nodo> camino = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            int indice = c;
            if (actual.hijos.get(indice) == null) {
                return; 
            }
            camino.add(actual);
            indices.add(indice); 
            actual = actual.hijos.get(indice);
        }
        actual.valor = null; 
        for (int i = palabra.length() - 1; i >= 0; i--) {
            Nodo padre = camino.get(i);
            int indice = indices.get(i);
            if (tieneHijos(actual)) {
                break;
            }
            padre.hijos.set(indice, null);
            actual = padre; 
        }
    }
    private boolean tieneHijos(Nodo nodo) {
        for (Nodo hijo : nodo.hijos) {
            if (hijo != null) {
                return true;
            }
        }
        return false;
    }
    

    public String[] claves() {
        ArrayList<String> claves = new ArrayList<>();
        obtenerClaves(raiz, new StringBuilder(), claves);
        return claves.toArray(new String[0]);
    }

    private void obtenerClaves(Nodo nodo, StringBuilder actual, ArrayList<String> claves) {
        if (nodo.valor != null) {
            claves.add(actual.toString());
        }
        for (int i = 0; i < nodo.hijos.size(); i++) {
            Nodo hijo = nodo.hijos.get(i);
            if (hijo != null) {
                actual.append((char) i); 
                obtenerClaves(hijo, actual, claves);
                actual.deleteCharAt(actual.length() - 1);
            }
        }
    }
}

