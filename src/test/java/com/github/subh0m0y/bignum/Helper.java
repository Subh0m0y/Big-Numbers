package com.github.subh0m0y.bignum;

import java.util.Random;

/**
 * @author Subhomoy Haldar
 * @version 1.0
 */
class Helper {
    // Stress-testing constants
    private static final int MEAN = 1_000_000;
    private static final int DEV = 5000;
    static final int SIZE = 16;
    // Debugging constants
//    private static final int MEAN = 16384;
//    private static final int DEV = 1;
//    static final int SIZE = 10;

    static int getRandomBitSize(final Random random) {
        int dev = random.nextInt(DEV);
        return MEAN + (random.nextBoolean() ? +dev : -dev);
    }
}
