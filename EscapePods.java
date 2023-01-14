import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public static int solution(int[] entrances, int[] exits, int[][] path) {
        int n = path.length;
        int source = n;
        int sink = n + 1;
        int[][] graph = new int[n + 2][n + 2];
        
        // Create the graph
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = path[i][j];
            }
        }
        // Add edges from source to entrances
        for (int entrance : entrances) {
            graph[source][entrance] = Integer.MAX_VALUE;
        }
        // Add edges from exits to sink
        for (int exit : exits) {
            graph[exit][sink] = Integer.MAX_VALUE;
        }
        
        // Ford-Fulkerson algorithm
        int maxFlow = 0;
        while (bfs(graph, source, sink)) {
            int pathFlow = Integer.MAX_VALUE;
            int current = sink;
            while (current != source) {
                int previous = parent[current];
                pathFlow = Math.min(pathFlow, graph[previous][current]);
                current = previous;
            }
            current = sink;
            while (current != source) {
                int previous = parent[current];
                graph[previous][current] -= pathFlow;
                graph[current][previous] += pathFlow;
                current = previous;
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    // Helper function for Ford-Fulkerson algorithm
    static int[] parent;
    private static boolean bfs(int[][] graph, int source, int sink) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        parent = new int[graph.length];
        Arrays.fill(parent, -1);
        parent[source] = -2;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int i = 0; i < graph.length; i++) {
                if (graph[current][i] > 0 && parent[i] == -1) {
                    parent[i] = current;
                    queue.add(i);
                    if (i == sink) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
