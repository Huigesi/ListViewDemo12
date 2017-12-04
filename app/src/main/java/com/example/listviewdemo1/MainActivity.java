package com.example.listviewdemo1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.listviewdemo1.MainActivity;
import com.example.listviewdemo1.SetDialog;
import com.example.listviewdemo1.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private static final int UP_DATE = 0x01;
	private static final int SHOW_DIALOG = 0x02;
	private ProgressDialog mDialog;
	private DataAdapter mAdapter;
	private Handler mHandler;
	private List<ItemBean> mList;
	private Button button_find;
	private Button button_all;
	private ListView mListView;
	private Thread mThread;
	private String mString;
	// �״���ܽ᣺�ٷ�����api����action��
	private String mPath = "http://192.168.1.243:8080/transportservice/type/jason/action/GetTrafficLightConfigAction.do";
	private int time = 0;
	private List<String> arr_list;
	private ArrayAdapter<String> arr_adapter;
	private Spinner mSpinner;

	int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getDialog();
		handMessage();
		initData();
		initView();
		setListener();
		setListView();
		// ��Ҫ��һ��ʼִ�У���Ӱ�찴ť����ȥʱ�Ľ��
		/*
		 * setThread(); mThread.start();
		 */

	}

	private void setThread() {
		mThread = new Thread() {
			@Override
			public void run() {
				int count = 1;
				while (count <= 5) {
					mHandler.sendEmptyMessage(SHOW_DIALOG);
					// �״�㣺�ٷ���post����Ҳ�д�Ӧ��ΪId,����ID��
					mString = HttpClients.sendMessage(mPath,
							"{\"TrafficLightId\":" + count + "}");
					Log.i("mString", mString);
					try {
						parseJson(count);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}
				super.run();
				sort();
				// ����Ū����ˢ��ҳ�棨�������ˣ�
				mHandler.sendEmptyMessage(UP_DATE);
			}
		};
	}

	private void sort() {
		String item = mSpinner.getSelectedItem().toString();
		if (item.equals("�ƺ�����")) {
			idSort1();
		} else if (item.equals("�������")) {
			redSort1();
		} else if (item.equals("�̵�����")) {
			greenSort1();
		} else if (item.equals("�Ƶ�����")) {
			yellowSort1();
		} else if (item.equals("�ƺŽ���")) {
			idSort2();
		} else if (item.equals("��ƽ���")) {
			redSort2();
		} else if (item.equals("�̵ƽ���")) {
			greenSort2();
		} else if (item.equals("�Ƶƽ���")) {
			yellowSort2();
		}
	}

	private void parseJson(int count) throws Exception {

		JSONObject jsonObject = new JSONObject(mString);
		String jString = jsonObject.getString("serverinfo");
		JSONObject mJsonObject = new JSONObject(jString);
		Log.i("jString", jString);
		ItemBean bean = new ItemBean();
		bean.setmId(count);
		bean.setmRed(mJsonObject.getInt("RedTime"));
		bean.setmGreen(mJsonObject.getInt("GreenTime"));
		bean.setmYellow(mJsonObject.getInt("YellowTime"));
		mList.add(bean);
	}

	private void setListView() {
		// �״���ܽ᣺������MainActivity.this������ֱ��this
		mAdapter = new DataAdapter(MainActivity.this, mList);
		mListView.setAdapter(mAdapter);
		arr_adapter = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_spinner_item, arr_list);
		arr_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(arr_adapter);
	}

	private void setListener() {
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mList.clear();
				setThread();
				mThread.start();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		button_find.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mList.clear();
				setThread();
				mThread.start();
				// mHandler.sendEmptyMessage(UP_DATE);

			}
		});
		button_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				settingDialog();
			}
		});
	}

	public void settingDialog() {
		final SetDialog mSetDialog = new SetDialog(MainActivity.this);
		mSetDialog.setCallBack(new SetDialog.MyCallBack() {

			@Override
			public void ok() {
				// TODO Auto-generated method stub
				mSetDialog.dismiss();
			}

			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				mSetDialog.dismiss();
			}
		});
		mSetDialog.setTitle("���̵�����");
		mSetDialog.setCancelable(true);
		mSetDialog.show();

	}

	private void initView() {
		button_find = (Button) findViewById(R.id.button_find);
		mListView = (ListView) findViewById(R.id.listview);
		mSpinner = (Spinner) findViewById(R.id.spinner);
		button_all = (Button) findViewById(R.id.button_all);
	}

	private void initData() {
		mList = new ArrayList<ItemBean>();
		arr_list = new ArrayList<String>();

		arr_list.add("�������");
		arr_list.add("�ƺ�����");
		arr_list.add("�̵�����");
		arr_list.add("�Ƶ�����");
		arr_list.add("�ƺŽ���");
		arr_list.add("��ƽ���");
		arr_list.add("�̵ƽ���");
		arr_list.add("�Ƶƽ���");
	}

	private void handMessage() {
		mHandler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				// �״���ܽ᣺����ѶԻ���ȥ����ȥˢ��ҳ��
				mDialog.dismiss();
				switch (msg.what) {
				case UP_DATE:
					update();
					break;
				case SHOW_DIALOG:
					mDialog.show();
					break;
				}
				return false;
			}
		});

	}

	public void update() {
		if (flag == 1) {
			mSpinner.getSelectedItem().toString();
			sort();
		}
		mAdapter.notifyDataSetChanged();
	}

	private void getDialog() {
		// ���������this
		mDialog = new ProgressDialog(this);
		mDialog.setMessage("���ڼ���");
	}

	private void redSort1() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				// �״�㣬b1,b2�ǵø�
				if (b1.getmRed() <= b2.getmRed()) {
					return -1;
				} else {
					return 0;
				}

			}
		});

	}

	private void redSort2() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmRed() >= b2.getmRed()) {
					return -1;
				} else {
					return 0;
				}

			}
		});

	}

	private void greenSort1() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmGreen() <= b2.getmGreen()) {
					return -1;
				} else {
					return 0;
				}
			}
		});

	}

	private void greenSort2() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmGreen() >= b2.getmGreen()) {
					return -1;
				} else {
					return 0;
				}

			}
		});

	}

	private void yellowSort1() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmYellow() <= b2.getmYellow()) {
					return -1;
				} else {
					return 0;
				}
			}
		});

	}

	private void yellowSort2() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmYellow() >= b2.getmYellow()) {
					return -1;
				} else {
					return 0;
				}

			}
		});
	}

	private void idSort1() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmId() <= b2.getmId()) {
					return -1;
				} else {
					return 0;
				}

			}
		});

	}

	private void idSort2() {
		Collections.sort(mList, new Comparator<ItemBean>() {

			@Override
			public int compare(ItemBean b1, ItemBean b2) {
				if (b1.getmId() >= b2.getmId()) {
					return -1;
				} else {
					return 0;
				}

			}
		});

	}
}
