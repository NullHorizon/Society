import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by CallMeF3AR on 28.04.2016.
 */
public class ToolsForm extends JPanel {
    //ivate SwingWorker worker;

    public void start()
    {
        JFrame f = new JFrame("Controls");

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new FlowLayout());

        JButton Btn1 = new JButton("Start");
        JButton Btn2 = new JButton("Continue");
        JButton Btn3 = new JButton("Stop");

        f.add(Btn1);
        f.add(Btn2);
        f.add(Btn3);

        Btn1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SwingWorker worker = new SwingWorker<Void, Void>() {
                            @Override
                            public Void doInBackground() {
                                Controls Ctrls = new Controls();
                                Ctrls.ID = Thread.currentThread().getId();
                                Ctrls.startSimulate();
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
                                Controls Ctrls = new Controls();
                                Ctrls.ID = Thread.currentThread().getId();
                                return null;
                            }
                        };
                        worker.execute();
                    }
                }
        );
        f.pack();
        f.setSize(400,400);
        f.setVisible(true);
    }
}
