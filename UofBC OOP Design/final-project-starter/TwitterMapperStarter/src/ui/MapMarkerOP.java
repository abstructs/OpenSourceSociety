package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import twitter4j.Status;
import java.awt.*;

public class MapMarkerOP  extends MapMarkerCircle {
    public static final double defaultMarkerSize = 5.0;
    public Status status;

    public MapMarkerOP(Layer layer, Coordinate coord, Color color, Status s) {
        super(layer, null, coord, defaultMarkerSize, MapMarker.STYLE.FIXED, getDefaultStyle());
        this.status = s;
        setColor(color);
        setBackColor(color);
    }
}
