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
public class ConversionTest {
    private BigInteger[] bigIntegers;

    @BeforeMethod
    public void setUp() throws Exception {
        Random random = new Random();
        bigIntegers = new BigInteger[Helper.SIZE];
        for (int i = 0; i < Helper.SIZE; i++) {
            bigIntegers[i] = new BigInteger(Helper.getRandomBitSize(random), random);
            if (bigIntegers[i].signum() < 0) {
                bigIntegers[i] = bigIntegers[i].negate();
            }
        }
    }

    @Test
    public void testFromBytesAndToHexString() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            int[] number = Conversion.fromBytes(bigIntegers[i].toByteArray());
            assertEquals(Conversion.toHexString(number), bigIntegers[i].toString(16));
        }
    }

    @Test
    public void testFromHexStringAndToBytes() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
//        System.out.println(bigIntegers.toString(16));
            int[] array = Conversion.fromHexString(bigIntegers[i].toString(16));
//        System.out.println(Conversion.toHexString(array));
//        System.out.println(Arrays.toString(bigIntegers.toByteArray()));
//        System.out.println(Arrays.toString(Conversion.toBytes(array)));
            assertEquals(Conversion.toBytes(array), bigIntegers[i].toByteArray());
        }
    }
}