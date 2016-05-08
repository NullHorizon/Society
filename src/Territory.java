import javax.swing.border.Border;
import java.awt.*;
import java.util.HashSet;
import java.util.Vector;

/**
 * Created by CallMeF3AR on 27.04.2016.
	edt in may 8
 */
public class Territory {
    private Vector<Room> Rooms;

    Territory()
    {
        Rooms = new Vector<Room>();
        return;
    }

    public void addRoom(Room x)
    {
        Rooms.add(x);
        return;
    }

    public boolean removeRoom(Room x)
    {
        for(int i = 0; i < Rooms.size(); i++)
            if (Rooms.get(i).ID == x.ID) {
                Rooms.remove(i);
                return true;
            }
        return false;
    }

    public Vector<Room> getRooms()
    {
        return Rooms;
    }

    protected static class Room {

        protected static class Exit {
            protected Point First;
            protected Point Second;
            protected int ID;
            Exit(Point x, Point y, int id)
            {
                First = x;
                Second = y;
                ID = id;
            }
        }

        protected static class Box {
            private Polygon Polygon;
            private Color InsideColor;
            private Color BorderColor;
            private int BorderWidth;
            protected int ID;

            Box(Color CurrentBorderColor, Color CurrentInsideColor, int CurrentBorderWidth, int id)
            {
                BorderColor = CurrentBorderColor;
                InsideColor = CurrentInsideColor;
                BorderWidth = CurrentBorderWidth;
                Polygon = new Polygon();
                ID = id;
            }
            public void setInsideColor(Color x)
            {
                InsideColor = x;
                return;
            }
            public void setBorderColor(Color x)
            {
                BorderColor = x;
                return;
            }
            public void setBorderWidth(int x)
            {
                BorderWidth = x;
                return;
            }
            public void setPolygon(Polygon p)
            {
                Polygon = p;
                return;
            }
            public Color getInsideColor() { return InsideColor; }
            public Color getBorderColor() { return BorderColor; }
            public int getBorderWidth() { return BorderWidth; }
            public Polygon getPolygon() { return Polygon; }
        }

        private Polygon Polygon;
        private Vector<Exit> Exits;
        private Vector<Box> Boxes;
        private Color InsideColor;
        private Color BorderColor;
        private int BorderWidth;
        protected int ID;

        Room(Color CurrentBorderColor, Color CurrentInsideColor, int CurrentBorderWidth, int id)
        {
            BorderColor = CurrentBorderColor;
            InsideColor = CurrentInsideColor;
            BorderWidth = CurrentBorderWidth;
            ID = id;
            Exits = new Vector<Exit>();
            Boxes = new Vector<Box>();
            Polygon = new Polygon();
        }
        public void setInsideColor(Color x)
        {
            InsideColor = x;
            return;
        }
        public void setBorderColor(Color x)
        {
            BorderColor = x;
            return;
        }
        public void setBorderWidth(int x)
        {
            BorderWidth = x;
            return;
        }
        public void setPolygon(Polygon p)
        {
            Polygon = p;
            return;
        }
        public void addBox(Box x)
        {
            Boxes.add(x);
            return;
        }
        public boolean removeBox(Box x)
        {
            for(int i = 0; i < Boxes.size(); i++)
                if (Boxes.get(i).ID == x.ID) {
                    Boxes.remove(i);
                    return true;
                }
            return false;
        }
        public void addExit(Exit x)
        {
            Exits.add(x);
            return;
        }
        public boolean removeExit(Exit x)
        {
            for(int i = 0; i < Exits.size(); i++)
                if (Exits.get(i).ID == x.ID) {
                    Exits.remove(i);
                    return true;
                }
            return false;
        }
        public Color getInsideColor() { return InsideColor; }
        public Color getBorderColor() { return BorderColor; }
        public int getBorderWidth() { return BorderWidth; }
        public Polygon getPolygon() { return Polygon; }
        public Vector<Exit> getExits() { return Exits; }
        public Vector<Box> getBoxes() { return Boxes; }
    }

}
