package com.github.subh0m0y.bignum;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
class BitHacks {
    static int bits(final int[] a) {
        // Find the location of the first non-zero byte
        int n = a.length - 1;
        while (a[n] == 0) {
            n++;
        }
        // n is now the index of the highest non-zero int
        // in the array. It is also equal to the number of
        // non-zero ints in the array - 1.
        return n * Integer.SIZE
                + (Integer.SIZE - Integer.numberOfLeadingZeros(a[n]));
    }

    static void shiftRight(final int[] source, final int[] destination,
                           final int amount) {
        // nInts = number of ints (actual indices in the array) to be shifted.
        int nInts = amount >>> 5;
        // nBits = number of bits every element needs to be shifted.
        int nBits = amount & 0b11111;

    }
}
