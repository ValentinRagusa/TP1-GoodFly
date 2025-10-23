package GoodFly;

import java.util.*;

public class BFS {

    // Busca el camino más corto (sin considerar pesos) entre dos destinos
    public static List<Integer> caminoMinimo(Grafo g, String origen, String destino) {
        int src = g.idxOrThrow(origen);
        int dst = g.idxOrThrow(destino);
        int n = g.getN();

        int[] dist = new int[n];    // cantidad de saltos desde el origen
        int[] padre = new int[n];   // para reconstruir el camino después
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(padre, -1);

        Queue<Integer> cola = new LinkedList<>();
        dist[src] = 0;  // el origen tiene distancia 0
        cola.add(src);

        // recorro en anchura
        while (!cola.isEmpty()) {
            int u = cola.poll();
            if (u == dst) break; // si llego al destino, corto
            for (int v : g.vecinos(u)) {
                // si el nodo no fue visitado todavía
                if (dist[v] == Integer.MAX_VALUE) {
                    dist[v] = dist[u] + 1; // aumento un salto
                    padre[v] = u;          // guardo el nodo anterior
                    cola.add(v);
                }
            }
        }

        // si el destino quedó sin visitar, no hay camino posible
        if (dist[dst] == Integer.MAX_VALUE) return List.of();

        // reconstruyo el camino desde el destino hacia el origen
        List<Integer> camino = new ArrayList<>();
        for (int v = dst; v != -1; v = padre[v]) camino.add(v);
        Collections.reverse(camino); // lo invierto para que empiece desde el origen
        return camino;
    }

    // imprime el camino por nombres de los destinos
    public static void imprimirCamino(Grafo g, List<Integer> camino) {
        if (camino.isEmpty()) {
            System.out.println("No hay camino.");
            return;
        }

        for (int i = 0; i < camino.size(); i++) {
            if (i > 0) System.out.print(" -> ");
            System.out.print(g.nombre(camino.get(i)));
        }

        // los “saltos” son básicamente la cantidad de vuelos
        System.out.println("  (saltos=" + (camino.size() - 1) + ")");
    }
}
