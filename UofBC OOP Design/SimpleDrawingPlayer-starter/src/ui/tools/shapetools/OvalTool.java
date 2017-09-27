package ui.tools.shapetools;
import ui.DrawingEditor;
import ui.tools.ShapeTool;
import model.shapes.Oval;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class OvalTool extends ShapeTool {
    public OvalTool(DrawingEditor editor, JComponent parent) {
        super(editor, parent);
    }

    //EFFECTS: Returns the string for the label.
    @Override
    protected String getLabel() {
        return "Oval";
    }

    //EFFECTS: Constructs and returns the new shape
    protected void makeShape(MouseEvent e) {
        shape = new Oval(e.getPoint(), editor.getMidiSynth());
    }
}
