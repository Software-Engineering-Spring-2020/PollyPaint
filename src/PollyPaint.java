import processing.core.PApplet;
import frontend.*;
import frontend.controlP5.*;
import frontend.handler.*;
import backend.*;
/**
 *<h1> PollyPaint </h1>
 * This is our primary class. Our `main` method is stored here. When launching the program this is what launches.
 *
 * This is our `sketch` and it is broken into five methods.
 * <h4>main</h4>
 * 	This is called first and passes any arguments to PApplets.
 * <h4>settings</h4>
 *  "The settings() function is necessary when using Processing code outside of the Processing Development Environment (PDE).
 *  For example, when using the Eclipse code editor, it's necessary to use settings() to define the size() and smooth() values for a sketch." ~ Processing Docs
 *  Here we enbale smoth() which is anti-aliasing (aa). We can increase this by using smoth(4) for x4 aa, or smoth(8) for x8 aa.
 * <h4>setup</h4>
 * 	Setup runs once at the start of the program. We can build and define things here.
 * <h4>draw</h4>
 *  Draw gets called with the framerate.
 * <h4>controlEvent</h4>
 * 	controEvent is called by `controlP5`. It then passes all events generated by the gui to the handler where it is parsed.
 *
 *
 *
 * <h3>Frontend</h3>
 *
 * <h3>Backend</h3>
 *
 *
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.09.2019
 */

public class PollyPaint extends PApplet {
GUI gui;
CanvasSupport cs;
Handler h;

float canvasX, canvasY, canvasWidth, canvasHeight;

/**
 * [main is what runs by default. This should not be called by any other class.]
 * @param passedArgs [description]
 */
	public static void main(String[] passedArgs) {
		String[] appletArgs = { "PollyPaint" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
    }

/**
 * [settings description]
 */
	public void settings() {
		size(1000, 500);
		smooth();
	}


/**
 * [setup description]
 */
	public void setup(){
		surface.setResizable(true);
		gui = new GUI(this);
		gui.setup();
		//Init Canvas Position and Size
		canvasX = width/2;
		canvasY = height/2;
		canvasWidth = width/2;
		canvasHeight = height/2;

		cs = new CanvasSupport(this, canvasX, canvasY, canvasWidth, canvasHeight);
		h = new Handler(cs);
	}

/**
 * [draw description]
 */
	public void draw(){
		background(64);
		//ellipse(mouseX, mouseY, 20, 20);
		gui.display();
	}


	/**
	 * [controlEvent is called whenever a controlP5 controller is used]
	 * @param theEvent [is passed by ControlP5 and contains the event that was just triggered]
	 */
	public void controlEvent(ControlEvent theEvent) {
    h.handle(theEvent);
	}

}
