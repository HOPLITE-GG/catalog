import java.util.HashMap;
import java.util.Map;

public class ShamirSecretSharing_testcase1 {

    // Function to convert a value from a specific base to decimal
    public static long convertBase(String numeralBase, String encodedValue) {
        int base = Integer.parseInt(numeralBase);
        return Long.parseLong(encodedValue, base); // Convert the value to base 10
    }

    // Function to perform Lagrange Interpolation to compute the constant term (secret)
    public static double computeLagrangeInterpolation(Map<Integer, Long> coordinatePairs, int threshold) {
        double interpolatedResult = 0.0;

    // Lagrange interpolation logic
    for (Map.Entry < Integer, Long > primaryEntry : coordinatePairs.entrySet()) {
            int primaryX = primaryEntry.getKey();
            long primaryY = primaryEntry.getValue();

            double term = primaryY;
        for (Map.Entry < Integer, Long > secondaryEntry : coordinatePairs.entrySet()) {
                int secondaryX = secondaryEntry.getKey();
            if (primaryX != secondaryX) {
                term *= (0 - secondaryX) / (double)(primaryX - secondaryX);
            }
        }
        interpolatedResult += term;
    }

    return interpolatedResult;
}

    public static void main(String[] args) {
        // Input JSON as a string
        String inputJson = "{\n" +
        "    \"keys\": {\n" +
        "        \"n\": 4,\n" +
        "        \"k\": 3\n" +
        "    },\n" +
        "    \"1\": {\n" +
        "        \"base\": \"10\",\n" +
        "        \"value\": \"4\"\n" +
        "    },\n" +
        "    \"2\": {\n" +
        "        \"base\": \"2\",\n" +
        "        \"value\": \"111\"\n" +
        "    },\n" +
        "    \"3\": {\n" +
        "        \"base\": \"10\",\n" +
        "        \"value\": \"12\"\n" +
        "    },\n" +
        "    \"6\": {\n" +
        "        \"base\": \"4\",\n" +
        "        \"value\": \"213\"\n" +
        "    }\n" +
        "}";


        // Parsed key counts
        int totalKeys = 9;
        int requiredKeys = 6;

    // Prepare input as a map of (x, [base, value])
    Map < Integer, String[] > encodedPoints = new HashMap <> ();
    encodedPoints.put(1, new String[]{ "10", "28735619723837"});
    encodedPoints.put(2, new String[]{ "16", "1A228867F0CA"});
    encodedPoints.put(3, new String[]{ "12", "32811A4AA0B7B"});
    encodedPoints.put(4, new String[]{ "11", "917978721331A"});
    encodedPoints.put(5, new String[]{ "16", "1A22886782E1"});
    encodedPoints.put(6, new String[]{ "10", "28735619654702"});
    encodedPoints.put(7, new String[]{ "14", "71AB5070CC4B"});
    encodedPoints.put(8, new String[]{ "9", "122662581541670"});
    encodedPoints.put(9, new String[]{ "8", "642121030037605"});

    // Decode points (x, y) from the input map
    Map < Integer, Long > decodedPoints = new HashMap <> ();
    for (Map.Entry < Integer, String[] > entry : encodedPoints.entrySet()) {
            int xCoordinate = entry.getKey();
        String[] baseValuePair = entry.getValue();
            String numeralBase = baseValuePair[0];
            String encodedValue = baseValuePair[1];

            long decodedY = convertBase(numeralBase, encodedValue);
        decodedPoints.put(xCoordinate, decodedY);
    }

        // Compute the secret using Lagrange interpolation
        double secretConstant = computeLagrangeInterpolation(decodedPoints, requiredKeys);

    // Output the result
    System.out.println("The secret (constant term) is: " + secretConstant);
}
}
