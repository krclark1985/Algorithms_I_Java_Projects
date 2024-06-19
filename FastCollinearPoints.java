/* *****************************************************************************
 *  Name:              Kyle Clark
 *  Last modified:     9/2023
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collections;
// import java.util.Comparator;

public class FastCollinearPoints {
    private int segments;
    private LineSegment[] collinear;
    private Point currentOrigin;
    private Point[] sortedPoints;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null array");
        for (int a = 0; a < points.length; a++) {
            if (points[a] == null) throw new IllegalArgumentException("null point");
            for (int b = a + 1; b < points.length; b++) {
                if (points[a] == points[b]) {
                    throw new IllegalArgumentException("point " + a + " and point " + b + " are equal");
                }
            }
        }
        collinear = new LineSegment[points.length];
        // Arrays.sort(points); // sort points[] by natural order
        sortedPoints = new Point[points.length]; // create new array to permanently store nat order-sorted points[]
        System.arraycopy(points, 0, sortedPoints, 0, points.length); // copy nat order-sorted points[] to sortedPoints[]
        for (int i = 0; i < points.length; i++) {    // points.length - 1?
            System.arraycopy(points, 0, sortedPoints, 0, points.length);
            Arrays.sort(sortedPoints);
            currentOrigin = sortedPoints[i];
            Arrays.sort(sortedPoints, currentOrigin.slopeOrder());
            int counter = 0;
            Point subsequent = null;
            for (int j = 1; j < (points.length - 1); j++) {
                if (sortedPoints[j].slopeTo(currentOrigin) == sortedPoints[j + 1].slopeTo(currentOrigin)) {
                    counter++;
                    if (counter == 1) subsequent = sortedPoints[j];
                    if (counter == 2 && currentOrigin.compareTo(subsequent) < 0) { // points[j - 1]
                        LineSegment newCo = new LineSegment(currentOrigin, sortedPoints[j + 1]);
                        collinear[segments] = newCo;
                        segments++;
                    } else if (counter > 2 && currentOrigin.compareTo(subsequent) < 0) {
                        LineSegment newCo = new LineSegment(currentOrigin, sortedPoints[j + 1]);
                        collinear[segments - 1] = newCo;
                    }
                }
                else counter = 0;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] coCopy = new LineSegment[segments];
        for (int i = 0; i < segments; i++) {
            coCopy[i] = collinear[i];
        }
        return coCopy;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        FastCollinearPoints fast = new FastCollinearPoints(points);
        LineSegment[] a = fast.segments();
        StdOut.println(fast.segments);
        for (int i = 0; i < fast.segments; i++) {
            StdOut.println(a[i].toString());
        }
    }
}
