import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.awt.image.*;

/**
 * A demonstration of Java2D shapes
 */
public class Shapes implements GraphSample {
    static final int WIDTH = 725, HEIGHT = 250;    // Size of our example

    public String getName() {
        return "Shapes";
    }     // From GraphSample

    public int getWidth() {
        return WIDTH;
    }        // From GraphSample

    public int getHeight() {
        return HEIGHT;
    }      // From GraphSample

    Shape[] shapes = new Shape[]{
            // A straight line segment
            new Line2D.Float(0, 0, 100, 100),
            // A quadratic bezier curve.  Two end points and one control point
            new QuadCurve2D.Float(0, 0, 80, 15, 100, 100),
            // A cubic bezier curve.  Two end points and two control points
            new CubicCurve2D.Float(0, 0, 80, 15, 10, 90, 100, 100),
            // A 120 degree portion of an ellipse
            new Arc2D.Float(-30, 0, 100, 100, 60, -120, Arc2D.OPEN),
            // A 120 degree portion of an ellipse, closed with a chord
            new Arc2D.Float(-30, 0, 100, 100, 60, -120, Arc2D.CHORD),
            // A 120 degree pie slice of an ellipse
            new Arc2D.Float(-30, 0, 100, 100, 60, -120, Arc2D.PIE),
            // An ellipse
            new Ellipse2D.Float(0, 20, 100, 60),
            // A rectangle
            new Rectangle2D.Float(0, 20, 100, 60),
            // A rectangle with rounded corners
            new RoundRectangle2D.Float(0, 20, 100, 60, 15, 15),
            // A triangle
            new Polygon(new int[]{0, 0, 100}, new int[]{20, 80, 80}, 3),
            // A random polygon, initialized in code below
            null,
            // A spiral: an instance of a custom Shape implementation
            new Spiral(50, 50, 5, 0, 50, 4 * Math.PI),
    };

    {   // Initialize the null shape above as a Polygon with random points
        Polygon p = new Polygon();
        for (int i = 0; i < 10; i++)
            p.addPoint((int) (100 * Math.random()), (int) (100 * Math.random()));
        shapes[10] = p;
    }

    // These are the labels for each of the shapes 
    String[] labels = new String[]{
            "Line2D", "QuadCurve2D", "CubicCurve2D", "Arc2D (OPEN)",
            "Arc2D (CHORD)", "Arc2D (PIE)", "Ellipse2D", "Rectangle2D",
            "RoundRectangle2D", "Polygon", "Polygon (random)", "Spiral"
    };

    /**
     * Draw the example
     */
    public void draw(Graphics2D g, Component c) {
        // Set basic drawing attributes
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));      // select font
        g.setStroke(new BasicStroke(2.0f));                    // 2 pixel lines
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    // antialiasing
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.translate(10, 10);                                   // margins

        // Loop through each shape
        for (int i = 0; i < shapes.length; i++) {
            g.setColor(Color.green);             // Set a color
            g.fill(shapes[i]);                   // Fill the shape with it
            g.setColor(Color.black);             // Switch to black
            g.draw(shapes[i]);                   // Outline the shape with it
            g.drawString(labels[i], 0, 110);     // Label the shape
            g.translate(120, 0);                 // Move over for next shape
            if (i % 6 == 5) g.translate(-6 * 120, 120);  // Move down after 6
        }
    }
}

////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {
    // The class name of the requested example
    static final String classname = "Shapes";

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
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // In addition to the Quit menu item, also handle window close events
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Insert each of the example objects into the tabbed pane
        for (int i = 0; i < examples.length; i++) {
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
            setMaximumSize(size);
        }

        /**
         * Draw the component and the example it contains
         */
        public void paintComponent(Graphics g) {
            g.setColor(Color.white);                    // set the background
            g.fillRect(0, 0, size.width, size.height);  // to white
            g.setColor(Color.black);             // set a default drawing color
            example.draw((Graphics2D) g, this);  // ask example to draw itself
        }

        // These methods specify how big the component must be
        public Dimension getPreferredSize() {
            return size;
        }

        public Dimension getMinimumSize() {
            return size;
        }
    }

    public static void main(String[] args) {
        GraphSample[] examples = new GraphSample[1];

        // Try to instantiate the named GraphSample class
        try {
            Class exampleClass = Class.forName(classname);
            examples[0] = (GraphSample) exampleClass.newInstance();
        } catch (ClassNotFoundException e) {  // unknown class
            System.err.println("Couldn't find example: " + classname);
            System.exit(1);
        } catch (ClassCastException e) {      // wrong type of class
            System.err.println("Class " + classname +
                    " is not a GraphSample");
            System.exit(1);
        } catch (Exception e) {  // class doesn't have a public constructor
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
