package com.example.algorithm.java.math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class WhatsOurVectorVictor {
    static final double EPS = 1e-6;

    static class Point {
        double[] coords;

        Point(int dimensions) {
            coords = new double[dimensions];
        }

        Point(double[] coords) {
            this.coords = coords.clone();
        }

        Point subtract(Point other) {
            double[] result = new double[coords.length];
            for (int i = 0; i < coords.length; i++) {
                result[i] = coords[i] - other.coords[i];
            }
            return new Point(result);
        }

        Point multiply(double scalar) {
            double[] result = new double[coords.length];
            for (int i = 0; i < coords.length; i++) {
                result[i] = coords[i] * scalar;
            }
            return new Point(result);
        }

        double dot(Point other) {
            double sum = 0.0;
            for (int i = 0; i < coords.length; i++) {
                sum += coords[i] * other.coords[i];
            }
            return sum;
        }

        double length() {
            double sum = 0.0;
            for (double coord : coords) {
                sum += coord * coord;
            }
            return Math.sqrt(sum);
        }
    }

    static class SphereState {
        Point center;
        double radius;
        List<Point> basis = new ArrayList<>();
    }

    private static Point project(Point vector, List<Point> basis) {
        Point result = new Point(vector.coords);
        for (Point base : basis) {
            double projection = result.dot(base);
            result = result.subtract(base.multiply(projection));
        }
        return result;
    }

    private static void updateState(SphereState state, Point nextCenter, double nextRadius) {
        Point diff = state.center.subtract(nextCenter);
        Point hyperplane = project(diff, state.basis);
        Point normal = diff.subtract(hyperplane);
        double normalDistance = normal.length();

        double newRadius = Math.sqrt(Math.max(0.0, nextRadius * nextRadius - normalDistance * normalDistance));
        double hyperDistance = hyperplane.length();

        if (hyperDistance < EPS) {
            return;
        }

        Point unit = hyperplane.multiply(1.0 / hyperDistance);
        state.basis.add(unit);

        double x = (hyperDistance * hyperDistance - newRadius * newRadius + state.radius * state.radius)
                / (2 * hyperDistance);
        double y = Math.sqrt(Math.max(0.0, state.radius * state.radius - x * x));

        state.center = state.center.subtract(unit.multiply(x));
        state.radius = y;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Random random = new Random();
        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            if (inputLine.trim().isEmpty())
                continue;

            StringTokenizer st = new StringTokenizer(inputLine);
            int dimensions = Integer.parseInt(st.nextToken());
            int numSpheres = Integer.parseInt(st.nextToken());

            SphereState state = new SphereState();
            List<Point> centers = new ArrayList<>();
            List<Double> radii = new ArrayList<>();

            st = new StringTokenizer(br.readLine());
            double[] firstCoords = new double[dimensions];
            for (int i = 0; i < dimensions; i++) {
                firstCoords[i] = Double.parseDouble(st.nextToken());
            }
            double firstRadius = Double.parseDouble(st.nextToken());

            state.center = new Point(firstCoords);
            state.radius = firstRadius;
            centers.add(state.center);
            radii.add(firstRadius);

            for (int i = 1; i < numSpheres; i++) {
                st = new StringTokenizer(br.readLine());
                double[] coords = new double[dimensions];
                for (int j = 0; j < dimensions; j++) {
                    coords[j] = Double.parseDouble(st.nextToken());
                }
                double radius = Double.parseDouble(st.nextToken());

                centers.add(new Point(coords));
                radii.add(radius);
                updateState(state, new Point(coords), radius);
            }

            Point result = new Point(dimensions);
            for (int i = 0; i < dimensions; i++) {
                result.coords[i] = 1000000 + random.nextInt(1000000);
            }

            result = project(result, state.basis);
            double length = result.length();
            if (length < EPS) {
                result = new Point(state.center.coords);
            } else {
                result = state.center.subtract(result.multiply(state.radius / length));
            }

            StringBuilder output = new StringBuilder();
            for (int i = 0; i < dimensions; i++) {
                if (i > 0)
                    output.append(" ");
                output.append(String.format("%.17f", result.coords[i]));
            }
            System.out.println(output);
        }
    }
}

/*
2 3
0 0 2.5
3 0 2.5
1.5 0.5 2.5

1.50000000000000000 -2.00000000000000000


4 3
0 1 2 3 2
1 2 -1 7 5
1 0.3 3.4 1.2 3.3

0.72489126774003610 -0.09271419215638510 0.51458227475703210 2.72789243717186160
*/