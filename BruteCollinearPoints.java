/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     9/2023
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// import javax.sound.sampled.Line;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Collections;
// import java.util.Objects;

public class BruteCollinearPoints {
    private int segments;
    private LineSegment[] collinear;
    private ArrayList<Point> endpoints;
    private ArrayList<Point> startPoints;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null array");
        for (int a = 0; a < points.length; a++) {
            if (points[a] == null) throw new IllegalArgumentException("null point");
            for (int b = a + 1; b < points.length; b++) {
                if (points[a] == points[b]) {
                    throw new IllegalArgumentException("point " + a + " and point " + b + " are equal");
                }
            }
        }
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        segments = 0;
        collinear = new LineSegment[points.length];
        endpoints = new ArrayList<>();
        startPoints = new ArrayList<>();
        for (i = 0; i < points.length; i++)
            for (j = i + 1; j < points.length; j++)
                for (k = j + 1; k < points.length; k++)
                    for (m = k + 1; m < points.length; m++) {
                            double jSlope = points[j].slopeTo(points[i]);
                            double kSlope = points[k].slopeTo(points[i]);
                            double mSlope = points[m].slopeTo(points[i]);
                            if (jSlope == kSlope && jSlope == mSlope) {
                                ArrayList<Point> y = new ArrayList<>();
                                y.add(0, points[i]);
                                y.add(1, points[j]);
                                y.add(2, points[k]);
                                y.add(3, points[m]);
                                Collections.sort(y);
                                LineSegment x = new LineSegment(y.get(0), y.get(3));
                                boolean startSwap = false;
                                boolean endSwap = false;
                                boolean ignore = false;
                                // maybe try an if (segments == 0), else execute for loop?
                                for (int a = 0; a < segments; a++) {
                                    if (y.get(3).slopeTo(y.get(0)) == endpoints.get(a).slopeTo(y.get(0))) {
                                        if (y.get(3).compareTo(endpoints.get(a)) > 0 && y.get(0).compareTo(startPoints.get(a)) >= 0) {
                                            endpoints.set(a, y.get(3));
                                            LineSegment newEnd = new LineSegment(startPoints.get(a), y.get(3));
                                            collinear[a] = newEnd;
                                            endSwap = true;
                                        }
                                        if (y.get(0).compareTo(startPoints.get(a)) < 0 && y.get(3).compareTo(endpoints.get(a)) <= 0) {
                                            startPoints.set(a, y.get(0));
                                            LineSegment newStart = new LineSegment(y.get(0), endpoints.get(a));
                                            collinear[a] = newStart;
                                            startSwap = true;
                                        }
                                        if (y.get(0).compareTo(startPoints.get(a)) < 0 && y.get(3).compareTo(endpoints.get(a)) > 0) {
                                            startPoints.set(a, y.get(0));
                                            endpoints.set(a, y.get(3));
                                            collinear[a] = x;
                                            startSwap = true;
                                            endSwap = true;
                                        }
                                        if (y.get(0).compareTo(startPoints.get(a)) >= 0 && y.get(3).compareTo(endpoints.get(a)) <= 0) {
                                            ignore = true;
                                        }
                                    }
                                }
                                if (!startSwap && !endSwap && !ignore) {
                                    collinear[segments] = x;
                                    startPoints.add(segments, y.get(0));
                                    endpoints.add(segments, y.get(3));
                                    segments++;
                                } // else if (!startSwap) {
                                     //   startPoints.add(segments, y.get(0));
                                   // } else if (!endSwap) {
                                    //    endpoints.add(segments, y.get(3));
                                  //  }
                            }
                    }
        // segments(); <-- to use or not to use?
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments;
    }
    // the line segments --> still need to figure out how to eliminate duplicates (either this method or above); possibly making endpoints & startPoints instance variables?
    // ...also, possibly by making collinear itself an ArrayList<LineSegment>; code below doesn't work, need to re-do
    public LineSegment[] segments() {
        for (int i = 0; i < segments; i++)
            for (int j = i + 1; j < segments; j++) {
                if (startPoints.get(i) == startPoints.get(j) && endpoints.get(i) == endpoints.get(j)) {
                    collinear[i] = null;
                    segments--;
                }
            }
        LineSegment[] coCopy = new LineSegment[segments];
        for (int i = 0; i < segments; i++) {
            coCopy[i] = collinear[i];
        }
        return coCopy;
    }

    // need to format this using LineSegment.toString() method (see example)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        LineSegment[] a = brute.segments();
        for (int i = 0; i < brute.segments; i++) {
            StdOut.println(a[i].toString());
        }
    }
}
