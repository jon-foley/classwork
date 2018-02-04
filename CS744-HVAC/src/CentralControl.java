//
// Stub for CentralControl <<control>> class
// Sends commands to Temperature Control and sets desired temperature
// Update run() method and add methods as needed to test TemperatureCtrl implementation
// 
// R. Pettit - 2016
//

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



/**
 * Corresponds to the Central Monitor in the Smart Home application.
 * 
 * @author jfoley
 */
public class CentralControl extends Thread {
	
	/**
	 * Queue for aynchronous message passing
	 */
	protected BlockingQueue<Mode> queue = null; 
	
	/**
	 * The current temperature
	 */
	private int currentTemp;
	
	/**
	 * Constructor assigns new queue
	 */
	public CentralControl(BlockingQueue<Mode> queue){
		this.queue = queue;
	}
	
	
	/**
	 * Gets the current temperature field's value
	 * 
	 * @return the current temperature
	 */
	private synchronized int getCurrentTemp(){
		return currentTemp;
	}
	
	/**
	 * Starts the Central control
	 */
	@Override
	public void run(){
		//Begin monitoring current temperature
		Timer tempTimer = new Timer();
		tempTimer.schedule(new CurrentTempMonitor(), 0, 1000);
		
		//Check the user's choice: console test or manual input via GUI
		if (HVAC.choice == 1){
			System.out.println("Beginning manual tests in a loop.");
			while(true){
				try{
					/*
					 * Manual tests demonstrating that the system is functional in each mode
					 */
					System.out.println("CentralControl: Current Temp is "+ getCurrentTemp());
					System.out.println("=========");
					System.out.println("Set mode cool (demonstrates cooling)");
					int desiredTemp = 65;
					Temperature.setDesired(desiredTemp);
					System.out.println("Desired temp: " + desiredTemp);
					Thread.sleep(1000);
					System.out.println("=========");
					queue.put(Mode.COOLING);
					for (int i=0; i<20; i++){
						Thread.sleep(1000);
						System.out.println("CentralControl: Current Temp is "+ getCurrentTemp());
					}
					Thread.sleep(1000);
					System.out.println("=========");
					System.out.println("Set mode heat (demonstrates primary and backup)");
					desiredTemp = 220;
					Temperature.setDesired(desiredTemp);
					System.out.println("Desired temp: " + desiredTemp);
					Thread.sleep(1000);
					System.out.println("=========");
					queue.put(Mode.HEATING);
					for (int i=0; i<125; i++){
						Thread.sleep(1000);
						System.out.println("CentralControl: Current Temp is "+ getCurrentTemp());
					}
					Thread.sleep(1000);
					System.out.println("=========");
					System.out.println("Set mode off (demonstrates off mode)");
					desiredTemp = 0;
					Temperature.setDesired(desiredTemp);
					System.out.println("Desired temp (should not affect temperature when off): " + desiredTemp);
					Thread.sleep(1000);
					System.out.println("=========");
					queue.put(Mode.OFF);
					for (int i=0; i<20; i++){
						Thread.sleep(1000);
						System.out.println("CentralControl: Current Temp is "+ getCurrentTemp());
					}
				}catch (InterruptedException ex){
					// Just ignore any exceptions
				}
			}	
		}
		else if(HVAC.choice == 2){
			//Launch the GUI
			GUI centralGUI = this.new GUI();
			centralGUI.startGUI();
		}
	}
	
	/**
	 * Monitor for the current temperature
	 * 
	 * @author jfoley
	 */
	private class CurrentTempMonitor extends TimerTask{
		
		/**
		 * Monitors the current temperature
		 */
		@Override
		public void run() {
			currentTemp = Temperature.getCurrent();
		}
		
	}
	
	/**
	 * GUI for the Central Control (called Central Monitor) allowing the user
	 * to set desired temperature and mode as well as monitor the current
	 * temperature.
	 * 
	 * @author jfoley
	 */
	public class GUI extends JPanel implements ActionListener{
		
