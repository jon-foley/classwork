package ducksim;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MakeDuckDialog extends JDialog {

    // Fields
    
    // Duck panel
    private final JPanel duckPanel = new JPanel();
    private final JLabel duckLabel = new JLabel("Duck");
    private final String[] duckStrings = {"Mallard", "Redhead", "Rubber", "Decoy", "Goose"};
    private final JComboBox duckOptions = new JComboBox(duckStrings);
    
    //Bling panel
    private final JPanel blingPanel;
    private final JLabel starLabel = new JLabel("Star");
    private final JLabel starCounter = new JLabel("0");
    private final JLabel moonLabel = new JLabel("Moon");
    private final JLabel moonCounter = new JLabel("0");
    private final JLabel crossLabel = new JLabel("Cross");
    private final JLabel crossCounter = new JLabel("0");
    private final JButton incStarButton = new JButton("+");
    private final JButton incMoonButton = new JButton("+");
    private final JButton incCrossButton = new JButton("+");
    private final JButton decStarButton = new JButton("-");
    private final JButton decMoonButton = new JButton("-");
    private final JButton decCrossButton = new JButton("-");
    
    // Button panel
    private final JPanel buttonPanel = new JPanel();
    private final JButton okayButton = new JButton("Okay");
    private final JButton cancelButton = new JButton("Cancel");

    // Stored Data
    private String duckType = "Mallard";
    private int crossCount = 0;
    private int starCount = 0;
    private int moonCount = 0;
    private int blingCount = 0;
    
    // Duck Factory
    private final DuckFactory factory = DuckFactory.getInstance();
    
    // Constructor
    public MakeDuckDialog(DuckSimModel model, DuckSimView view) {

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // add duck panel
        duckPanel.add(duckLabel);
        duckOptions.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            duckType = (String) cb.getSelectedItem();
        });
        duckPanel.add(duckOptions);
        this.add(duckPanel);
        
        //create bling panel
        GridLayout grid = new GridLayout();
        grid.setRows(3);
        grid.setColumns(4);
        blingPanel = new JPanel(grid);
        
        //star row
        blingPanel.add(starLabel);
        blingPanel.add(starCounter);
        incStarButton.addActionListener(e -> {
        	if (starCount < 3 && blingCount < 3){
        		starCount++;
        		blingCount++;
        		starCounter.setText(Integer.toString(starCount));
        	}
        });
        blingPanel.add(incStarButton);
        decStarButton.addActionListener(e -> {
        	if (starCount > 0){
        		starCount--;
        		blingCount--;
        		starCounter.setText(Integer.toString(starCount));
        	}
        });
        blingPanel.add(decStarButton);
        
        //moon row
        blingPanel.add(moonLabel);
        blingPanel.add(moonCounter);
        incMoonButton.addActionListener(e -> {
        	if (moonCount < 3 && blingCount < 3){
        		moonCount++;
        		blingCount++;
        		moonCounter.setText(Integer.toString(moonCount));
        	}
        });
        blingPanel.add(incMoonButton);
        decMoonButton.addActionListener(e -> {
        	if (moonCount > 0){
        		moonCount--;
        		blingCount--;
        		moonCounter.setText(Integer.toString(moonCount));
        	}
        });
        blingPanel.add(decMoonButton);
        
        //add bling panel
        this.add(blingPanel);
        
        //cross row
        blingPanel.add(crossLabel);
        blingPanel.add(crossCounter);
        incCrossButton.addActionListener(e -> {
        	if (crossCount < 3 && blingCount < 3){
        		crossCount++;
        		blingCount++;
        		crossCounter.setText(Integer.toString(crossCount));
        	}
        });
        blingPanel.add(incCrossButton);
        decCrossButton.addActionListener(e -> {
        	if (crossCount > 0){
        		crossCount--;
        		blingCount--;
        		crossCounter.setText(Integer.toString(crossCount));
        	}
        });
        blingPanel.add(decCrossButton);
        
        
        // add button panel
        cancelButton.addActionListener(e -> {
            this.dispose();
        });
        buttonPanel.add(cancelButton);
        okayButton.addActionListener(e -> {
            // makeDuckDialog
            Duck duck = factory.createDuck(duckType, starCount, moonCount, crossCount);
            if (duck != null) {
                model.addNewDuck(duck);
    			factory.notifyObservers();
    			factory.addObserver(duck);
            }
            view.repaint();
            this.dispose();
        });
        buttonPanel.add(okayButton);
        this.add(buttonPanel);
    }

    // Public Methods
    public String getDuckType() {
        return duckType;
    }
}
