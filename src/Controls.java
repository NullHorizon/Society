import sun.awt.OrientableFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private Territory generateTerritory()
    {
        Territory currentTerritory = new Territory();
        Polygon p = new Polygon();
        p.addPoint(50, 50);
        p.addPoint(50, 200);
        p.addPoint(200, 200);
        p.addPoint(200, 50);
        Territory.Room x = new Territory.Room(Defaults.RoomBorderColor, Defaults.RoomInsideColor, Defaults.RoomBorderWidth, 0);
        x.setPolygon(p);
        Territory.Room.Box Box = new Territory.Room.Box(Defaults.BoxBorderColor, Defaults.BoxInsideColor, Defaults.BoxBorderWidth, 0);;
        Polygon p1 = new Polygon();
        p1.addPoint(100, 100);
        p1.addPoint(100, 150);
        p1.addPoint(150, 150);
        p1.addPoint(150, 100);
        Box.setPolygon(p1);
        x.addBox(Box);
        x.addExit(new Territory.Room.Exit(new Point(200, 100), new Point(200, 150), 0));
        x.addExit(new Territory.Room.Exit(new Point(100, 200), new Point(150, 200), 1));
        currentTerritory.addRoom(x);

        Territory.Room y = new Territory.Room(Defaults.RoomBorderColor, Defaults.RoomInsideColor, Defaults.RoomBorderWidth, 1);
        p = new Polygon();
        p.addPoint(200, 50);
        p.addPoint(200, 200);
        p.addPoint(400, 200);
        p.addPoint(400, 50);
        y.setPolygon(p);
        currentTerritory.addRoom(y);

        y = new Territory.Room(Defaults.RoomBorderColor, Defaults.RoomInsideColor, Defaults.RoomBorderWidth, 1);
        p = new Polygon();
        p.addPoint(50, 200);
        p.addPoint(200, 200);
        p.addPoint(200, 350);
        p.addPoint(50, 350);
        y.setPolygon(p);
        currentTerritory.addRoom(y);

        return currentTerritory;
    }

    public void startSimulate()
    {
        Break = false;
        Exit = false;

        System.out.println(ID);

        Terr = generateTerritory();

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
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, Defaults.WindowW, Defaults.WindowH - Bottom);
            //Drawing WorkArea
            Territory Terr = CurrentTerritory;

            //Drawing Rooms
            Vector<Territory.Room> Rooms = Terr.getRooms();
            for(int i = 0; i < Rooms.size(); i++)
            {
                g2.setColor(Rooms.get(i).getInsideColor());
                g2.fillPolygon(Rooms.get(i).getPolygon());
                g2.setColor(Rooms.get(i).getBorderColor());
                g2.setStroke(new BasicStroke(Rooms.get(i).getBorderWidth()));
                g2.drawPolygon(Rooms.get(i).getPolygon());
                //Drawing Boxes
                Vector<Territory.Room.Box> Boxes = Rooms.get(i).getBoxes();
                for(int j = 0; j < Boxes.size(); j++)
                {
                    g2.setColor(Boxes.get(i).getInsideColor());
                    g2.fillPolygon(Boxes.get(i).getPolygon());
                    g2.setColor(Boxes.get(i).getBorderColor());
                    g2.setStroke(new BasicStroke(Boxes.get(i).getBorderWidth()));
                    g2.drawPolygon(Boxes.get(i).getPolygon());
                }
            }

            //Drawing Exits
            //Если делать в общем цикле - перекрываются стенками комнат
            g2.setColor(Color.RED);
            for(int i = 0; i < Rooms.size(); i++) {
                Vector<Territory.Room.Exit> Exits = Rooms.get(i).getExits();
                for (int j = 0; j < Exits.size(); j++) {
                    g2.drawLine(Exits.get(j).First.x, Exits.get(j).First.y, Exits.get(j).Second.x, Exits.get(j).Second.y);
                }
            }

            g2.setStroke(new BasicStroke());
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
