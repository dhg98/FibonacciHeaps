package FibonacciHeaps;

public class Node<T extends Comparable<T>> {
    protected T key;
    protected int degree = 0;             //Height of the tree 
    protected Node<T> child = null;          //One of the child (used to reach the children of this Node).
    protected Node<T> leftSibling = null;    //Left and right sibling to reach the siblings of one node.
    protected Node<T> rightSibling = null;   
    protected Node<T> father = null;         //Father to go up in the tree.
    protected boolean marked = false;     //Whether this Node has lost a child or not.
    
    public Node(T key) {
        this.key = key;
    }

    public Node(T key, int degree, Node<T> child, Node<T> leftSibling, Node<T> rightSibling,
            Node<T> father, boolean marked) {
        this.key = key;
        this.degree = degree;
        this.child = child;
        this.leftSibling = leftSibling;
        this.rightSibling = rightSibling;
        this.father = father;
        this.marked = marked;
    }

    public int getDegree() {
        return degree;
    }

    public T getKey() {
        return key;
    }
    
    public Node<T> getLeftSibling() {
        return leftSibling;
    }

    public Node<T> getRightSibling() {
        return rightSibling;
    }

    public Node<T> getFather() {
        return father;
    }

    public boolean isMarked() {
        return marked;
    }

    public Node<T> getChild() {
        return child;
    }
    
}
