package backend.objects;
import java.io.Serializable;

import backend.Shape;
import processing.core.PApplet;
import processing.core.PConstants;

class Ellipse extends Shape implements Serializable {
  private static final long serialVersionUID = 3L;
  Ellipse(PApplet sketch, float x, float y, float w, float h, float strokeWeight, int[] fillColor, int[] boarderColor){
    super(sketch, x, y, strokeWeight, fillColor, boarderColor);
    this.pixelWidth = w;
    this.pixelHeight = h;
    shape = sketch.createShape(PConstants.ELLIPSE, 0, 0, this.pixelWidth, this.pixelHeight);
    setFillColor(255,255,255,0);
    setSettings();
  }

  Ellipse(PApplet sketch, float x, float y, float d, float strokeWeight,  int[] fillColor, int[] boarderColor){
    this(sketch, x, y, d, d, strokeWeight, fillColor, boarderColor);
  }
  Ellipse(PApplet sketch, float x, float y, float strokeWeight,  int[] fillColor, int[] boarderColor){
    this(sketch, x, y, 50, 25, strokeWeight, fillColor, boarderColor);
  }

  protected void init(PApplet sketch){
    super.init(sketch);
    shape = sketch.createShape(PConstants.ELLIPSE, 0, 0, this.pixelWidth, this.pixelHeight);
    setSettings();
  }

  protected void display(){
    super.display();
    sketch.shape(shape);
  }
}
