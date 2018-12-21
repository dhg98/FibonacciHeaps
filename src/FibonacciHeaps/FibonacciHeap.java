package FibonacciHeaps;

import java.util.List;
import java.util.ArrayList;

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
    
    private void insertInHeapLeftMinimum(Node<T> toInsert) {
        if (toInsert != null) {
            toInsert.father = null;
            if (min == null) {
                //If we do not have elements in the heap, there is now only one tree
                //and the siblings of the root is only himself.
                min = toInsert;
                min.leftSibling = min;
                min.rightSibling = min;
            } else {
                min.leftSibling.rightSibling = toInsert;
                toInsert.leftSibling = min.leftSibling;
                min.leftSibling = toInsert;
                toInsert.rightSibling = min;                
                if (min.key.compareTo(toInsert.key) == 1) {
                    min = toInsert;
                }
            }
        }
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
            insertInHeapLeftMinimum(toInsert);
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
                //We insert the element in otherHeap inside the "this" object.
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
    
    private void fibHeapLink(Node<T> y, Node<T> x) {
        //Deletion of y from the root list of the heap.        
        y.rightSibling.leftSibling = y.leftSibling;
        y.leftSibling.rightSibling = y.rightSibling;
        
        if (x.child == null) {
            //Only one children, the new one.
            x.child = y;
            //We have to update the siblings of y (this node does not have sibling).
            y.leftSibling = y;
            y.rightSibling = y;
        } else {
            //We link y with the children of x.
            y.leftSibling = x.child.leftSibling;
            x.child.leftSibling.rightSibling = y;
            y.rightSibling = x.child;
            x.child.leftSibling = y;            
        }        
        
        //The y node has been made child of the x node, so we update the attribute.
        y.father = x;
        //Update of the degree (there is now one more son)
        x.degree++;
        //The Node y is has been made child of another node, so we change his mark.
        y.marked = false;
    }
        
    private void consolidate() {
        //If the size of the heap is 0, log in every base is undefined, so we have to exclude that case.
        if (min != null) {
            //The size of the Array is sizeA
            int sizeA = (int) (2 * (Math.ceil(log(size, 2))));
            List<Node<T>> A = new ArrayList<>();
            //Every position in the array is initialized as null, which is appropriate.
            for (int i = 0; i < sizeA; ++i) {
                A.add(null);
            }
            
            Node<T> act = min, next = min.rightSibling;
            do {
                int deg = act.degree;
                while (A.get(deg) != null) {
                    Node<T> y = A.get(deg);
                    //If y.key is less than act.key
                    if (act.key.compareTo(y.key) == 1) {
                        //Exchange act with y.
                        Node<T> aux = act;
                        act = y;
                        y = aux;
                    }

                    /* If the minimum is the one we are going to put as children, we have to 
                     * change it. Otherwise, we will be losing the access to the main list. 
                     * In addition, if the next one we are going to visit is the minimum, we 
                     * modify the next one as well.
                     * */
                    
                    if (y == min) {
                        if (next == min) {
                            next = y.rightSibling;
                        }
                        min = y.rightSibling;
                    }
                    
                    //Link both trees.
                    fibHeapLink(y, act);
                    
                    A.set(deg, null);
                    deg++;
                }
                A.set(deg, act);
                act = next;
                next = next.rightSibling;
            } while (act != min);
            
            //We go over the Array and we insert every position in the Heap.
            min = null;
            for (int i = 0; i < A.size(); ++i) {
                insertInHeapLeftMinimum(A.get(i));
            }
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
        insertInHeapLeftMinimum(child);
        
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
