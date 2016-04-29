import java.awt.*;

/**
 * Created by CallMeF3AR on 14.04.2016.
 */
public class Individ {
    private int X, Y;
    private Color Color;
    private Defaults.RobotType Type;

    Individ(Defaults.RobotType t, int x, int y)
    {
        setCoord(x, y);
        setType(t);
        return;
    }

    Individ()
    {
        setCoord(0, 0);
        setType(Defaults.RobotType.Neutral);
        return;
    }

    public void setType(Defaults.RobotType x)
    {
        Type = x;
        switch (Type)
        {
            case Neutral:
                Color = Color.GRAY;
                break;
            case Rescuer:
                Color = Color.BLUE;
                break;
            case Enemy:
                Color = Color.RED;
                break;
        }
        return;
    }
    public Defaults.RobotType getType()
    {
        return Type;
    }

    public void setCoord(int x, int y)
    {
        X = x;
        Y = y;
        return;
    }
    public Point getCoord()
    {
        return new Point(X, Y);
    }

    public Color getColor()
    {
        return Color;
    }
}
