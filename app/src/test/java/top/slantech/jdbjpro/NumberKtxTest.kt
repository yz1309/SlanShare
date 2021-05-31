package top.slantech.jdbjpro

import top.slantech.slannet.utils.ktx.isPureInt
import org.junit.Assert
import org.junit.Test

class NumberKtxTest {

    private fun testNumberIsPureIntInternal(number: Double, predictValue: Boolean) {
        Assert.assertEquals(predictValue, number.isPureInt())
    }

    @Test
    fun testZero() = testNumberIsPureIntInternal(0.0, true)

    @Test
    fun testPositiveZero() = testNumberIsPureIntInternal(+0.0, true)

    @Test
    fun testNegativeZero() = testNumberIsPureIntInternal(-0.0, true)

    @Test
    fun testNegativeNumber() = testNumberIsPureIntInternal(-100.0, true)

    @Test
    fun testPositiveNumber() = testNumberIsPureIntInternal(+100.0, true)

    @Test
    fun testPositiveDefaultNumber() = testNumberIsPureIntInternal(100.0, true)

    @Test
    fun testLargeDecision() = testNumberIsPureIntInternal(123.00000000000000000000000000, true)

    @Test
    fun testLargeDecisionFalse() = testNumberIsPureIntInternal(123.000000001000000000000000000, false)

    @Test
    fun testShortFalse() = testNumberIsPureIntInternal(123.002, false)

    @Test
    fun testFinite() = testNumberIsPureIntInternal(Double.POSITIVE_INFINITY, false)

    @Test
    fun testInfinite() = testNumberIsPureIntInternal(Double.NEGATIVE_INFINITY, false)

    @Test
    fun testMaxValue() = testNumberIsPureIntInternal(Double.MAX_VALUE, false)

    @Test
    fun testMinValue() = testNumberIsPureIntInternal(Double.MIN_VALUE, false)
}