package FibonacciHeaps;

import java.util.HashMap;
import java.util.Map;

class struct <T extends Comparable<T>> {
    protected Node<T> node;
    protected FibonacciHeap<T> heap;
}

public class FamilyHeaps<T extends Comparable<T>> {
    private Map<T, struct<T>> components = new HashMap<>();
    private Map<Integer, FibonacciHeap<T>> family = new HashMap<>();
    
    public void decreaseKey(T oldKey, T newKey) {
        if (!components.containsKey(oldKey)) {
            throw new IllegalArgumentException("The element you are trying to modify does not exist");
        } else {
            //We do not admit duplicates
            if (components.containsKey(newKey)) {
                throw new IllegalArgumentException("There is already an element with such value");
            } else {
                struct<T> old = components.get(oldKey);
                old.heap.decreaseKey(old.node, newKey);
            }
        }
    }
    
    public void delete(T elem) {
        if (!components.containsKey(elem)) {
            throw new IllegalArgumentException("The element you are trying to delete does not exist");
        } else {
            struct<T> st = components.get(elem);
            st.heap.delete(st.node);
        }
    }

    public void push(T elem) {
        if (components.containsKey(elem)) {
            throw new IllegalArgumentException("There element you are trying to insert already exists");
        } else {
            
            
        }
    }
}
