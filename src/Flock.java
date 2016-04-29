import java.util.Vector;

/**
 * Created by CallMeF3AR on 27.04.2016.
 */
public class Flock {
    private Vector<Individ> Members;

    Flock()
    {
        Members = new Vector<Individ>();
        return;
    }
    public void addMember(Individ x)
    {
        Members.add(x);
        return;
    }
    public Vector<Individ> getMembers()
    {
        return Members;
    }
    public void setMembers(Vector<Individ> x)
    {
        Members = x;
        return;
    }
}
