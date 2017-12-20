package query;

import filters.Filter;
import javafx.beans.binding.ListBinding;
import javafx.collections.ObservableList;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import twitter.TwitterSource;
import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.Twitter;
import ui.MapMarkerOP;
import ui.MapMarkerSimple;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A query over the twitter stream.
 * TODO: Task 4: you are to complete this class.
 */
public class Query implements Observer {
    // The map on which to display markers when the query matches
    private final JMapViewer map;
    // Each query has its own "layer" so they can be turned on and off all at once
    private Layer layer;
    // The color of the outside area of the marker
    private final Color color;
    // The string representing the filter for this query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;
    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;
    public List<MapMarkerOP> markers;

    public Color getColor() {
        return color;
    }
    public String getQueryString() {
        return queryString;
    }
    public Filter getFilter() {
        return filter;
    }
    public Layer getLayer() {
        return layer;
    }
    public JCheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public void setVisible(boolean visible) {
        layer.setVisible(visible);
    }
    public boolean getVisible() { return layer.isVisible(); }

    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
        this.markers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    /**
     * This query is no longer interesting, so terminate it and remove all traces of its existence.
     *
     * TODO: Implement this method
     */
    public void terminate() {
        for(MapMarkerOP m : markers) {
            this.map.removeMapMarker(m);
        }
    }

    public void update(Observable o, Object arg) {
        TwitterSource ts = (TwitterSource) o;
        Status s = (Status) arg;

        if(this.filter.matches((Status) arg)) {
            GeoLocation geo = s.getGeoLocation();
            Coordinate coord = new Coordinate(geo.getLatitude(), geo.getLongitude());
            MapMarkerOP marker = new MapMarkerOP(this.layer, coord, this.color, s);
            this.map.addMapMarker(marker);
            this.markers.add(marker);
        }
    }

}

