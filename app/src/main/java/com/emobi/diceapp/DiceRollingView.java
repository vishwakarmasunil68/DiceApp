
//  DiceRollingView.java  Copyright (c) Kari Laitinen

//  http://www.naturalprogramming.com/

//  2015-12-30  File created.
//  2016-01-01  Last modification.


/*  

*/

package com.emobi.diceapp;

import android.graphics.* ;  // Classes Canvas, Color, Paint, RectF, etc.
import android.graphics.drawable.Drawable ;
import android.view.View ;
import android.view.MotionEvent ;
import android.view.GestureDetector ;
import android.content.Context ;
import android.content.res.Resources ;
import java.util.ArrayList ;

import android.util.AttributeSet ;


class Dice
{
   static final int DICE_WIDTH   =  128 ;
   static final int DICE_HEIGHT  =  128 ;

   int dice_center_point_x = 0 ;
   int dice_center_point_y = 0 ;

   int visible_dots = (int) ( Math.random() * 6 ) + 1 ; // random number 1 ... 6

   boolean dice_is_rolling = false ;
   int rolling_counter = 0 ;
   int current_rotation = 0 ;

   ArrayList<Drawable> dice_images = new ArrayList<Drawable>() ;

   public Dice( Context given_context )
   {
      Resources resources = given_context.getResources();

      // dice1, dice2, etc. refer to files dice1.png, dice2.png, etc., respectively.

      dice_images.add( resources.getDrawable( R.drawable.dice1, null ) ) ;
      dice_images.add( resources.getDrawable( R.drawable.dice2, null ) ) ;
      dice_images.add( resources.getDrawable( R.drawable.dice3, null ) ) ;
      dice_images.add( resources.getDrawable( R.drawable.dice4, null ) ) ;
      dice_images.add( resources.getDrawable( R.drawable.dice5, null ) ) ;
      dice_images.add( resources.getDrawable( R.drawable.dice6, null ) ) ;

      // As each dice will be drawn so that the coordinates system is
      // translated to the center point of the dice, the bounds of each dice
      // can be set as is done in the following loop.

      for ( Drawable dice_image : dice_images )
      {
         dice_image.setBounds( - DICE_WIDTH / 2, - DICE_HEIGHT / 2,
                               DICE_WIDTH / 2, DICE_HEIGHT / 2 ) ;
      }
   }

   public void set_position( int given_center_point_x,
                             int given_center_point_y )
   {
      dice_center_point_x = given_center_point_x ;
      dice_center_point_y = given_center_point_y ;
   }

   public void start_rolling_operation()
   {
      dice_is_rolling = true ;
      rolling_counter = 20 ;      
   }

   public void roll()
   {
      visible_dots = (int) ( Math.random() * 6 ) + 1 ; // random number 1 ... 6

      current_rotation  =  current_rotation + 36 ;

      if ( current_rotation >= 360 )
      {
         current_rotation  =  0 ;
      }

      rolling_counter -- ;

      if ( rolling_counter <= 0 )
      {
         dice_is_rolling = false ;
      }
   }

   public boolean is_rolling()
   {
      return ( dice_is_rolling == true ) ;
   }

   public boolean contains_point( int given_point_x, int given_point_y )
   {
      return ( 
         given_point_x  >=  dice_center_point_x  -  DICE_WIDTH / 2 &&
         given_point_x  <=  dice_center_point_x  +  DICE_WIDTH / 2 &&
         given_point_y  >=  dice_center_point_y  -  DICE_HEIGHT / 2 &&
         given_point_y  <=  dice_center_point_y  +  DICE_HEIGHT / 2 ) ;
   } 

   public void draw( Canvas canvas )
   {
      canvas.save() ;  //  Save the original canvas state

      //  First we move the zero point of the coordinate system into
      //  the center point of the dice image.
      canvas.translate( dice_center_point_x, dice_center_point_y ) ;

      if ( dice_is_rolling == true )
      {
         //  Rotate the coordinate system as much as is the value of
         //  the data field current_rotation.
         canvas.rotate( current_rotation ) ;
      }

      dice_images.get( visible_dots - 1 ).draw( canvas ) ;

      //  Finally we restore the original coordinate system.
      canvas.restore() ;
   }
}

class DiceRollingView extends View implements Runnable
{
   Thread  animation_thread ;
   boolean  thread_must_be_executed ;

   int  view_width, view_height ;

   Dice left_dice, right_dice ;

   public DiceRollingView( Context context, AttributeSet attributes ) 
   {
      super( context, attributes ) ;

      setBackgroundColor( 0xFF41C300 ) ;

      left_dice = new Dice( context ) ;
      right_dice = new Dice( context ) ;
   }

   public void onSizeChanged( int current_width_of_this_view,
                              int current_height_of_this_view,
                              int old_width_of_this_view,
                              int old_height_of_this_view )
   {
      view_width  = current_width_of_this_view ;
      view_height = current_height_of_this_view ;

      left_dice.set_position( view_width / 2 - 96, view_height / 2 ) ;
      right_dice.set_position( view_width / 2 + 96, view_height / 2 ) ;
   }

   public void start_animation_thread()
   {
      if ( animation_thread  ==  null )
      {
         thread_must_be_executed  =  true ;
         animation_thread  =  new  Thread( this ) ;

         animation_thread.start() ;
      }
   }

   public void stop_animation_thread()
   {
      if ( animation_thread  !=  null )
      {
         thread_must_be_executed  =  false ;
         animation_thread.interrupt() ;

         animation_thread  =  null ;
      }
   }

   public void run()
   {
      while ( thread_must_be_executed  ==  true ) 
      {
         if ( left_dice.is_rolling() )
         {
            left_dice.roll() ;
         }

         if ( right_dice.is_rolling() )
         {
            right_dice.roll() ;
         }

         postInvalidate() ;

         try
         {
            Thread.sleep( 100 ) ;
         }
         catch ( InterruptedException caught_exception )
         {
            // No actions to handle the exception.
         }
      }  
   }


   public boolean onTouchEvent ( MotionEvent motion_event )
   {
      if ( motion_event.getAction() == MotionEvent.ACTION_DOWN )
      {
         if ( left_dice.contains_point( (int) motion_event.getX(),
                                        (int) motion_event.getY() ) )
         {
            left_dice.start_rolling_operation() ;
         }
         else if ( right_dice.contains_point( (int) motion_event.getX(),
                                              (int) motion_event.getY() ) )
         {
            right_dice.start_rolling_operation() ;
         }
      }

      return true ;
   }


   public void roll_all_dice()
   {
      left_dice.start_rolling_operation() ;
      right_dice.start_rolling_operation() ;
   }


   @Override
   protected void onDraw( Canvas canvas ) 
   {
      left_dice.draw( canvas ) ;
      right_dice.draw( canvas ) ;
   }
}


