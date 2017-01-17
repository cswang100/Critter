import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;

class CrittersController extends WindowController implements ActionListener {
  private final String start_message = "please add two or more critters.";
  private final String stop_message = "Simulation is stopped.";
  private final String run_message = "Simulation is running.";
  private final String select_message = "Select which critter to place:";
  private final String click_message = "Click on canvas to place a ";
  private final int START_STATE = 0;
  private final int STOP_STATE = 1;
  private final int RUN_STATE = 2;
  private final int CHASER = 0;
  private final int RUNNER = 1;
  private final int RANDOM = 3;
  private JButton start_button;
  private JButton stop_button;
  private JButton clear_button;
  private JButton chaser_button;
  private JButton runner_button;
  private JButton random_button;
  private JLabel statusLabel;
  private JLabel messageLabel;
  private int simulation_state = START_STATE;
  private int critter_type;
  private boolean in_critter_mode = false;
  private ArrayList<Critter> critters;
  private CrittersSimulator simulator;

  public void begin() {
    JPanel northPanel1 = new JPanel();
    statusLabel = new JLabel(start_message);
    northPanel1.add(statusLabel);

    JPanel northPanel2 = new JPanel();
    start_button = new JButton("Start");
    stop_button  = new JButton("Stop");
    clear_button = new JButton("Clear");
    northPanel2.setLayout(new GridLayout(1,3));
    northPanel2.add(start_button);
    northPanel2.add(stop_button);
    northPanel2.add(clear_button);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(1,2));
    northPanel.add(northPanel1);
    northPanel.add(northPanel2);

    JPanel southPanel1 = new JPanel();
    messageLabel = new JLabel(select_message);
    southPanel1.add(messageLabel);

    JPanel southPanel2 = new JPanel();
    chaser_button = new JButton("Chaser");
    runner_button = new JButton("Runner");
    random_button = new JButton("Random");
    southPanel2.setLayout(new GridLayout(1,3));
    southPanel2.add(chaser_button);
    southPanel2.add(runner_button);
    southPanel2.add(random_button);
    
    JPanel southPanel = new JPanel();
    southPanel.setLayout(new GridLayout(2,1));
    southPanel.add(southPanel1);
    southPanel.add(southPanel2);
    
    Container contentPane = getContentPane();
    contentPane.add(northPanel,BorderLayout.NORTH);
    contentPane.add(southPanel,BorderLayout.SOUTH);
    contentPane.validate();

    stop_button.addActionListener(this);
    start_button.addActionListener(this);
    clear_button.addActionListener(this);
    chaser_button.addActionListener(this);
    runner_button.addActionListener(this);
    random_button.addActionListener(this);

    critters = new ArrayList<Critter>();
    simulator = new CrittersSimulator(critters);
    simulator.set_stop_mode(false);
    simulation_state = START_STATE;
  }
    

  public void onMouseClick(Location point) {
    if (in_critter_mode) {
      switch (critter_type) {
        case CHASER:
          Chaser chaser = new Chaser(point,canvas);
          critters.add(chaser);
          break;
        case RUNNER:
          Runner runner = new Runner(point,canvas);
          critters.add(runner);
          break;
        case RANDOM:
          Random random = new Random(point,canvas);
          critters.add(random);
          break;
        default:
          break;
      }
      if (critters.size() >= 2 && simulation_state != STOP_STATE) {
        simulation_state = RUN_STATE;
        statusLabel.setText(run_message);
        simulator.set_stop_mode(false);
      }
    }
  }

  public void actionPerformed(ActionEvent evt) {
    JButton button = (JButton) evt.getSource();

    if (button == stop_button) {
      if (simulation_state == RUN_STATE) {
        simulation_state = STOP_STATE;
        statusLabel.setText(stop_message);
        messageLabel.setText(select_message);
        in_critter_mode = false;
        simulator.set_stop_mode(true);
      }
    }
    else
    if (button == start_button) {
      if (simulation_state == STOP_STATE) {
        simulation_state = RUN_STATE;
        statusLabel.setText(run_message);
        messageLabel.setText(select_message);
        in_critter_mode = false;
        simulator.set_stop_mode(false);
      }
    }
    else
    if (button == clear_button) {
      for (Critter critter: critters)
        critter.kill();
      critters.clear();
      simulation_state = START_STATE;
      statusLabel.setText(start_message);
      messageLabel.setText(select_message);
      in_critter_mode = false;
    }
    else
    if (button == chaser_button) {
      in_critter_mode = true;
      critter_type = CHASER;
      messageLabel.setText(click_message + "Chaser");
    }
    else
    if (button == runner_button) {
      in_critter_mode = true;
      critter_type = RUNNER;
      messageLabel.setText(click_message + "Runner");
    }
    else
    if (button == random_button) {
      in_critter_mode = true;
      critter_type = RANDOM;
      messageLabel.setText(click_message + "Random");
    }
  }
}

public class MyController {
  public static void main(String[] args) {
    new CrittersController().startController(600,800);
  }
}

