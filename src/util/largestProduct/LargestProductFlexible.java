package src.util.largestProduct;

import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayDeque;

import src.interfaces.Solution;

import java.util.ArrayList;
import java.util.Comparator;

class Node {
    int x; 
    int y;
    int value;

    public Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}

class NodeCompatator implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        return Long.compare(n2.value, n1.value);
    }
}


public class LargestProductFlexible implements Solution {

    public int x, y;
    public int[] factors;
    int[][] grid;
    int k;
    long maxProduct;


    @Override
    public void reset() {
        x = 0; y = 0;
        factors = null;
    }

    @Override
    public int getRow() {
        return x;
    }

    @Override
    public int getColumn() {
        return y;
    }

    @Override
    public int[] getFactors() {
        return factors;
    }


    public LargestProductFlexible(int[][] grid, int k) {
        this.grid = grid;
        this.k = k;
    }

    private enum Direction {
        HORIZONTAL(0, 1),
        VERTICAL(1, 0),
        POSITIVE_DIAGONAL(1, 1),
        NEGATIVE_DIAGONAL(1, -1);

        final int x, y;
        Direction(int x, int y) {
            this.x = x; 
            this.y = y;
        }
    }
    
    void visitNode(Node n, long pathProduct, int depth, Queue<Node> queue, HashSet<String> visited, int[] factors) {
        if(n == null) return;
        String coords = n.x + "," + n.y;
        if(visited.contains(coords)) {
            visitNode(queue.poll(), pathProduct, depth, queue, visited, factors);
            return;
        }
        if(depth >= k) {
            if(depth == k && pathProduct > maxProduct) {
                this.maxProduct = pathProduct;
                this.factors = factors.clone();
                this.x = n.x; this.y = n.y;
            }
            return;
        }
        var adjs = adjacentNodes(n);
        visited.add(coords);
        factors[depth] = n.value;
        for(Node adj : adjs)
            visitNode(adj, pathProduct * n.value, depth + 1, queue, visited, factors);

        for(String coord: visited.toArray(String[]::new)) {
            var coords2 = coord.split(",");
            int a = Integer.parseInt(coords2[0]);
            int b = Integer.parseInt(coords2[1]);
            var n2 = new Node(a, b, grid[a][b]);
            for(Node n3 : adjacentNodes(n2))
                visitNode(n3, pathProduct * n.value, depth + 1, queue, visited, factors);
        }
        visited.remove(coords);
    }

    @Override
    public long calculate() {
        maxProduct = Long.MIN_VALUE;
        var queue = new ArrayDeque<Node>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                HashSet<String> visited = new HashSet<>();
                int[] nums = new int[k];
                visitNode(new Node(i, j, grid[i][j]), 1, 0, queue, visited, nums);
                queue.clear();
            }
        }
        return this.maxProduct;
    }

    private List<Node> adjacentNodes(Node node) {
        var adjacent = new ArrayList<Node>();
        for(Direction direction : Direction.values()) {
            var newX = node.x + direction.x;
            var newY = node.y + direction.y;
            if(!(newX >= grid.length || newY >= grid[0].length || newY < 0))
                adjacent.add(new Node(newX, newY, grid[newX][newY]));
        }
        return adjacent;
    }
}