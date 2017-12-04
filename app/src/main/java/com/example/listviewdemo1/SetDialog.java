package com.example.listviewdemo1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class SetDialog extends Dialog {
	private Button b_ok;
	private Button b_cancel;
	private EditText et_red;
	private EditText et_green;
	private EditText et_yellow;
    private MyCallBack myCallBack;
	

	public SetDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public void setCallBack(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_dialog);
		initView();
		setListener();
	}

	private void setListener() {
		b_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				myCallBack.ok();
			}
		});
		b_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				myCallBack.cancel();
			}
		});
	}

	private void initView() {
		et_red=(EditText)findViewById(R.id.red_et);
		et_green=(EditText)findViewById(R.id.green_et);
		et_yellow=(EditText)findViewById(R.id.yellow_et);
		b_ok=(Button)findViewById(R.id.b_ok);
		b_cancel=(Button)findViewById(R.id.b_cancel);
		
	}
	public interface MyCallBack{
        void ok();
        void cancel();
    }
	

}