		/**
		 * Action event string for the Cooling mode radio button
		 */
		private static final String COOLING_MODE_STRING = "Cooling";
		
		/**
		 * Action event string for the Heating mode radio button
		 */
		private static final String HEATING_MODE_STRING = "Heating";
		
		/**
		 * Action event string for the Off mode radio button
		 */
		private static final String OFF_MODE_STRING = "Off";
		
		/**
		 * Radio button for setting the HVAC system to cooling mode
		 */
		private JRadioButton coolingButton;
		
		/**
		 * Radio button for setting the HVAC system to heating mode
		 */
		private JRadioButton heatingButton;
		
		/**
		 * Radio button for setting the HVAC system to off mode
		 */
		private JRadioButton offButton;
		
		/**
		 * Label for the title of the current temperature components
		 */
		private JLabel currentTempTitleLabel;
		
		/**
		 * Label in which the current temperature is displayed
		 */
		private JLabel currentTempLabel;
		
		/**
		 * The text field in which the desired temperature is entered by the user
		 */
		private JTextField desiredTempTextField;
		
		/**
		 * The button that submits the desired temperature
		 */
		private JButton submitTempButton;
		
		/**
		 * A Swing Timer to periodically repaint this component
		 */
		private javax.swing.Timer repaintCurrentTempLabelTimer;
		
		/**
		 * The label for the modes radio butons
		 */
		private JLabel modesLabel;
		
		/**
		 * The label for the title of the desired temperature components
		 */
		private JLabel desiredTempTitleLabel;
		
		/**
		 * The label containing the value of the desired temperature that has been set
		 */
		private JLabel desiredTempValueLabel;
		
		
		/**
		 * Auto-generated serial ID
		 */
		private static final long serialVersionUID = -2191074802078732591L;
		
		/**
		 * Creates this component and adds necessary sub-components
		 */
		public GUI(){
			//Radio buttons for the HVAC mode
			coolingButton = new JRadioButton(COOLING_MODE_STRING);
			coolingButton.setActionCommand(COOLING_MODE_STRING);
			
			heatingButton = new JRadioButton(HEATING_MODE_STRING);
			heatingButton.setActionCommand(HEATING_MODE_STRING);
			
			offButton = new JRadioButton(OFF_MODE_STRING);
			offButton.setActionCommand(OFF_MODE_STRING);
			offButton.setSelected(true);
			
			//Group radio buttons together to enforce mutual exclusion
			ButtonGroup radioGroup = new ButtonGroup();
			radioGroup.add(offButton);
			radioGroup.add(coolingButton);
			radioGroup.add(heatingButton);
			
			RadioListener radioListener = new RadioListener();
			offButton.addActionListener(radioListener);
			coolingButton.addActionListener(radioListener);
			heatingButton.addActionListener(radioListener);
			
			//Label for the mode radio buttons
			modesLabel = new JLabel("Active HVAC Mode");
			modesLabel.setOpaque(true);
			
			//Add to the radio panel
			JPanel radioPanel = new JPanel(new GridLayout(0,1));
			radioPanel.add(modesLabel);
			radioPanel.add(offButton);
			radioPanel.add(coolingButton);
			radioPanel.add(heatingButton);
			
			//Current temperature
			currentTempTitleLabel = new JLabel("Current Temperature:");
			currentTempTitleLabel.setOpaque(true);
			
			//Label with the actual current temperature
			currentTempLabel = new JLabel(String.valueOf(getCurrentTemp()));
			currentTempLabel.setOpaque(true);
			
			//Add to the current temperature panel
			JPanel currentTempPanel = new JPanel(new GridLayout(1,2));
			currentTempPanel.add(currentTempTitleLabel);
			currentTempPanel.add(currentTempLabel);
			
			//Desired temperature components
			//Title
			desiredTempTitleLabel = new JLabel("Desired Temperature:");
			desiredTempTitleLabel.setOpaque(true);
			
			//Current desired temperature
			desiredTempValueLabel = new JLabel("72");
			desiredTempValueLabel.setOpaque(true);
			
			//Text field for entering the desired temperature
			desiredTempTextField = new JTextField(3);
			
			//Button to submit the desired temperature
			submitTempButton = new JButton("Set Desired Temp");
			submitTempButton.addActionListener(new SubmitTempButtonListener());
			
			//Add to the desired temperature panel
			JPanel desiredTempPanel = new JPanel(new GridLayout(2, 2));
			desiredTempPanel.add(desiredTempTitleLabel);
			desiredTempPanel.add(desiredTempValueLabel);
			desiredTempPanel.add(desiredTempTextField);
			desiredTempPanel.add(submitTempButton);
			
			//Add panels to this panel
			add(radioPanel);
			add(currentTempPanel);
			add(desiredTempPanel);			
		}
		
