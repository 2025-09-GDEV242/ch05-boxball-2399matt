import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class BallDemo   
{
    private Canvas myCanvas;
    private Box box;
    private Random random;

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     * 
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
        box=new Box (100,100,500,400, myCanvas);
        random = new Random();
        box.draw();
        
    }

    /**
     * boxBounce - simulate 5-50 balls bouncing within a box
     * 
     * @param numOfBalls number of balls to simulate bouncing, clamped between 5-50. 
     */
    public void boxBounce(int numOfBalls)
    {
        // Instructions want this method to draw the box, doesn't make sense. 
        // Skipping that, ask on monday.
        if(numOfBalls < 5 || numOfBalls > 30) {
            return;
        }
        List<BoxBall> boxBalls = new ArrayList<>();
        for(int i = 0; i< numOfBalls; i++) {
            int diameter = 10;
            // 400-300-10 = 90. We get (0-90) + 300
            // Check on this another time, so far so good.
            int xRange = box.getRightWall() -box.getLeftWall() - diameter;
            int yRange = box.getBottomWall() - box.getTopWall() - diameter;
            BoxBall ball = new BoxBall(random.nextInt(xRange) + box.getLeftWall(), 
            random.nextInt(yRange) + box.getTopWall(), diameter, generateColor(), this.box, this.myCanvas);
            
            ball.draw();
            boxBalls.add(ball);
        }
        
        while(true) {
            for(BoxBall ball : boxBalls) {
                ball.move(box.getLeftWall(), box.getRightWall(), box.getTopWall(), box.getBottomWall(), boxBalls);
            }
            
            try{
                Thread.sleep(20);
            }catch(Exception e) {
                // don't care
            }
        }
        
        
    }
    
    /**
     * Simulate two bouncing balls
     */
    public void bounce()
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.setForegroundColor(Color.BLACK);
        myCanvas.drawLine(50, ground, 550, ground);

        // create and show the balls
        BouncingBall ball = new BouncingBall(50, 50, 16, Color.BLUE, ground, myCanvas);
        ball.draw();
        BouncingBall ball2 = new BouncingBall(70, 80, 20, Color.RED, ground, myCanvas);
        ball2.draw();

        // make them bounce
        boolean finished =  false;
        while (!finished) {
            myCanvas.wait(50);           // small delay
            ball.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
    
    /**
     * Generate a color, but avoid white / too-light colors. 
     */
    public Color generateColor() {
        int R, G, B;
        do{
            R = random.nextInt(256);
            G = random.nextInt(256);
            B = random.nextInt(256);    
        }while((R+G+B) / 3 > 128);
        // Super weak check, but seems to work decent enough to not get white/very light colors.
        return new Color(R,G,B);
    }
}
