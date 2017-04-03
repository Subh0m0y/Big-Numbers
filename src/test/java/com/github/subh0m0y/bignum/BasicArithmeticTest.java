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
public class BasicArithmeticTest {
    private BigInteger a;
    private BigInteger b;
    private Random random;

    @BeforeMethod
    public void setUp() throws Exception {
        random = new Random();
        a = new BigInteger(Utils.getRandomBitSize(random), random);
        b = new BigInteger(Utils.getRandomBitSize(random), random);
    }

    @Test
    public void testAdd() throws Exception {
        BigInteger c = a.add(b);
        int[] arrayA = Conversion.fromBytes(a.toByteArray());
        int[] arrayB = Conversion.fromBytes(b.toByteArray());
        int[] arrayC = new int[Math.max(arrayA.length, arrayB.length) + 1];
        BasicArithmetic.add(arrayA, arrayB, arrayC);
        assertEquals(c.toString(16), Conversion.toHexString(arrayC));
    }

    @Test
    public void testSubtract() throws Exception {
        if (a.compareTo(b) < 0) {
            // Swap to ensure that a >= b
            BigInteger temp = a;
            a = b;
            b = temp;
        }
        BigInteger c = a.subtract(b);
        int[] arrayA = Conversion.fromBytes(a.toByteArray());
        int[] arrayB = Conversion.fromBytes(b.toByteArray());
        int[] arrayC = new int[Math.max(arrayA.length, arrayB.length)];
        BasicArithmetic.subtract(arrayA, arrayB, arrayC);
        assertEquals(c.toString(16), Conversion.toHexString(arrayC));
    }

    @Test
    public void testAddWord() throws Exception {
        // For a
        int[] arrayA = Conversion.fromBytes(a.toByteArray());
        int[] arrayC = new int[arrayA.length + 1];
        int word = random.nextInt();
        BigInteger value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        BigInteger c = a.add(value);
        BasicArithmetic.addWord(arrayA, word, arrayC);
        assertEquals(c.toString(16), Conversion.toHexString(arrayC));
        // For b
        int[] arrayB = Conversion.fromBytes(b.toByteArray());
        arrayC = new int[arrayB.length + 1];
        word = random.nextInt();
        value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        c = b.add(value);
        BasicArithmetic.addWord(arrayB, word, arrayC);
        assertEquals(c.toString(16), Conversion.toHexString(arrayC));
    }

    @Test
    public void testMultiplyWord() throws Exception {
        // For a
        int[] arrayA = Conversion.fromBytes(a.toByteArray());
        int[] arrayC = new int[arrayA.length + 1];
        int word = random.nextInt();
        BigInteger value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        BigInteger c = a.multiply(value);
        BasicArithmetic.multiplyWord(arrayA, word, arrayC);
        assertEquals(c.toByteArray(), Conversion.toBytes(arrayC));
        assertEquals(c.toString(16), Conversion.toHexString(arrayC));
        // For b
        int[] B = Conversion.fromBytes(b.toByteArray());
        arrayC = new int[B.length + 1];
        word = random.nextInt();
        value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        c = b.multiply(value);
        BasicArithmetic.multiplyWord(B, word, arrayC);
        assertEquals(c.toByteArray(), Conversion.toBytes(arrayC));
        assertEquals(c.toString(16), Conversion.toHexString(arrayC));
    }

    @Test
    public void testMultiplyAndAugment() throws Exception {
        BigInteger c = a.multiply(b);
        BigInteger d = a.multiply(b).add(a);
        int[] A = Conversion.fromBytes(a.toByteArray());
        int[] B = Conversion.fromBytes(b.toByteArray());
        int[] C = new int[A.length + B.length];
        BasicArithmetic.multiplyAndAugment(A, B, C);
        int[] D = new int[A.length + B.length];
        System.arraycopy(A, 0, D, 0, A.length);
        BasicArithmetic.multiplyAndAugment(A, B, D);
        String cString = c.toString(16);
        String dString = d.toString(16);
        assertEquals(cString, Conversion.toHexString(C));
        assertEquals(dString, Conversion.toHexString(D));
        assertNotEquals(c, d);
    }

}