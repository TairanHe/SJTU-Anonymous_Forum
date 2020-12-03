package org.wkfg.utils;

public class RandomGenerator {
    public long seed;
    public long a;
    public long b;

    public RandomGenerator(long s) {
        seed = s;
        a = s;
        b = 19260817;
    }

    public long next() {
        if (seed == 0) {
            a += 1;
            return a;
        } else {
            long t = a, s = b;
            a = s;
            t ^= (t << 23);
            t ^= (t >> 17);
            t ^= (s ^ (s >> 26));
            b = t;
            return (s + t) & (Long.MAX_VALUE);

        }
    }
}
