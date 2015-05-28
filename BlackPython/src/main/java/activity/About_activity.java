package activity;

import com.blackpython.R;
import com.blackpython.R.id;
import com.blackpython.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class About_activity extends ActionBarActivity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);


		switch(getIntent().getExtras().getString("Mode")) {
			case "ABOUT":
				setContentView(R.layout.fragment_about);
				TextView tv1 = (TextView) findViewById(R.id.textView1);
				tv1.setText("O nás");
				TextView tv2 = (TextView) findViewById(R.id.textView2);
				tv2.setText("Sme šiesti krásni chlapci čo vytvorili super aplikáciu!");
				break;
			case "RULES":
				setContentView(R.layout.fragment_rules);
				TextView rules1 = (TextView)findViewById(R.id.textView1);
				rules1.setText("Pravidlá");
				TextView rules2 = (TextView)findViewById(R.id.textView2);
				rules2.setText("Blablabla pravidla každý kupón možno použiť iba raz");
				//throw new RuntimeException("This is a crash");
				break;
			case "INFO":
				setContentView(R.layout.fragment_info);
				TextView info = (TextView)findViewById(R.id.textView1);
				info.setText("Info");
				TextView info2 = (TextView)findViewById(R.id.textView2);
				info2.setText("Neviem co sem mam napisat, mozno by tu bol pekny aj nejaky obrazok alebo tak nejaky design spravit aj na tieto okna");
				break;
		}
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
