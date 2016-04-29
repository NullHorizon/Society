import javax.swing.*;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by CallMeF3AR on 28.04.2016.
 */
public class Controls {
    public static Territory Terr;
    public static Flock Flock;
    public boolean Break, Exit;

    public static Territory getTerritory()
    {
        return Terr;
    }

    public static Flock getFlock()
    {
        return Flock;
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
        Simulation sim = new Simulation();
        Break = false;
        Exit = false;
        sim.setVisible(true);

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

        sim.Start();
        return;
    }
}
