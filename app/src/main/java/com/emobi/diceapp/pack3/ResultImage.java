
package com.emobi.diceapp.pack3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.emobi.diceapp.R;

public class ResultImage extends Activity {
    int[] images_ids1={R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6};
    int[] images_ids2={R.drawable.img7,R.drawable.img8,R.drawable.img9,R.drawable.img10,R.drawable.img11,R.drawable.img12};
    int[] images_ids3={R.drawable.img13,R.drawable.img14,R.drawable.img15,R.drawable.img16,R.drawable.img17,R.drawable.img18};
    int[] images_ids4={R.drawable.img19,R.drawable.img20,R.drawable.img21,R.drawable.img22,R.drawable.img23,R.drawable.img24};
    int[] images_ids5={R.drawable.img25,R.drawable.img26,R.drawable.img27,R.drawable.img28,R.drawable.img29,R.drawable.img30};
    int[] images_ids6={R.drawable.img31,R.drawable.img32,R.drawable.img33,R.drawable.img34,R.drawable.img35,R.drawable.img36};
    int[] images_ids7={R.drawable.img37,R.drawable.img38,R.drawable.img39,R.drawable.img40,R.drawable.img41,R.drawable.img42};

    ImageView iv_love1,iv_love2,iv_love3,iv_love4,iv_love5,iv_love6,iv_love7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_image);
        iv_love1= (ImageView) findViewById(R.id.iv_love1);
        iv_love2= (ImageView) findViewById(R.id.iv_love2);
        iv_love3= (ImageView) findViewById(R.id.iv_love3);
        iv_love4= (ImageView) findViewById(R.id.iv_love4);
        iv_love5= (ImageView) findViewById(R.id.iv_love5);
        iv_love6= (ImageView) findViewById(R.id.iv_love6);
        iv_love7= (ImageView) findViewById(R.id.iv_love7);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            String result=bundle.getString("img");
            Log.d("sunil",result);
            String split[]=result.split("\\+");
            for(String s:split){
                Log.d("sunil",s);
            }
            ShowImageView(split,split.length);

        }
    }
    public void ShowImageView(String[] split,int size){
        switch (size){
            case 1:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.GONE);
                iv_love3.setVisibility(View.GONE);
                iv_love4.setVisibility(View.GONE);
                iv_love5.setVisibility(View.GONE);
                iv_love6.setVisibility(View.GONE);
                iv_love7.setVisibility(View.GONE);
                try{
                    double id=Double.parseDouble(split[0]);
                    Log.d("sunil","id1:-"+id);
                    iv_love1.setImageResource(images_ids1[((int) id)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;
            case 2:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.VISIBLE);
                iv_love3.setVisibility(View.GONE);
                iv_love4.setVisibility(View.GONE);
                iv_love5.setVisibility(View.GONE);
                iv_love6.setVisibility(View.GONE);
                iv_love7.setVisibility(View.GONE);
                try{
                    double id0=Double.parseDouble(split[0]);
                    double id1=Double.parseDouble(split[1]);
                    iv_love1.setImageResource(images_ids1[((int) id0)-1]);
                    iv_love2.setImageResource(images_ids2[((int) id1)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;
            case 3:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.VISIBLE);
                iv_love3.setVisibility(View.VISIBLE);
                iv_love4.setVisibility(View.GONE);
                iv_love5.setVisibility(View.GONE);
                iv_love6.setVisibility(View.GONE);
                iv_love7.setVisibility(View.GONE);
                try{
                    double id2=Double.parseDouble(split[2]);
                    double id1=Double.parseDouble(split[1]);
                    double id0=Double.parseDouble(split[0]);
                    iv_love1.setImageResource(images_ids1[((int) id0)-1]);
                    iv_love2.setImageResource(images_ids2[((int) id1)-1]);
                    iv_love3.setImageResource(images_ids3[((int) id2)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;
            case 4:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.VISIBLE);
                iv_love3.setVisibility(View.VISIBLE);
                iv_love4.setVisibility(View.VISIBLE);
                iv_love5.setVisibility(View.GONE);
                iv_love6.setVisibility(View.GONE);
                iv_love7.setVisibility(View.GONE);
                try{
                    double id3=Double.parseDouble(split[3]);
                    double id2=Double.parseDouble(split[2]);
                    double id1=Double.parseDouble(split[1]);
                    double id0=Double.parseDouble(split[0]);
                    iv_love1.setImageResource(images_ids1[((int) id0)-1]);
                    iv_love2.setImageResource(images_ids2[((int) id1)-1]);
                    iv_love3.setImageResource(images_ids3[((int) id2)-1]);
                    iv_love4.setImageResource(images_ids4[((int) id3)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;
            case 5:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.VISIBLE);
                iv_love3.setVisibility(View.VISIBLE);
                iv_love4.setVisibility(View.VISIBLE);
                iv_love5.setVisibility(View.VISIBLE);
                iv_love6.setVisibility(View.GONE);
                iv_love7.setVisibility(View.GONE);
                try{
                    double id4=Double.parseDouble(split[4]);
                    double id3=Double.parseDouble(split[3]);
                    double id2=Double.parseDouble(split[2]);
                    double id1=Double.parseDouble(split[1]);
                    double id0=Double.parseDouble(split[0]);
                    iv_love1.setImageResource(images_ids1[((int) id0)-1]);
                    iv_love2.setImageResource(images_ids2[((int) id1)-1]);
                    iv_love3.setImageResource(images_ids3[((int) id2)-1]);
                    iv_love4.setImageResource(images_ids4[((int) id3)-1]);
                    iv_love5.setImageResource(images_ids5[((int) id4)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;
            case 6:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.VISIBLE);
                iv_love3.setVisibility(View.VISIBLE);
                iv_love4.setVisibility(View.VISIBLE);
                iv_love5.setVisibility(View.VISIBLE);
                iv_love6.setVisibility(View.VISIBLE);
                iv_love7.setVisibility(View.GONE);
                try{
                    double id5=Double.parseDouble(split[5]);
                    double id4=Double.parseDouble(split[4]);
                    double id3=Double.parseDouble(split[3]);
                    double id2=Double.parseDouble(split[2]);
                    double id1=Double.parseDouble(split[1]);
                    double id0=Double.parseDouble(split[0]);
                    iv_love1.setImageResource(images_ids1[((int) id0)-1]);
                    iv_love2.setImageResource(images_ids2[((int) id1)-1]);
                    iv_love3.setImageResource(images_ids3[((int) id2)-1]);
                    iv_love4.setImageResource(images_ids4[((int) id3)-1]);
                    iv_love5.setImageResource(images_ids5[((int) id4)-1]);
                    iv_love6.setImageResource(images_ids6[((int) id5)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;
            case 7:
                iv_love1.setVisibility(View.VISIBLE);
                iv_love2.setVisibility(View.VISIBLE);
                iv_love3.setVisibility(View.VISIBLE);
                iv_love4.setVisibility(View.VISIBLE);
                iv_love5.setVisibility(View.VISIBLE);
                iv_love6.setVisibility(View.VISIBLE);
                iv_love7.setVisibility(View.VISIBLE);
                try{
                    double id6=Double.parseDouble(split[6]);
                    double id5=Double.parseDouble(split[5]);
                    double id4=Double.parseDouble(split[4]);
                    double id3=Double.parseDouble(split[3]);
                    double id2=Double.parseDouble(split[2]);
                    double id1=Double.parseDouble(split[1]);
                    double id0=Double.parseDouble(split[0]);
                    iv_love1.setImageResource(images_ids1[((int) id0)-1]);
                    iv_love2.setImageResource(images_ids2[((int) id1)-1]);
                    iv_love3.setImageResource(images_ids3[((int) id2)-1]);
                    iv_love4.setImageResource(images_ids4[((int) id3)-1]);
                    iv_love5.setImageResource(images_ids5[((int) id4)-1]);
                    iv_love6.setImageResource(images_ids6[((int) id5)-1]);
                    iv_love7.setImageResource(images_ids7[((int) id6)-1]);
                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }
                break;

        }
    }
}
