package FibonacciHeaps;

import java.util.HashMap;
import java.util.Map;

class struct <T extends Comparable<T>> {
    protected Node<T> node;
    protected FibonacciHeap<T> heap;
    
    public struct(Node<T> node, FibonacciHeap<T> heap) {
        this.node = node;
        this.heap = heap;
    }
}

public class FamilyHeaps<T extends Comparable<T>> {
    private Map<T, struct<T>> components = new HashMap<>();
    private Map<Integer, FibonacciHeap<T>> family = new HashMap<>();
    
    public int numberHeaps() {
        return family.size();
    }
    
    public int size(int heap) {
        if (heap > family.size() || heap <= 0) 
            throw new IllegalArgumentException("The heap does not exist");
        else {
            return family.get(heap).getSize();
        }
    }
    
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
                //We modify the map accordingly.
                components.remove(oldKey);
                components.put(newKey, old);
            }
        }
    }
    
    public void delete(T elem) {
        if (!components.containsKey(elem)) {
            throw new IllegalArgumentException("The element you are trying to delete does not exist");
        } else {
            struct<T> st = components.get(elem);
            st.heap.delete(st.node);
            components.remove(elem);
        }
    }

    public void push(T elem, int heap) {
        if (components.containsKey(elem)) {
            throw new IllegalArgumentException("The element you are trying to insert already exists");
        } else {
            if (heap > family.size() || heap <= 0) throw new IllegalArgumentException("The heap in which you are trying"
                    + " to insert does not exist");
            else {
                Node<T> nod = new Node<>(elem);
                struct<T> newS = new struct<>(nod, family.get(heap));
                components.put(elem, newS);
                //We insert the node into the heap.
                newS.heap.push(nod);
            }
        }
    }
    
    public void push(T elem) {
        if (components.containsKey(elem)) {
            throw new IllegalArgumentException("The element you are trying to insert already exists");
        } else {
            Node<T> nod = new Node<> (elem);
            FibonacciHeap<T> fib = new FibonacciHeap<>();
            struct<T> newS = new struct<T>(nod, fib);
            components.put(elem, newS);
            //Insert the node into the heap
            fib.push(nod);
            //Insert the new heap in the family.
            family.put(family.size() + 1, fib);
        }
    }
    
    public void union(int heap1, int heap2) {
        if (heap1 > family.size() || heap2 > family.size() || heap1 <= 0 || heap2 <= 0) {
            throw new IllegalArgumentException("One of the heaps you are trying to link does not exist");
        } else {
            if (heap1 != heap2) {
                FibonacciHeap<T> he1 = family.get(heap1);
                FibonacciHeap<T> he2 = family.get(heap2);
                he1.union(he2);
                
                //Now the heap associated to both heap1 and heap2 is the same.
                family.put(heap1, he1);
                family.put(heap2, he1);
            }
        }
    }
    
    public T top(int heap) {
        if (heap > family.size() || heap <= 0) {
            throw new IllegalArgumentException("The heap you are trying to consult does not exist");
        } else {
            return family.get(heap).top();
        }
    }
    
    public Node<T> pop(int heap) {
        if (heap > family.size() || heap <= 0) {
            throw new IllegalArgumentException("The heap in which you are trying to delete the minimum "
                    + "does not exist");
        } else {
            return family.get(heap).pop();
        }
    }
    
    public T randomElement() {
        //Method used to decrease some of the keys we have in the family. Only for testing purposes.
        if (components.isEmpty()) throw new NullPointerException("There are no elements inside of the family");
        for (T elem : components.keySet()) {
            return elem;
        }
        return null;
    }
    
    //For mor information, see the appendix.
    public void incrementKey(T oldKey, T newKey) {
        if (!components.containsKey(oldKey)) {
            throw new IllegalArgumentException("The element you are trying to modify does not exist");
        } else {
            //We do not admit duplicates
            if (components.containsKey(newKey)) {
                throw new IllegalArgumentException("There is already an element with such value");
            } else {
                struct<T> old = components.get(oldKey);
                old.heap.incrementKey(old.node, newKey);
                //We modify the map accordingly.
                components.remove(oldKey);
                components.put(newKey, old);
            }
        }
    }
}
