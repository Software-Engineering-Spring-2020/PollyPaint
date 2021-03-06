package backend;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import java.io.Serializable;

public abstract class PollyObject implements Serializable {
    private static final long serialVersionUID = 10L;
    transient protected PApplet sketch;
    protected float xpos, ypos, rot = 0, pixelWidth, pixelHeight;
    transient protected float prevRot = 0, ro = 0;
    protected float zoom = 1, offset = 3F, xcenter = xpos, ycenter = ypos;

    public PollyObject(PApplet sketch, float x, float y) {
        this.sketch = sketch;
        xpos = x;
        ypos = y;
        xcenter = xpos;
        ycenter = ypos;
    }

    protected void init(PApplet sketch){
      this.sketch = sketch;
    }

    protected float[] getPosition() {
        return new float[]{xpos, ypos};
    }

    protected void setPosition(float x, float y) {
        xcenter += (xpos - x);
        ycenter += (ypos - y);
        xpos = x;
        ypos = y;
    }

    /*protected void setRelativeRotate(float ro) {
        rot += ro;
        rot = rot%360;
    }*/

    protected void setRotate(float r) {
        rot = r % 360;
    }

    protected void setRelativeRotate(float r) {
      ro = r - prevRot;
      if(sketch.abs(ro) > 0){
        rot += ro;
        rot = rot%360;
      }
        prevRot = r;
    }

    protected float[] getDimensions(){
        return new float[]{xpos, ypos, pixelWidth, pixelHeight};
    }

    /* description: checks if the provided x, y are within the pivoted bounding box of this
    shape */
    protected boolean withinScope(float x, float y) {
        // approach: rotate the given x, y by the NEGATIVE current rotation
        // this will move it back into the original bounding box (based on width and height)
        // if it is within the shape

        float width = pixelWidth;
        float height = pixelHeight;


        PVector rotatedPoint = rotateAbout(new PVector(x, y), new PVector(xcenter, ycenter), -rot);

        if ((rotatedPoint.x >= xcenter - width / 2 && rotatedPoint.x <= xcenter + width / 2) &&
            (rotatedPoint.y >= ycenter - height / 2 && rotatedPoint.y <= ycenter + height / 2)) {
                return true;
        }
        return false;
    }

    protected void showBoundingBox(){
      sketch.push();
      sketch.noFill();
      sketch.stroke(215,165,0);
      sketch.strokeWeight(2);
      PVector[] vert = getRotatedBoundingBoxPoints(xcenter, ycenter);
      sketch.quad(vert[0].x, vert[0].y, vert[1].x, vert[1].y, vert[2].x, vert[2].y, vert[3].x, vert[3].y);
      sketch.pop();
    }

    protected PVector[] getBoundingBoxPoints(float xcenter, float ycenter) {
        float width = pixelWidth*zoom;
        float height = pixelHeight*zoom;
        offset = 3;
        // this uses the existing bounding box system as much as possible
        // using width and height to generate four points
        // these points are NOT rotated per this PollyObject's rotation

        // represented with four PVectors: topLeft, topRight, bottomRight, bottomLeft (like NESW)
        PVector[] boundingBoxPoints = new PVector[4];
        // topLeft
        boundingBoxPoints[0] = new PVector(-width/2 + xcenter - offset, -height/2 + ycenter - offset);
        // topRight
        boundingBoxPoints[1] = new PVector(width/2 + xcenter + offset, -height/2 + ycenter - offset);
        // bottomRight
        boundingBoxPoints[2] = new PVector(width/2 + xcenter + offset, height/2 + ycenter + offset);
        // bottomLeft
        boundingBoxPoints[3] = new PVector(-width/2 + xcenter - offset, height/2 + ycenter + offset);
        return boundingBoxPoints;
    }

    protected PVector[] getRotatedBoundingBoxPoints(float xcenter, float ycenter) { //Can Display
        float rot = this.rot;
        PVector[] rotatedBoundingBoxPoints = new PVector[4];
        PVector[] boundingBoxPoints = getBoundingBoxPoints(xcenter, ycenter);
        PVector center = new PVector(xcenter, ycenter);
        for (int i = 0; i < 4; i++) {
            rotatedBoundingBoxPoints[i] = rotateAbout(boundingBoxPoints[i], center, rot);
        }
        return rotatedBoundingBoxPoints;
    }

    protected PVector rotateAbout(PVector rotatePoint, PVector anchor, float degrees) {
        PVector finalPoint = new PVector(0, 0);
        float radians = (float) (Math.PI / 180) * degrees;

        // make finalPoint like rotatePoint as if aboutPoint were the origin
        finalPoint.x = rotatePoint.x - anchor.x;
        finalPoint.y = rotatePoint.y - anchor.y;

        // rotate finalPoint about the origin
        float tempX = finalPoint.x * (float) Math.cos(radians) -  finalPoint.y * (float) Math.sin(radians);
        float tempY = finalPoint.x * (float) Math.sin(radians) +  finalPoint.y * (float) Math.cos(radians);

        // translate finalPoint back to where it should be based on aboutPoint
        finalPoint.x = tempX + anchor.x;
        finalPoint.y = tempY + anchor.y;

        return finalPoint;
    }

    protected void display() {
        sketch.translate(xcenter, ycenter);
        sketch.rotate((float) Math.toRadians(rot));
        sketch.scale(zoom);
     }

    protected void pan(float xo, float yo){
        xpos = xpos + xo;
        ypos = ypos + yo;
        xcenter += xo;
        ycenter += yo;
    }

    protected void resize(float factor) {
        //pixelHeight *= (1+factor);
        //pixelWidth *= (1+factor);
        zoom = Math.max(.1F, 1 + factor);
        sketch.println(pixelWidth+", "+pixelHeight);
        //pixelHeight *= zoom;
        //pixelWidth *= zoom;
    }
}
