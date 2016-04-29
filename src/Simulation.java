import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by CallMeF3AR on 14.04.2016.
 */
public class Simulation extends JPanel {
    JFrame frame;
    int OvalW = 7;
    int OvalH = 7;

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, Defaults.WindowW, Defaults.WindowH);
        //Drawing WorkArea
        Territory Terr = Main.Ctrls.Terr;

        //Drawing Main Polygon
        Point Points[] = Terr.getPolygonPoints();
        Polygon p = new Polygon();
        for(int i = 0; i < Points.length; i++)
        {
            p.addPoint(Points[i].x, Points[i].y);
        }
        g2.setColor(Terr.getInsideColor());
        g2.fillPolygon(p);
        g2.setColor(Terr.getBorderColor());
        g2.setStroke(new BasicStroke(Terr.getBorderWidth()));
        g2.drawPolygon(p);
        g2.setStroke(new BasicStroke());
        p.reset();

        //Drawing EXITS
        Vector<HashSet<Point>> Exits = Terr.getExits();
        for(int i = 0; i < Exits.size(); i++)
        {
            p = new Polygon();
            for (Point pt : Exits.get(i))
                p.addPoint(pt.x, pt.y);
            g2.setColor(Color.RED);
            g2.fillPolygon(p);
            g2.drawPolygon(p);
        }

        //Drawing Individs in Flock
        Flock Flock = Main.Ctrls.Flock;
        Vector<Individ> Members = Flock.getMembers();
        for(int i = 0; i < Members.size(); i++)
        {
            Individ member = Members.get(i);
            g2.setColor(member.getColor());
            g2.fillOval(member.getCoord().x, member.getCoord().y, OvalH, OvalW);
        }
    }

    public void Start()
    {
        Simulation sim = new Simulation();
        frame = new JFrame("Society");
        frame.add(sim);
        frame.setSize(Defaults.WindowW, Defaults.WindowH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sim.repaint();

        while (1 == 1)
        {
            if (Main.Ctrls.Exit)
            {
                frame.dispose();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                //Handle exception
            }
            if (!Main.Ctrls.Break)
            {
                sim.repaint();

                Flock Flock = Main.Ctrls.getFlock();
                Vector<Individ> Members = Flock.getMembers();
                for (int i = 0; i < Members.size(); i++)
                    Members.get(i).setCoord(Members.get(i).getCoord().x + 1, Members.get(i).getCoord().y + 1);
                Main.Ctrls.Flock.setMembers(Members);
            }
        }

    }
}