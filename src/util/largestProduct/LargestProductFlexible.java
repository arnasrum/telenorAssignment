package src.util.largestProduct;

import java.util.List;
import java.util.HashSet;
import java.util.PriorityQueue;

import src.interfaces.Solution;
import java.util.Comparator;

class Node {
    int x; 
    int y;
    long value;

    public Node(int x, int y, long value) {
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
    
    void visitNode(Node n, long pathProduct, int depth, PriorityQueue<Node> queue, HashSet<String> visited, int[] factors) {
        if(n == null || n.value == Long.MIN_VALUE) return;
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
        factors[depth] = grid[n.x][n.y];
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
        Node[] adjecent = {
            new Node(node.x, node.y + 1, horizontal(node.x, node.y, 1)), 
            new Node(node.x, node.y - 1, horizontal(node.x, node.y, -1)), 
            new Node(node.x + 1, node.y, vertical(node.x, node.y, 1)), 
            new Node(node.x + 1, node.y + 1, diagonal(node.x, node.y, 1, 1)),
            new Node(node.x + 1, node.y - 1, diagonal(node.x, node.y, 1, -1))
        };
        return List.of(adjecent);
    }

    private long horizontal(int row, int column, int direction) {
        if(column + direction < 0 || column + direction >= grid[0].length)
            return Long.MIN_VALUE;
        if(row >= grid.length || row < 0)
            return Long.MIN_VALUE;
        return grid[row][column + direction];
    }

    private long vertical(int row, int column, int direction) {
        if(row + direction < 0 || row + direction >= grid.length)
            return Long.MIN_VALUE;
        if(column < 0 || column >= grid[0].length)
            return Long.MIN_VALUE;
        return grid[row + direction][column];
    }
    
    private long diagonal(int row, int column, int vertialDirection, int horizontalDirection) {
        if(row + vertialDirection < 0 || row + vertialDirection >= grid.length)
            return Long.MIN_VALUE;
        if(column + horizontalDirection < 0 || column + horizontalDirection >= grid[0].length)
            return Long.MIN_VALUE;
        return grid[row + vertialDirection][column + horizontalDirection];
    }
}