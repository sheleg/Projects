import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/** A demonstration of how Stroke objects work */
public class Stroking implements GraphSample {
    static final int WIDTH = 725, HEIGHT = 250;  // Size of our example
    public String getName() {return "Stroking";} // From GraphSample
    public int getWidth() { return WIDTH; }      // From GraphSample
    public int getHeight() { return HEIGHT; }    // From GraphSample

    /** Draw the example */
    public void draw(Graphics2D g, Component c) {
	// Create the shape we'll work with.  See convenience method below.
	Shape pentagon = createRegularPolygon(5, 75);

	// Set up basic drawing attributes
	g.setColor(Color.black);                          // Draw in black
	g.setStroke(new BasicStroke(1.0f));               // Use thin lines
	g.setFont(new Font("SansSerif", Font.PLAIN, 12)); // Basic small font

	g.translate(100, 100);                        // Move to position
	g.draw(pentagon);                             // Outline the shape
	g.drawString("The shape", -30, 90);           // Draw the caption

	g.translate(175, 0);                          // Move over
	g.fill(pentagon);                             // Fill the shape
	g.drawString("The filled shape", -50, 90);    // Another caption

	// Now use a Stroke object to create a "stroked shape" for our shape
	BasicStroke wideline = new BasicStroke(10.0f);
	Shape outline = wideline.createStrokedShape(pentagon);

	g.translate(175, 0);                          // Move over
	g.draw(outline);                              // Draw the stroked shape
	g.drawString("A Stroke creates",-50,90);      // Draw the caption
	g.drawString("a new shape", -35, 105);

	g.translate(175,0);                           // Move over
	g.fill(outline);                              // Fill the stroked shape
	g.drawString("Filling the new shape",-65,90); // Draw the caption
	g.drawString("outlines the old one",-65,105);
    }

    // A convenience method to define a regular polygon.
    // Returns a shape that represents a regular polygon with the specified
    // radius and number of sides, and centered at the origin.
    public Shape createRegularPolygon(int numsides, int radius) {
	Polygon p = new Polygon();               
	double angle = 2 * Math.PI / numsides;   // Angle between vertices
	for(int i = 0; i < numsides; i++)  // Compute location of each vertex
	    p.addPoint((int)(radius * Math.sin(angle*i)),
		       (int)(radius * -Math.cos(angle*i)));
	return p;  
    }
}

////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "Stroking";
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
