package com.chenmeng.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author chenmeng
 */
@SpringBootTest
public class BigDecimalTest {

    @Test
    void testConstructor() {
        System.out.println("==================构造器==================");

        // 通过 int 值构造
        BigDecimal intBig = new BigDecimal(10);
        System.out.println("intBig = " + intBig);

        // 通过 double 值构造，会有精度问题（不推荐）
        BigDecimal doubleBig = new BigDecimal(20.45);
        System.out.println("doubleBig = " + doubleBig);

        // 通过 long 值构造
        BigDecimal longBig = new BigDecimal(30L);
        System.out.println("longBig = " + longBig);

        // 通过 String 值构造（推荐）
        BigDecimal stringBig = new BigDecimal("123.45");
        System.out.println("stringBig = " + stringBig);

        // 将 double 值转换成 String 类型再构造
        Double doubleValue = new Double(20.45);
        BigDecimal doubleBig2 = new BigDecimal(doubleValue.toString());
        System.out.println("doubleBig2 = " + doubleBig2);

        // intBig = 10
        // doubleBig = 20.449999999999999289457264239899814128875732421875
        // longBig = 30
        // stringBig = 123.45
        // doubleBig2 = 20.45
    }

    @Test
    void testAsmd() {
        System.out.println("==================加减乘除==================");

        BigDecimal value1 = new BigDecimal("100.00");
        BigDecimal value2 = new BigDecimal("12.34");

        // 加法
        BigDecimal sum = value1.add(value2); // 112.34
        System.out.println("sum = " + sum);

        // 减法
        BigDecimal difference = value1.subtract(value2); // 87.66
        System.out.println("difference = " + difference);

        // 乘法
        BigDecimal product = value1.multiply(value2); // 1234.0000
        System.out.println("product = " + product);

        // 除法（四舍五入，保留两位小数）
        BigDecimal quotient = value1.divide(value2, 2, RoundingMode.HALF_UP); // 8.10
        System.out.println("quotient = " + quotient);

        // sum = 112.34
        // difference = 87.66
        // product = 1234.0000
        // quotient = 8.10
    }

    @Test
    void testRoundingMode() {
        System.out.println("==================舍入模式==================");
        // 注意：断言请求的操作具有精确的结果，因此【不需要舍入】。
        // 如果对获得精确结果的操作指定此舍入模式，则抛出 ArithmeticException。 -- 如果这里小数选择保留 1 位就会抛异常
        BigDecimal bigDecimal = new BigDecimal("1.55").setScale(2, RoundingMode.UNNECESSARY);
        System.out.println("Rounding mode: " + RoundingMode.UNNECESSARY + ", Value: " + bigDecimal);

        testRoundingMode(new BigDecimal("1.55"), RoundingMode.values());

        /* Rounding mode: UNNECESSARY, Value: 1.55
        Rounding mode: UP, Value: 1.6
        Rounding mode: DOWN, Value: 1.5
        Rounding mode: CEILING, Value: 1.6
        Rounding mode: FLOOR, Value: 1.5
        Rounding mode: HALF_UP, Value: 1.6
        Rounding mode: HALF_DOWN, Value: 1.5
        Rounding mode: HALF_EVEN, Value: 1.6 */
    }

    private static void testRoundingMode(BigDecimal value, RoundingMode[] modes) {
        for (RoundingMode mode : modes) {
            BigDecimal roundedValue = value.setScale(1, mode);
            System.out.printf("Rounding mode: %s, Value: %s%n", mode, roundedValue);
        }
    }

    @Test
    void testCompare() {
        System.out.println("==================比较操作==================");
        BigDecimal value = new BigDecimal("100.00");

        BigDecimal value1 = new BigDecimal("99.00");
        BigDecimal value2 = new BigDecimal("100.00");
        BigDecimal value3 = new BigDecimal("101.00");

        // 比较值
        int cmp1 = value.compareTo(value1);
        int cmp2 = value.compareTo(value2);
        int cmp3 = value.compareTo(value3);
        System.out.println("cmp1 = " + cmp1); // 1
        System.out.println("cmp2 = " + cmp2); // 0
        System.out.println("cmp3 = " + cmp3); // -1

        // 检查是否相等
        boolean isEqual1 = value.equals(value1);
        boolean isEqual2 = value.equals(value2);
        boolean isEqual3 = value.equals(value3);
        System.out.println("isEqual1 = " + isEqual1); // false
        System.out.println("isEqual2 = " + isEqual2); // true
        System.out.println("isEqual3 = " + isEqual3); // false

        // 检查是否大于
        boolean isGreaterThan1 = value.compareTo(value1) > 0;
        boolean isGreaterThan2 = value.compareTo(value2) > 0;
        boolean isGreaterThan3 = value.compareTo(value3) > 0;
        System.out.println("isGreaterThan1 = " + isGreaterThan1); // true
        System.out.println("isGreaterThan2 = " + isGreaterThan2); // false
        System.out.println("isGreaterThan3 = " + isGreaterThan3); // false
    }

    @Test
    void testFormat() {
        System.out.println("==================格式化输出==================");

        DecimalFormat df = new DecimalFormat("#,##0.00");
        double number1 = 123456.789;
        double number2 = 1233123456.783;
        String formattedNumber1 = df.format(number1); // 123,456.79
        String formattedNumber2 = df.format(number2); // 1,233,123,456.78
        System.out.println("Formatted Number1: " + formattedNumber1);
        System.out.println("Formatted Number2: " + formattedNumber2);
    }

    public static void main(String[] args) {

    }
}
