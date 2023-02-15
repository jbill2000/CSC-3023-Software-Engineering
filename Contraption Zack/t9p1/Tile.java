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

public class Tile {

    Color col; // color of the tile

    String name;

    boolean isWalkable = true; // this will keep track whether or not this tile can be walked on

    Rectangle tileRect;
    Point center;

    String symbol = "";

    //rectangle and point class are used for collision
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

    // copy constructor, which will be used a lot in building the level
    public Tile(Tile other) {

        this.col = other.col;
        this.isWalkable = other.isWalkable;
        name = "Tile";

    }

    //method to turn tile data into a string
    public String write(){
        String s = "Tile ";

        s+= symbol + " ";
        s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";

        if(isWalkable){
            s+= 1 + " ";
        }
        else{
            s+= 0 + " ";
        }

        return s;

    }

    // inital constructor
    public Tile(Color c, int walkable) {

        col = c;
        name = "Tile";
        
        if (walkable == 0) {
            isWalkable = false;
        }

    }
    
    // the draw method, which takes a graphics context and draws the tile on it
    public void draw(GraphicsContext gc, int xpos, int ypos) {
      
        gc.setFill(col);
        gc.fillRect(xpos,ypos,100,100);

        tileRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 100, ypos + 100));

    }

    // returns true if the coords passed in overlap with this tile
    public boolean collision(Double xpos, Double ypos, int height, int width) {
        Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

        return isOverlapping(characterRect);
    }

    public boolean isOverlapping(Rectangle other) {
        if (tileRect.topRight.getY() < other.bottomLeft.getY()
                || tileRect.bottomLeft.getY() > other.topRight.getY()) {
            return false;
        }
        if (tileRect.topRight.getX() < other.bottomLeft.getX()
                || tileRect.bottomLeft.getX() > other.topRight.getX()) {
            return false;
        }
        return true;
    }

    public boolean isWalkable() {
        return isWalkable;
    }
    
}
