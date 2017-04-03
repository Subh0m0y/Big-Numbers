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
        int[] A = Conversion.fromBytes(a.toByteArray());
        int[] B = Conversion.fromBytes(b.toByteArray());
        int[] C = new int[Math.max(A.length, B.length) + 1];
        BasicArithmetic.add(A, B, C);
        assertEquals(c.toString(16), Conversion.toHexString(C));
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
        int[] A = Conversion.fromBytes(a.toByteArray());
        int[] B = Conversion.fromBytes(b.toByteArray());
        int[] C = new int[Math.max(A.length, B.length)];
        BasicArithmetic.subtract(A, B, C);
        assertEquals(c.toString(16), Conversion.toHexString(C));
    }

    @Test
    public void testAddWord() throws Exception {
        // For a
        int[] A = Conversion.fromBytes(a.toByteArray());
        int[] C = new int[A.length + 1];
        int word = random.nextInt();
        BigInteger value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        BigInteger c = a.add(value);
        BasicArithmetic.addWord(A, word, C);
        assertEquals(c.toString(16), Conversion.toHexString(C));
        // For b
        int[] B = Conversion.fromBytes(b.toByteArray());
        C = new int[B.length + 1];
        word = random.nextInt();
        value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        c = b.add(value);
        BasicArithmetic.addWord(B, word, C);
        assertEquals(c.toString(16), Conversion.toHexString(C));
    }

    @Test
    public void testMultiplyWord() throws Exception {
        // For a
        int[] A = Conversion.fromBytes(a.toByteArray());
        int[] C = new int[A.length + 1];
        int word = random.nextInt();
        BigInteger value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        BigInteger c = a.multiply(value);
        BasicArithmetic.multiplyWord(A, word, C);
        assertEquals(c.toByteArray(), Conversion.toBytes(C));
        assertEquals(c.toString(16), Conversion.toHexString(C));
        // For b
        int[] B = Conversion.fromBytes(b.toByteArray());
        C = new int[B.length + 1];
        word = random.nextInt();
        value = BigInteger.valueOf(word & 0xFFFFFFFFL);
        c = b.multiply(value);
        BasicArithmetic.multiplyWord(B, word, C);
        assertEquals(c.toByteArray(), Conversion.toBytes(C));
        assertEquals(c.toString(16), Conversion.toHexString(C));
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