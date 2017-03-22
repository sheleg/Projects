import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.*;

/** A demonstration of Java2D transformations */
public class Paints implements GraphSample {
    static final int WIDTH = 800, HEIGHT = 375;  // Size of our example
    public String getName() { return "Paints"; } // From GraphSample
    public int getWidth() { return WIDTH; }      // From GraphSample
    public int getHeight() { return HEIGHT; }    // From GraphSample

    /** Draw the example */
    public void draw(Graphics2D g, Component c) {
	// Paint the entire background using a GradientPaint.
	// The background color varies diagonally from deep red to pale blue
	g.setPaint(new GradientPaint(0, 0, new Color(150, 0, 0),
				     WIDTH, HEIGHT, new Color(200, 200, 255)));
	g.fillRect(0, 0, WIDTH, HEIGHT);          // fill the background

	// Use a different GradientPaint to draw a box.
	// This one alternates between deep opaque green and transparent green.
	// Note: the 4th arg to Color() constructor specifies color opacity
	g.setPaint(new GradientPaint(0, 0, new Color(0, 150, 0),
				     20, 20, new Color(0, 150, 0, 0), true));
	g.setStroke(new BasicStroke(15));         // use wide lines
	g.drawRect(25, 25, WIDTH-50, HEIGHT-50);  // draw the box

	// The glyphs of fonts can be used as Shape objects, which enables
	// us to use Java2D techniques with letters Just as we would with
	// any other shape.  Here we get some letter shapes to draw.
	Font font = new Font("Serif", Font.BOLD, 8);  // a basic font
	Font bigfont =                                // a scaled up version
	    font.deriveFont(AffineTransform.getScaleInstance(30.0, 30.0));
	GlyphVector gv = bigfont.createGlyphVector(g.getFontRenderContext(),
						   "JAVA");
	Shape jshape = gv.getGlyphOutline(0);   // Shape of letter J
	Shape ashape = gv.getGlyphOutline(1);   // Shape of letter A
	Shape vshape = gv.getGlyphOutline(2);   // Shape of letter V
	Shape a2shape = gv.getGlyphOutline(3);  // Shape of second letter A

	// We're going to outline the letters with a 5-pixel wide line
	g.setStroke(new BasicStroke(5.0f));

	// We're going to fake shadows for the letters using the
	// following Paint and AffineTransform objects
	Paint shadowPaint = new Color(0, 0, 0, 100);     // Translucent black
	AffineTransform shadowTransform =
	    AffineTransform.getShearInstance(-1.0, 0.0); // Shear to the right
	shadowTransform.scale(1.0, 0.5);                 // Scale height by 1/2

	// Move to the baseline of our first letter
	g.translate(65, 270);

	// Draw the shadow of the J shape
	g.setPaint(shadowPaint);
	g.translate(15,20);     // Compensate for the descender
	// transform the J into the shape of its shadow, and fill it
	g.fill(shadowTransform.createTransformedShape(jshape));
	g.translate(-15,-20);   // Undo the translation above

	// Now fill the J shape with a solid (and opaque) color
	g.setPaint(Color.blue);     // Fill with solid, opaque blue
	g.fill(jshape);             // Fill the shape
	g.setPaint(Color.black);    // Switch to solid black
	g.draw(jshape);             // And draw the outline of the J

	// Now draw the A shadow
	g.setPaint(shadowPaint);    // Set shadow color
	g.translate(15,20);     // Compensate for the descender
	g.fill(shadowTransform.createTransformedShape(ashape)); // draw shadow
	g.translate(-15,-20);   // Undo the translation above

	// Draw the A shape using a solid transparent color
	g.setPaint(new Color(0, 255, 0, 125));  // Transparent green as paint
	g.fill(ashape);                         // Fill the shape
	g.setPaint(Color.black);                // Switch to solid back
	g.draw(ashape);                         // Draw the outline
	
	// Move to the right and draw the shadow of the letter V
	g.setPaint(shadowPaint);
	g.translate(15,20);     // Compensate for the descender
	g.fill(shadowTransform.createTransformedShape(vshape));
	g.translate(-15,-20);   // Undo the translation above

	// We're going to fill the next letter using a TexturePaint, which
	// repeatedly tiles an image. The first step is to obtain the image.
	// We could load it from an image file, but here we create it 
	// ourselves by drawing a into an off-screen image.  Note that we use
	// a GradientPaint to fill the off-screen image, so the fill pattern
	// combines features of both Paint classes.
	BufferedImage tile =                   // Create an image
	    new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
	Graphics2D tg = tile.createGraphics(); // Get its Graphics for drawing
	tg.setColor(Color.pink);       
	tg.fillRect(0, 0, 50, 50);     // Fill tile background with pink
	tg.setPaint(new GradientPaint(40, 0, Color.green,  // diagonal gradient
				      0, 40, Color.gray)); // green to gray
	tg.fillOval(5, 5, 40, 40);     // Draw a circle with this gradient

	// Use this new tile to create a TexturePaint and fill the letter V
	g.setPaint(new TexturePaint(tile, new Rectangle(0, 0, 50, 50)));
	g.fill(vshape);                        // Fill letter shape
	g.setPaint(Color.black);               // Switch to solid black
	g.draw(vshape);                        // Draw outline of letter

	// Move to the right and draw the shadow of the final A
	g.setPaint(shadowPaint);
	g.translate(15,20);     // Compensate for the descender
	g.fill(shadowTransform.createTransformedShape(a2shape));
	g.translate(-15,-20);   // Undo the translation above

	// For the last letter, use a custom Paint class to fill with a 
	// complex mathematically defined pattern. The GenericPaint
	// class is defined in GenericPaint.java.
	g.setPaint(new GenericPaint() {
		public int computeRed(double x, double y) { return 128; }
		public int computeGreen(double x, double y) {
		    return (int)((Math.sin(x/7) + Math.cos(y/5) + 2)/4 *255);
		}
		public int computeBlue(double x, double y) {
		    return ((int)(x*y))%256;
		}
		public int computeAlpha(double x, double y) {
		    return ((int)x%25*8+50) + ((int)y%25*8+50);
		}
	    });
	g.fill(a2shape);               // Fill letter A
	g.setPaint(Color.black);       // Revert to solid black
	g.draw(a2shape);               // Draw the outline of the A
    }
}


////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "Paints";
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
