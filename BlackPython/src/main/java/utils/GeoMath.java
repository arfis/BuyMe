package utils;

import org.osmdroid.util.GeoPoint;

public class GeoMath {

    //ToDO: spravit aby to vracalo metre a nie kilometre
    public static final int EARTH_MEAN_RADIUS = 6371000; // earth's mean radius in meters

    public static double getDistance(double startLatitude, double startLongitude,
                                     double endLatitude, double endLongitude){

        return distHaversine(startLatitude,startLongitude,endLatitude,endLongitude);
    }

    public static double getDistance(GeoPoint point1, GeoPoint point2){

        return distHaversine(point1.getLatitudeE6()/1E6, point1.getLongitudeE6()/1E6,
                             point2.getLatitudeE6()/1E6, point2.getLongitudeE6()/1E6);
    }

    private static double getSpanInRadians(double max, double min){

        return Math.toRadians(max - min);
    }

    //Distance in Km between point1 (lat1,lon1) and point2 (lat2,lon2) Haversine formula
    private static double distHaversine(double lat1, double lon1, double lat2, double lon2) {

        double dLat = getSpanInRadians(lat2,lat1);
        double dLon = getSpanInRadians(lon2,lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = EARTH_MEAN_RADIUS * c;
        return Math.round(dist * 1000)/1000; //3 decimal places
    }

    // Get GeoPoint at distance along a bearing
    // bearing in degrees
    // distance in Km
    /*
    public static GeoPoint getGeoPointAlongBearing(GeoPoint location, double bearing, double distance){

        double PI = Math.PI;
        double NM = 1.852; //1 nm = 1.852 Km -> nm = Km/NM

        GeoPoint geoPointAlongBearing;

        double locationLatRad = Math.toRadians(location.getLatitudeE6()/1E6);
        double locationLongRad = Math.toRadians(location.getLongitudeE6()/1E6)*(-1.0d);

        double distanceRad = distance/NM * PI/(180*60);

        double bearingRad = Math.toRadians(bearing);

        double latAlongBearingRad = Math.asin(Math.sin(locationLatRad) * 
                                    Math.cos(distanceRad) +
                                    Math.cos(locationLatRad) *
                                    Math.sin(distanceRad) * 
                                    Math.cos(bearingRad));

        double lonAlongBearingRad = mod(locationLongRad - 
                                    Math.asin(Math.sin(bearingRad) * 
                                    Math.sin(distanceRad) / 
                                    Math.cos(latAlongBearingRad)) + PI, 2 * PI) - PI;

        double latAlongBearing = rad2lat(latAlongBearingRad);
        double lonAlongBearing = rad2lng(lonAlongBearingRad) * (-1);

        geoPointAlongBearing = new GeoPoint((int)(latAlongBearing*1E6),(int)(lonAlongBearing*1E6));
        return geoPointAlongBearing;
    }
*/

	private static double mod(double y, double x) {

        return y - x * Math.floor(y/x);
    }

}