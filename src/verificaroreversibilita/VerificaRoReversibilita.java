/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verificaroreversibilita;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo & Giulia
 */
public class VerificaRoReversibilita {

    public static int WHITE = 0;
    public static int BLACK = 1;
    public static int GREY = 2;
    public static int NIL = -1;

    public static boolean naiveReversibleUpTo(double[][] p, int[] ro) {
        int v = p.length;
        int color[] = new int[v];
        double x[] = new double[v];
        for (int u = 0; u < v; u++) {
            color[u] = WHITE;
            x[u] = Double.MAX_VALUE;
        }
        x[0] = 1;
        return dfsNaiveReversibleUpTo(p, 0, x, color, ro);
    }

    public static boolean dfsNaiveReversibleUpTo(double[][] p, int u, double[] x, int[] color, int[] ro) {
        boolean bool = true;
        int[] adj = adiacenti(u, p);
        for (int i = 0; bool && i < adj.length; i++) {
            int v = adj[i];
            if (p[ro[v]][ro[u]] == 0) {
                bool = false;
            } else {
                if (color[v] != WHITE && (int) (x[u] * p[u][v] * 1000000000) != (int) (x[v] * p[ro[v]][ro[u]] * 1000000000)) {
                    bool = false;
                }
                if (color[v] == WHITE) {
                    color[v] = GREY;
                    x[v] = x[u] * (p[u][v] / p[ro[v]][ro[u]]);
                    bool = bool && dfsNaiveReversibleUpTo(p, v, x, color, ro);
                }
            }
        }
        color[u] = BLACK;
        return bool;
    }

    private static int[] adiacenti(int u, double[][] p) {
        int n = 0;
        int[] vet = new int[p.length];
        int[] ret;
        for (int i = 0; i < p.length; i++) {
            if (p[u][i] != 0) {
                vet[n] = i;
                n++;
            }
        }
        ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = vet[i];
        }
        return ret;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        double[][] p;
        int ro[];
        BufferedReader in = new BufferedReader(new FileReader("cateneRoReversibili.txt"));
        BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
        int numeroCatene = Integer.valueOf(in.readLine());//leggo il primo valore che rappresenta il numero di catene
        int n = Integer.valueOf(in.readLine());//leggo il secondo valore che rappresenta il numero di nodi (tutte le cstene hanno lo stesso numero di nodi)
        boolean risultato;
        for (int i = 0; i < numeroCatene; i++) {
            ro = leggiRo(in, n);
            p = leggiP(in, n);
            risultato = naiveReversibleUpTo(p, ro);
            System.out.println(risultato);
            out.write(risultato + "");
            out.newLine();
        }
        out.close();
        in.close();
    }

    private static int[] leggiRo(BufferedReader in, int n) throws IOException {
        int[] ret = new int[n];
        String[] riga = in.readLine().split(",");
        for (int i = 0; i < n; i++) {
            ret[i] = Integer.valueOf(riga[i]);
        }
        return ret;
    }

    private static double[][] leggiP(BufferedReader in, int n) throws IOException {
        double[][] ret = new double[n][n];
        String[] riga;
        for (int i = 0; i < n; i++) {
            riga = in.readLine().split(",");
            for (int j = 0; j < n; j++) {
                ret[i][j] = Double.valueOf(riga[j]);
            }
        }
        return ret;
    }

}
