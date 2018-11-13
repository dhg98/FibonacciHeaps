
public class Node<T extends Comparable<T>> {
    private T key;
    private int degree = 0;             //Height of the tree 
    private Node<T> child = null;          //One of the child (used to reach the children of this Node).
    private Node<T> leftSibling = null;    //Left and right sibling to reach the siblings of one node.
    private Node<T> rightSibling = null;   
    private Node<T> father = null;         //Father to go up in the tree.
    private boolean marked = false;     //Whether this Node has lost a child or not.
    
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

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }

    public void setLeftSibling(Node<T> leftSibling) {
        this.leftSibling = leftSibling;
    }

    public void setRightSibling(Node<T> rightSibling) {
        this.rightSibling = rightSibling;
    }

    public void setFather(Node<T> father) {
        this.father = father;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setChild(Node<T> child) {
        this.child = child;
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

    public void setKey(T key) {
        this.key = key;
    }
       
    
    
    
}
