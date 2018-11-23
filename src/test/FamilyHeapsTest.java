package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import FibonacciHeaps.FamilyHeaps;

public class FamilyHeapsTest {
    public static final int UPPER_BOUND = 5000;    
    
    @Test
    public void DecreaseKeyTest() {
        FamilyHeaps<Integer> family = new FamilyHeaps<>();
        for (int i = 2; i < UPPER_BOUND; ++i) {
            family.push(i);
        }
        
        try {
            family.decreaseKey(UPPER_BOUND, 1);
            fail("The old key does not exist");
        } catch (IllegalArgumentException e) {
            //OK
        }
        try {
            family.decreaseKey(3, 2);
            fail("There is already one key with such value in the family");
        } catch (IllegalArgumentException e) {
            //OK
        }
        family.decreaseKey(UPPER_BOUND - 1, 1);
        family.top(UPPER_BOUND - 2);
    }
    
    @Test
    public void DeleteTest() {
        FamilyHeaps<Integer> family = new FamilyHeaps<>();
        for (int i = 1; i < UPPER_BOUND; ++i) {
            family.push(i);
        }
        for (int i = UPPER_BOUND; i < UPPER_BOUND * 2 - 1; ++i) {
            family.push(i, (i % UPPER_BOUND) + 1);
        }
        
        try {
            family.delete(UPPER_BOUND * 2);
            fail ("The key does not exist");
        } catch (IllegalArgumentException e) {
            //It is OK
        }
        
        for (int i = UPPER_BOUND; i < UPPER_BOUND * 2 - 1; ++i) {
            family.delete(i);
        }
        
        for (int i = 1; i < UPPER_BOUND; ++i) {
            assertEquals((Integer) i, family.top(i));
            assertEquals(1, family.size(i));
        }
    }
    
    @Test
    public void pushTest() {
        FamilyHeaps<Integer> family = new FamilyHeaps<>();
        for (int i = 1; i < UPPER_BOUND; ++i) {
            family.push(i);
        }
        try {
            family.push(1);
            fail("Can not intsert duplicates");
        } catch (IllegalArgumentException e) {
            //It is OK
        }
        try {
            family.push(2, 1);
            fail("Can not intsert duplicates");
        } catch (IllegalArgumentException e) {
            //It is OK
        }
        try {
            family.push(1, UPPER_BOUND + 1);
            fail ("The heap in which you are trying to insert does not exist");
        } catch (IllegalArgumentException e) {
            //It is OK
        }
    }

}
