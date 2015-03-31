package async;

import com.data.CouponSet;
import com.data.MemoryStorage;
import com.data.UserInformation;

import fakeData.FalseCoupons;
import activity.Index;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

	public class LoadData extends AsyncTask < String , Context , Void > {

	    private ProgressDialog      progressDialog ;
	    private Context             targetCtx ;

	    public LoadData ( Context context ) {
	        this.targetCtx = context ;
	        //this.needToShow = true;
	       // progressDialog = new ProgressDialog ( targetCtx ) ;
	       // progressDialog.setCancelable ( false ) ;
	       // progressDialog.setMessage ( "Retrieving data..." ) ;
	        //progressDialog.setTitle ( "Please wait" ) ;
	       // //progressDialog.setIndeterminate ( true ) ;
	    }

	    @ Override
	    protected void onPreExecute ( ) {
	       // progressDialog.show ( ) ;
	    }

	    @ Override
	    protected Void doInBackground ( String ... params ) {
	    	 MemoryStorage coup = new MemoryStorage(targetCtx);
	    	 UserInformation.setMemory(coup);  
	    	 if(coup.isDatabaseEmpty())
	    	 {
	    	 FalseCoupons.setDatabase(targetCtx);	
	    	 }
	    	 CouponSet.setCoupons(coup.getAllCoupons());
	    	

	       return null ;
	    }

	    @ Override
	    protected void onPostExecute ( Void result ) {
	       // if(progressDialog != null && progressDialog.isShowing()){
	        //    progressDialog.dismiss ( ) ;
	            
	            Intent intent = new Intent(targetCtx,
						Index.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle b = new Bundle();
				b.putInt("login", 1);
				intent.putExtras(b);
				targetCtx.startActivity(intent);
	            
	        //}
	    }
	}
