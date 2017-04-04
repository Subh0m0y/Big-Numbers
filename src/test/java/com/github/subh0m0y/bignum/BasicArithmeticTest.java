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
    private BigInteger[] a;
    private BigInteger[] b;
    private Random random;

    @BeforeMethod
    public void setUp() throws Exception {
        random = new Random();
        a = new BigInteger[Helper.SIZE];
        b = new BigInteger[Helper.SIZE];
        for (int i = 0; i < Helper.SIZE; i++) {
            a[i] = new BigInteger(Helper.getRandomBitSize(random), random);
            b[i] = new BigInteger(Helper.getRandomBitSize(random), random);
        }
    }

    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            BigInteger c = a[i].add(b[i]);
            int[] arrayA = Conversion.fromBytes(a[i].toByteArray());
            int[] arrayB = Conversion.fromBytes(b[i].toByteArray());
            int[] arrayC = new int[Math.max(arrayA.length, arrayB.length) + 1];
            BasicArithmetic.add(arrayA, arrayB, arrayC);
            assertEquals(c.toString(16), Conversion.toHexString(arrayC));
        }
    }

    @Test
    public void testSubtract() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            if (a[i].compareTo(b[i]) < 0) {
                // Swap to ensure that a >= b
                BigInteger temp = a[i];
                a[i] = b[i];
                b[i] = temp;
            }
            BigInteger c = a[i].subtract(b[i]);
            int[] arrayA = Conversion.fromBytes(a[i].toByteArray());
            int[] arrayB = Conversion.fromBytes(b[i].toByteArray());
            int[] arrayC = new int[Math.max(arrayA.length, arrayB.length)];
            BasicArithmetic.subtract(arrayA, arrayB, arrayC);
            assertEquals(c.toString(16), Conversion.toHexString(arrayC));
        }
    }

    @Test
    public void testAddWord() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            // For a
            int[] arrayA = Conversion.fromBytes(a[i].toByteArray());
            int[] arrayC = new int[arrayA.length + 1];
            int word = random.nextInt();
            BigInteger value = BigInteger.valueOf(word & 0xFFFFFFFFL);
            BigInteger c = a[i].add(value);
            BasicArithmetic.addWord(arrayA, word, arrayC);
            assertEquals(c.toString(16), Conversion.toHexString(arrayC));
            // For b
            int[] arrayB = Conversion.fromBytes(b[i].toByteArray());
            arrayC = new int[arrayB.length + 1];
            word = random.nextInt();
            value = BigInteger.valueOf(word & 0xFFFFFFFFL);
            c = b[i].add(value);
            BasicArithmetic.addWord(arrayB, word, arrayC);
            assertEquals(c.toString(16), Conversion.toHexString(arrayC));
        }
    }

    @Test
    public void testMultiplyWord() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            // For a
            int[] arrayA = Conversion.fromBytes(a[i].toByteArray());
            int[] arrayC = new int[arrayA.length + 1];
            int word = random.nextInt();
            BigInteger value = BigInteger.valueOf(word & 0xFFFFFFFFL);
            BigInteger c = a[i].multiply(value);
            BasicArithmetic.multiplyWord(arrayA, word, arrayC);
            assertEquals(c.toByteArray(), Conversion.toBytes(arrayC));
            assertEquals(c.toString(16), Conversion.toHexString(arrayC));
            // For b
            int[] B = Conversion.fromBytes(b[i].toByteArray());
            arrayC = new int[B.length + 1];
            word = random.nextInt();
            value = BigInteger.valueOf(word & 0xFFFFFFFFL);
            c = b[i].multiply(value);
            BasicArithmetic.multiplyWord(B, word, arrayC);
            assertEquals(c.toByteArray(), Conversion.toBytes(arrayC));
            assertEquals(c.toString(16), Conversion.toHexString(arrayC));
        }
    }

    @Test
    public void testMultiplyAndAugment() throws Exception {
        for (int i = 0; i < Helper.SIZE; i++) {
            BigInteger c = a[i].multiply(b[i]);
            BigInteger d = a[i].multiply(b[i]).add(a[i]);
            int[] A = Conversion.fromBytes(a[i].toByteArray());
            int[] B = Conversion.fromBytes(b[i].toByteArray());
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

}