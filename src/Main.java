import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main{
    private static final int FPS = 60;
    public static void run(){
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        CannonControl cannonControl = new CannonControl();
        CannonObject cannonObject = new CannonObject(Color.WHITE, Color.gray, cannonControl);
        frame.setTitle("Cannon");
        frame.setSize(1000,750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cannonControl, BorderLayout.NORTH);
        frame.add(cannonObject);

        int delay = 1000 / FPS;
        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cannonObject.move(0.75);
                cannonObject.repaint();
                frame.repaint();
            }
        });

        timer.start();

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                cannonObject.setAngle(e.getX(), e.getY());
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                cannonObject.setAngle(e.getX(), e.getY());
                cannonObject.shoot();
            }
        });
        frame.setVisible(true);
    }
    public static void main(String[] args){
        run();
    }
}
