package frontend;
/**
 * <h1>IOHandler</h1>
 * IOHandler takes in all IO actions. They are passed from PApplet PollyPaint and are parsed here.
 * For example when the mouse is on a GUI eliment you dont want to pan the canvas by accident.
 *
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 *
 * TODO Finish
 *
 * TODO Comment
 */



import processing.core.*;
import processing.event.*;
import backend.Window;
import processing.core.*;
import java.awt.event.KeyEvent;



public class IOHandler{
  PApplet sketch;
  Window win;
  //gui holds our state. The state is a structure of varibales that represent the state of the GUI.
  //For example the selected tool and color are stored in the state.
  GUI gui;




  //zoom sensitivity
  float mouseSense = 5;
  /**
   * [IOHandler constructor]
   * @param sketch [PollyPaint, needed for easy access to IO variables like the mouse's location]
   */
  public IOHandler(PApplet sketch,  Window win, GUI gui){
    this.sketch = sketch;
    this.gui = gui;
    this.win = win;
  }

/**
 * [offGUI returns a boolean representing if the mouse is on the GUI or not]
 * @return [returns true of the mouse is not on GUI elements]
 */
  public boolean onCanvas(){
    return win.withinCanvas(sketch.mouseX, sketch.mouseY);
  }



  public void mouseClicked(){
    //Needs to parse the gui tool selected
    if(onCanvas()){
      if(gui.getTool() == 'r')
        win.createRect(sketch.mouseX, sketch.mouseY);
      if(gui.getTool() == 'e')
        win.createEllipse(sketch.mouseX, sketch.mouseY);
      //if((gui.getTool() == 's') && (sketch.keyCode.getKeyCode() == KeyCode.CONTROL))
      //  win.multiSelect(sketch.mouseX, sketch.mouseY);
      if(gui.getTool() == 's'){
        win.select(sketch.mouseX, sketch.mouseY);
        //upadte sliderRBG here
        //gui.updateRGB(win.getRGB)
      }
      if(gui.getTool() == 't'){
        //System.out.println("this is running");
        win.createTextBox(sketch.mouseX, sketch.mouseY, gui.getCurrentString(), "arial", 20);
      }
      if(gui.getTool() == 'c'){
        //System.out.println("this is running");
        win.createComment(sketch.mouseX, sketch.mouseY, gui.getCurrentString(), "arial", 20);
      }
      if(gui.getTool() == 'l')
        win.createLine(sketch.mouseX, sketch.mouseY);
      if(gui.getTool() == 'u')
        win.createCurve(sketch.mouseX, sketch.mouseY);
      if(gui.getTool() == 'x')
        win.createPollyGon(sketch.mouseX, sketch.mouseY, gui.getPolyCount());
    }

  }

  public void mouseReleased(){
    if(gui.getTool() == 'p')
      win.createFreeForm();
  }

  public void mouseDragged() {
    //if pan tool is selected
    if(gui.getTool() == 'p' && onCanvas())
      win.freeDraw(sketch.mouseX, sketch.mouseY);
    else if(onCanvas())
      win.pan(sketch.mouseX, sketch.mouseY, sketch.pmouseX, sketch.pmouseY);
  }


  public void mouseWheel(MouseEvent event) {
    win.zoom(event.getCount()/mouseSense);
  }


}
