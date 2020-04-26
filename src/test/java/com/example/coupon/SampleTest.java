package com.example.coupon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class SampleTest {

    @Test
    public void sayhello() {
        System.out.println("Say Hello!");
        Random rand = new Random(System.currentTimeMillis());
        String newCode = String.valueOf(Math.abs(rand.nextLong())).substring(0, 11);

        newCode = Optional.of(String.valueOf(Math.abs(rand.nextLong())).substring(0, 11))
                .map(s -> s.substring(0,4).concat("-")
                        .concat(s.substring(4,8)).concat("-")
                        .concat(s.substring(8,11))).get();

        System.out.println(newCode);
    }
}
