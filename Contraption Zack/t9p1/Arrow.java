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

public class Arrow {

    int groupAssocation; // this keeps track of which button the spike is tied too
    Color col; //color of the arrow
    double[] xcoords = new double[3]; // xcoords for spike drawing
    double[] ycoords = new double[3];// ycoords for spike drawing
    String type;
    Rectangle arrowRect;
    String symbol = ""; //this is the symbol used in the tileDatabase

    //Point class is used for Rectangles
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

    //Rectangle class used for tracking collision
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

    // Constructor
    public Arrow(Color c, double[] xpos, double[] ypos, String name) {
        col = c;
        type = name;
        for (int i = 0; i < 3; i++) {
            xcoords[i] = xpos[i];
            ycoords[i] = ypos[i];
        }

    }

    //accessor functions
    public double[] getX() {
        return xcoords;

    }

    public double[] getY() {

        return ycoords;
    }

    public String getName() {

        return type;
    }

    //this is the method to turn the arrow data into a string
    public String write(){

        String s = "Arrow ";

        s+= symbol + " " + type + " ";
        s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
        s+= xcoords[0] + " " + xcoords[1] + " " + xcoords[2] + " ";
        s+= ycoords[0] + " " + ycoords[1] + " " + ycoords[2] + " ";
        s+= groupAssocation + " ";

        return s;

    }


    //method used to draw the arrow
    public void draw(GraphicsContext gc, double[] xpos, double[] ypos, String name) {

        if (name.equals("l")) { //drawing a left arrow
            gc.setFill(Color.GRAY);
            gc.fillRect(xpos[0], ypos[1], 50, 50);
            gc.setFill(col);
            gc.fillPolygon(xpos, ypos, 3);
            double rectx = xpos[1];
            double recty = ypos[0] - 9;
            gc.fillRect(rectx, recty, 25, 20);
            arrowRect = new Rectangle(new Point(rectx, recty), new Point(rectx + 25, recty + 20));

        } else if (name.equals("u")) { //drawing a up arrow
            gc.setFill(Color.GRAY);
            gc.fillRect(xpos[0], ypos[1], 50, 50);
            double rectx = xpos[1] - 10;
            double recty = ypos[1] + 25;

            gc.setFill(col);
            gc.fillPolygon(xpos, ypos, 3);
            gc.fillRect(rectx, recty, 20, 25);
            arrowRect = new Rectangle(new Point(rectx, recty), new Point(rectx + 20, recty + 25));

        } else if (name.equals("d")) { //drawing a down arrow
            gc.setFill(Color.GRAY);
            gc.fillRect(xpos[0], ypos[1] - 50, 50, 50);
            double rectx = xpos[1] - 10;
            double recty = ypos[0] - 25;

            gc.setFill(col);
            gc.fillPolygon(xpos, ypos, 3);
            gc.fillRect(rectx, recty, 20, 32);
            arrowRect = new Rectangle(new Point(rectx, recty), new Point(rectx + 25, recty + 20));

        } else if (name.equals("r")) { //drawing a right arrow
            gc.setFill(Color.GRAY);
            gc.fillRect(xpos[0] - 50, ypos[1], 50, 50);
            double rectx = xpos[1] - 25;
            double recty = ypos[0] - 8;
            gc.setFill(col);
            gc.fillPolygon(xpos, ypos, 3);
            gc.fillRect(rectx, recty, 25, 20);
            arrowRect = new Rectangle(new Point(rectx, recty), new Point(rectx + 25, recty + 20));

        }

    }

    // returns true if the coords passed in overlap with this tile
    public boolean collision(Double xpos, Double ypos, int height, int width) {
        Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

        return isOverlapping(characterRect);
    }

    //this method does the grunt work for collision
    public boolean isOverlapping(Rectangle other) {
        if (arrowRect.topRight.getY() < other.bottomLeft.getY()
                || arrowRect.bottomLeft.getY() > other.topRight.getY()) {
            return false;
        }
        if (arrowRect.topRight.getX() < other.bottomLeft.getX()
                || arrowRect.bottomLeft.getX() > other.topRight.getX()) {
            return false;
        }
        return true;
    }

}
