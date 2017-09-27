package ui.tools.shapetools;
import ui.DrawingEditor;
import ui.tools.ShapeTool;
import model.shapes.Rectangle;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class RectangleTool extends ShapeTool {
    public RectangleTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
    }

    //EFFECTS: Returns the string for the label.
    @Override
    protected String getLabel() {
        return "Rectangle";
    }

    //EFFECTS: Constructs and returns the new shape
    protected void makeShape(MouseEvent e) {
        shape = new Rectangle(e.getPoint(), editor.getMidiSynth());
	}
}
