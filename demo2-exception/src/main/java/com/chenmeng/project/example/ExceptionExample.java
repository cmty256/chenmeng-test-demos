package com.chenmeng.project.example;

import lombok.SneakyThrows;

/**
 * @author chenmeng
 */
public class ExceptionExample {

    @SneakyThrows({ArrayIndexOutOfBoundsException.class})
    public static void main(String[] args) {
        String str = null;
        // NullPointerException
        int length = str.length();

        int[] arr = new int[5];
        // ArrayIndexOutOfBoundsException
        int value = arr[10];
    }
}