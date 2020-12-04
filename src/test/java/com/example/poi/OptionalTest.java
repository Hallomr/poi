package com.example.poi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class OptionalTest {
    @Test
    public void optional(){
        Optional<ArrayList<Object>> optional = Optional.of(new ArrayList<>());
        boolean present = optional.isPresent();
        System.out.println(present);
        ArrayList<Object> s = optional.get();
        System.out.println(s);
        Optional<Object> o = Optional.ofNullable(null);
        boolean present1 = o.isPresent();
        System.out.println(present1);
        Object zkx = o.orElse("zkx");
        System.out.println(zkx);
    }

    @Test
    public void streams(){
        List<String> strings = Arrays.asList("a", "b", "c", "d","a");
        boolean a = strings.stream().anyMatch((s) -> s.startsWith("a"));
        boolean b = strings.stream().allMatch((s) -> s.startsWith("b"));
        boolean e = strings.stream().noneMatch((s) -> s.startsWith("e"));
        Optional<String> reduce1 = strings.stream().reduce(String::concat);
        System.out.println(reduce1.get());
        System.out.println(a);
        System.out.println(b);
        System.out.println(e);
        long a1 = strings.stream().filter((s) -> s.startsWith("a")).count();
        System.out.println(a1);

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Optional<Integer> reduce = integers.parallelStream().reduce(Integer::sum);
        Integer integer = reduce.get();
        System.out.println(integer);
    }
}
