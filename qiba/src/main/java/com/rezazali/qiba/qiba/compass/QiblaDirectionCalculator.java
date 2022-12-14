package com.rezazali.qiba.qiba.compass;

public class QiblaDirectionCalculator {
    public static final double QIBLA_LATITUDE = Math.toRadians(21.423333);
    public static final double QIBLA_LONGITUDE = Math.toRadians(39.823333);

    public static double getQiblaDirectionFromNorth(double degLatitude,
                                                    double degLongitude) {
        double latitude2 = Math.toRadians(degLatitude);
        double longitude = Math.toRadians(degLongitude);

        double soorat = Math.sin(QIBLA_LONGITUDE - longitude);
        double makhraj = Math.cos(latitude2) * Math.tan(QIBLA_LATITUDE)
                - Math.sin(latitude2) * Math.cos(QIBLA_LONGITUDE - longitude);
        double returnValue = Math.toDegrees(Math.atan(soorat / makhraj));
        // Math.atan will return value between -90...90 but arc tan of +180
        // degree plus is also the same
        // Never remove thes if..else segments or you will get qibla direction
        // with 180 degree difference
        // Until ***
        if (latitude2 > QIBLA_LATITUDE) {
            if ((longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > 0 && returnValue <= 90)) {

                returnValue += 180;

            } else if (!(longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > -90 && returnValue < 0)) {

                returnValue += 180;

            }

        }
        if (latitude2 < QIBLA_LATITUDE) {

            if ((longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > 0 && returnValue < 90)) {

                returnValue += 180;

            }
            if (!(longitude > QIBLA_LONGITUDE || longitude < (Math
                    .toRadians(-180d) + QIBLA_LONGITUDE))
                    && (returnValue > -90 && returnValue <= 0)) {

                returnValue += 180;
            }

        }
        // ***
        return returnValue - 10;
    }
    //
    // private static double getDistance(double latitude, double longitude) {
    //
    //
    //
    // double $earth_radius = 6367000;
    //
    // double $dlon = QIBLA_LONGITUDE - longitude;
    // double $dlat = QIBLA_LATITUDE - latitude;
    // double $a = Math.pow(Math.sin($dlat / 2), 2) + Math.cos(latitude)
    // * Math.cos(QIBLA_LATITUDE) * Math.pow((Math.sin($dlon / 2)), 2);
    // double $d = 2 * Math.atan2(Math.sqrt($a), Math.sqrt(1 - $a));
    // return $earth_radius * $d;
    //
    // }
}
