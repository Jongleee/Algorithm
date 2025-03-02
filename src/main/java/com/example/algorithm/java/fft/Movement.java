package com.example.algorithm.java.fft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Movement {
    static class Complex {
        double real, imag;

        Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        Complex add(Complex o) {
            return new Complex(real + o.real, imag + o.imag);
        }

        Complex subtract(Complex o) {
            return new Complex(real - o.real, imag - o.imag);
        }

        Complex multiply(Complex o) {
            return new Complex(real * o.real - imag * o.imag, real * o.imag + imag * o.real);
        }

        Complex divide(double val) {
            return new Complex(real / val, imag / val);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[] x = new long[n * 2];
        long[] y = new long[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            x[i] = Long.parseLong(st.nextToken());
            x[i + n] = x[i];
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            y[n - i - 1] = Long.parseLong(st.nextToken());
        }

        long[] result = multiply(x, y);

        long maxScore = 0;
        for (int i = n - 1; i < 2 * n - 1; i++) {
            maxScore = Math.max(maxScore, result[i]);
        }

        System.out.println(maxScore);
    }

    static long[] multiply(long[] a, long[] b) {
        int n = 1;
        while (n < a.length + b.length)
            n <<= 1;

        Complex[] fa = new Complex[n];
        Complex[] fb = new Complex[n];
        Arrays.fill(fa, new Complex(0, 0));
        Arrays.fill(fb, new Complex(0, 0));

        for (int i = 0; i < a.length; i++)
            fa[i] = new Complex(a[i], 0);
        for (int i = 0; i < b.length; i++)
            fb[i] = new Complex(b[i], 0);

        fft(fa, false);
        fft(fb, false);

        for (int i = 0; i < n; i++)
            fa[i] = fa[i].multiply(fb[i]);

        fft(fa, true);

        long[] result = new long[n];
        for (int i = 0; i < n; i++) {
            result[i] = Math.round(fa[i].real);
        }
        return result;
    }

    static void fft(Complex[] a, boolean invert) {
        int n = a.length;
        int l = 0;
        while ((1 << l) < n) {
            l++;
        }

        for (int i = 0; i < n; i++) {
            int j = 0;
            int x = i;
            for (int k = 0; k < l; k++) {
                j = (j << 1) | (x & 1);
                x >>= 1;
            }

            if (i < j) {
                Complex temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        for (int len = 2; len <= n; len <<= 1) {
            getA(a, invert, n, len);
        }
        if (invert) {
            for (int i = 0; i < n; i++) {
                a[i] = a[i].divide(n);
            }
        }
    }

    private static void getA(Complex[] a, boolean invert, int n, int len) {
        double angle = 2 * Math.PI / len * (invert ? -1 : 1);
        Complex wLen = new Complex(Math.cos(angle), Math.sin(angle));
        for (int i = 0; i < n; i += len) {
            Complex w = new Complex(1, 0);
            for (int j = 0; j < len / 2; j++) {
                Complex u = a[i + j];
                Complex v = a[i + j + len / 2].multiply(w);
                a[i + j] = u.add(v);
                a[i + j + len / 2] = u.subtract(v);
                w = w.multiply(wLen);
            }
        }
    }
}

/*
10
23 4 95 20 17 94 63 44 13 96
87 54 13 18 61 24 17 94 53 2

28886


4
1 2 3 4
6 7 8 5

70
*/