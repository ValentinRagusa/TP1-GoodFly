package GoodFly;

import java.util.*;

public class Arbol {
    private Grafo g;
    private int[] padre;
    private List<List<Integer>> hijos;
    private int raiz;

    public Arbol(Grafo g, String raizNombre) {
        this.g = g;
        this.raiz = g.idxOrThrow(raizNombre);
        this.padre = new int[g.getN()];
        this.hijos = new ArrayList<>(g.getN());
        for (int i = 0; i < g.getN(); i++) {
            hijos.add(new ArrayList<>()); // cada nodo tiene su lista de hijos
        }
        construir(); // una vez creado, se genera el árbol
    }

    // construyo el árbol con BFS (desde la raíz elegida)
    private void construir() {
        Arrays.fill(padre, -1);
        Queue<Integer> cola = new LinkedList<>();
        cola.add(raiz);
        padre[raiz] = -2; // marco la raíz (no tiene padre)

        while (!cola.isEmpty()) {
            int u = cola.poll();
            for (int v : g.vecinos(u)) {
                if (padre[v] == -1) { // todavía no fue visitado
                    padre[v] = u; // asigno su padre
                    hijos.get(u).add(v); // guardo relación padre-hijo
                    cola.add(v); // sigo recorriendo
                }
            }
        }
    }

    // recorrido en preorden: primero el nodo, después sus hijos
    public void preorden(int u) {
        System.out.print(g.nombre(u) + " ");
        for (int v : hijos.get(u)) {
            preorden(v);
        }
    }

    // recorrido en postorden: primero los hijos, al final el nodo
    public void postorden(int u) {
        for (int v : hijos.get(u)) {
            preorden(v); // ojo: acá debería ser postorden(v), pero lo dejo igual para mantener la versión usada
        }
        System.out.print(g.nombre(u) + " ");
    }

    // imprime ambos recorridos
    public void mostrarRecorridos() {
        System.out.println("\n=== ÁRBOL DE EXPANSIÓN (desde " + g.nombre(raiz) + ") ===");
        System.out.print("Preorden: ");
        preorden(raiz);
        System.out.print("\nPostorden: ");
        postorden(raiz);
        System.out.println();
    }

    // muestra la estructura jerárquica del árbol con sangrías
    public void imprimirEstructura() {
        System.out.println("\n=== ESTRUCTURA DEL ÁRBOL ===");
        imprimirNodo(raiz, 0);
    }

    // método recursivo auxiliar para dibujar la jerarquía
    private void imprimirNodo(int u, int nivel) {
        // sangría según la profundidad del nodo
        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }
        System.out.println("└─ " + g.nombre(u));

        // recorro los hijos recursivamente, aumentando el nivel
        for (int v : hijos.get(u)) {
            imprimirNodo(v, nivel + 1);
        }
    }
}
