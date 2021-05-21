package com.codewithme.bhochhi;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier
import spock.lang.Specification;

public class SequenceSpec extends Specification {


   def "whenGeneratingNumbersWithTuplesState_thenFibonacciSequenceIsProduced"() {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        Flux<Integer> fibonacciFlux = sequenceGenerator.generateFibonacciWithTuples().take(5);

        StepVerifier.create(fibonacciFlux)
                .expectNext(0, 1, 1, 2, 3)
                .expectComplete()
                .verify();
    }
}
