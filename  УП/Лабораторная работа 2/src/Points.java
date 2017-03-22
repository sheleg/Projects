/**
 * Created by vladasheleg on 14.02.17.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Points implements GraphSample {
    static final int WIDTH = 1080, HEIGHT = 375;
    public String getName() { return "Paints"; }
    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }

    public void draw(Graphics2D ig, Component c) {

        BufferedImage bimage = new BufferedImage(WIDTH/2, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setPaint(new GradientPaint(0, 0, new Color(121, 121, 121), 0, HEIGHT/2, new Color(200, 200, 255)));
        g.fillRect(0, 0, WIDTH/2, HEIGHT);


        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(20));
        g.drawRect(25, 25, WIDTH/2 - 50, HEIGHT - 50);

        Font font = new Font("Serif", Font.BOLD, 8);
        Font bigfont = font.deriveFont(AffineTransform.getScaleInstance(30.0, 30.0));
        GlyphVector gv = bigfont.createGlyphVector(g.getFontRenderContext(),
                "GO");
        Shape gshape = gv.getGlyphOutline(0);
        Shape oshape = gv.getGlyphOutline(1);

        g.setStroke(new BasicStroke(5.0f));

        Paint shadowPaint = new Color(0, 0, 0, 100);
        AffineTransform shadowTransform = AffineTransform.getShearInstance(-1.0, 0.0);
        shadowTransform.scale(1.0, 0.5);

        g.translate(65, 270);
        g.setPaint(shadowPaint);
        g.translate(15, 20);
        g.fill(shadowTransform.createTransformedShape(gshape));
        g.translate(-15, -20);

        g.translate(15, 20);
        g.fill(shadowTransform.createTransformedShape(oshape));
        g.translate(-15, -20);
        g.setPaint(Color.blue);
        g.fill(gshape);
        g.fill(oshape);

        ig.drawImage(bimage, 0,0,c);
        ig.drawString("Image", 10 , 13);

        AffineTransform mirrorTransform = AffineTransform.getTranslateInstance(WIDTH/2,0);
        mirrorTransform.scale(-1.0,1.0);
        ig.drawImage(new AffineTransformOp(mirrorTransform,AffineTransformOp.TYPE_BILINEAR).filter(bimage, null), WIDTH/2,0, c);
        ig.drawString("Mirror image", WIDTH - 80,13);

        ig.drawLine(WIDTH/2, 0,WIDTH/2, HEIGHT);
    }
}
////////////////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////////////////
class GraphSampleFrame extends JFrame {

    static final String classname = "Points";
    public GraphSampleFrame(final GraphSample[] examples) {
        super("GraphSampleFrame");

        Container cpane = getContentPane();
        cpane.setLayout(new BorderLayout());
        final JTabbedPane tpane = new JTabbedPane();
        cpane.add(tpane, BorderLayout.CENTER);

        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);
        JMenu filemenu = new JMenu("File");
        menubar.add(filemenu);
        JMenuItem quit = new JMenuItem("Quit");
        filemenu.add(quit);


        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        for (int i = 0; i < examples.length; i++) {
            GraphSample e = examples[i];
            tpane.addTab(e.getName(), new GraphSamplePane(e));
        }
    }

    public class GraphSamplePane extends JComponent {
        GraphSample example;
        Dimension size;

        public GraphSamplePane(GraphSample example) {
            this.example = example;
            size = new Dimension(example.getWidth(), example.getHeight());
            setMaximumSize( size );
        }

        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(Color.black);
            example.draw((Graphics2D) g, this);
        }

        public Dimension getPreferredSize() { return size; }
        public Dimension getMinimumSize() { return size; }
    }

    public static void main(String[] args) {
        GraphSample[] examples = new GraphSample[1];

        try {
            Class exampleClass = Class.forName(classname);
            examples[0] = (GraphSample) exampleClass.newInstance();
        }
        catch (ClassNotFoundException e) {
            System.err.println("Couldn't find example: "  + classname);
            System.exit(1);
        }
        catch (ClassCastException e) {
            System.err.println("Class " + classname +
                    " is not a GraphSample");
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Couldn't instantiate example: " +
                    classname);
            System.exit(1);
        }

        GraphSampleFrame f = new GraphSampleFrame(examples);
        f.pack();
        f.setVisible(true);
    }
}
////////////////////////////////////////////////////////////////////////////
// interface GraphSample
////////////////////////////////////////////////////////////////////////////
interface GraphSample {
    public String getName();
    public int getWidth();
    public int getHeight();
    public void draw(Graphics2D g, Component c);
}

