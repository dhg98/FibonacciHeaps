package FibonacciHeaps;

import java.util.Random;

public class main {
    
    //The interval of insertions is [0, UPPER_BOUND]
    public final static int UPPER_BOUND = 20000000;
    public final static int RATIO_DELETE_MIN = 2; //2 Insertions, 1 deletion.
    
    public final static int NUMBER_ITERATIONS = 2; 
    public final static int NUMBER_INSERTIONS = 1250;
    
    public final static int RATIO_INSERT_OTHER = 2;
    public final static int NUMBER_MAX_HEAPS = 2100;
    public final static int MAX_INSERTIONS_FIRST = 80;

    public static void main(String... args) {
        //deleteMinAmortizedTime();
        decreaseInsertDeleteMin();
    }
    
    public static void deleteMinAmortizedTime() {
        Random rand = new Random();
        
        long totalTime = 0;
        
        for (int i = 0; i < NUMBER_ITERATIONS; ++i) {
            FamilyHeaps<Integer> fam = new FamilyHeaps<>();
                        
            //This two operations are to create the Fibonacci Heap number 1.
            //We create one Fibonacci Heap by executing this instruction.
            fam.push(rand.nextInt(UPPER_BOUND) + 1);
            //We delete the minimum.
            fam.pop(1);
            
            
            long initTime = System.nanoTime();
            int j = 0;
            int totalInsertions = 0;
            while (totalInsertions < NUMBER_INSERTIONS) {
                if (j < RATIO_DELETE_MIN) {
                    try {
                        //We insert the number the Random Object gives us to
                        //the Fibonacci Heap 1 (the only one we will have).
                        fam.push(rand.nextInt(UPPER_BOUND) + 1, 1);
                        totalInsertions++;
                    } catch (IllegalArgumentException e) {
                        //It is okay, we will not raise the Exception, but just continue.
                    }
                } else {
                    fam.pop(1);
                }
                j = (j + 1) % (RATIO_DELETE_MIN + 1);
            }
            long endTime = System.nanoTime();
            totalTime += endTime - initTime;
        }
        totalTime = totalTime / NUMBER_ITERATIONS;
        
        System.out.println("Total deleteMin time: " + totalTime);
        System.out.println("Amortized deleteMin time: " + totalTime / (NUMBER_INSERTIONS + (NUMBER_INSERTIONS) / RATIO_DELETE_MIN));
    }
    
    public static void decreaseInsertDeleteMin() {
        Random rand = new Random();
                
        long totalTime = 0;
        
        for (int i = 0; i < NUMBER_ITERATIONS; ++i) {
            FamilyHeaps<Integer> fam = new FamilyHeaps<>();
            
            for (int j = 0; j < NUMBER_MAX_HEAPS; ++j) {
                //We create the Heaps from 1 to NUMBER_MAX_HEAPS.
                try {
                    fam.push(rand.nextInt(UPPER_BOUND) + 1);
                } catch (IllegalArgumentException e) {
                    //It is okay.
                }
                
            }
            
            //We insert in every heap, MAX_INSERTIONS_FIRST elements.
            for (int j = 1; j <= fam.numberHeaps(); ++j) {
                for (int k = 0; k < MAX_INSERTIONS_FIRST; ++k) {
                    try {
                        fam.push(rand.nextInt(UPPER_BOUND) + 1, j);
                    } catch (IllegalArgumentException e) {
                        //It is okay.
                    }
                }
            }
            
            long initTime = System.nanoTime();
            int j = 0;
            int totalInsertions = 0;
            
            while (totalInsertions < NUMBER_INSERTIONS) {
                if (j < RATIO_INSERT_OTHER) {
                    //We insert the number the Random Object gives us to
                    //the Fibonacci Heap the Random Object gives us.
                    try {
                        fam.push(rand.nextInt(UPPER_BOUND) + 1, rand.nextInt(fam.numberHeaps()) + 1);
                        totalInsertions++;
                    } catch (IllegalArgumentException e) {
                        //We do not insert, but it is alright
                    }
                } else if (j == RATIO_INSERT_OTHER) {       //Deletion
                    fam.pop(rand.nextInt(fam.numberHeaps()) + 1);
                } else if (j == RATIO_INSERT_OTHER + 1) {   //Union
                    fam.union(rand.nextInt(fam.numberHeaps()) + 1, rand.nextInt(fam.numberHeaps()) + 1);
                } else {                                    //Decrease
                    Integer elem = fam.randomElement();
                    try {
                        fam.decreaseKey(elem, rand.nextInt(elem - 1) + 1);
                    } catch (IllegalArgumentException e) {
                        //We do not decrease any key, but it is alright.
                    }
                }
                j = (j + 1) % (RATIO_INSERT_OTHER + 3);
            }
            
            long endTime = System.nanoTime();
            totalTime += endTime - initTime;
        }
        totalTime = totalTime / NUMBER_ITERATIONS;
        
        System.out.println("Total global time: " + totalTime);
        System.out.println("Amortized global time: " + totalTime / (NUMBER_INSERTIONS + 3 * (NUMBER_INSERTIONS / RATIO_INSERT_OTHER)));
    }

}