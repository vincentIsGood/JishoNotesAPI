package com.vincentcodes.jishoapi.subtests;

import com.vincentcodes.jishoapi.helpers.JishoSearchExpressionProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchExpressionMatchingTest {
    @Test
    public void test_expression_processor_for_true_1(){
        String sample = "事前に連携コードを発行しておくことで移行先の端末に引き継ぐことができます — ブルアカ";
        String expr = "連携&&事前||asd";
        assertTrue(JishoSearchExpressionProcessor.matches(sample, expr));
    }

    @Test
    public void test_expression_processor_for_true_2(){
        String sample = "事前に連携コードを発行しておくことで移行先の端末に引き継ぐことができます — ブルアカ";
        String expr = "連携&&asd||コード";
        assertTrue(JishoSearchExpressionProcessor.matches(sample, expr));
    }

    @Test
    public void test_expression_processor_for_true_3(){
        String sample = "事前に連携コードを発行しておくことで移行先の端末に引き継ぐことができます — ブルアカ";
        String expr = "qwe||発行&&asd||コード";
        assertTrue(JishoSearchExpressionProcessor.matches(sample, expr));
    }

    @Test
    public void test_expression_processor_for_false_1(){
        String sample = "事前に連携コードを発行しておくことで移行先の端末に引き継ぐことができます — ブルアカ";
        String expr = "連携&&asd||wea";
        assertFalse(JishoSearchExpressionProcessor.matches(sample, expr));
    }

    @Test
    public void test_expression_processor_for_false_2(){
        String sample = "事前に連携コードを発行しておくことで移行先の端末に引き継ぐことができます — ブルアカ";
        String expr = "qwe&&asd||コード";
        assertFalse(JishoSearchExpressionProcessor.matches(sample, expr));
    }
}
