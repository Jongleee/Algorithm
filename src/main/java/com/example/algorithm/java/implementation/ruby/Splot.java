package com.example.algorithm.java.implementation.ruby;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Splot {
    enum FunctionType {
        FORWARD, BACKWARD, BOTH, THROUGH, NONE
    }

    enum SplotType {
        NODE, SERIES, PARALLEL
    }

    static class SPlot {
        private static final int NEG = -100000000;
        private static final int[][] S_RULE = {
                { FunctionType.FORWARD.ordinal(), FunctionType.FORWARD.ordinal(), FunctionType.NONE.ordinal() },
                { FunctionType.FORWARD.ordinal(), FunctionType.THROUGH.ordinal(), FunctionType.FORWARD.ordinal() },
                { FunctionType.BACKWARD.ordinal(), FunctionType.NONE.ordinal(), FunctionType.BACKWARD.ordinal() },
                { FunctionType.BACKWARD.ordinal(), FunctionType.BACKWARD.ordinal(), FunctionType.THROUGH.ordinal() },
                { FunctionType.BOTH.ordinal(), FunctionType.FORWARD.ordinal(), FunctionType.BACKWARD.ordinal() },
                { FunctionType.BOTH.ordinal(), FunctionType.BOTH.ordinal(), FunctionType.THROUGH.ordinal() },
                { FunctionType.BOTH.ordinal(), FunctionType.THROUGH.ordinal(), FunctionType.BOTH.ordinal() },
                { FunctionType.THROUGH.ordinal(), FunctionType.THROUGH.ordinal(), FunctionType.THROUGH.ordinal() },
                { FunctionType.NONE.ordinal(), FunctionType.NONE.ordinal(), FunctionType.NONE.ordinal() },
                { -1, -1, -1 }
        };

        private static final int[][] P_RULE = {
                { FunctionType.FORWARD.ordinal(), 1, 0, FunctionType.NONE.ordinal(), FunctionType.NONE.ordinal() },
                { FunctionType.FORWARD.ordinal(), 0, 0, FunctionType.FORWARD.ordinal(),
                        FunctionType.FORWARD.ordinal() },
                { FunctionType.FORWARD.ordinal(), 0, 0, FunctionType.THROUGH.ordinal(), FunctionType.BOTH.ordinal() },
                { FunctionType.FORWARD.ordinal(), 0, 0, FunctionType.BOTH.ordinal(), FunctionType.THROUGH.ordinal() },
                { FunctionType.FORWARD.ordinal(), 0, 1, FunctionType.THROUGH.ordinal(),
                        FunctionType.FORWARD.ordinal() },
                { FunctionType.FORWARD.ordinal(), 0, 1, FunctionType.FORWARD.ordinal(),
                        FunctionType.THROUGH.ordinal() },
                { FunctionType.BACKWARD.ordinal(), 0, 1, FunctionType.NONE.ordinal(), FunctionType.NONE.ordinal() },
                { FunctionType.BACKWARD.ordinal(), 0, 0, FunctionType.BACKWARD.ordinal(),
                        FunctionType.BACKWARD.ordinal() },
                { FunctionType.BACKWARD.ordinal(), 0, 0, FunctionType.THROUGH.ordinal(), FunctionType.BOTH.ordinal() },
                { FunctionType.BACKWARD.ordinal(), 0, 0, FunctionType.BOTH.ordinal(), FunctionType.THROUGH.ordinal() },
                { FunctionType.BACKWARD.ordinal(), 1, 0, FunctionType.THROUGH.ordinal(),
                        FunctionType.BACKWARD.ordinal() },
                { FunctionType.BACKWARD.ordinal(), 1, 0, FunctionType.BACKWARD.ordinal(),
                        FunctionType.THROUGH.ordinal() },
                { FunctionType.BOTH.ordinal(), 1, 1, FunctionType.NONE.ordinal(), FunctionType.NONE.ordinal() },
                { FunctionType.BOTH.ordinal(), 1, 0, FunctionType.BACKWARD.ordinal(), FunctionType.BACKWARD.ordinal() },
                { FunctionType.BOTH.ordinal(), 0, 1, FunctionType.FORWARD.ordinal(), FunctionType.FORWARD.ordinal() },
                { FunctionType.BOTH.ordinal(), 0, 0, FunctionType.BOTH.ordinal(), FunctionType.BOTH.ordinal() },
                { FunctionType.THROUGH.ordinal(), 0, 0, FunctionType.THROUGH.ordinal(), FunctionType.BOTH.ordinal() },
                { FunctionType.THROUGH.ordinal(), 0, 0, FunctionType.BOTH.ordinal(), FunctionType.THROUGH.ordinal() },
                { FunctionType.NONE.ordinal(), 0, 0, FunctionType.NONE.ordinal(), FunctionType.NONE.ordinal() },
                { -1, -1, -1, -1, -1 }
        };

        SplotType type;
        int source;
        int sink;
        SPlot first;
        SPlot second;
        int[] result;
        int[] how;

        SPlot(SplotType type, int source, int sink, SPlot first, SPlot second) {
            this.type = type;
            this.source = source;
            this.sink = sink;
            this.first = first;
            this.second = second;
            this.result = new int[5];
            this.how = new int[5];
            Arrays.fill(result, -1);
            Arrays.fill(how, -1);
        }

        static SPlot newNode(char c) {
            return new SPlot(SplotType.NODE, c == 'x' ? 1 : 0, c == 'x' ? 1 : 0, null, null);
        }

        static SPlot newSeries(SPlot first, SPlot second) {
            return new SPlot(SplotType.SERIES, 0, 0, first, second);
        }

        static SPlot newParallel(char sourceChar, char sinkChar, SPlot first, SPlot second) {
            return new SPlot(SplotType.PARALLEL, sourceChar == 'x' ? 1 : 0, sinkChar == 'x' ? 1 : 0, first, second);
        }

        private int nodeResult(int f) {
            if (f == FunctionType.FORWARD.ordinal() || f == FunctionType.BACKWARD.ordinal()
                    || f == FunctionType.BOTH.ordinal()) {
                return result[f] = 1;
            } else {
                if (source == 1) {
                    return result[f] = NEG;
                } else {
                    return result[f] = 0;
                }
            }
        }

        private int seriesResult(int f) {
            int best = NEG;
            for (int l = 0; S_RULE[l][0] != -1; l++) {
                if (S_RULE[l][0] != f)
                    continue;
                int leftType = S_RULE[l][1];
                int rightType = S_RULE[l][2];
                int leftVal = first.getResult(leftType);
                int rightVal = second.getResult(rightType);
                if (leftVal == NEG || rightVal == NEG)
                    continue;
                int total = leftVal + rightVal;
                if (total > best) {
                    best = total;
                    how[f] = l;
                }
            }
            return result[f] = best;
        }

        private int parallelResult(int f) {
            int best = NEG;
            for (int l = 0; P_RULE[l][0] != -1; l++) {
                if (P_RULE[l][0] != f)
                    continue;
                int news = P_RULE[l][1];
                int newt = P_RULE[l][2];
                if ((source == 1 && news == 0) || (sink == 1 && newt == 0))
                    continue;
                int leftType = P_RULE[l][3];
                int rightType = P_RULE[l][4];
                int leftVal = first.getResult(leftType);
                int rightVal = second.getResult(rightType);
                if (leftVal == NEG || rightVal == NEG)
                    continue;
                int total = leftVal + rightVal + news + newt;
                if (total > best) {
                    best = total;
                    how[f] = l;
                }
            }
            return result[f] = best;
        }

        int getResult(int f) {
            if (result[f] != -1) {
                return result[f];
            }
            switch (type) {
                case NODE:
                    return nodeResult(f);
                case SERIES:
                    return seriesResult(f);
                case PARALLEL:
                    return parallelResult(f);
                default:
                    return NEG;
            }
        }

        SPlot reconstruct(int f) {
            switch (type) {
                case NODE:
                    return recNode(f);
                case SERIES:
                    return recSeries(f);
                case PARALLEL:
                    return recParallel(f);
                default:
                    return null;
            }
        }

        private SPlot recNode(int f) {
            if (f == FunctionType.FORWARD.ordinal() || f == FunctionType.BACKWARD.ordinal()
                    || f == FunctionType.BOTH.ordinal()) {
                return newNode('x');
            } else {
                return newNode('o');
            }
        }

        private SPlot recSeries(int f) {
            int l = how[f];
            int leftType = S_RULE[l][1];
            int rightType = S_RULE[l][2];
            return newSeries(first.reconstruct(leftType), second.reconstruct(rightType));
        }

        private SPlot recParallel(int f) {
            int l = how[f];
            int news = P_RULE[l][1];
            int newt = P_RULE[l][2];
            char sourceChar = news == 1 ? 'x' : 'o';
            char sinkChar = newt == 1 ? 'x' : 'o';
            int leftType = P_RULE[l][3];
            int rightType = P_RULE[l][4];
            return newParallel(sourceChar, sinkChar, first.reconstruct(leftType), second.reconstruct(rightType));
        }

        void buildString(StringBuilder sb) {
            switch (type) {
                case NODE:
                    sb.append(source == 1 ? 'x' : 'o');
                    break;
                case SERIES:
                    sb.append('S');
                    first.buildString(sb);
                    second.buildString(sb);
                    sb.append('#');
                    break;
                case PARALLEL:
                    sb.append('P');
                    sb.append(source == 1 ? 'x' : 'o');
                    sb.append('|');
                    first.buildString(sb);
                    second.buildString(sb);
                    sb.append('|');
                    sb.append(sink == 1 ? 'x' : 'o');
                    sb.append('#');
                    break;
            }
        }
    }

    static class Parser {
        String s;
        int p;

        Parser(String s) {
            this.s = s;
            this.p = 0;
        }

        SPlot parse() {
            return doParse();
        }

        private SPlot doParse() {
            char c = s.charAt(p++);
            if (c == 'o' || c == 'x') {
                return SPlot.newNode(c);
            } else if (c == 'S') {
                SPlot first = doParse();
                SPlot second = doParse();
                p++;
                return SPlot.newSeries(first, second);
            } else if (c == 'P') {
                char sourceChar = s.charAt(p++);
                p++;
                SPlot first = doParse();
                SPlot second = doParse();
                p++;
                char sinkChar = s.charAt(p++);
                p++;
                return SPlot.newParallel(sourceChar, sinkChar, first, second);
            } else {
                throw new RuntimeException("Unexpected character: " + c);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String input = br.readLine().trim();
        Parser parser = new Parser(input);
        SPlot splot = parser.parse();
        int best = splot.getResult(FunctionType.FORWARD.ordinal());
        bw.write(best + "\n");
        SPlot filled = splot.reconstruct(FunctionType.FORWARD.ordinal());
        StringBuilder sb = new StringBuilder();
        filled.buildString(sb);
        bw.write(sb.toString() + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}

/*
Po|Px|Sxo#Soo#|o#Soo#|o#

3
Po|Px|Sxo#Sox#|o#Soo#|o#


Po|SPo|oo|o#Px|oo|o##Po|Sxo#Po|ox|o#|o#|o#

7
Po|SPo|xx|o#Px|ox|o##Po|Sxx#Po|ox|o#|o#|o#
*/