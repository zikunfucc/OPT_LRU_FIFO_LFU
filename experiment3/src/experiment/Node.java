package experiment;

import java.util.Comparator;

public class Node {
    public int key;
    public int value;
    public Node() {
        key = 0;
        value = 0;
    }
    public Node(int Key) {
        key = Key;
        value = 1;
    }
}

class NodeCompare implements Comparator<Node> {
    @Override
    public int compare(Node n1, Node n2) {
        if(n1.value > n2.value) {
            return 1;
        } else if(n1.value < n2.value) {
            return -1;
        }
        return 0;
    }
}
