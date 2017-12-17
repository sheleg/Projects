import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.image.*;

public class CompositeEffects implements GraphSample {
    Image cover;  // The image we'll be displaying, and its size
    static final int COVERWIDTH = 127, COVERHEIGHT = 190;

    /** This constructor loads the cover image */
    public CompositeEffects() {
	java.net.URL imageurl = this.getClass().getResource("cover.gif");
	cover = new javax.swing.ImageIcon(imageurl).getImage();
    }

    // These are basic GraphSample methods
    public String getName() {return "Composite Effects";} 
    public int getWidth() { return 6*COVERWIDTH + 70; } 
    public int getHeight() { return COVERHEIGHT + 35; }

    /** Draw the example */
    public void draw(Graphics2D g, Component c) {
	// fill the background 
	g.setPaint(new Color(175, 175, 175));
	g.fillRect(0, 0, getWidth(), getHeight());

	// Set text attributes
	g.setColor(Color.black);
	g.setFont(new Font("SansSerif", Font.BOLD, 12));

	// Draw the unmodified image
	g.translate(10, 10);
	g.drawImage(cover, 0, 0, c);
	g.drawString("SRC_OVER", 0, COVERHEIGHT+15);

	// Draw the cover again, using AlphaComposite to make the opaque
	// colors of the image 50% translucent
	g.translate(COVERWIDTH+10, 0);
	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						  0.5f));
	g.drawImage(cover, 0, 0, c);

	// Restore the pre-defined default Composite for the screen, so
	// opaque colors stay opaque.
	g.setComposite(AlphaComposite.SrcOver);
	// Label the effect
	g.drawString("SRC_OVER, 50%", 0, COVERHEIGHT+15);

	// Now get an offscreen image to work with.  In order to achieve
	// certain compositing effects, the drawing surface must support
	// transparency. Onscreen drawing surfaces cannot, so we have to do the
	// compositing in an offscreen image that is specially created to have
	// an "alpha channel", then copy the final result to the screen.
	BufferedImage offscreen =
	    new BufferedImage(COVERWIDTH, COVERHEIGHT,
			      BufferedImage.TYPE_INT_ARGB);
	
	// First, fill the image with a color gradient background that varies
	// left-to-right from opaque to transparent yellow
	Graphics2D osg = offscreen.createGraphics();
	osg.setPaint(new GradientPaint(0, 0, Color.yellow,
				       COVERWIDTH, 0,
				       new Color(255, 255, 0, 0))); 
	osg.fillRect(0,0, COVERWIDTH, COVERHEIGHT);

	// Now copy the cover image on top of this, but use the DstOver rule
	// which draws it "underneath" the existing pixels, and allows the
	// image to show depending on the transparency of those pixels.
	osg.setComposite(AlphaComposite.DstOver);
	osg.drawImage(cover, 0, 0, c);
	
	// And display this composited image on the screen.  Note that the
	// image is opaque and that none of the screen background shows through
	g.translate(COVERWIDTH+10, 0);
	g.drawImage(offscreen, 0, 0, c);
	g.drawString("DST_OVER", 0, COVERHEIGHT+15);

	// Now start over and do a new effect with the off-screen image.
	// First, fill the offscreen image with a new color gradient.  We
	// don't care about the colors themselves; we just want the
	// translucency of the background to vary.  We use opaque black to
	// transparent black. Note that since we've already used this offscreen
	// image, we set the composite to Src, we can fill the image and
	// ignore anything that is already there.
	osg.setComposite(AlphaComposite.Src); 
	osg.setPaint(new GradientPaint(0, 0, Color.black,
				       COVERWIDTH, COVERHEIGHT,
				       new Color(0, 0, 0, 0))); 
	osg.fillRect(0,0, COVERWIDTH, COVERHEIGHT);
	
	// Now set the compositing type to SrcIn, so colors come from the 
	// source, but translucency comes from the destination
	osg.setComposite(AlphaComposite.SrcIn);
	
	// Draw our loaded image into the off-screen image, compositing it.
	osg.drawImage(cover, 0, 0, c);
	
	// And then copy our off-screen image to the screen.  Note that the
	// image is translucent and some of the image shows through.
	g.translate(COVERWIDTH+10, 0);
	g.drawImage(offscreen, 0, 0, c);
	g.drawString("SRC_IN", 0, COVERHEIGHT+15);
	
	// If we do the same thing but use SrcOut, then the resulting image 
	// will have the inverted translucency values of the destination
	osg.setComposite(AlphaComposite.Src); 
	osg.setPaint(new GradientPaint(0, 0, Color.black,
				       COVERWIDTH, COVERHEIGHT,
				       new Color(0, 0, 0, 0))); 
	osg.fillRect(0,0, COVERWIDTH, COVERHEIGHT);
	osg.setComposite(AlphaComposite.SrcOut);
	osg.drawImage(cover, 0, 0, c);
	g.translate(COVERWIDTH+10, 0);
	g.drawImage(offscreen, 0, 0, c);
	g.drawString("SRC_OUT", 0, COVERHEIGHT+15);

	// Here's a cool effect; it has nothing to do with compositing, but
	// uses an arbitrary shape to clip the image.  It uses Area to combine
	// shapes into more complicated ones.
	g.translate(COVERWIDTH+10, 0);
	Shape savedClip = g.getClip();  // Save current clipping region
	// Create a shape to use as the new clipping region.  
	// Begin with an ellipse
	Area clip = new Area(new Ellipse2D.Float(0,0,COVERWIDTH,COVERHEIGHT));
	// Intersect with a rectangle, truncating the ellipse.
	clip.intersect(new Area(new Rectangle(5,5,
					      COVERWIDTH-10,COVERHEIGHT-10)));
	// Then subtract an ellipse from the bottom of the truncated ellipse.
	clip.subtract(new Area(new Ellipse2D.Float(COVERWIDTH/2-40,
						   COVERHEIGHT-20, 80, 40)));
	// Use the resulting shape as the new clipping region
	g.clip(clip);
	// Then draw the image through this clipping region
	g.drawImage(cover, 0, 0, c);
	// Restore the old clipping region so we can label the effect
	g.setClip(savedClip);
	g.drawString("Clipping", 0, COVERHEIGHT+15);
    }
}

////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "CompositeEffects";
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
