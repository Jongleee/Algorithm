package com.example.algorithm.java.ntt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class ThinkSmall {
    public static void main(String[] args) throws Exception {
        final int SIZE = 1 << 21;
        final long P1 = 998244353L;
        final long P2 = 2281701377L;
        final long W = 3L;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        long[] a = new long[SIZE];
        long[] b = new long[SIZE];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i <= n; i++)
            a[i] = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i <= m; i++)
            b[i] = Long.parseLong(st.nextToken());

        long[] result = nttConvolution(a, b, SIZE, P1, P2, W);

        long answer = 0;
        for (long value : result)
            answer ^= value;

        bw.write(Long.toString(answer));
        bw.flush();
        bw.close();
    }

    private static long[] nttConvolution(long[] a, long[] b, int size, long p1, long p2, long w) {
        long[] fa = fft(a, 1, p1, w, size);
        long[] fb = fft(b, 1, p1, w, size);

        for (int i = 0; i < size; i++)
            fa[i] = fa[i] * fb[i] % p1;
        fa = fft(fa, -1, p1, w, size);

        long[] ga = fft(a, 1, p2, w, size);
        long[] gb = fft(b, 1, p2, w, size);

        for (int i = 0; i < size; i++)
            ga[i] = ga[i] * gb[i] % p2;
        ga = fft(ga, -1, p2, w, size);

        long m1 = modInverse(p2, p1);
        long m2 = modInverse(p1, p2);

        long[] res = new long[size];
        for (int i = 0; i < size; i++) {
            long x = fa[i];
            long y = ga[i];
            res[i] = x >= y
                    ? (y + (x - y) * m1 % p1 * p2)
                    : (x + (y - x) * m2 % p2 * p1);
        }
        return res;
    }

    private static long[] fft(long[] a, int dir, long mod, long root, int size) {
        long[] res = bitReverseCopy(a, size);
        if (dir == -1)
            root = modInverse(root, mod);

        for (int len = 2; len <= size; len <<= 1) {
            long wlen = modPow(root, (mod - 1) / len, mod);
            for (int i = 0; i < size; i += len) {
                long w = 1;
                for (int j = 0; j < len / 2; j++) {
                    long u = res[i + j];
                    long v = res[i + j + len / 2] * w % mod;
                    res[i + j] = (u + v) % mod;
                    res[i + j + len / 2] = (u - v + mod) % mod;
                    w = w * wlen % mod;
                }
            }
        }

        if (dir == -1) {
            long invSize = modInverse(size, mod);
            for (int i = 0; i < size; i++)
                res[i] = res[i] * invSize % mod;
        }
        return res;
    }

    private static long[] bitReverseCopy(long[] src, int size) {
        long[] res = new long[size];
        int logSize = Integer.numberOfTrailingZeros(size);
        for (int i = 0; i < size; i++) {
            int rev = Integer.reverse(i) >>> (32 - logSize);
            res[rev] = i < src.length ? src[i] : 0;
        }
        return res;
    }

    private static long modPow(long base, long exp, long mod) {
        long result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }

    private static long modInverse(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }
}

/*
1 1
1 2
3 2

15
*/