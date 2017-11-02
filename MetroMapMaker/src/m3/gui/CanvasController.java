package m3.gui;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import m3.data.m3Data;
import m3.data.Draggable;
import m3.data.m3State;
import static m3.data.m3State.DRAGGING_NOTHING;
import static m3.data.m3State.DRAGGING_SHAPE;
import static m3.data.m3State.SELECTING_SHAPE;
import static m3.data.m3State.SIZING_SHAPE;
import djf.AppTemplate;

/**
 * This class responds to interactions with the rendering surface.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class CanvasController {

    AppTemplate app;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    /**
     * Respond to mouse presses on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMousePress(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(SELECTING_SHAPE)) {
            // SELECT THE TOP SHAPE
            Shape shape = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();

            // AND START DRAGGING IT
            if (shape != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(m3State.DRAGGING_SHAPE);
                app.getGUI().updateToolbarControls(false);
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(m3State.STARTING_LINE)) {
            //ASK INFO
            dataManager.startNewLine(x, y);
        } else if (dataManager.isInState(m3State.ADDING_STATION)) {
            dataManager.startNewStation(x, y);
        } else if(dataManager.isInState(m3State.STARTING_IMAGE)){
            dataManager.startNewImage(x, y);
        } else if (dataManager.isInState(m3State.ADDING_LABEL)){
            dataManager.startNewLabel(x, y);
        }
        m3Workspace workspace = (m3Workspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * Respond to mouse dragging on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseDragged(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
            Draggable newDraggableShape = (Draggable) dataManager.getNewShape();
            newDraggableShape.size(x, y);
        } else if (dataManager.isInState(DRAGGING_SHAPE)) {
            Draggable selectedDraggableShape = (Draggable) dataManager.getSelectedShape();
            selectedDraggableShape.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }
    }

    /**
     * Respond to mouse button release on the rendering surface, which we call canvas,
     * but is actually a Pane.
     */
    public void processCanvasMouseRelease(int x, int y) {
        m3Data dataManager = (m3Data) app.getDataComponent();
        if (dataManager.isInState(SIZING_SHAPE)) {
            dataManager.selectSizedShape();
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(m3State.DRAGGING_SHAPE)) {
            dataManager.setState(SELECTING_SHAPE);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(m3State.DRAGGING_NOTHING)) {
            dataManager.setState(SELECTING_SHAPE);
        }
    }
}
