import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;

public class CrittersSimulator extends ActiveObject {
  public static final double DELAY = 50;
  private ArrayList<Critter> critters;
  private boolean stop_mode = false;

  public CrittersSimulator(ArrayList<Critter> critters) {
    this.critters = critters;
    start();
  }

  public void run() { 
    while (true) {
      pause(DELAY);
      if (stop_mode == false && critters.size() >= 2) {
        for (int i = 0; i < critters.size(); ++i) {
          Critter current = critters.get(i);
          Location cur_loc = current.getLoc();
          Critter closest_critter = null;
          if (current.getClass().equals(Random.class)) {
            current.reactTo(null);
          }
          else if (current.getClass().equals(Runner.class)) {
            double closest_dist = Double.MAX_VALUE;
            for (int j = 0; j < critters.size(); ++j) {
              Critter other = critters.get(j);
              if (current != other) {
                Location other_loc = other.getLoc();
                double distance = cur_loc.distanceTo(other_loc);
                if (distance < closest_dist) {
                  closest_dist = distance;
                  closest_critter = other;
                }
              }
            }
            current.reactTo(closest_critter);
          }
          else if (current.getClass().equals(Chaser.class)) {
            double closest_dist = Double.MAX_VALUE;
            for (int j = 0; j < critters.size(); ++j) {
              Critter other = critters.get(j);
              if (current != other && !(other.getClass().equals(Chaser.class))) {
                Location other_loc = other.getLoc();
                double distance = cur_loc.distanceTo(other_loc);
                if (distance < closest_dist) {
                  closest_dist = distance;
                  closest_critter = other;
                }
              }
            }
            current.reactTo(closest_critter);
          }
        }
      }
    }
  }

  public void set_stop_mode(boolean stop_mode) {
    this.stop_mode = stop_mode;
  }

}

