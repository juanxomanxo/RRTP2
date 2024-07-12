package aed;
import java.util.ArrayList;


public class TrieDictionary<T> {
    private static class TrieNode {
        char value;//caracter almacenado en el nodo
        boolean finDePalabra;//indica si este nodo reprecenta el final de una palabra
        Object storedValue;//valor asociado a la palabra
        ArrayList<TrieNode> hijos;//lista de nodos hijos

        TrieNode(char value) {
            this.value = value;
            this.finDePalabra = false;
            this.hijos = new ArrayList<>();
            this.storedValue = null;
        }//Constructor del nodo
         
        TrieNode getChild(char c) {
            for (TrieNode hijo : hijos) {
                if (hijo.value == c) {
                    return hijo;
                }
            }
            return null;
        }//Devuelve el hijo de un caracter pasado como parametro, si tiene


        void addChild(TrieNode child) {
            hijos.add(child);
        }//Añade un nodo hijo a la lista de hijos


        void removeChild(char c) {
            hijos.removeIf(child -> child.value == c);
        }//Elimina el hijo que tiene el carácter dado
    }

    private TrieNode raiz;
    private int tamaño;

    public TrieDictionary() {
        raiz = new TrieNode('\0');
        tamaño = 0;
    }//Inicializa el trie con una raíz vacia y tamaño cero

    private TrieNode getNode(String key) {
        TrieNode current = raiz;
        for (char c : key.toCharArray()) {
            current = current.getChild(c);
            if (current == null) {
                return null;
            }
        }
        return current;
    }//Devuelve el nodo final de una clave dada

    public boolean containsKey(String key) {
        TrieNode node = getNode(key);
        return node != null && node.finDePalabra;
    }//Verifica si una clave está en el trie. Devuelve true si la clave existe y es el final de una palabra

    public void put(String key, Object value) {
        TrieNode current = raiz;
        for (char c : key.toCharArray()) {
            TrieNode child = current.getChild(c);
            if (child == null) {
                child = new TrieNode(c);
                current.addChild(child);
            }
            current = child;
        }
        if (!current.finDePalabra) {
            tamaño++;
        }
        current.finDePalabra = true;
        current.storedValue = value;
    }//Inserta una clave y su valor asociado en el trie, ademas crea nodos necesarios y actualiza finDePalabra y storedValue.

    public Object get(String key) {
        TrieNode node = getNode(key);
        return node.storedValue;
    }//Recupera el valor asociado a una clave. Devuelve null si la clave no existe o no es el final de una palabra.

    public void remove(String key) {
        remove(raiz, key, 0); {
        tamaño--;
        }
    }//Elimina una clave del trie y decrementa el tamaño del diccinario

    private boolean remove(TrieNode current, String key, int index) {
        if (index == key.length()) {
            current.finDePalabra = false;
            current.storedValue = null;
            return current.hijos.isEmpty();
        }
        char c = key.charAt(index);
        TrieNode child = current.getChild(c);
        boolean shouldDeleteCurrentNode = remove(child, key, index + 1);
    
        if (shouldDeleteCurrentNode) {
            current.removeChild(c);
            return current.hijos.isEmpty() && !current.finDePalabra;
        }
        return false;
    }//Elimina de forma recursiva los nodos correspondientes a la clave

    public String[] getAllKeys() {
        ArrayList<String> keys = new ArrayList<>();
        collectAllKeys(raiz, new StringBuilder(), keys);
        return keys.toArray(new String[0]);
    }//Devuelve todas las claves almacenadas en el trie en un array ordenado

    private void collectAllKeys(TrieNode node, StringBuilder prefix, ArrayList<String> keys) {
        if (node.finDePalabra) {
            keys.add(prefix.toString());
        }
        node.hijos.sort((a, b) -> Character.compare(a.value, b.value));
        for (TrieNode child : node.hijos) {
            collectAllKeys(child, prefix.append(child.value), keys);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }//Metodo recursivo que recopila y ordena todas las claves de un nodo
}

    