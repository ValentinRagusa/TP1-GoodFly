package GoodFly;

import java.util.*;

public class DFS {
    private Grafo g;
    private int[] color;
    private boolean hayCiclo;

    public DFS(Grafo g) {
        this.g = g;
        this.color = new int[g.getN()];
        this.hayCiclo = false;
    }

    // Recorre todo el grafo y detecta si existe al menos un ciclo
    public void analizar() {
        Arrays.fill(color, 0);
        hayCiclo = false;
        for (int u = 0; u < g.getN(); u++) {
            if (color[u] == 0) {
                dfs(u);
            }
        }
    }

    // recorrido DFS normal para detección de ciclos
    private void dfs(int u) {
        color[u] = 1; // gris = nodo en proceso
        for (int v : g.vecinos(u)) {
            if (color[v] == 0) {
                dfs(v);
            } else if (color[v] == 1) {
                // si entro a un nodo que todavía está “gris”, hay un ciclo
                hayCiclo = true;
            }
        }
        color[u] = 2; // negro = nodo finalizado
    }

    // devuelve true si el grafo tiene algún ciclo
    public boolean tieneCiclo() {
        return hayCiclo;
    }

    // versión extendida que analiza los componentes y muestra si son cíclicos o no
    public void analizarComponentes() {
        Arrays.fill(color, 0);
        int comp = 0;

        for (int u = 0; u < g.getN(); u++) {
            if (color[u] == 0) {
                comp++;
                List<Integer> nodosComponente = new ArrayList<>();
                boolean ciclo = dfsComponente(u, nodosComponente);

                System.out.print("Componente " + comp + ": ");
                for (int v : nodosComponente) {
                    System.out.print(g.nombre(v) + " ");
                }
                System.out.println(ciclo ? "(cíclica)" : "(acíclica)");
            }
        }
    }

    // DFS recursivo usado para recorrer cada componente
    private boolean dfsComponente(int u, List<Integer> nodos) {
        boolean ciclo = false;
        color[u] = 1;
        nodos.add(u);

        for (int v : g.vecinos(u)) {
            if (color[v] == 0) {
                if (dfsComponente(v, nodos)) ciclo = true;
            } else if (color[v] == 1) {
                ciclo = true;
            }
        }

        color[u] = 2;
        return ciclo;
    }
}
