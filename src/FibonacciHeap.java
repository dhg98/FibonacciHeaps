
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

    public void push (T elem) {
        Node<T> toInsert = new Node<>(elem);   
        //If the heap is empty, we have to connect the minimum to toInsert;
        if (min == null) {
            //We do not have any other sibling, so he is his self sibling.
            toInsert.setLeftSibling(toInsert);
            toInsert.setRightSibling(toInsert);
            min = toInsert;
        } else {
            //We insert the Node to the left of the minimum.
            Node<T> left = min.getLeftSibling();
            left.setRightSibling(toInsert);
            toInsert.setLeftSibling(left);
            toInsert.setRightSibling(min);
            min.setLeftSibling(toInsert);
            //Update of the minimum
            if (min.getKey().compareTo(toInsert.getKey()) > 0) {
                min = toInsert;                
            }
        }
        size++;
    }
    
    public T top() {
        if (min == null) {
            throw new NullPointerException ("The Heap is empty");
        } else {
            return min.getKey();
        }
    }
    
    public void union (FibonacciHeap<T> otherHeap) {
        if (otherHeap != null) {
            if (min == null) {
                min = otherHeap.getMin();
            } else {
                Node<T> minOther = otherHeap.getMin();
                Node<T> leftNode = min.getLeftSibling();
                min.getLeftSibling().setRightSibling(minOther);
                minOther.getLeftSibling().setRightSibling(min);
                min.setLeftSibling(minOther.getLeftSibling());
                minOther.setLeftSibling(leftNode);
                //Update the minimum
                if (min.getKey().compareTo(minOther.getKey()) > 0) {
                    min = minOther;
                }
            }
            size += otherHeap.getSize();
        }
    }
    
    public T pop() {
        if (min == null) throw new NullPointerException("The heap is empty");
        else {
            Node<T> minimum = min;
            
            
            return minimum.getKey();
        }
    }
    
    public void decreaseKey(Node<T> node, T newKey) {
        if (node.getKey().compareTo(newKey) < 0) {
            throw new IllegalArgumentException("The new key " + '(' + newKey + ')' +
                    " is greater than the old one " + '(' + node.getKey() + ')');
        } else {
            node.setKey(newKey);
            Node<T> y = node.getFather();
            if (y != null && node.getKey().compareTo(y.getKey()) < 0) {
                cut(node, y);
                cascadingCut(y);
            }
            if (node.getKey().compareTo(min.getKey()) < 0) {
                min = node;
            }
        }
    }
    
    private void cascadingCut(Node<T> y) {
        Node<T> z = y.getFather();
        if (z != null) {
            if (!y.isMarked()) {
                y.setMarked(true);
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }
    
    private void cut(Node<T> child, Node<T> father) {
        //If the Node is his only sibling, if we delete it, his father will have no children.
        if (child.getLeftSibling() != child) {
            Node<T> left = child.getLeftSibling();
            Node<T> right = child.getRightSibling();
            left.setRightSibling(right);
            right.setLeftSibling(left);
        } else {
            father.setChild(null);
        }
        //If this child was the one the father had assigned, we have to change it (with the right one).
        if (father.getChild() == child) {
            father.setChild(child.getRightSibling());
        }
        
        father.setDegree(father.getDegree() - 1);
        
        //Add child to the left of the minimum
        min.getLeftSibling().setRightSibling(child);
        child.setLeftSibling(min.getLeftSibling());
        child.setRightSibling(min);
        min.setLeftSibling(child);
        
        child.setFather(null);
        child.setMarked(false);
    }
    
}
