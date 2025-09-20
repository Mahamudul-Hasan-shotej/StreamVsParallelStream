package com.example.stream.service;

import com.example.stream.entity.Fruit;
import com.example.stream.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {
    private final FruitRepository fruitRepository;

    public FruitServiceImpl(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public void testStreamVsParallelStream() {
        List<Fruit> fruits = fruitRepository.findAll();
        System.out.println("Processing " + fruits.size() + " items");
        
        // Warm-up JVM
        System.out.println("Warming up...");
        for (int i = 0; i < 5; i++) {
            fruits.stream().map(Fruit::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            fruits.parallelStream().map(Fruit::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        
        // Test parameters
        int iterations = 10;
        long totalSeqTime = 0;
        long totalParallelTime = 0;
        
        for (int i = 0; i < iterations; i++) {
            // Sequential Stream
            Instant seqStart = Instant.now();
            BigDecimal seqSum = fruits.stream()
                    .map(Fruit::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            long seqTime = java.time.Duration.between(seqStart, Instant.now()).toMillis();
            totalSeqTime += seqTime;
            
            // Parallel Stream
            Instant parallelStart = Instant.now();
            BigDecimal parallelSum = fruits.parallelStream()
                    .map(Fruit::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            long parallelTime = java.time.Duration.between(parallelStart, Instant.now()).toMillis();
            totalParallelTime += parallelTime;
            
            // Verify results match
            if (!seqSum.equals(parallelSum)) {
                System.out.println("Warning: Sequential and parallel results don't match!");
            }
            
            System.out.printf("Run %d: Sequential=%dms, Parallel=%dms%n", 
                i+1, seqTime, parallelTime);
        }
        
        // Calculate averages
        double avgSeqTime = totalSeqTime / (double)iterations;
        double avgParallelTime = totalParallelTime / (double)iterations;
        double speedup = (1 - (avgParallelTime / avgSeqTime)) * 100;
        
        // Print summary
        System.out.println("\n=== Summary ===");
        System.out.printf("Average Sequential: %.2fms%n", avgSeqTime);
        System.out.printf("Average Parallel:   %.2fms%n", avgParallelTime);
        System.out.printf("Parallel was %.2f%% faster%n", speedup);
    }
}
