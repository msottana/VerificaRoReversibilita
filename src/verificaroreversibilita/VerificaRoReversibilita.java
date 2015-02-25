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

    public static boolean naiveReversibleUpTo(double[][] p, int[] rho) {
        int v = p.length;
        int color[] = new int[v];
        double x[] = new double[v];
        for (int u = 0; u < v; u++) {
            color[u] = WHITE;
            x[u] = Double.MAX_VALUE;
        }
        x[0] = 1;
        return dfsNaiveReversibleUpTo(p, 0, x, color, rho);
    }

    public static boolean dfsNaiveReversibleUpTo(double[][] p, int u, double[] x, int[] color, int[] rho) {
        boolean bool = true;
        int[] adj = adjacent(u, p);
        for (int i = 0; bool && i < adj.length; i++) {
            int v = adj[i];
            if (p[rho[v]][rho[u]] == 0) {
                bool = false;
            } else {
                if (color[v] != WHITE && (int) (x[u] * p[u][v] * 1000000000) != (int) (x[v] * p[rho[v]][rho[u]] * 1000000000)) {
                    bool = false;
                }
                if (color[v] == WHITE) {
                    color[v] = GREY;
                    x[v] = x[u] * (p[u][v] / p[rho[v]][rho[u]]);
                    bool = bool && dfsNaiveReversibleUpTo(p, v, x, color, rho);
                }
            }
        }
        color[u] = BLACK;
        return bool;
    }

    private static int[] adjacent(int u, double[][] p) {
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
        System.arraycopy(vet, 0, ret, 0, n);
        return ret;
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        double[][] p;
        int[] rho;
        BufferedReader in = new BufferedReader(new FileReader("inputRhoReversible.txt"));
        BufferedWriter out = new BufferedWriter(new FileWriter("outputRhoReversible.txt"));
        int numberOfChains = Integer.valueOf(in.readLine());//read the number of chains in the files
        int n = Integer.valueOf(in.readLine());//read the number of vertices in the chain
        //all chains have the same number of vertices
        boolean result;
        for (int i = 0; i < numberOfChains; i++) {
            rho = readRho(in, n);
            p = readProbabilityMatrix(in, n);
            result = naiveReversibleUpTo(p, rho);
            System.out.println(result);
            out.write(result + "");
            out.newLine();
        }
        out.close();
        in.close();
    }

    private static int[] readRho(BufferedReader in, int n) throws IOException {
        int[] ret = new int[n];
        String[] row = in.readLine().split(",");
        for (int i = 0; i < n; i++) {
            ret[i] = Integer.valueOf(row[i]);
        }
        return ret;
    }

    private static double[][] readProbabilityMatrix(BufferedReader in, int n) throws IOException {
        double[][] ret = new double[n][n];
        String[] row;
        for (int i = 0; i < n; i++) {
            row = in.readLine().split(",");
            for (int j = 0; j < n; j++) {
                ret[i][j] = Double.valueOf(row[j]);
            }
        }
        return ret;
    }

}
