
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
    
}
