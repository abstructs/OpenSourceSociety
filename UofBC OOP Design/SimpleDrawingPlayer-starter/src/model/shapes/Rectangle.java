package model.shapes;
import model.Shape;
import sound.MidiSynth;

import java.awt.*;

public class Rectangle extends Shape {

    public Rectangle(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.width = w;
        this.height = h;
    }

    public Rectangle(Point topLeft, MidiSynth midiSynth) {
        super(topLeft, midiSynth);
    }

    //EFFECTS: draws the shape
    @Override
    protected void drawGraphics(Graphics g) {
        g.drawRect(x, y, width, height);
    }

    //EFFECTS: fills the shape
    @Override
    protected void fillGraphics(Graphics g) {
        g.fillRect(x, y, width, height);
    }

    @Override
    protected int computeArea() {
        return width * height;
    }

    // EFFECTS: return true if the given Point (x,y) is contained within the bounds of this Shape
    @Override
    public boolean contains(Point point) {
        int point_x = point.x;
        int point_y = point.y;

        return containsX(point_x) && containsY(point_y);
    }
}
