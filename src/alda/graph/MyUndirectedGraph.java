/*
    erik hörnström, erho7892
    jens plate, jepl4052
    mikael sundström, misu5792
 */

// Ändra inte på paketet
package alda.graph;

import java.util.*;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

    private int weight;

    private Map<T, Node<T>> nodeMap;
    private List<Edge<T>> edgeList;

    private Map<Node<T>, Node<T>> path;
    private ArrayDeque<T> stack;
    private ArrayDeque<T> queue;


    public MyUndirectedGraph() {
        this.nodeMap = new HashMap<>();
        this.edgeList = new ArrayList<>();
    }

    // ToDo: metod för att räkna den totala vikten

    public void getWeight() {}

    /**
     * Antalet noder i grafen.
     *
     * @return antalet noder i grafen.
     */
    public int getNumberOfNodes() {
        return nodeMap.size();
    }

    /**
     * Antalet bågar i grafen.
     *
     * @return antalet bågar i grafen.
     */
    public int getNumberOfEdges() {
        return edgeList.size();
    }

    /**
     * Lägger till en ny nod i grafen.
     *
     * @param newNode
     *            datat för den nya noden som ska läggas till i grafen.
     * @return false om noden redan finns.
     */
    public boolean add(T newNode) {

        if(nodeMap.containsKey(newNode)) {
            return false;

        } else {

            Node<T> node = new Node<>(newNode);
            nodeMap.put(newNode, node);

            return true;
        }
    }

    /**
     * Kopplar samman tvä noder i grafen. Eftersom grafen är oriktad så spelar
     * det ingen roll vilken av noderna som står först. Det är också
     * fullständigt okej att koppla ihop en nod med sig själv. Däremot tillåts
     * inte multigrafer. Om två noder kopplas ihop som redan är ihopkopplade
     * uppdateras bara deras kostnadsfunktion.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @param cost
     *            kostnaden för att ta sig mellan noderna. Denna måste vara >0
     *            för att noderna ska kunna kopplas ihop.
     * @return true om bägge noderna finns i grafen och kan kopplas ihop.
     */
    public boolean connect(T node1, T node2, int cost) {

        if(cost > 0) {

            if(nodeMap.containsKey(node1) && nodeMap.containsKey(node2)) {

                if (!isConnected(node1, node2)) {

                    Edge<T> edge = new Edge<>(nodeMap.get(node1), nodeMap.get(node2), cost);
                    edgeList.add(edge);
                    nodeMap.get(node1).edges.add(edge);
                    nodeMap.get(node2).edges.add(edge);

                    return true;
                }
                else {

                    for(Edge e : edgeList) {

                        if(e.n1.data == node1 && e.n2.data == node2 || e.n1.data == node2 && e.n2.data == node1) {

                            e.cost = cost;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Berättar om två noder är sammanbundan av en båge eller inte.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @return om noderna är sammanbundna eller inte.
     */
    public boolean isConnected(T node1, T node2) {
        for(Edge e : edgeList){
            if(e.n1.data == node1 && e.n2.data == node2
                    || e.n1.data == node2 && e.n2.data == node1)
                return true;
        }
        return false;
    }

    /**
     * Returnerar kostnaden för att ta sig mellan två noder.
     *
     * @param node1
     *            den ena noden.
     * @param node2
     *            den andra noden.
     * @return kostnaden för att ta sig mellan noderna eller -1 om noderna inte
     *         är kopplade.
     */
    public int getCost(T node1, T node2) {
        for(Edge e : edgeList) {
            if(e.n1.data == node1 && e.n2.data == node2
                    || e.n1.data == node2 && e.n2.data == node1)
                return e.cost;
        }
        return -1;
    }

    /**
     * Gär en djupet-först-sökning efter en väg mellan två noder.
     *
     * Observera att denna metod inte använder sig av viktinformationen.
     *
     * @param start
     *            startnoden.
     * @param end
     *            slutnoden.
     * @return en lista över alla noder på vägen mellan start- och slutnoden. Om
     *         ingen väg finns är listan tom.
     */
    public List<T> depthFirstSearch(T start, T end) {

        path = new HashMap<>();
        stack = new ArrayDeque<>();

        if (!nodesExists(start, end)) {
            return new ArrayList<>();

        } else {
            Node<T> startNode = nodeMap.get(start);
            Node<T> endNode = nodeMap.get(end);

            return findDepthFirst(startNode, endNode);
        }
    }

    private List<T> findDepthFirst(Node<T> startNode, Node<T> endNode) {

        stack.push(startNode.data);
        startNode.visited = true;

        if(startNode == endNode)
            return makeList(stack);

        while(!stack.isEmpty()) {
            if(nodeMap.get(stack.peek()).hasUnvisitedNeighbour()) {
                Node<T> neighbour = nodeMap.get(stack.peek()).getUnvisitedNeighbour();
                stack.push(neighbour.data);
                neighbour.visited = true;

                if(neighbour.data == endNode.data) {
                    break;
                }
            }
            else {
                stack.pop();
            }
        }
        return makeList(stack);
    }

    private boolean nodesExists(T node1, T node2) {
        return nodeMap.containsKey(node1) && nodeMap.containsKey(node2);
    }

    /**
     * Gär en bredden-först-sökning efter en väg mellan två noder.
     *
     * Observera att denna metod inte använder sig av viktinformationen. Ni ska
     * alltså inte implementera Dijkstra eller A*.
     *
     * @param start
     *            startnoden.
     * @param end
     *            slutnoden.
     * @return en lista över alla noder på vägen mellan start- och slutnoden. Om
     *         ingen väg finns är listan tom.
     */
    public List<T> breadthFirstSearch(T start, T end) {

        path = new HashMap<>();
        queue = new ArrayDeque<>();

        if (!nodesExists(start, end)) {
            return new ArrayList<>();

        } else {
            Node<T> startNode = nodeMap.get(start);
            Node<T> endNode = nodeMap.get(end);

            return findBreadthFirst(startNode, endNode);
        }
    }

    private List<T> findBreadthFirst(Node<T> startNode, Node<T> endNode) {

        queue.add(startNode.data);
        startNode.visited = true;

        path = new HashMap<>();
        List<T> returnList = new ArrayList<>();

        if(startNode == endNode)
            return makeList(queue);

        while(!queue.isEmpty()) {

            Node<T> first = nodeMap.get(queue.pollFirst());

            if (first.hasUnvisitedNeighbour()) {

                for (Node<T> node : first.getUnvisitedNeighbourNodes()) {
                    queue.add(node.data);
                    node.visited = true;
                    path.put(node, first);

                    if (node == endNode) {
                        break;
                    }
                }

                if (nodeMap.get(queue.getLast()) == endNode) {
                    break;
                }
            }
        }

        while (endNode != startNode) {
            returnList.add(0, endNode.data);
            endNode = path.get(endNode);
        }

        returnList.add(0, startNode.data);

        return returnList;
    }


    private List<T> makeList(ArrayDeque<T> stack) {
        ArrayList<T> pathList = new ArrayList<>();
        for(T node : stack) {
            pathList.add(node);
        }
        Collections.reverse(pathList);
        return pathList;
    }

    /**
     * Returnerar en ny graf som utgär ett minimalt spännande träd till grafen.
     * Ni kan förutsätta att alla noder ingär i samma graf.
     *
     * @return en graf som representerar ett minimalt spånnande träd.
     */
    public UndirectedGraph<T> minimumSpanningTree() {

        return null;
    }

    private UndirectedGraph<T> kruskals(List<Edge<T>> allEdges, int numNodes) {

        return null;
    }

    private class Node<T> {

        private T data;
        private List<Edge<T>> edges;
        private boolean visited;

        public Node(T data) {
            this.data = data;
            this.edges = new ArrayList<>();
        }

        public boolean hasUnvisitedNeighbour() {

            for(Edge<T> edge : edges) {
                if(edge.n1.visited == false || edge.n2.visited == false)
                    return true;
            }
            return false;
        }

        public Node<T> getUnvisitedNeighbour() {
            for(Edge<T> node : edges) {
                if(node.n1.visited == false) {
                    return node.n1;
                }
                else if (node.n2.visited == false) {
                    return node.n2;
                }
            }
            return null;
        }

        public List<Node<T>> getUnvisitedNeighbourNodes() {
            List<Node<T>> unvisited = new ArrayList<>();

            for(Edge<T> edge : edges) {
                if(edge.n1.visited == false) {
                    unvisited.add(edge.n1);
                    edge.n1.visited = true;
                }
                else if (edge.n2.visited == false) {
                    unvisited.add(edge.n2);
                    edge.n2.visited = true;
                }
            }
            return unvisited;
        }
    }

    private class Edge<T> {

        private int cost;
        private Node<T> n1;
        private Node<T> n2;

        public Edge(Node<T> n1, Node<T> n2, int cost) {
            this.n1 = n1;
            this.n2 = n2;
            this.cost = cost;
        }

    }
}