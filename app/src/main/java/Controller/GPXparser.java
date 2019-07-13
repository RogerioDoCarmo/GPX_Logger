package Controller;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import Model.GPX_Structure;

import static Controller.GPSController.getDeviceName;

public class GPXparser {

    String cellName;
    String cidade = "PRUDENTE";

    private final String CREATOR = "GPX_LOGGER_ROGERIO";
    StringBuilder textContent;

    GPX_Structure gpx_structure;

    public String getFullText() {
        return  textContent.toString();
    }

    public StringBuilder getStringBuilder() {
        return  this.textContent;
    }

    public void make_header() {
        String standard_header = "<?xml version=\"1.0\"?>\n" +
                "<gpx version=\"1.0\" creator=\"CREATOR_FIELD\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                " xmlns=\"http://www.topografix.com/GPX/1/0\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/0 http://www.topografix.com/GPX/1/0/gpx.xsd\">";

        standard_header = standard_header.replace("CREATOR_FIELD", CREATOR);

        textContent.append(standard_header);
    }

    public GPXparser(GPX_Structure gpx_structure) {
        textContent = new StringBuilder();

        cellName = getDeviceName();

        this.gpx_structure = gpx_structure;

        make_header();
        make_body();
    }

    @SuppressLint("DefaultLocale")
    public void make_body () {

        textContent.append("<trk>");
        textContent.append("\n");
        textContent.append("<name><![CDATA[CITY_FIELD]]></name>".replace("CITY_FIELD", cidade));
        textContent.append("\n");
        textContent.append("<trkseg>");
        textContent.append("\n");

        for (int i = 0; i < gpx_structure.listPoints.size(); i++) { // TODO REMOVE PUBLIC VARIABLE

            Date date = new Date(gpx_structure.listPoints.get(i).getTimeUTCmilli());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            String time = format.format(date);

            textContent.append( String.format("<trkpt lat=\"%f\" lon=\"%f\"><ele>%f</ele><time>%s</time></trkpt>",
                                              gpx_structure.listPoints.get(i).getLatitudeDegrees(),
                                              gpx_structure.listPoints.get(i).getLongitudeDegrees(),
                                              gpx_structure.listPoints.get(i).getElevationMeters(),
                                              time
                                        )
            );
            textContent.append("\n");
        }

        textContent.append("</trkseg>");
        textContent.append("\n");
        textContent.append("</trk>");
        textContent.append("\n");
        textContent.append("</gpx>");
    }

}
