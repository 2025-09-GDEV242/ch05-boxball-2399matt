import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.List;

/**
 * Class BoxBall - a graphical ball that moves similar to the ball in PONG.
 * It bounces around the confines of a box as defined by the Box class.
 * Details of movement are determined by the ball itself. It will move to
 * the right when xspeed is positive and to the left when xspeed is negative.
 * Due to the way computer graphics are drawn using awt, the ball moves
 * DOWN when yspeed is positive and UP when yspeed is negative.
 * 
 * It is the ball's responsibility to determine if it has hit a wall and 
 * to reverse direction
 *
 * This movement can be initiated by repeated calls to the "move" method.
 * 
 * @author Michael Kölling (mik)
 * @author David J. Barnes
 * @author Bruce Quig
 * @author William Crosbie
 *
 * @version 2025.10.06
 */

public class BoxBall
{
    private Box myBox;

    private Ellipse2D.Double circle;    // represents the ball
    private Color color;        // color of the ball (can be rgb value)
    private int diameter;       // width of ball in number of pixels
    private int xPosition;      // horizontal position of bounding square
    private int yPosition;      // vertical position of bounding square
    private Canvas canvas;
    private int ySpeed;         // vertical speed
    private int xSpeed;         // horizontal speed
    private Random random;

    /**
     * Constructor for objects of class BoxBall
     *
     * @param xPos  the horizontal coordinate of the ball
     * @param yPos  the vertical coordinate of the ball
     * @param ballDiameter  the diameter (in pixels) of the ball
     * @param ballColor  the color of the ball
     * @param box  the bounding box (where the ball will bounce)
     * @param drawingCanvas  the canvas to draw this ball on
     */
    public BoxBall(int xPos, int yPos, int ballDiameter, Color ballColor,
                        Box box, Canvas drawingCanvas)
    {
        xPosition = xPos;
        yPosition = yPos;
        color = ballColor;
        diameter = ballDiameter;
        random = new Random();
        xSpeed = random.nextInt(15) - 7;
        ySpeed = random.nextInt(15) - 7; 
        while(xSpeed == 0 || ySpeed == 0) {
            xSpeed = random.nextInt(15) - 7;
            ySpeed = random.nextInt(15) - 7; 
        }
        canvas = drawingCanvas;
    }

    /**
     * Draw this ball at its current position onto the canvas.
     **/
    public void draw()
    {
        canvas.setForegroundColor(color);
        canvas.fillCircle(xPosition, yPosition, diameter);
    }

    /**
     * Erase this ball at its current position.
     **/
    public void erase()
    {
        canvas.eraseCircle(xPosition, yPosition, diameter);
    }    

    /**
     * Move this ball according to its position and speed and redraw.
     **/
    public void move(int leftWall, int rightWall, int topWall, int bottomWall)
    {
        // remove from canvas at the current position
        erase();
        
        // compute new position
        this.xPosition += xSpeed;
        this.yPosition += ySpeed;
        // figure out if it has hit the left or right wall
        if(xPosition + diameter >= rightWall) {
            // Need diameter checks on right / bottom because top left circle is (0,0).
            xPosition = rightWall-diameter;
            xSpeed = -xSpeed;
        }
        if(xPosition <= leftWall) {
            xPosition = leftWall+1;
            xSpeed = -xSpeed;
        }
        // figure out if it has hit the top or bottom wall
        if(yPosition <= topWall) {
            // I don't know why this works. erase() might be chipping away the wall?
            // Can't make diameter checks for left/top, so just move it slightly.
            yPosition = topWall+1;
            ySpeed = -ySpeed;
        }
        if(yPosition+diameter >= bottomWall) {
            yPosition=bottomWall-diameter;
            ySpeed = -ySpeed;
        }
        draw();
    }    

    /**
     * return the horizontal position of this ball
     */
    public int getXPosition()
    {
        return xPosition;
    }

    /**
     * return the vertical position of this ball
     */
    public int getYPosition()
    {
        return yPosition;
    }
}
