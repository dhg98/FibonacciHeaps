package FibonacciHeaps;

import java.util.Random;

public class main {
    
    //The interval of insertions is [0, UPPER_BOUND]
    public final static int UPPER_BOUND = 200000;
    public final static int NUMBER_INSERTIONS = 7500;
    public final static int RATIO = 2; //4 Insertions, 1 deletion.
    public final static int NUMBER_ITERATIONS = 1500;

    public static void main(String... args) {
        deleteMinAmortizedTime();
    }
    
    public static void deleteMinAmortizedTime() {
        Random rand = new Random();
        
        long totalTime = 0;
        
        for (int i = 0; i < NUMBER_ITERATIONS; ++i) {
            FamilyHeaps<Integer> fam = new FamilyHeaps<>();
                        
            //This two operations are to create the Fibonacci Heap number 1.
            //We create one Fibonacci Heap by executing this instruction.
            fam.push(rand.nextInt(UPPER_BOUND + 1));
            //We delete the minimum.
            fam.pop(1);
            
            
            long initTime = System.nanoTime();
            int j = 0;
            int totalInsertions = 0;
            while (totalInsertions < NUMBER_INSERTIONS) {
                if (j < RATIO) {
                    try {
                        //We insert the number the Random Object gives us to
                        //the Fibonacci Heap 1 (the only one we will have).
                        fam.push(rand.nextInt(UPPER_BOUND + 1), 1);
                        totalInsertions++;
                    } catch (IllegalArgumentException e) {
                        //It is okay, we will not raise the Exception, but just continue.
                    }
                } else {
                    fam.pop(1);
                }
                j = (j + 1) % (RATIO + 1);
            }
            long endTime = System.nanoTime();
            totalTime += endTime - initTime;
        }
        totalTime = totalTime / NUMBER_ITERATIONS;
        
        System.out.println("Total deleteMin time: " + totalTime);
        System.out.println("Amortized deleteMin time: " + totalTime / (NUMBER_INSERTIONS + (NUMBER_INSERTIONS) / RATIO));
    }

}