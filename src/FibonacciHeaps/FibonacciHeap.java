package FibonacciHeaps;

import java.util.List;
import java.util.ArrayList;

public class FibonacciHeap<T extends Comparable <T>> {
    private Node<T> min = null;
    private int size = 0;
    
    public FibonacciHeap() {
        min = null;
        size = 0;
    }

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

    public void push (Node<T> toInsert) {
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
    
    public Node<T> pop() {
        if (min == null) throw new NullPointerException("The heap is empty");
        else {
            Node<T> minimum = min;
            Node<T> child = minimum.child;
            if (child != null) {
                //Insertion of the children in the main 'deque'.
                min.leftSibling.rightSibling = child;
                Node<T> leftSiblingChild = child.leftSibling;
                leftSiblingChild.rightSibling = min;
                child.leftSibling = min.leftSibling;
                min.leftSibling = leftSiblingChild;
                
                //Delete the parent from the children.
                while (child != min) {
                    child.father = null;
                    child = child.rightSibling;
                }                
            }
            //The minimum has got no children.
            minimum.child = null;
            
            //Delete the minimum from the list.
            minimum.leftSibling.rightSibling = minimum.rightSibling;
            minimum.rightSibling.leftSibling = minimum.leftSibling;
            
            //If there was only one Node left, we have not got to do anything.
            if (size == 1) {
                min = null;
            } else {
                //Otherwise, we have to change the minimum and consolidate.
                min = minimum.rightSibling;
                consolidate();                
            }
            size--;
            return minimum;
        }
    }
    
    private static double log(double num, int base) {
        return (Math.log10(num) / Math.log10(base));
    }
    
    private void consolidate() {
        //The size of the Array is s
        int sizeA = (int) (Math.ceil(log(size, 2)));
        List<Node<T>> A = new ArrayList<>(sizeA);
        
        
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
        if (child.getLeftSibling() != child) {
            Node<T> left = child.leftSibling;
            Node<T> right = child.rightSibling;
            left.rightSibling = right;
            right.leftSibling = left;
        } else {
            //If the Node is his only sibling, if we delete it, his father will have no children.
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
    
    public void delete(Node<T> toDelete) {
        Node<T> minimum = min;
        //Delete the minimum from the Heap
        pop();
        //Decrease the key of the Node we want to delete to the minimum of the Heap.
        decreaseKey(toDelete, minimum.key);
        //Delete the minimum
        pop();
        //Restore the minimum.
        push(minimum);        
    }
    
}
