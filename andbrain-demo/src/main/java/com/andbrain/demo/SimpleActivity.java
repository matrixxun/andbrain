package com.andbrain.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import andbrain.annotations.AfterTextChanged;

import andbrain.annotations.BeforeTextChanged;
import andbrain.annotations.OnCheckRadioGroup;
import andbrain.annotations.OnCheckedChanged;
import andbrain.annotations.OnClick;
import andbrain.annotations.OnEditorAction;
import andbrain.annotations.OnFocusChange;
import andbrain.annotations.OnItemClick;
import andbrain.annotations.OnItemLongClick;
import andbrain.annotations.OnKey;
import andbrain.annotations.OnLongClick;
import andbrain.annotations.OnSeekBarChanged;
import andbrain.annotations.OnStartTrackingTouch;
import andbrain.annotations.OnStopTrackingTouch;
import andbrain.annotations.OnTextChanged;
import andbrain.annotations.OnTouch;



public class SimpleActivity extends Activity  {
     static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana"};
     com.andbrain.demo.SimpleActivity$ h;

    Context mContext;
    private ArrayAdapter<String> listAdapter ;


    @OnClick({R.id.button,R.id.button})
    public void onclick(View v){
        Toast.makeText(v.getContext(),"button2 clicked",Toast.LENGTH_LONG).show();
    }

    @OnLongClick(R.id.button)
    public void onlongclick(View v){
        Toast.makeText(v.getContext(),"button2 long clicked",Toast.LENGTH_LONG).show();
    }

   @OnTouch(R.id.button)
    public void ontouch( MotionEvent event,View v){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(v.getContext(),"button2 touched DOWN",Toast.LENGTH_LONG).show();
                break;

            case MotionEvent.ACTION_MOVE:
                Toast.makeText(v.getContext(),"button2 touched MOVE",Toast.LENGTH_LONG).show();
                break;

            case MotionEvent.ACTION_UP:
                Toast.makeText(v.getContext(),"button2 touched UP",Toast.LENGTH_LONG).show();


                break;
        }

    }
    @OnKey(R.id.editText3)
    public void onkey( KeyEvent event, int keyCode,View v){

        Toast.makeText(v.getContext(),"keycode "+keyCode,Toast.LENGTH_SHORT).show();
    }
    @OnEditorAction(R.id.editText4)
    public void oneditaction(TextView v, int actionId, KeyEvent event){
        if  (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
            String message = v.getText().toString();
            Toast.makeText(v.getContext(),"message  "+message,Toast.LENGTH_SHORT).show();
        }

    }
    @OnFocusChange(R.id.editText3)
    public void onfocuschanged3(View v){
        Toast.makeText(v.getContext(),"Focus Changed editText3 1983  333333",Toast.LENGTH_SHORT).show();
    }
    @OnFocusChange(R.id.editText4)
    public void onfocuschanged4(View v,boolean b){
       if(b){ Toast.makeText(v.getContext(),"Focus Changed editText4 active",Toast.LENGTH_SHORT).show();}else{
           Toast.makeText(v.getContext(),"Focus Changed editText4 not active",Toast.LENGTH_SHORT).show();
       }
    }
    @OnItemClick(R.id.list)
    public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
        Toast.makeText(view.getContext(),"simple click"+((TextView) view).getText(), Toast.LENGTH_SHORT).show();
}
    @OnItemLongClick(R.id.list)
    public void onItemlongClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Toast.makeText(view.getContext(),"long click"+((TextView) view).getText(), Toast.LENGTH_SHORT).show();
    }

    @OnTextChanged(R.id.editText5)
    public void ontextchanged(java.lang.CharSequence c, int i1,int i2,int i3){
        Toast.makeText(mContext,"text was changed "+c,Toast.LENGTH_LONG).show();
    }
    @BeforeTextChanged(R.id.editText5)
    public void beforetextchanged(java.lang.CharSequence c, int i1,int i2,int i3){
        Toast.makeText(mContext,"text before changed "+c,Toast.LENGTH_LONG).show();
    }
    @AfterTextChanged(R.id.editText5)
    public void aftertextchanged(android.text.Editable edit){
        Toast.makeText(mContext,"text after changed ",Toast.LENGTH_LONG).show();
    }
    @OnCheckedChanged(R.id.chkBoxAndroid)
    public void oncheakedchanged(boolean yes){
     if(yes){Toast.makeText(mContext,"cheaked 3333333",Toast.LENGTH_LONG).show();}
        else{Toast.makeText(mContext,"not cheaked 33333",Toast.LENGTH_LONG).show();}
    }
    @OnCheckRadioGroup(R.id.myRadioGroup)
    public void oncheakradiogroup(android.widget.RadioGroup rad0, int int1){

        // find which radio button is selected

        if(int1 == R.id.silent) {

            Toast.makeText(getApplicationContext(), "choice: Silent",

                    Toast.LENGTH_SHORT).show();

        } else if(int1 == R.id.sound) {

            Toast.makeText(getApplicationContext(), "choice: Sound",

                    Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "choice: Vibration", Toast.LENGTH_SHORT).show();

        }

    }

    @OnStartTrackingTouch(R.id.seekBar)
    public void onstartbarchanged(){
        Toast.makeText(mContext,"start tracking  ",Toast.LENGTH_LONG).show();
    }
    @OnStopTrackingTouch(R.id.seekBar)
    public void onstopbarchanged(){
        Toast.makeText(mContext,"stop tracking  ",Toast.LENGTH_LONG).show();
    }
    @OnSeekBarChanged(R.id.seekBar)
     public void onseekbarchanged(int i){
         Toast.makeText(mContext,"seek bar is changed "+i,Toast.LENGTH_LONG).show();
     }



     @Override protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         h= new com.andbrain.demo.SimpleActivity$(this);
         mContext=this.getBaseContext();


     }


 }
