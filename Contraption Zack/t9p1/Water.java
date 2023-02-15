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
import java.io.*;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.io.FileWriter;
import java.io.IOException;
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

public class Water {
  Color col; // color of the tile
  Image image1 = new Image("water.jpg");
  Image image2 = new Image("water2.jpg");
  Image curImage = image1;
  int currentImage = 1;
  int currentTime = 0;

  String name;

  boolean isWalkable = true; // this will keep track whether or not this tile can be walked on

  Rectangle waterRect;

  String symbol = "";

  int xpos;
  int ypos;
  int height;
  int width;

  public Water(int xpos, int ypos, int width, int height) {
    this.waterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));
    this.xpos = xpos;
    this.ypos = ypos;
    this.height = height;
    this.width = width;
  }

  public void drawWater(GraphicsContext gc) {
    currentTime += 1;
    if (currentTime % 10 == 0) {

      if (currentImage == 1) {
        currentImage = 2;
        curImage = image2;
      } else {
        currentImage = 1;
        curImage = image1;
      }
    }

    gc.setFill(Color.TURQUOISE);
    
    gc.drawImage(curImage, xpos, ypos, width, height);
  }

  //point and rectangle are used for collision purposes
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

  // returns true if the coords passed in overlap with this tile
  public boolean collision(Double xpos, Double ypos, int height, int width) {
    Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

    return isOverlapping(characterRect);
  }

  public boolean isOverlapping(Rectangle other) {
    if (waterRect.topRight.getY() < other.bottomLeft.getY()
        || waterRect.bottomLeft.getY() > other.topRight.getY()) {
      return false;
    }
    if (waterRect.topRight.getX() < other.bottomLeft.getX()
        || waterRect.bottomLeft.getX() > other.topRight.getX()) {
      return false;
    }
    return true;
  }
}
