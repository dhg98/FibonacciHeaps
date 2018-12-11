package FibonacciHeaps;

import java.util.Random;

public class main {
    
    //The interval of insertions is [0, UPPER_BOUND]
    public final static int UPPER_BOUND = 200000;
    public final static int NUMBER_INSERTIONS = 5000;
    public final static int NUMBER_MIN_DELETIONS = 4500;
    public final static int NUMBER_ITERATIONS = 10000;

    public static void main(String... args) {
        Random rand = new Random();
        
        long amortizedTime = 0, totalTime = 0;
        
        for (int i = 0; i < NUMBER_ITERATIONS; ++i) {
            FamilyHeaps<Integer> fam = new FamilyHeaps<>();
            
            
            long initTime = System.nanoTime();
            
            //We create one Fibonacci Heap by executing this instruction.
            fam.push(rand.nextInt(UPPER_BOUND + 1));
            while (fam.size(1) < NUMBER_INSERTIONS) {
                try {
                    //We insert the number the Random Object gives us to
                    //the Fibonacci Heap 1 (the only one we will have).
                    fam.push(rand.nextInt(UPPER_BOUND + 1), 1);
                } catch (IllegalArgumentException e) {
                    //It is okay, we will not raise the Exception, but just continue.
                }
            }
            for (int j = 0; j < NUMBER_MIN_DELETIONS; ++j) {
                fam.pop(1);
            }
            
            long endTime = System.nanoTime();
            
           if (totalTime < endTime - initTime) totalTime = endTime - initTime;
           if (amortizedTime < (endTime - initTime) / (NUMBER_INSERTIONS + NUMBER_MIN_DELETIONS)) 
               amortizedTime = (endTime - initTime) / (NUMBER_INSERTIONS + NUMBER_MIN_DELETIONS);
        }
        System.out.println("Total time: " + totalTime);
        System.out.println("Amortized time: " + amortizedTime);
    }

}
