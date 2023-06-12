package com.sap.cc.unittesting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RomanNumeralsTest {

    private RomanNumerals romanNumerals;

    @BeforeEach
    public void setup() {
        romanNumerals = new RomanNumerals();
    }

    @Test
    public void exampleTest() {
        assertThat(40 + 2).isEqualTo(42);
    }

    @Test
    public void toArabic_I_1() {
        assertThat(romanNumerals.toArabic("I")).isEqualTo(1);
    }

    @Test
    public void toArabic_V_5() {
        assertThat(romanNumerals.toArabic("V")).isEqualTo(5);
    }

    @Test
    public void toArabic_M_1000() {
        assertThat(romanNumerals.toArabic("V")).isEqualTo(5);
    }

    @Test
    public void toArabic_T_ErrorValue() {
        assertThat(romanNumerals.toArabic("T")).isEqualTo(-1);
    }

    @Test
    public void toArabic_$_ErrorValue() {
        assertThat(romanNumerals.toArabic("$")).isEqualTo(-1);
    }

    @Test
    public void toArabic_emptyString_ErrorValue() {
        assertThat(romanNumerals.toArabic("")).isEqualTo(-1);
    }

    @Test
    public void toArabic_null_ErrorValue() {
        assertThat(romanNumerals.toArabic(null)).isEqualTo(-1);
    }

    @Test
    public void toArabic_1_ErrorValue() {
        assertThat(romanNumerals.toArabic("1")).isEqualTo(-1);
    }

    @Test
    public void toArabic_42_ErrorValue() {
        assertThat(romanNumerals.toArabic("42")).isEqualTo(-1);
    }

    @Test
    public void toArabic_II_2() {
        assertThat(romanNumerals.toArabic("II")).isEqualTo(1 + 1);
    }

    @Test
    public void toArabic_VI_6() {
        assertThat(romanNumerals.toArabic("VI")).isEqualTo(5 + 1);
    }

    @Test
    public void toArabic_CXI_111() {
        assertThat(romanNumerals.toArabic("CXI")).isEqualTo(100 + 10 + 1);
    }

    @Test
    public void toArabic_IV_4() {
        assertThat(romanNumerals.toArabic("IV")).isEqualTo(5 - 1);
    }

    @Test
    public void toArabic_XL_40() {
        assertThat(romanNumerals.toArabic("XL")).isEqualTo(50 - 10);
    }

    @Test
    public void toArabic_XC_90() {
        assertThat(romanNumerals.toArabic("XC")).isEqualTo(100 - 10);
    }

    @Test
    public void toArabic_XIV_14() {
        assertThat(romanNumerals.toArabic("XIV")).isEqualTo(10 + 5 - 1);
    }

    @Test
    public void toArabic_CMXL_940() {
        assertThat(romanNumerals.toArabic("CMXL")).isEqualTo(1000 - 100 + 50 - 10);
    }

    @Test
    public void toArabic_MMXXI_940() {
        assertThat(romanNumerals.toArabic("MMXXI")).isEqualTo(1000 + 1000 + 10 + 10 + 1);
    }

}
