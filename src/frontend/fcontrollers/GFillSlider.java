package frontend.fcontrollers;
/**
 * <h1>FButton</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class GFillSlider extends FSlider{
  public GFillSlider(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Gfill", parrent, gui);
    super.slider.plugTo(this);
    super.slider.setMax(255);
    //super.slider.setColor(new CColor(0, 0,))
  }


  public void Gfill(int val){

    getGUI().setFill(val, 'g');
  }

}
