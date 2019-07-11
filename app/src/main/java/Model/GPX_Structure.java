package Model;

import java.util.ArrayList;

public class GPX_Structure {
    public ArrayList<GPX_Point> listPoints;

    public GPX_Structure () {
        listPoints = new ArrayList<>();
    }

    public boolean add_GPXpoint(long time, float lat, float lon, double elev) {
        GPX_Point new_point = new GPX_Point(time, lat, lon, elev);
        return  ( listPoints.add(new_point) );
    }

    public class GPX_Point {
        private long timeUTCmilli;
        private float latitudeDegrees;
        private float longitudeDegrees;
        private double elevationMeters;

        public GPX_Point(long time, float lat, float lon, double elev) {
            this. timeUTCmilli = time;
            this.latitudeDegrees = lat;
            this.longitudeDegrees = lon;
            this.elevationMeters = elev;
        }

        public long getTimeUTCmilli() {
            return timeUTCmilli;
        }

        public void setTimeUTCmilli(long timeUTCmilli) {
            this.timeUTCmilli = timeUTCmilli;
        }

        public float getLatitudeDegrees() {
            return latitudeDegrees;
        }

        public void setLatitudeDegrees(float latitudeDegrees) {
            this.latitudeDegrees = latitudeDegrees;
        }

        public float getLongitudeDegrees() {
            return longitudeDegrees;
        }

        public void setLongitudeDegrees(float longitudeDegrees) {
            this.longitudeDegrees = longitudeDegrees;
        }

        public double getElevationMeters() {
            return elevationMeters;
        }

        public void setElevationMeters(int elevationMeters) {
            this.elevationMeters = elevationMeters;
        }
    }
}
