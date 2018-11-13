package FibonacciHeaps;

public class FibonacciHeap<T extends Comparable <T>> {
    private Node<T> min = null;
    private int size = 0;
    
    //There is no need to type a Constructor, we initialize the attributes in the declaration.
    
    public Node<T> getMin() {
        return min;
    }

    public int getSize() {
        return size;
    }
    
    private void insertLeftMinimum(Node<T> node) {
        min.leftSibling.rightSibling = node; 
        node.leftSibling = min.leftSibling;
        node.rightSibling = min;
        min.leftSibling = node;
    }

    public void push (T elem) {
        Node<T> toInsert = new Node<>(elem);   
        //If the heap is empty, we have to connect the minimum to toInsert;
        if (min == null) {
            //We do not have any other sibling, so he is his self sibling.
            toInsert.leftSibling = toInsert;
            toInsert.rightSibling = toInsert;
            min = toInsert;
        } else {
            //We insert the Node to the left of the minimum.
            insertLeftMinimum(toInsert);
            //Update of the minimum
            if (min.key.compareTo(toInsert.key) > 0) {
                min = toInsert;                
            }
        }
        size++;
    }
    
    public T top() {
        if (min == null) {
            throw new NullPointerException ("The Heap is empty");
        } else {
            return min.key;
        }
    }
    
    public void union (FibonacciHeap<T> otherHeap) {
        if (otherHeap != null) {
            if (min == null) {
                min = otherHeap.min;
            } else {
                Node<T> minOther = otherHeap.min;
                Node<T> leftNode = min.leftSibling;
                min.leftSibling.rightSibling = minOther;
                minOther.leftSibling.rightSibling = min;
                min.leftSibling = minOther.leftSibling;
                minOther.leftSibling = leftNode;
                //Update the minimum
                if (min.key.compareTo(minOther.key) > 0) {
                    min = minOther;
                }
            }
            size += otherHeap.size;
        }
    }
    
    public T pop() {
        if (min == null) throw new NullPointerException("The heap is empty");
        else {
            Node<T> minimum = min;
            
            
            return minimum.key;
        }
    }
    
    public void decreaseKey(Node<T> node, T newKey) {
        if (node.key.compareTo(newKey) < 0) {
            throw new IllegalArgumentException("The new key " + '(' + newKey + ')' +
                    " is greater than the old one " + '(' + node.key + ')');
        } else {
            node.key = newKey;
            Node<T> y = node.father;
            //If the node has to go up in the tree
            if (y != null && node.key.compareTo(y.key) < 0) {
                cut(node, y);
                cascadingCut(y);
            }
            //Update minimum
            if (node.key.compareTo(min.key) < 0) {
                min = node;
            }
        }
    }
    
    private void cascadingCut(Node<T> y) {
        Node<T> z = y.father;
        if (z != null) {
            if (!y.marked) {
                y.marked = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }
    
    private void cut(Node<T> child, Node<T> father) {
        //If the Node is his only sibling, if we delete it, his father will have no children.
        if (child.getLeftSibling() != child) {
            Node<T> left = child.leftSibling;
            Node<T> right = child.rightSibling;
            left.rightSibling = right;
            right.leftSibling = left;
        } else {
            father.child = null;
        }
        //If this child was the one the father had assigned, we have to change it (with the right one).
        if (father.child == child) {
            father.child = child.rightSibling;
        }
        
        father.degree--;
        
        //Add child to the left of the minimum
        insertLeftMinimum(child);
        
        child.father = null;
        child.marked = false;
    }
    
}
