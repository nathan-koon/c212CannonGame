import javax.swing.*;
import java.awt.*;

public class CannonControl extends JPanel {
    private JButton setButton;
    private JTextField powerTextField;
    private JTextField radiusTextField;
    private JTextField barrelHeightTextField;
    private JTextField barrelWidthTextField;
    private int power;
    private int radius;
    private int barrelHeight;
    private int barrelWidth;
    public CannonControl(){
        powerTextField = new JTextField(10);
        add(new JLabel("Power: "));
        add(powerTextField);
        radiusTextField = new JTextField(10);
        add(new JLabel("Base Radius: "));
        add(radiusTextField);
        barrelHeightTextField = new JTextField(10);
        add(new JLabel("Barrel Height : "));
        add(barrelHeightTextField);
        barrelWidthTextField = new JTextField(10);
        add(new JLabel("Barrel Width: "));
        add(barrelWidthTextField);
        setButton = new JButton("Set");
        add(setButton, BorderLayout.AFTER_LINE_ENDS);
        this.power = 50;
        this.radius = 50;
        this.barrelHeight = 90;
        this.barrelWidth = 30;

        setButton.addActionListener(e -> {
            this.power = Integer.parseInt(powerTextField.getText());
            this.radius = Integer.parseInt(radiusTextField.getText());
            this.barrelHeight = Integer.parseInt(barrelHeightTextField.getText());
            this.barrelWidth = Integer.parseInt(barrelWidthTextField.getText());
        });
    }
    public int getPower(){
        return this.power;
    }
    public int getRadius(){
        return this.radius;
    }
    public int getBarrelHeight(){
        return this.barrelHeight;
    }
    public int getBarrelWidth(){
        return this.barrelWidth;
    }
}

