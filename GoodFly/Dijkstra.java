package GoodFly;

import java.util.*;

public class Dijkstra {

    // Clase interna para guardar el resultado del algoritmo
    // (camino recorrido y distancia total)
    public static class Resultado {
        public final List<Integer> camino;
        public final int distanciaTotal;

        Resultado(List<Integer> camino, int distanciaTotal) {
            this.camino = camino;
            this.distanciaTotal = distanciaTotal;
        }
    }

    // calcula el camino más corto (ponderado) entre dos destinos
    public static Resultado caminoMinimo(Grafo g, String origen, String destino) {
        int n = g.getN();
        int src = g.idxOrThrow(origen);
        int dst = g.idxOrThrow(destino);

        int[] dist = new int[n];      // guarda la distancia mínima a cada nodo
        int[] padre = new int[n];     // sirve para reconstruir el camino
        boolean[] visitado = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(padre, -1);

        dist[src] = 0; // el origen siempre tiene distancia 0

        // uso una cola de prioridad (distancia, nodo)
        // para elegir siempre el nodo más cercano no visitado
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] actual = pq.poll();
            int u = actual[1];
            if (visitado[u]) continue;
            visitado[u] = true;

            // recorro todos los vecinos del nodo actual
            for (var arista : g.adj.get(u)) {
                int v = arista.to;
                int peso = arista.peso;

                // si encuentro un camino más corto, lo actualizo
                if (dist[u] + peso < dist[v]) {
                    dist[v] = dist[u] + peso;
                    padre[v] = u;
                    pq.add(new int[]{dist[v], v});
                }
            }
        }

        // si el destino quedó con distancia infinita, no hay conexión posible
        if (dist[dst] == Integer.MAX_VALUE)
            return new Resultado(List.of(), Integer.MAX_VALUE);

        // reconstruyo el camino desde el destino hacia el origen usando el arreglo padre
        List<Integer> camino = new ArrayList<>();
        for (int v = dst; v != -1; v = padre[v]) camino.add(v);
        Collections.reverse(camino); // lo doy vuelta para que empiece en el origen

        return new Resultado(camino, dist[dst]);
    }

    // imprime el camino y la distancia total en formato legible
    public static void imprimirResultado(Grafo g, Resultado r) {
        if (r.camino.isEmpty()) {
            System.out.println("No existe camino entre los destinos.");
            return;
        }

        for (int i = 0; i < r.camino.size(); i++) {
            if (i > 0) System.out.print(" -> ");
            System.out.print(g.nombre(r.camino.get(i)));
        }

        System.out.println("  (distancia total = " + r.distanciaTotal + " km)");
    }
}
