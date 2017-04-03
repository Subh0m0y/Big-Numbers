package com.github.subh0m0y.bignum;

/**
 * A class that does unchecked, raw operations with integer arrays.
 * The numbers are stored in little endian form, with the least
 * significant word having the least index, i.e. zero and so on.
 * <p>
 * This class handles basic arithmetic like addition (multi-word
 * +multi-word or multi-word+single-word), subtraction (similar)
 * and multiplication (the quadratic algorithm).
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
class BasicArithmetic {

    /**
     * This is the bit mask that is used to extract data from
     * the built-in signed int types as 32bit unsigned data.
     * Without this, the system would try to work with negative
     * numbers, which is not desirable in this case.
     */
    private static final long INT_SATURATED = 0xFFFFFFFFL;

    /**
     * This is the "base of all operations". Literally.
     */
    private static final long RADIX = 0x100000000L;

    /**
     * Adds the contents of array a and b into c. This assumes that
     * c is large enough to accommodate the contents.
     * <p>
     * How to set the size of c:
     * Just make sure that the length of c is max(len(a), len(b)) + 1.
     *
     * @param a First number.
     * @param b Second number.
     * @param c The storage array.
     */
    static void add(final int[] a, final int[] b, final int[] c) {
        boolean comparison = a.length > b.length;
        int[] a0 = comparison ? a : b;
        int[] b0 = comparison ? b : a;
        long carry = 0;
        int index = 0;
        while (index < a0.length) {
            long A = a0[index] & INT_SATURATED;
            long B = index < b0.length ? b0[index] & INT_SATURATED : 0;
            long sum = A + B + carry;
            carry = sum >>> Integer.SIZE;
            c[index++] = (int) (sum & INT_SATURATED);
        }
        if (carry != 0) {
            c[index] = (int) carry;
        }
    }


    /**
     * Subtracts the number in b from a and stores the difference in c.
     * This assumes that a is bigger in magnitude than b.
     * <p>
     * In this case, the size of array c must at least be equal to that
     * of the large array, i.e. a.
     *
     * @param a The larger number to be subtracted from.
     * @param b The smaller number to be subtracted.
     * @param c The storage for the new number.
     */
    static void subtract(final int[] a, final int[] b, final int[] c) {
        boolean borrow = false;
        for (int index = 0; index < a.length; index++) {
            long A = a[index] & INT_SATURATED;
            long B = index < b.length ? b[index] & INT_SATURATED : 0;
            B += borrow ? 1 : 0;
            long diff = borrow ? RADIX + A - B : A - B;
            borrow = A < B || borrow && A == B;
            c[index] = (int) (diff & INT_SATURATED);
        }
    }

    /**
     * Adds the given word to the number represented in a.
     *
     * @param a The number to add to.
     * @param b The word to add.
     * @param c The storage for the sum.
     */
    static void addWord(final int[] a, final int b, final int[] c) {
        long word = b & INT_SATURATED;
        int index = 0;
        while (index < a.length) {
            long sum = (a[index] & INT_SATURATED) + word;
            c[index] = (int) (sum & INT_SATURATED);
            word = sum >>> Integer.SIZE;
            index++;
        }
        if (word != 0) {
            c[index] = (int) word;
        }
    }

    /**
     * Calculates the product of the given number in a and the given
     * word in b and stores the product in c.
     * <p>
     * The size of c must be at least len(a) + 1.
     *
     * @param a The multiplicand.
     * @param b The multiplier.
     * @param c The storage.
     */
    static void multiplyWord(final int[] a, final int b, final int[] c) {
        final long multiplier = b & INT_SATURATED;
        int index = 0;
        long carry = 0;
        while (index < a.length) {
            long A = a[index] & INT_SATURATED;
            long partial = (A * multiplier) + carry;
            c[index] = (int) (partial & INT_SATURATED);
            carry = partial >>> Integer.SIZE;
            index++;
        }
        if (carry != 0) {
            c[index] = (int) carry;
        }
    }

    /**
     * This method calculates the product of a and b and ADDS it to
     * the existing value in c. Hence "augment". If you just want the
     * product of a and b, pass in a new array for c.
     * <p>
     * The size of c must be at least len(a) + len(b) + 1.
     * <p>
     * NOTE: This is the naive algorithm with a quadratic runtime.
     *
     * @param a The first multiplicand.
     * @param b The second multiplicand.
     * @param c The number to "augment" to.
     */
    static void multiplyAndAugment(final int[] a, final int[] b, final int[] c) {
        for (int j = 0; j < b.length; j++) {
            int index = 0;
            long multiplier = b[j] & INT_SATURATED;
            long carry = 0;
            while (index < a.length) {
                long partial = (a[index] & INT_SATURATED) * multiplier
                        + carry
                        + (c[index + j] & INT_SATURATED);
                c[index + j] = (int) (partial & INT_SATURATED);
                carry = partial >>> Integer.SIZE;
                index++;
            }
            if (carry != 0) {
                c[index + j] = (int) carry;
            }
        }
    }
}
