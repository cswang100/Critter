import objectdraw.*;

public abstract class Critter {
  protected Location pt; // the center of the critter
  protected DrawingCanvas canvas;
// other constants you might find useful (SIZE, ...)
// Constructor MUST at least have these params
  public Critter(Location loc, DrawingCanvas canvas) { }
  public abstract void reactTo(Critter other); // must have this
  public abstract void kill(); // removes critter from canvas
// All critters have a location, so we define these values here
  public Location getLoc() { return(pt); }
  public void setLoc(Location loc) { pt = loc; }
}

