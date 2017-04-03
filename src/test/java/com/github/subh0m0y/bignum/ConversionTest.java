package com.github.subh0m0y.bignum;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class ConversionTest {
    private BigInteger bigInteger;

    @BeforeMethod
    public void setUp() throws Exception {
        Random random = new Random();
        bigInteger = new BigInteger(Utils.getRandomBitSize(random), random);
        if (bigInteger.signum() < 0) {
            bigInteger = bigInteger.negate();
        }
    }

    @Test
    public void testFromBytesAndToHexString() throws Exception {
        int[] number = Conversion.fromBytes(bigInteger.toByteArray());
        assertEquals(Conversion.toHexString(number), bigInteger.toString(16));
    }

    @Test
    public void testFromHexStringAndToBytes() throws Exception {
//        System.out.println(bigInteger.toString(16));
        int[] array = Conversion.fromHexString(bigInteger.toString(16));
//        System.out.println(Conversion.toHexString(array));
//        System.out.println(Arrays.toString(bigInteger.toByteArray()));
//        System.out.println(Arrays.toString(Conversion.toBytes(array)));
        assertEquals(Conversion.toBytes(array), bigInteger.toByteArray());
    }
}