import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by CallMeF3AR on 27.04.2016.
 */
public class Territory {
    private Point Points[];
    private Color BorderColor;
    private Color InsideColor;
    private int BorderWidth;
    private Vector<HashSet<Point>> Exits;

    Territory()
    {
        Points = new Point[0];
        BorderColor = Color.BLACK;
        InsideColor = Color.WHITE;
        BorderWidth = 1;
        Exits = new Vector<HashSet<Point>>();
        return;
    }

    public void addExit(HashSet Points)
    {
        Exits.add(Points);
        return;
    }

    public void setBorderColor(Color clr)
    {
        BorderColor = clr;
        return;
    }
    public void setInsideColor(Color clr)
    {
        InsideColor = clr;
        return;
    }
    public void setBorderWidth(int x)
    {
        BorderWidth = x;
        return;
    }
    public void setPolygonPoints(Point[] arr)
    {
        Points = new Point[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            Points[i] = arr[i];
        }
        return;
    }
    public Point[] getPolygonPoints()
    {
        return Points;
    }
    public Color getBorderColor()
    {
        return BorderColor;
    }
    public Color getInsideColor()
    {
        return InsideColor;
    }
    public int getBorderWidth()
    {
        return BorderWidth;
    }
    public Vector<HashSet<Point>> getExits()
    {
        return Exits;
    }
}
