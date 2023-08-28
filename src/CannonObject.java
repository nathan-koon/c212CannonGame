import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class CannonObject extends JPanel{
    private int radius;
    private boolean facingRight;
    private int x;
    private int y;
    private double xVelocity;
    private double yVelocity;
    private Color barrelColor;
    private Color baseColor;
    private float angle;
    private float activeAngle;
    private int centerX;
    private int bottomY;
    private CannonControl cannonControl;

    public CannonObject(Color baseColor,Color barrelColor, CannonControl cannonControl){
        this.cannonControl = cannonControl;
        this.activeAngle = 0;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.radius = cannonControl.getBarrelWidth()/2;
        this.barrelColor = barrelColor;
        this.baseColor = baseColor;
        this.centerX = getWidth()/2;
        this.bottomY = getHeight();
        this.x = this.centerX;
        this.y = this.bottomY;
    }

    public void setAngle(int mousePosX, int mousePosY){
        double x1 = this.centerX;
        double y1 = this.bottomY;
        double x2 = mousePosX;
        double y2 = mousePosY - 0.5*this.cannonControl.getHeight();
        this.angle = (float)(Math.atan2(y2-y1,x2-x1) + 1.5);

        //DEBUG Print Angles

//        if((this.angle-1.5)*-1 <= 1.5) {
//            System.out.printf("Radians: %f Degrees: %f\n", (this.angle - 1.5) * -1, Math.toDegrees((this.angle - 1.5) * -1));
//        } else{
//            System.out.printf("Radians: %f Degrees: %f\n", (float)(1.5-((this.angle - 1.5) * -1)%1.5), Math.toDegrees((1.5-((this.angle - 1.5) * -1)%1.5)));
//        }
    }
    public void drawBarrel(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(this.barrelColor);
        int barrelX = this.centerX - this.cannonControl.getBarrelWidth() / 2;
        int barrelY = this.bottomY - this.cannonControl.getBarrelHeight();
        Rectangle barrel = new Rectangle(barrelX, barrelY, this.cannonControl.getBarrelWidth(), this.cannonControl.getBarrelHeight());
        AffineTransform initial = g2d.getTransform();
        g2d.rotate(this.angle, this.centerX, this.bottomY);
        g2d.fill(barrel);
        g2d.setTransform(initial);
    }
    public void drawBase(Graphics2D g2d){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(this.baseColor);
        g2d.fillOval(this.centerX - this.cannonControl.getRadius(), this.bottomY - this.cannonControl.getRadius(), this.cannonControl.getRadius()*2, this.cannonControl.getRadius()*2);
    }

    public void drawCannonBall(Graphics2D g2d) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(this.getBarrelColor());
            g2d.fillOval(this.x, this.y, this.radius*2, this.radius*2);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillRect(0,0, getWidth(), getHeight());
        this.centerX = getWidth()/2;
        this.bottomY = getHeight();
        this.drawBarrel(g2d);
        this.drawBase(g2d);
        this.drawCannonBall(g2d);
        this.repaint();
    }
    public Color getBarrelColor(){
        return this.barrelColor;
    }
    public void move(double sec){
        double acceleration = 9.81*Math.pow(sec, 2);
        if(this.y <= 1000 - this.cannonControl.getBarrelWidth()/2){
            if(this.facingRight) {
                this.x += (xVelocity * sec);
                this.y -= (yVelocity * sec);
                this.yVelocity -= acceleration;
            } else{
                this.x -= (xVelocity * sec);
                this.y -= (yVelocity * sec);
                this.yVelocity -= acceleration;
            }
        }

        //DEBUG Ball Position

//        System.out.printf("Ball Position: (%d, %d)\n", this.x,this.y);
    }

    public void shoot(){
        this.resetBall();
        float shootingAngle = (float)((this.angle-1.5)*-1);
        if(shootingAngle < 1.5){
            this.facingRight = true;
            shootingAngle = (float)((this.angle-1.5)*-1);
        }else{
            this.facingRight = false;
            shootingAngle = (float)(1.5-((this.angle - 1.5) * -1)%1.5);
        }
        float velocity = cannonControl.getPower();
        double xVelocity = velocity * Math.cos(shootingAngle);
        double yVelocity = velocity * Math.sin(shootingAngle);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.activeAngle = shootingAngle;
        System.out.println(this.activeAngle);
    }

    public void resetBall(){
        int r = (int)(this.cannonControl.getRadius());
        this.x = (int)((this.centerX - this.cannonControl.getBarrelWidth()/2) + r*Math.cos(this.angle-1.5));
        this.y = (int)((this.bottomY - this.cannonControl.getRadius()) + r*Math.sin(this.angle-1.5));
        System.out.printf("Starting Coords: (%d, %d)\n",this.x,this.y);
    }
}
