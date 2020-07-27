package ru.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreamAPI {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        System.out.println(oddOrEven(list));

        List<Integer> list1 = Arrays.asList(54, 72, 13, 46, 95, 66, 47, 8, 9, 10, 11);
        System.out.println(oddOrEven(list1));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (acc, s1) -> acc * 10 + s1);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().parallel().mapToInt((i) -> i).sum();
        return integers.stream().parallel().filter((i) -> (sum % 2 == 0) == (i % 2 != 0)).collect(Collectors.toList());
    }
}
