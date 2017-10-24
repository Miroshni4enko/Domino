package com.vimi.model.eulerian_path;

/**
 * Created by vymi1016 on 10/23/2017.
 */
public class Edge
{
    int u, v;
    Edge prev, next;

    public Edge(int u, int v)
    {
        this.u = u;
        this.v = v;
    }

    /**
     * Attaches a new edge to this edge, leading to the given node
     * and returns the newly created Edge. The node where the
     * attached edge starts doesn't need to be given, as it will
     * always be the node where this edge ends.
     *
     * @param node The node where the attached edge ends.
     */
    public Edge attach(int node)
    {
        next = new Edge(this.v, node);
        next.prev = this;
        return next;
    }
}
