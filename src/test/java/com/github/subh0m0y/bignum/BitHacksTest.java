package com.github.subh0m0y.bignum;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class BitHacksTest {
    private final Random random = new Random();
    private BigInteger[] bigIntegers;

    @BeforeMethod
    public void setUp() throws Exception {
        bigIntegers = new BigInteger[Helper.SIZE];
        for (int i = 0; i < Helper.SIZE; i++) {
            bigIntegers[i] = new BigInteger(Helper.getRandomBitSize(random), random);
        }
    }

    @Test
    public void testBits() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            int length = bigIntegers[i].bitLength();
            int[] array = Conversion.fromBytes(bigIntegers[i].toByteArray());
            assertEquals(BitHacks.bits(array), length);
        }
    }

    @Test
    public void testShiftRight() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            int shift = random.nextInt(bigIntegers[i].bitLength() * 2);
            BigInteger shifted = bigIntegers[i].shiftRight(shift);

            int[] array = Conversion.fromBytes(bigIntegers[i].toByteArray());
            int[] shiftedArray = new int[array.length];
            BitHacks.shiftRight(array, shiftedArray, shift);

            assertEquals(Conversion.toHexString(shiftedArray), shifted.toString(16));
        }
    }

}