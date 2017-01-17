import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;

public class Chaser extends Critter {
  private FilledRect rect;
  private double canvasHeight,canvasWidth;
  private RandomDoubleGenerator randomDouble_x;
  private RandomDoubleGenerator randomDouble_y;

  public Chaser(Location location, DrawingCanvas canvas) {
    super(location, canvas);
// set the center point X and Y making sure X and Y are
// within bounds
    setLoc(location);
    rect = new FilledRect(location,15,15,canvas);
    rect.setColor(Color.cyan);
    canvasHeight = canvas.getHeight();
    canvasWidth  = canvas.getWidth();
    randomDouble_x = new RandomDoubleGenerator(7.5,canvasWidth-7.5);
    randomDouble_y = new RandomDoubleGenerator(7.5,canvasHeight-7.5);
  }

  public void reactTo(Critter other) {
    if (other == null)
      return;
    Location other_loc = other.getLoc();
    Location cur_loc = getLoc();
    Location new_loc = cur_loc;
    double cur_x = cur_loc.getX();
    double cur_y = cur_loc.getY();
    double closest_dist = other_loc.distanceTo(cur_loc);

    for (int x = -1; x <= 1; ++x) {
      for (int y = -1; y <= 1; ++y) {
        Location loc = new Location(cur_x+x,cur_y+y);
        double new_dist = other_loc.distanceTo(loc);
        if (new_dist < closest_dist) {
          closest_dist = new_dist;
          new_loc = loc;
        }
      }
    }
    double new_x = new_loc.getX();
    double new_y = new_loc.getY();

    if ((new_x-7.5) < 0 || (new_x+7.5) > canvasWidth ||
        (new_y-7.5) < 0 || (new_y+7.5) > canvasHeight)
      new_loc = new Location(randomDouble_x.nextValue(),randomDouble_y.nextValue());
    setLoc(new_loc);
    rect.moveTo(new_loc);
  }

  public void kill() {
    rect.removeFromCanvas();
  }
}

