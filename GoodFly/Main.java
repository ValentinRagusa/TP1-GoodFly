package GoodFly;

public class Main {
    public static void main(String[] args) {
        // arranco creando el grafo con los 13 destinos del enunciado
        Grafo g = new Grafo();
        g.cargarDefault(); // cargo todas las rutas (distancias en km)

        // para chequear rápido que la carga quedó bien
        System.out.println("=== MATRIZ DE ADYACENCIA ===");
        g.imprimirMatriz();

        // pruebo BFS: me interesa el menor número de vuelos (sin considerar km)
        System.out.println("\n=== BFS ===");
        // d1 a d6 debería salir con 2 saltos: d1 -> d2 -> d6
        BFS.imprimirCamino(g, BFS.caminoMinimo(g, "d1", "d6"));
        // caso con 3 saltos esperados
        BFS.imprimirCamino(g, BFS.caminoMinimo(g, "d10", "d1"));
        // desde d8 a d12, BFS encuentra el de menos escalas (pasa por d13)
        BFS.imprimirCamino(g, BFS.caminoMinimo(g, "d8", "d12"));

        // ahora corro DFS para ver si el grafo tiene ciclos
        System.out.println("\n=== DFS ===");
        DFS dfs = new DFS(g);
        dfs.analizar();
        if (dfs.tieneCiclo()) {
            System.out.println("El grafo tiene al menos un ciclo.");
        } else {
            System.out.println("El grafo es acíclico.");
        }

        // desgloso por componentes y marco cuáles son cíclicas o acíclicas
        System.out.println("\n=== ANALISIS DE COMPONENTES ===");
        DFSComponentes analisis = new DFSComponentes(g);
        analisis.analizar();
        analisis.imprimirResumen();

        // construyo un árbol de expansión desde d1 para poder hacer recorridos
        System.out.println("\n=== ÁRBOL DE EXPANSIÓN ===");
        Arbol arbol = new Arbol(g, "d1"); // elijo d1 como raíz
        arbol.mostrarRecorridos();        // preorden y postorden

        // imprimo la estructura del árbol con sangrías para verlo jerárquico
        arbol.imprimirEstructura();

        // por último, Dijkstra: camino más corto en kilómetros (ponderado)
        System.out.println("\n=== DIJKSTRA ===");
        Dijkstra.Resultado r1 = Dijkstra.caminoMinimo(g, "d8", "d12");
        Dijkstra.imprimirResultado(g, r1);

        Dijkstra.Resultado r2 = Dijkstra.caminoMinimo(g, "d10", "d11");
        Dijkstra.imprimirResultado(g, r2);
    }
}
