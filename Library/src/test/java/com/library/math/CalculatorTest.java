package com.library.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    Calculator calculator = new Calculator();
    @Test
    void testFivePlusTwelveIsSeventeen() {
        //given
        int a = 5;
        int b = 12;

        //when
        int result = calculator.sum(a, b);

        //then
        assertEquals( 17, result );
        assertThat(result).isEqualTo(17);
    }
    @Test
    void testFivePlusNineteenIsTwentyfour() {
        //given
        int a = 5;
        int b = 19;

        //when
        int result = calculator.sum(a, b);

        //then
        assertEquals( 24, result );
    }
}