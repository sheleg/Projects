import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.awt.font.*;

/** A demonstration of writing custom Stroke classes */
public class CustomStrokes implements GraphSample {
    static final int WIDTH = 750, HEIGHT = 200;        // Size of our example
    public String getName() {return "Custom Strokes";} // From GraphSample
    public int getWidth() { return WIDTH; }            // From GraphSample
    public int getHeight() { return HEIGHT; }          // From GraphSample

    // These are the various stroke objects we'll demonstrate
    Stroke[] strokes = new Stroke[] {
	new BasicStroke(4.0f),          // The standard, predefined stroke
	new NullStroke(),               // A Stroke that does nothing
	new DoubleStroke(8.0f, 2.0f),   // A Stroke that strokes twice
	new ControlPointsStroke(2.0f),  // Shows the vertices & control points
	new SloppyStroke(2.0f, 3.0f)    // Perturbs the shape before stroking
    };

    /** Draw the example */
    public void draw(Graphics2D g, Component c) {
	// Get a shape to work with.  Here we'll use the letter B
	Font f = new Font("Serif", Font.BOLD, 200);
	GlyphVector gv = f.createGlyphVector(g.getFontRenderContext(), "B");
	Shape shape = gv.getOutline();

	// Set drawing attributes and starting position
	g.setColor(Color.black);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			   RenderingHints.VALUE_ANTIALIAS_ON);
	g.translate(10, 175);

	// Draw the shape once with each stroke
	for(int i = 0; i < strokes.length; i++) {
	    g.setStroke(strokes[i]);   // set the stroke
	    g.draw(shape);             // draw the shape
	    g.translate(140,0);        // move to the right
	}
    }
}

/**
 * This Stroke implementation does nothing.  Its createStrokedShape()
 * method returns an unmodified shape.  Thus, drawing a shape with
 * this Stroke is the same as filling that shape!
 **/
class NullStroke implements Stroke {
    public Shape createStrokedShape(Shape s) { return s; }
}

/**
 * This Stroke implementation applies a BasicStroke to a shape twice.
 * If you draw with this Stroke, then instead of outlining the shape,
 * you're outlining the outline of the shape.
 **/
class DoubleStroke implements Stroke {
    BasicStroke stroke1, stroke2;   // the two strokes to use
    public DoubleStroke(float width1, float width2) {
	stroke1 = new BasicStroke(width1);  // Constructor arguments specify
	stroke2 = new BasicStroke(width2);  // the line widths for the strokes
    }

    public Shape createStrokedShape(Shape s) {
	// Use the first stroke to create an outline of the shape
	Shape outline = stroke1.createStrokedShape(s);  
	// Use the second stroke to create an outline of that outline.
	// It is this outline of the outline that will be filled in
	return stroke2.createStrokedShape(outline);
    }
}

/**
 * This Stroke implementation strokes the shape using a thin line, and
 * also displays the end points and Bezier curve control points of all
 * the line and curve segments that make up the shape.  The radius
 * argument to the constructor specifies the size of the control point
 * markers. Note the use of PathIterator to break the shape down into
 * its segments, and of GeneralPath to build up the stroked shape.
 **/
class ControlPointsStroke implements Stroke {
    float radius;  // how big the control point markers should be
    public ControlPointsStroke(float radius) { this.radius = radius; }

    public Shape createStrokedShape(Shape shape) {
	// Start off by stroking the shape with a thin line.  Store the
	// resulting shape in a GeneralPath object so we can add to it.
	GeneralPath strokedShape = 
	    new GeneralPath(new BasicStroke(1.0f).createStrokedShape(shape));
	
	// Use a PathIterator object to iterate through each of the line and
	// curve segments of the shape.  For each one, mark the endpoint and
	// control points (if any) by adding a rectangle to the GeneralPath
	float[] coords = new float[6];
	for(PathIterator i=shape.getPathIterator(null); !i.isDone();i.next()) {
	    int type = i.currentSegment(coords);
	    Shape s = null, s2 = null, s3 = null;
	    switch(type) {
	    case PathIterator.SEG_CUBICTO:
		markPoint(strokedShape, coords[4], coords[5]); // falls through
	    case PathIterator.SEG_QUADTO:
		markPoint(strokedShape, coords[2], coords[3]); // falls through
	    case PathIterator.SEG_MOVETO:
	    case PathIterator.SEG_LINETO:
		markPoint(strokedShape, coords[0], coords[1]); // falls through
	    case PathIterator.SEG_CLOSE:
		break;
	    }
	}

	return strokedShape;
    }

    /** Add a small square centered at (x,y) to the specified path */
    void markPoint(GeneralPath path, float x, float y) {
	path.moveTo(x-radius, y-radius);  // Begin a new sub-path
	path.lineTo(x+radius, y-radius);  // Add a line segment to it
	path.lineTo(x+radius, y+radius);  // Add a second line segment
	path.lineTo(x-radius, y+radius);  // And a third
	path.closePath();                 // Go back to last moveTo position
    }
}

/**
 * This Stroke implementation randomly perturbs the line and curve segments
 * that make up a Shape, and then strokes that perturbed shape.  It uses
 * PathIterator to loop through the Shape and GeneralPath to build up the
 * modified shape.  Finally, it uses a BasicStroke to stroke the modified
 * shape.  The result is a "sloppy" looking shape.
 **/
class SloppyStroke implements Stroke {
    BasicStroke stroke;
    float sloppiness;
    public SloppyStroke(float width, float sloppiness) {
	this.stroke = new BasicStroke(width); // Used to stroke modified shape
	this.sloppiness = sloppiness;         // How sloppy should we be?
    }
    
    public Shape createStrokedShape(Shape shape) {
	GeneralPath newshape = new GeneralPath();  // Start with an empty shape
	
	// Iterate through the specified shape, perturb its coordinates, and
	// use them to build up the new shape.
	float[] coords = new float[6];
	for(PathIterator i=shape.getPathIterator(null); !i.isDone();i.next()) {
	    int type = i.currentSegment(coords);
	    switch(type) {
	    case PathIterator.SEG_MOVETO:
		perturb(coords, 2);
		newshape.moveTo(coords[0], coords[1]);
		break;
	    case PathIterator.SEG_LINETO:
		perturb(coords, 2);
		newshape.lineTo(coords[0], coords[1]);
		break;
	    case PathIterator.SEG_QUADTO:
		perturb(coords, 4);
		newshape.quadTo(coords[0], coords[1], coords[2], coords[3]);
		break;
	    case PathIterator.SEG_CUBICTO:
		perturb(coords, 6);
		newshape.curveTo(coords[0], coords[1], coords[2], coords[3],
				 coords[4], coords[5]);
		break;
	    case PathIterator.SEG_CLOSE:
		newshape.closePath();
		break;
	    }
	}

	// Finally, stroke the perturbed shape and return the result
	return stroke.createStrokedShape(newshape);
    }

    // Randomly modify the specified number of coordinates, by an amount
    // specified by the sloppiness field.
    void perturb(float[] coords, int numCoords) {
	for(int i = 0; i < numCoords; i++)
	    coords[i] += (float)((Math.random()*2-1.0)*sloppiness);
    }
}


////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "CustomStrokes";
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
