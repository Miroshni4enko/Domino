package com.vimi.model.eulerian_path;

/**
 * Created by vymi1016 on 10/23/2017.
 */
public class Service {
    public static void main(String[] args) {
        int[][] edges;
        edges = new int [][]{
                {1, 1, 0},
                {1, 0, 1},
                {0, 0, 0}
        };
        System.out.println(Cycle.solve(edges));
    }
}
