package aed;
import java.util.ArrayList;

public class Trie2<T> {
    private Nodo raiz;

    class Nodo {
        private T valor;
        private ArrayList<Nodo> hijos;

        public Nodo() {
            hijos = new ArrayList<>(256); // Inicializa la lista de hijos con 256 espacios (segun codigo ASCII)
            for (int i = 0; i < 256; i++) {
                hijos.add(null);
            }
        }
    }

    public Trie2() {
        this.raiz = new Nodo();
    }

    // Metodo para insertar una palabra en el Trie
    // Complejidad: O(|palabra|)
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

    // Metodo para buscar una palabra en el Trie
    // Complejidad: O(|palabra|)
    public boolean buscar(String palabra) {
        Nodo actual = raiz;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            int indice = letra;
            Nodo siguiente = actual.hijos.get(indice);
            if (siguiente == null) {
                return false; // Si no se encuentra el siguiente nodo, la palabra no esta en el Trie
            }
            actual = siguiente;
        }
        return actual.valor != null; // Retorna true si el nodo final tiene un valor (ej: habiendo insertado "camina" y "caminando", si buscamos "caminan", recorriendo cada nodo correspondiente `c`, `a`, `m`, `i`, `n`, `a`, `n` vamos a llegar a que actual.valor sera null porque esa palabra esta incompleta en el Trie)
    }

    // Metodo para obtener el valor asociado a una palabra en el Trie
    // Complejidad: O(|palabra|)
    public T obtener(String palabra) {
        Nodo actual = raiz;
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            int indice = letra;
            Nodo siguiente = actual.hijos.get(indice);
            if (siguiente == null) {
                return null; // Si no se encuentra el siguiente nodo, retorna null
            }
            actual = siguiente;
        }
        return actual.valor; // Retorna el valor del nodo final
    }

    // Metodo para borrar una palabra del Trie de manera iterativa
    // Complejidad: O(|palabra|)
    public void borrar(String palabra) {
        if (raiz == null) return;

        Nodo actual = raiz;
        ArrayList<Nodo> camino = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        // Primero, recorremos el Trie para encontrar el nodo final de la palabra
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            int indice = c;

            if (actual.hijos.get(indice) == null) {
                return; // La palabra no esta en el Trie
            }

            camino.add(actual); // Guarda el nodo actual en el camino
            indices.add(indice); // Guarda el indice actual
            actual = actual.hijos.get(indice);
        }

        actual.valor = null; // Elimina el valor en el nodo final

        // Recorrer el camino hacia atras para eliminar nodos innecesarios
        for (int i = palabra.length() - 1; i >= 0; i--) {
            Nodo padre = camino.get(i);
            int indice = indices.get(i);

            if (tieneHijos(actual)) {
                break; // Si el nodo tiene hijos, no seguimos eliminando
            }

            padre.hijos.set(indice, null); // Elimina el enlace al nodo actual
            actual = padre; // Retrocede al nodo padre
        }
    }

    // Metodo auxiliar para verificar si un nodo tiene hijos
    // Complejidad: O(256) = O(1) 
    private boolean tieneHijos(Nodo nodo) {
        for (Nodo hijo : nodo.hijos) {
            if (hijo != null) {
                return true; // Si encuentra un hijo no nulo, retorna true
            }
        }
        return false; // Si todos los hijos son nulos, retorna false
    }
}
