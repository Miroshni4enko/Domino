package com.vimi.model.eulerian_path;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by vymi1016 on 10/23/2017.
 */
public class Cycle
{
    public static final int NUMBER_OF_NODES = 50;
    Edge start;
    boolean[] used = new boolean[NUMBER_OF_NODES+1];

    public Cycle(Edge start)
    {
        // Store the cycle itself
        this.start = start;
        // And memorize which nodes are being used in this cycle
        used[start.u] = true;
        for (Edge e = start.next; e != start; e = e.next)
            used[e.u] = true;
    }

    /**
     * Checks if this cycle can join with the given cycle. That is
     * the case if and only if both cycles use a common node.
     *
     * @return {@code true} if this and that cycle can be joined,
     *         {@code false} otherwise.
     */
    public boolean canJoin(Cycle that)
    {
        // Find a commonly used node
        for (int node = 1; node <= NUMBER_OF_NODES; node++)
            if (this.used[node] && that.used[node])
                return true;
        return false;
    }

    /**
     * Joins the given cycle to this cycle. Both cycles will be broken
     * at a common node and the paths will then be connected to each
     * other. The given cycle should not be used after this call, as the
     * list of used nodes is most probably invalidated, only this cycle
     * will be updated and remains valid.
     *
     * @param that The cycle to be joined to this cycle.
     */
    public void join(Cycle that)
    {
        // Find the node where we'll join the two cycles
        int junction = 1;
        while (!this.used[junction] || !that.used[junction])
            junction++;
        // Find the join place in this cycle
        Edge joinAfterEdge = this.start;
        while (joinAfterEdge.v != junction)
            joinAfterEdge = joinAfterEdge.next;
        // Find the join place in that cycle
        Edge joinBeforeEdge = that.start;
        while (joinBeforeEdge.u != junction)
            joinBeforeEdge = joinBeforeEdge.next;
        // Connect them together
        joinAfterEdge.next.prev = joinBeforeEdge.prev;
        joinBeforeEdge.prev.next = joinAfterEdge.next;
        joinAfterEdge.next = joinBeforeEdge;
        joinBeforeEdge.prev = joinAfterEdge;
        // Update the used nodes
        for (int node = 1; node <= NUMBER_OF_NODES; node++)
            this.used[node] |= that.used[node];
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(start.u).append(" ").append(start.v);
        for (Edge curr = start.next; curr != start; curr = curr.next)
            s.append("\n").append(curr.u).append(" ").append(curr.v);
        return s.toString();
    }
    /**
     * @param edges A variant of an adjacency matrix: the number in edges[i][j]
     *        indicates how many links there are between node i and node j. Note
     *        that this means that every edge contributes two times in this
     *        matrix: one time from i to j and one time from j to i. This is
     *        also true in the case of a loop: the link still contributes in two
     *        ways, from i to j and from j to i, even though i == j.
     */
    public static Cycle solve(int[][] edges)
    {
        Deque<Cycle> cycles = new LinkedList<Cycle>();
        // First, find a place where we can start a new cycle
        for (int u = 1; u <= NUMBER_OF_NODES; u++)
            for (int v = 1; v <= NUMBER_OF_NODES; v++)
                if (edges[u][v] > 0)
                {
                    // The new cycle starts at the edge from u to v
                    Edge first, last = first = new Edge(u, v);
                    edges[last.u][last.v]--;
                    edges[last.v][last.u]--;
                    int curr = last.v;
                    // Extend the list of edges until we're back at the start
                    search: while (curr != u)
                    {
                        // Find any edge that extends the last edge
                        for (int next = 1; next <= NUMBER_OF_NODES; next++)
                            if (edges[curr][next] > 0)
                            {
                                // We found an edge, attach it to the last one
                                last = last.attach(next);
                                edges[last.u][last.v]--;
                                edges[last.v][last.u]--;
                                curr = next;
                                continue search;
                            }
                        // We can't form a cycle anymore, which
                        // means there is no Eulerian cycle.
                        return null;
                    }
                    // Connect the end to the start
                    last.next = first;
                    first.prev = last;
                    // Save it
                    cycles.add(new Cycle(last));
                    // And don't forget about the possibility that there are
                    // more edges running from u to v, so v should be
                    // re-examined in the next iteration.
                    v--;
                }
        // Now we have put all edges into cycles,
        // we join them all together (if possible)
        merge: while (cycles.size() > 1)
        {
            // Join the last cycle with any of the previous ones
            Cycle last = cycles.removeLast();
            for (Cycle curr : cycles)
                if (curr.canJoin(last))
                {
                    // Found one! Just join it and continue the merge
                    curr.join(last);
                    continue merge;
                }
            // No compatible cycle found, meaning there is no Eulerian cycle
            return null;
        }
        return cycles.getFirst();
    }
}