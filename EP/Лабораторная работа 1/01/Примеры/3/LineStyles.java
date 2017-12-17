import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/** A demonstration of Java2D line styles */
public class LineStyles implements GraphSample {
    public String getName() { return "LineStyles"; }  // From GraphSample
    public int getWidth() { return 450; }             // From GraphSample
    public int getHeight() { return 180; }            // From GraphSample

    int[] xpoints = new int[] { 0, 50, 100 };  // X coordinates of our shape
    int[] ypoints = new int[] { 75, 0, 75 };   // Y coordinates of our shape

    // Here are three different line styles we will demonstrate
    // They are thick lines with different cap and join styles
    Stroke[] linestyles = new Stroke[] {
	new BasicStroke(25.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL),
	new BasicStroke(25.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER),
	new BasicStroke(25.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND),
    };
    
    // Another line style: a 2 pixel-wide dot-dashed line
    Stroke thindashed = new BasicStroke(2.0f,  // line width
	/* cap style */			BasicStroke.CAP_BUTT,
	/* join style, miter limit */	BasicStroke.JOIN_BEVEL, 1.0f,
        /* the dash pattern */	        new float[] {8.0f, 3.0f, 2.0f, 3.0f},
	/* the dash phase */		0.0f);   /* on 8, off 3, on 2, off 3 */

    // Labels to appear in the diagram, and the font to use to display them.
    Font font = new Font("Helvetica", Font.BOLD, 12);
    String[] capNames = new String[] {"CAP_BUTT", "CAP_SQUARE","CAP_ROUND"};
    String[] joinNames = new String[] {"JOIN_BEVEL","JOIN_MITER","JOIN_ROUND"};

    /** This method draws the example figure */
    public void draw(Graphics2D g, Component c) {
	// Use anti-aliasing to avoid "jaggies" in the lines
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			   RenderingHints.VALUE_ANTIALIAS_ON);

	// Define the shape to draw
	GeneralPath shape = new GeneralPath();
	shape.moveTo(xpoints[0], ypoints[0]);   // start at point 0
	shape.lineTo(xpoints[1], ypoints[1]);   // draw a line to point 1
	shape.lineTo(xpoints[2], ypoints[2]);   // and then on to point 2

	// Move the origin to the right and down, creating a margin
	g.translate(20,40);

	// Now loop, drawing our shape with the three different line styles
	for(int i = 0; i < linestyles.length; i++) {
	    g.setColor(Color.blue);       // Draw a blue line
	    g.setStroke(linestyles[i]);   // Select the line style to use
	    g.draw(shape);                // Draw the shape

	    g.setColor(Color.yellow);     // Now use yellow
	    g.setStroke(thindashed);      // And the thin dashed line
	    g.draw(shape);                // And draw the shape again.

	    // Highlight the location of the vertexes of the shape
	    // This accentuates the cap and join styles we're demonstrating
	    for(int j = 0; j < xpoints.length; j++) 
		g.fillRect(xpoints[j]-2, ypoints[j]-2, 5, 5);

	    g.setColor(Color.black);             // black
	    g.drawString(capNames[i], 5, 105);   // Label the cap style
	    g.drawString(joinNames[i], 5, 120);  // Label the join style

	    g.translate(150, 0); // Move over to the right before looping again
	}
    }
}

////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "LineStyles";
    public GraphSampleFrame(final GraphSample[] examples) {
	super("GraphSampleFrame");

	Container cpane = getContentPane();   // Set up the frame 
	cpane.setLayout(new BorderLayout());
	final JTabbedPane tpane = new JTabbedPane(); // And the tabbed pane 
	cpane.add(tpane, BorderLayout.CENTER);

	// Add a menubar
	JMenuBar menubar = new JMenuBar();         // Create the menubar
	this.setJMenuBar(menubar);                 // Add it to the frame
	JMenu filemenu = new JMenu("File");        // Create a File menu
	menubar.add(filemenu);                     // Add to the menubar
	JMenuItem quit = new JMenuItem("Quit");    // Create a Quit item
	filemenu.add(quit);                        // Add it to the menu

	// Tell the Quit menu item what to do when selected
	quit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) { System.exit(0); }
	    });

	// In addition to the Quit menu item, also handle window close events
	this.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) { System.exit(0); }
	    });

	// Insert each of the example objects into the tabbed pane
	for(int i = 0; i < examples.length; i++) {
	    GraphSample e = examples[i];
	    tpane.addTab(e.getName(), new GraphSamplePane(e));
	}
    }

    /**
     * This inner class is a custom Swing component that displays
     * a GraphSample object.
     */
    public class GraphSamplePane extends JComponent {
	GraphSample example;  // The example to display
	Dimension size;           // How much space it requires
	
	public GraphSamplePane(GraphSample example) {
	    this.example = example;
	    size = new Dimension(example.getWidth(), example.getHeight());
            setMaximumSize( size );
	}

	/** Draw the component and the example it contains */
	public void paintComponent(Graphics g) {
	    g.setColor(Color.white);                    // set the background
	    g.fillRect(0, 0, size.width, size.height);  // to white
	    g.setColor(Color.black);             // set a default drawing color
	    example.draw((Graphics2D) g, this);  // ask example to draw itself
	}

	// These methods specify how big the component must be
	public Dimension getPreferredSize() { return size; }
	public Dimension getMinimumSize() { return size; }
    }

    public static void main(String[] args) {
	GraphSample[] examples = new GraphSample[1];

	    // Try to instantiate the named GraphSample class
	    try {
		Class exampleClass = Class.forName(classname);
		examples[0] = (GraphSample) exampleClass.newInstance();
	    }
	    catch (ClassNotFoundException e) {  // unknown class
		System.err.println("Couldn't find example: "  + classname);
		System.exit(1);
	    }
	    catch (ClassCastException e) {      // wrong type of class
		System.err.println("Class " + classname + 
				   " is not a GraphSample");
		System.exit(1);
	    }
	    catch (Exception e) {  // class doesn't have a public constructor
		// catch InstantiationException, IllegalAccessException
		System.err.println("Couldn't instantiate example: " +
				   classname);
		System.exit(1);
	    }

	// Now create a window to display the examples in, and make it visible
	GraphSampleFrame f = new GraphSampleFrame(examples);
	f.pack();
	f.setVisible(true);
    }
}
////////////////////////////////////////////////////////////////////////////
// interface GraphSample
////////////////////////////////////////////////////////////////////////////
/**
 * This interface defines the methods that must be implemented by an
 * object that is to be displayed by the GraphSampleFrame object
 */
interface GraphSample {
    public String getName();                      // Return the example name
    public int getWidth();                        // Return its width
    public int getHeight();                       // Return its height
    public void draw(Graphics2D g, Component c);  // Draw the example
}
