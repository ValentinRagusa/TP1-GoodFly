package GoodFly;

import java.util.*;

public class DFSComponentes {

    private Grafo g;
    private boolean[] visitado;
    private int[] color;
    private List<List<Integer>> componentes;
    private List<Boolean> esCiclica;

    public DFSComponentes(Grafo g) {
        this.g = g;
        this.visitado = new boolean[g.getN()];
        this.color = new int[g.getN()];
        this.componentes = new ArrayList<>();
        this.esCiclica = new ArrayList<>();
    }

    // recorre todo el grafo y agrupa los nodos en componentes
    // además, detecta si alguna de esas partes tiene ciclos
    public void analizar() {
        Arrays.fill(visitado, false);
        Arrays.fill(color, 0);
        componentes.clear();
        esCiclica.clear();

        for (int u = 0; u < g.getN(); u++) {
            if (!visitado[u]) {
                // cada vez que encuentro un nodo no visitado, arranca una nueva componente
                List<Integer> comp = new ArrayList<>();
                boolean[] ciclo = { false }; // uso un array de un solo valor para poder modificarlo dentro del dfs
                dfs(u, comp, ciclo);
                componentes.add(comp);
                esCiclica.add(ciclo[0]);
            }
        }
    }

    // recorrido en profundidad (DFS) para una componente
    private void dfs(int u, List<Integer> comp, boolean[] ciclo) {
        visitado[u] = true;
        color[u] = 1; // gris = en proceso
        comp.add(u);

        // recorro todos los vecinos del nodo actual
        for (int v : g.vecinos(u)) {
            if (!visitado[v]) {
                dfs(v, comp, ciclo);
            } else if (color[v] == 1) {
                // si llego a un nodo que todavía está en proceso, encontré un ciclo
                ciclo[0] = true;
            }
        }

        color[u] = 2; // negro = terminado
    }

    // muestra todas las componentes detectadas y si tienen ciclo o no
    public void imprimirResumen() {
        System.out.println("\n=== COMPONENTES DETECTADAS ===");
        for (int i = 0; i < componentes.size(); i++) {
            List<Integer> comp = componentes.get(i);
            boolean ciclo = esCiclica.get(i);
            System.out.print("Componente " + (i + 1) + ": ");

            // imprimo los nombres de los nodos de la componente
            for (int j = 0; j < comp.size(); j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(g.nombre(comp.get(j)));
            }

            // indico si es cíclica o acíclica
            System.out.println(ciclo ? "  → CÍCLICA" : "  → ACÍCLICA");
        }
    }
}
