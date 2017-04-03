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
    private BigInteger a;

    @BeforeMethod
    public void setUp() throws Exception {
        Random random = new Random();
        a = new BigInteger(Utils.getRandomBitSize(random), random);
    }

    @Test
    public void testFromBytesAndToHexString() throws Exception {
        int[] A = Conversion.fromBytes(a.toByteArray());
        System.out.println(a.toString(16));
        System.out.println(Conversion.toHexString(A));
        assertEquals(Conversion.toHexString(A), a.toString(16));
    }

    @Test
    public void testFromHexStringAndToBytes() throws Exception {
//        System.out.println(a.toString(16));
        int[] A = Conversion.fromHexString(a.toString(16));
//        System.out.println(Arrays.toString(a.toByteArray()));
//        System.out.println(Arrays.toString(Conversion.toBytes(A)));
        assertEquals(Conversion.toBytes(A), a.toByteArray());
    }
}