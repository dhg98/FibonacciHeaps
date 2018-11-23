package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import FibonacciHeaps.FibonacciHeap;
import FibonacciHeaps.Node;

//TODO LO QUE HAGA LLAMADAS A POP NO VA A FUNCIONAR TODAVIA AL NO ESTAR IMPLEMENTADA LA FUNCION CONSOLIDAR

public class FibonacciHeapTest {
    public static final int UPPER_BOUND = 5000;
    
    @Test
    public void testInsertAndDelete() {
        //We create the heap.
        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
        //We insert the elements [1, UPPER_BOUND] in decreasing order.
        for (int i = UPPER_BOUND; i > 0; --i) {
            Node<Integer> toInsert = new Node<>(i);
            heap.push(toInsert);
        }
        for (int i = 1; i <= UPPER_BOUND; ++i) {
            assertEquals((Integer)i, heap.top());
            heap.pop();
        }
    }
    
    @Test
    public void testUnion() {
        //We create 2 heaps
        FibonacciHeap<Integer> heap1 = new FibonacciHeap<>();
        FibonacciHeap<Integer> heap2 = new FibonacciHeap<>();
        
        //We insert the elements [1, UPPER_BOUND] in decreasing order in both heaps.
        for (int i = UPPER_BOUND; i > 0; --i) {
            Node<Integer> toInsert = new Node<>(i);
            if (i % 2 == 0) heap1.push(toInsert);
            else heap2.push(toInsert);
        }
        //We link both heaps.
        heap1.union(heap2);
        //We delete every key.
        for (int i = 1; i <= UPPER_BOUND; ++i) {
            assertEquals((Integer)i, heap1.top());
            heap1.pop();
        }
        try {
            heap1.pop();
        } catch (NullPointerException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void deleteTest() {
        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
        Node<Integer> toDelete = new Node<>(UPPER_BOUND);
        heap.push(toDelete);
        for (int i = UPPER_BOUND - 1; i > 0; --i) {
            Node<Integer> toInsert = new Node<>(i);
            heap.push(toInsert);
        }
        heap.delete(toDelete);
        while (heap.getSize() > 0) {
            assertNotEquals(toDelete.getKey(), heap.top());
            heap.pop();
        }
    }

    public void decreaseKey() {
        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
        Node<Integer> toDecrease = new Node<>(UPPER_BOUND);
        
        for (int i = UPPER_BOUND - 1; i > UPPER_BOUND/2; --i) {
            Node<Integer> toInsert = new Node<>(i);
            heap.push(toInsert);
        }
        //We do not insert UPPER_BOUND/2 value because we will
        //decrease the key later.
        for (int i = UPPER_BOUND/2 - 1; i > 0; --i) {
            Node<Integer> toInsert = new Node<>(i);
            heap.push(toInsert);
        }
        heap.decreaseKey(toDecrease, UPPER_BOUND/2);
        
        //The node we have decreased his key, should appear at UPPER_BOUND/2 iteration.
        for (int i = 1; i <= UPPER_BOUND; ++i) {
            if (i == UPPER_BOUND/2) {
                assertEquals((Integer) i, heap.top());
            }
            heap.pop();
        }
    }
}
