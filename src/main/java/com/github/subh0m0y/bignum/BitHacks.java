package com.github.subh0m0y.bignum;

import java.util.Arrays;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
class BitHacks {
    static int bits(final int[] a) {
        if (a.length == 0) {
            return 0;
        }
        // Find the location of the first non-zero byte
        int n = a.length - 1;
        while (n >= 0 && a[n] == 0) {
            n--;
        }
        // n is now the index of the highest non-zero int
        // in the array. It is also equal to the number of
        // non-zero ints in the array - 1.
        return n == -1
                ? 0
                : n * Integer.SIZE
                + (Integer.SIZE - Integer.numberOfLeadingZeros(a[n]));
    }

    static void shiftRight(final int[] source, int[] destination,
                           final int amount) {
        // nInts = number of ints (actual indices in the array) to be shifted.
        int nInts = amount >>> 5;
        // nBits = number of bits every element needs to be shifted.
        int nBits = amount & 0b11111;
        final int length = source.length - nInts;
        // If the amount is too big, set the result to be a zero.
        if (length < 0) {
            Arrays.fill(destination, 0);
            return;
        }
        if (nInts > 0) {
            System.arraycopy(source, nInts, destination, 0, length);
        }
        if (nBits > 0) {
            final int lowMask = (1 << nBits) - 1;
            final int carryShift = Integer.SIZE - nBits;
            int carry = 0;
            for (int i = length - 1, j = source.length - 1; i >= 0; i--, j--) {
                int tempCarry = source[j] & lowMask;
                destination[i]
                        = (carry << carryShift)
                        | (source[j] >>> nBits);
                carry = tempCarry;
            }
        }
    }
}
