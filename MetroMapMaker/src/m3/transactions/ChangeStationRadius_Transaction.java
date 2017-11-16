/**
 * This class implements jTPS_Transaction. It's used for changing station radius.
 * 
 * @author Kai 
 * @version 1.0
 */
package m3.transactions;
import javafx.scene.shape.Ellipse;
import jtps.jTPS_Transaction;

class ChangeStationRadius_Transaction implements jTPS_Transaction{
    private Ellipse station;
    private double radius;
    private double oldRadius;
    
public ChangeStationRadius_Transaction(Ellipse initStation, double initRadius) {
        station = initStation;
        radius = initRadius;
        oldRadius = station.getRadiusX(); // RadiusX or Y should be same 
    }

    @Override
    public void doTransaction() {
        station.setRadiusX(radius);
        station.setRadiusY(radius);
    }

    @Override
    public void undoTransaction() {
        station.setRadiusX(oldRadius);
        station.setRadiusY(oldRadius);
    }        
}
