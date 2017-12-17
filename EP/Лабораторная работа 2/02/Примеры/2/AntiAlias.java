import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.image.*;

/** A demonstration of anti-aliasing */
public class AntiAlias implements GraphSample {
    static final int WIDTH = 650, HEIGHT = 350;        // Size of our example
    public String getName() {return "AntiAliasing";}   // From GraphSample
    public int getWidth() { return WIDTH; }            // From GraphSample
    public int getHeight() { return HEIGHT; }          // From GraphSample

    /** Draw the example */
    public void draw(Graphics2D g, Component c) {
	BufferedImage image =                   // Create an off-screen image
	    new BufferedImage(65, 35, BufferedImage.TYPE_INT_RGB);
	Graphics2D ig = image.createGraphics(); // Get its Graphics for drawing

	// Set the background to a gradient fill.  The varying color of
	// the background helps to demonstrate the anti-aliasing effect
	ig.setPaint(new GradientPaint(0,0,Color.black,65,35,Color.white));
	ig.fillRect(0, 0, 65, 35);

	// Set drawing attributes for the foreground.
	// Most importantly, turn on anti-aliasing.
	ig.setStroke(new BasicStroke(2.0f));                   // 2-pixel lines
	ig.setFont(new Font("Serif", Font.BOLD, 18));          // 18-point font
	ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,   // Anti-alias!
			    RenderingHints.VALUE_ANTIALIAS_ON);

	// Now draw pure blue text and a pure red oval
	ig.setColor(Color.blue);
	ig.drawString("Java", 9, 22);
	ig.setColor(Color.red);
	ig.drawOval(1, 1, 62, 32);
	
	// Finally, scale the image by a factor of 10 and display it
	// in the window.  This will allow us to see the anti-aliased pixels
	g.drawImage(image, AffineTransform.getScaleInstance(10, 10), c);

	// Draw the image one more time at its original size, for comparison
	g.drawImage(image, 0, 0, c);
    }
}

////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "AntiAlias";
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
