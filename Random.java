import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;

public class Random extends Critter {
  private Line vLine;
  private Line hLine;
  private double canvasHeight,canvasWidth;
  private RandomDoubleGenerator randomDouble_x;
  private RandomDoubleGenerator randomDouble_y;

  public Random(Location location, DrawingCanvas canvas) {
    super(location, canvas);
// set the center point X and Y making sure X and Y are
// within bounds
    setLoc(location);
    double x = location.getX();
    double y = location.getY();
    vLine = new Line(x,y-7.5,x,y+7.5,canvas);
    hLine = new Line(x-7.5,y,x+7.5,y,canvas);
    vLine.setColor(Color.orange);
    hLine.setColor(Color.orange);
    canvasHeight = canvas.getHeight();
    canvasWidth  = canvas.getWidth();
    randomDouble_x = new RandomDoubleGenerator(7.5,canvasWidth-7.5);
    randomDouble_y = new RandomDoubleGenerator(7.5,canvasHeight-7.5);
  }

  public void reactTo(Critter other) {
    double x = randomDouble_x.nextValue();
    double y = randomDouble_y.nextValue();
    Location new_loc = new Location(x,y);
    Location vLine_new_loc = new Location(x,y-7.5);
    Location hLine_new_loc = new Location(x-7.5,y);

    vLine.moveTo(vLine_new_loc);
    hLine.moveTo(hLine_new_loc);
    setLoc(new_loc);
  }

  public void kill() {
    vLine.removeFromCanvas();
    hLine.removeFromCanvas();
  }
}