		/**
		 * Sets up and displays the GUI
		 */
		private void createAndShowGUI(){
			//Frame setup
			JFrame frame = new JFrame("Central Monitor");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Create and set up the content pane.
	        JComponent newContentPane = this;
	        newContentPane.setOpaque(true); 
	        frame.setContentPane(newContentPane);
	 
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
			
	        //Monitor changes in current temperature tracked in CentralMonitor every second
			Timer currentTempLabelTimer = new Timer();
			currentTempLabelTimer.schedule(new CurrentTempLabelUpdater(), 0, 1000);
			
			//Repaint this component to display the current temperature every second
			repaintCurrentTempLabelTimer = new javax.swing.Timer(1000, this);
			repaintCurrentTempLabelTimer.start();
		}
		
		/**
		 * Starts the GUI in a new thread
		 */
		public void startGUI(){
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
		}
		
		/**
		 * {@inheritDoc}
		 * 
		 * Repaints this component if an action event is received from the repaint timer
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(repaintCurrentTempLabelTimer)){
				repaint();
			}
			
		}
		
		/**
		 * Listens for actions from the HVAC Mode radio buttons in the GUI
		 * 
		 * @author jfoley
		 */
		private class RadioListener implements ActionListener{
			
			/**
			 * {@inheritDoc}
			 * 
			 * Sets the HVAC mode in the application to the mode corresponding
			 * to the selected radio button.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if (e.getActionCommand().equals(COOLING_MODE_STRING)){
						queue.put(Mode.COOLING);
					}
					else if (e.getActionCommand().equals(HEATING_MODE_STRING)){
						queue.put(Mode.HEATING);
					}
					else if (e.getActionCommand().equals(OFF_MODE_STRING)){
						queue.put(Mode.OFF);
					}
				}
				catch(InterruptedException ex){
					//Do nothing
				}
			}
			
		}
		
		/**
		 * Listens for actions from the Submit Desired Temperature button in the GUI
		 * 
		 * @author jfoley
		 */
		private class SubmitTempButtonListener implements ActionListener{
			
			/**
			 * {@inheritDoc}
			 * 
			 * Sets the desired text in the application if the text in the
			 * Desired Temperature Text Field is an integer and updates the 
			 * Desired Tempereature Value Label. Otherwise, clears the text 
			 * field and does nothing.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String fieldText = desiredTempTextField.getText();
				int temp;
				try{
					temp = Integer.valueOf(fieldText);
				}
				catch (NumberFormatException ex){
					desiredTempTextField.setText("");
					return;
				}
				Temperature.setDesired(temp);
				desiredTempValueLabel.setText(fieldText);			}
		}
		
		/**
		 * Updater for the text of the Current Temperature label in the GUI
		 * 
		 * @author jfoley
		 */
		private class CurrentTempLabelUpdater extends TimerTask{
			
			/**
			 * @inheritDoc
			 * 
			 * Updates the text of the Current Temperature label in the GUI.
			 */
			@Override
			public void run() {
				currentTempLabel.setText(String.valueOf(getCurrentTemp()));
			}
			
		}		
	}
}
