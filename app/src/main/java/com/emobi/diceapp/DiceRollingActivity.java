
//  DiceRollingActivity.java  Copyright (c) Kari Laitinen

//  http://www.naturalprogramming.com/

//  2015-12-17  File created.
//  2016-03-24  Last modification.

/*  
    This app shows two dice on the screen. You can 'roll' them
    either by pressing the button or by touching the dice.

    To run this program set up an Andoid Studio project and
    set DiceRollingActivity as the name of its main activity.
    Use the package name given below. Overwrite the automatically
    created .java file with this file. Copy also the 
    file DiceRollingView.java to the same folder, and overwrite
    also the activity_dice_rolling.xml.

    Additionally, you have to copy the .png files from the folder
    /res/drawable to the corresponding local folder.

*/

package com.emobi.diceapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DiceRollingActivity extends Activity
{
   DiceRollingView dice_rolling_view ;

   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState ) ;
        
      requestWindowFeature( Window.FEATURE_NO_TITLE ) ;

      getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN ) ;

      setContentView( R.layout.activity_dice_rolling ) ;

      dice_rolling_view = (DiceRollingView)
                             findViewById( R.id.dice_rolling_view ) ;
   }

   public void onResume()
   {
      super.onResume() ;

      dice_rolling_view.start_animation_thread() ;
   }

   public void onPause()
   {
      super.onPause() ;

      dice_rolling_view.stop_animation_thread() ;
   }

   public void button_to_roll_dice_was_pressed( View view )
   {
      dice_rolling_view.roll_all_dice() ;
   }
}

