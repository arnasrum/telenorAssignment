package src.util.largestProduct;

import java.util.List;
import java.util.HashSet;
import java.util.PriorityQueue;

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
    
    void visitNode(Node n, long pathProduct, int depth, PriorityQueue<Node> queue, HashSet<String> visited, int[] factors) {
        if(n == null) return;
        if(visited.contains(n.x + "," + n.y)) {
            visitNode(queue.poll(), pathProduct, depth, queue, visited, factors);
            return;
        }
        if(depth >= k) {
            if(depth == k && pathProduct > maxProduct) {
                this.maxProduct = pathProduct;
                this.factors = factors;
                this.x = n.x; this.y = n.y;
            }
            return;
        }
        queue.addAll(adjacentNodes(n));
        visited.add(n.x + "," + n.y);
        factors[depth] = n.value;
        visitNode(queue.poll(), pathProduct * n.value, depth + 1, queue, visited, factors);
    }

    @Override
    public long calculate() {
        maxProduct = Long.MIN_VALUE;
        var queue = new PriorityQueue<Node>(new NodeCompatator());

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