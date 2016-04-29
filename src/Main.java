/**
 * Created by CallMeF3AR on 14.04.2016.
 */

public class Main {
    public static Controls Ctrls;

    public static void main(String[] args) {
        ToolsForm tools = new ToolsForm();
        Ctrls = new Controls();
        tools.setVisible(true);
        tools.start();
        //Main.Ctrls.Simulate();
    }
}
