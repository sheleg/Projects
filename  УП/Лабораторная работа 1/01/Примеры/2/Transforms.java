import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/** A demonstration of Java2D transformations */
public class Transforms implements GraphSample {
    public String getName() { return "Transforms"; } // From GraphSample
    public int getWidth() { return 750; }            // From GraphSample
    public int getHeight() { return 250; }           // From GraphSample

    Shape shape;                   // The shape to draw
    AffineTransform[] transforms;  // The ways to transform it
    String[] transformLabels;      // Labels for each transform

    /** Create a shape to draw */
    static Shape createShape() {
	GeneralPath path = new GeneralPath();  	// Create a shape to draw
	path.append(new Line2D.Float(0.0f, 0.0f, 0.0f, 100.0f), false);
	path.append(new Line2D.Float(-10.0f, 50.0f, 10.0f, 50.0f), false);
	path.append(new Polygon(new int[] { -5, 0, 5 },
				new int[] { 5, 0, 5 }, 3), false);
        return path;
    }

    /** 
     * This constructor sets up the Shape and AffineTransform objects we need
     **/
    public Transforms() {
	//this.shape = path;  // Remember this shape
        this.shape = createShape(); // Remember this shape

	// Set up some transforms to alter the shape
	this.transforms = new AffineTransform[6];
	// 1) the identity transform
	transforms[0] = new AffineTransform();  
	// 2) A scale tranform: 3/4 size
	transforms[1] = AffineTransform.getScaleInstance(0.75, 0.75);
	// 3) A shearing transform
	transforms[2] = AffineTransform.getShearInstance(-0.4, 0.0);
	// 4) A 30 degree clockwise rotation about the origin of the shape
	transforms[3] = AffineTransform.getRotateInstance(Math.PI*2/12);
	// 5) A 180 degree rotation about the midpoint of the shape
	transforms[4] = AffineTransform.getRotateInstance(Math.PI, 0.0, 50.0);
	// 6) A combination transform
	transforms[5] = AffineTransform.getScaleInstance(0.5, 1.5);
	transforms[5].shear(0.0, 0.4);
	transforms[5].rotate(Math.PI/2, 0.0, 50.0);  // 90 degrees

	// Define names for the transforms
	transformLabels = new String[] {
	    "identity", "scale", "shear", "rotate", "rotate", "combo"
	};
    }

    /** Draw the defined shape and label, using each transform */
    public void draw(Graphics2D g, Component c) {
	// Define basic drawing attributes
	g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE,  // 2-pixel
				    BasicStroke.JOIN_BEVEL));
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,        // antialias
			   RenderingHints.VALUE_ANTIALIAS_ON);

	// Now draw the shape once using each of the transforms we've defined
	for(int i = 0; i < transforms.length; i++) {
	    g.setColor(Color.blue);                     // blue
	    AffineTransform save = g.getTransform();    // save current state
	    g.translate(i*125 + 50, 50);                // move origin
	    g.transform(transforms[i]);                 // apply transform
	    g.draw(shape);                              // draw shape
	    g.setColor(Color.red);                      // red
	    g.drawString(transformLabels[i], -25, 125); // draw label
	    g.setColor(Color.black);                    // black
	    g.drawRect(-40, -10, 80, 150);              // draw box
	    g.setTransform(save);                       // restore transform
	}
    }
}

////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "Transforms";
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
