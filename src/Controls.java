import sun.awt.OrientableFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

/**
 * Created by CallMeF3AR on 28.04.2016.
 */
public class Controls {
    private static Territory Terr;
    private static Flock Flock;
    public static long ID;
    protected boolean Break, Exit;
    private static Simulation CurrentSimulation;

    Controls()
    {
        CurrentSimulation = new Simulation();
        Terr = new Territory();
        Flock = new Flock();
        Break = false;
        Exit = false;
    }

    public static Territory getTerritory()
    {
        return Terr;
    }

    public static Flock getFlock()
    {
        return Flock;
    }

    public static void setFlock(Flock newFlock)
    {
        Flock = newFlock;
        return;
    }

    public void stopSimulate()
    {
        Break = true;
        return;
    }

    public void exitSimulate()
    {
        Exit = true;
        return;
    }

    public void continueSimulate()
    {
        Break = false;
        return;
    }

    public void startSimulate()
    {
        Break = false;
        Exit = false;

        System.out.println(ID);

        Terr = new Territory();
        Point pts[] = new Point[4];
        pts[0] = new Point(50, 50);
        pts[1] = new Point(50, 200);
        pts[2] = new Point(200, 200);
        pts[3] = new Point(200, 50);
        Terr.setPolygonPoints(pts);

        Flock = new Flock();
        Random rnd = new Random();
        for(int i = 0; i < 100; i+=10)
            for(int j = 0; j < 100; j+=10) {
                int x = Math.abs(rnd.nextInt()) % 3;
                switch (x)
                {
                    case 0:
                        Flock.addMember(new Individ(Defaults.RobotType.Neutral, i + 50, j + 50));
                        break;
                    case 1:
                        Flock.addMember(new Individ(Defaults.RobotType.Rescuer, i + 50, j + 50));
                        break;
                    case 2:
                        Flock.addMember(new Individ(Defaults.RobotType.Enemy, i + 50, j + 50));
                        break;
                }
            }
        HashSet<Point> Exit = new HashSet<Point>();
        Exit.add(new Point(200, 100));
        Exit.add(new Point(210, 125));
        Exit.add(new Point(200, 150));
        Terr.addExit(Exit);

        CurrentSimulation = new Simulation();
        CurrentSimulation.setVisible(true);
        CurrentSimulation.Start(Terr, Flock);
        return;
    }

    private static class Simulation extends JPanel {
        private int OvalW = Defaults.OvalW;
        private int OvalH = Defaults.OvalH;
        private Territory CurrentTerritory;
        private Flock CurrentFlock;
        private JFrame frame;
        private boolean Exit, Break;
        private int Bottom = 80;

        Simulation()
        {
            frame = new JFrame();
            //this.Ctrls = new Controls();
            Exit = false;
            Break = true;
        }

        @Override
        public void paint(Graphics g)
        {
            //System.out.println(g.hashCode());
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(0, 0, Defaults.WindowW, Defaults.WindowH - Bottom);
            //Drawing WorkArea
            Territory Terr = CurrentTerritory;

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
            Flock Flock = CurrentFlock;
            Vector<Individ> Members = Flock.getMembers();
            for(int i = 0; i < Members.size(); i++)
            {
                Individ member = Members.get(i);
                g2.setColor(member.getColor());
                g2.fillOval(member.getCoord().x, member.getCoord().y, OvalH, OvalW);
            }
            g2.dispose();
        }

        public void Start(Territory CurrentTerritorySrc, Flock CurrentFlockSrc)
        {
            CurrentTerritory = CurrentTerritorySrc;
            CurrentFlock = CurrentFlockSrc;
            frame = new JFrame("Society" + Thread.currentThread().getId());
            //frame.setBounds(0, 0, Defaults.WindowW, Defaults.WindowH);
            frame.setSize(Defaults.WindowW, Defaults.WindowH);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JButton Btn1 = new JButton("Start");
            JButton Btn2 = new JButton("Stop");
            JButton Btn3 = new JButton("Exit");

            JPanel Buttons = new JPanel();
            Buttons.setLayout(new FlowLayout());
            Buttons.add(Btn1);
            Buttons.add(Btn2);
            Buttons.add(Btn3);

            Buttons.setSize(Defaults.WindowW, Bottom);
            this.setSize(Defaults.WindowW, Defaults.WindowH - Bottom);
            this.setBounds(0, 0, Defaults.WindowW, Defaults.WindowH - Bottom);
            Buttons.setBounds(0, Defaults.WindowH - Bottom, Defaults.WindowW, Defaults.WindowH);
            frame.add(Buttons);
            frame.add(this);

            //frame.add(this, BorderLayout.NORTH);
            //frame.add(Buttons, BorderLayout.SOUTH);

            Btn1.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SwingWorker worker = new SwingWorker<Void, Void>() {
                                @Override
                                public Void doInBackground() {
                                    Break = false;
                                    Exit = false;
                                    return null;
                                }
                            };
                            worker.execute();
                        }
                    }
            );

            Btn2.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SwingWorker worker = new SwingWorker<Void, Void>() {
                                @Override
                                public Void doInBackground() {
                                    Break = true;
                                    return null;
                                }
                            };
                            worker.execute();
                        }
                    }
            );

            Btn3.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            SwingWorker worker = new SwingWorker<Void, Void>() {
                                @Override
                                public Void doInBackground() {
                                    Exit = true;
                                    return null;
                                }
                            };
                            worker.execute();
                        }
                    }
            );

            while (1 == 1)
            {
                if (Exit)
                {
                    frame.dispose();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    //Handle exception
                }
                if (!Break)
                {
                    this.repaint();

                    Flock Flock = CurrentFlock;
                    Vector<Individ> Members = Flock.getMembers();
                    Random rnd = new Random();
                    for (int i = 0; i < Members.size(); i++)
                        Members.get(i).setCoord(Members.get(i).getCoord().x + rnd.nextInt() % 3, Members.get(i).getCoord().y + rnd.nextInt() % 3);
                    Flock.setMembers(Members);
                    CurrentFlock = Flock;
                }
            }



        }
    }
}
