import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.animation.*;
import java.util.*;
import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.util.*;

public class Spike {

    Rectangle spikeRect;
    Point center;
    boolean spikeDown = false;
    boolean spikeUp = true;
    String symbol = "";

    int groupAssocation; // this keeps track of which button the spike is tied too
    Color col;
    double[] xcoords = new double[3]; // xcoords for spike drawing
    double[] ycoords = new double[3];// ycoords for spike drawing

    //point and rectangle are for collision purposes
    public class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public class Rectangle {
        Point bottomLeft;
        Point topRight;

        public Rectangle(Point bottomLeft, Point topRight) {
            this.bottomLeft = bottomLeft;
            this.topRight = topRight;
        }

        public Point getBottomLeft() {
            return bottomLeft;
        }

        public Point getTopRight() {
            return topRight;
        }
    }

    boolean spikesUp = true; // boolean keeps track of spike state

    // Constructor
    public Spike(Color c, double[] xpos, double[] ypos) {
        col = c;
        for (int i = 0; i < 3; i++) {
            xcoords[i] = xpos[i];
            ycoords[i] = ypos[i];
        }

    }

    public double[] getX() {
        return xcoords;

    }

    public double[] getY() {

        return ycoords;
    }


    //method to turn spike data into a string
    public String write(){

        String s = "Spike ";
        s+= symbol + " ";
        s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
        s+= xcoords[0] + " " + xcoords[1] + " " + xcoords[2] + " ";
        s+= ycoords[0] + " " + ycoords[1] + " " + ycoords[2] + " ";
        s+= groupAssocation + " ";

        if(spikeUp){
            s+= 1 + " ";
        }
        else{
            s+= 0 + " ";
        }


        return s;

    }

    //draws spike, differnt depending on if spike is up or down
    public void draw(GraphicsContext gc, double[] xpos, double[] ypos) {

        gc.setFill(col);

        if(spikeUp){
            gc.fillPolygon(xpos, ypos, 3);
        }
        else{
            gc.fillOval(xpos[0]+20, ypos[0]-20, 10,10);

        }

        spikeRect = new Rectangle(new Point(xpos[0], ypos[1]), new Point(xpos[2], ypos[0]));
        
    }

    //changes the status of the spike
    public void swapSpike() {
        spikeUp = !spikeUp;
        
    }

    //these two methods are used to check collision
    public boolean collision(Double xpos, Double ypos, int height, int width) {
        Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

        return isOverlapping(characterRect);
    }

    public boolean isOverlapping(Rectangle other) {
        if (spikeRect.topRight.getY() < other.bottomLeft.getY()
                || spikeRect.bottomLeft.getY() > other.topRight.getY()) {
            return false;
        }
        if (spikeRect.topRight.getX() < other.bottomLeft.getX()
                || spikeRect.bottomLeft.getX() > other.topRight.getX()) {
            return false;
        }
        return true;
    }

}
