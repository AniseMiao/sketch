package util;

public class CommomUtil {
    private static final double TOLERANCE = 0.001;
    //
    public static boolean equals(double num1, double num2) {
        return Math.abs(num1 - num2) < TOLERANCE;
    }
}
