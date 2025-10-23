package GoodFly;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

// Clase que representa una conexión entre dos destinos (una arista del grafo)
class Arista {
    final int to;   // índice del destino al que apunta
    final int peso; // distancia en kilómetros

    Arista(int to, int peso) {
        this.to = to;
        this.peso = peso;
    }
}

public class Grafo {
    // número total de destinos (d1...d13)
    final int n = 13;

    // matriz de adyacencia (distancias) y lista de adyacencia (estructura de conexiones)
    final int[][] M;
    final List<List<Arista>> adj;

    // mapas auxiliares para poder convertir entre nombre (d1, d2...) y su índice
    final Map<String, Integer> idx = new HashMap<>();
    final String[] name;

    public Grafo() {
        // inicializo matriz y lista de adyacencia
        M = new int[n][n];
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // guardo nombres de los destinos y sus índices
        name = new String[n];
        for (int i = 0; i < n; i++) {
            name[i] = "d" + (i + 1);
            idx.put(name[i], i);
        }
    }

    // permite agregar una arista directamente con índices
    public void agregarArista(int u, int v, int w) {
        M[u][v] = w; // como el grafo es dirigido, solo guardo una dirección
        adj.get(u).add(new Arista(v, w));
    }

    // sobrecarga para agregar aristas usando los nombres tipo "d1", "d2"
    public void agregarArista(String du, String dv, int w) {
        agregarArista(idx.get(du), idx.get(dv), w);
    }

    // carga automática con todos los vuelos del enunciado (distancias en km)
    public void cargarDefault() {
        agregarArista("d1", "d2", 200);
        agregarArista("d1", "d13", 250);
        agregarArista("d1", "d9", 290);

        agregarArista("d2", "d6", 360);
        agregarArista("d2", "d3", 190);

        agregarArista("d3", "d6", 250);
        agregarArista("d3", "d5", 190);
        agregarArista("d3", "d1", 300);

        agregarArista("d4", "d3", 180);

        agregarArista("d5", "d6", 300);
        agregarArista("d5", "d10", 400);

        agregarArista("d6", "d11", 350);
        agregarArista("d6", "d12", 300);

        agregarArista("d7", "d4", 300);
        agregarArista("d7", "d3", 250);
        agregarArista("d7", "d1", 150);

        agregarArista("d8", "d7", 200);
        agregarArista("d8", "d1", 220);

        agregarArista("d9", "d8", 180);
        agregarArista("d9", "d13", 180);

        agregarArista("d10", "d4", 200);

        agregarArista("d11", "d10", 700);
        agregarArista("d11", "d5", 200);

        agregarArista("d12", "d2", 150);

        agregarArista("d13", "d12", 100);
        agregarArista("d13", "d2", 200);
    }

    // devuelve la lista de destinos alcanzables desde un nodo dado
    public List<Integer> vecinos(int u) {
        List<Integer> vs = new ArrayList<>();
        for (Arista a : adj.get(u)) vs.add(a.to);
        return vs;
    }

    // métodos de acceso rápidos
    public String nombre(int i) { return name[i]; }
    public int idxOrThrow(String nombre) { return idx.get(nombre); }
    public int getN() { return n; }

    // imprime la matriz de adyacencia completa (para chequear la carga inicial)
    public void imprimirMatriz() {
        for (int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(M[i]));
        }
    }
}
