/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bujok.sharelocation;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bujok.sharelocation.backendcomms.ServletPostAsyncTask;
import com.bujok.sharelocation.dialogs.ConfirmLocRequestDialogFragment;
import com.bujok.sharelocation.models.User;
import com.bujok.sharelocation.ui.adapters.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


public class  MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private FirebaseDatabase mDatabaseReference;
    private DatabaseReference mUserDBRef;
    private DatabaseReference mLocationDBRef;

    private ListView mUserListing;
    private String appFCMid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseReference = FirebaseDatabase.getInstance();
        mUserDBRef = mDatabaseReference.getReference("users");
        mLocationDBRef = mDatabaseReference.getReference("userTesting");

        // Button launches NewPostActivity
        findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserListing();
                //UserLocationHistory userLocationHistory = new UserLocationHistory(1,100,100, Calendar.getInstance().getTimeInMillis());
                //mLocationDBRef.child(String.valueOf(userLocationHistory.getUserID())).setValue(userLocationHistory);
            }
        });

        findViewById(R.id.sendUpstreamMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUserListing = (ListView) findViewById(R.id.userListView);



        //get GCM token
        // Get token
        appFCMid = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        String msg = getString(R.string.msg_token_fmt, appFCMid);
        Log.d(TAG, msg);
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

        /// this needs removing, this is handled in other code in myfirebaseinstanceservice.
        String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(UserID).child("fcmToken").setValue(appFCMid);



    }

    private void getUserListing() {
       final List <String> users = new ArrayList<String>();
        final ArrayList<User> arrayOfUsers = new ArrayList<User>();
        mUserDBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " ,""+dataSnapshot.getChildrenCount());

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User u = userSnapshot.getValue(User.class);
                    arrayOfUsers.add(u);
                    users.add(u.username);
                    Log.e(TAG, u.username);
                }
               // User[] userListArray = users.toArray(new User[users.size()]);


                UsersAdapter adapter = new UsersAdapter(MainActivity.this, arrayOfUsers);
                mUserListing.setAdapter(adapter);
                mUserListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        User u = (User) arg0.getAdapter().getItem(arg2);
                        Log.d("############","Items " );

                        ConfirmLocRequestDialogFragment confirmDialog = new ConfirmLocRequestDialogFragment();
                        Bundle args = new Bundle();
                        args.putString("sender", u.fcmToken);
                        args.putString("receiver",appFCMid );
                        confirmDialog.setArguments(args);
                        confirmDialog.show(getSupportFragmentManager(),"ConfirmFragmentDialog");


                       // ConfirmLocRequestDialogFragment confirmDialog = new ConfirmLocRequestDialogFragment();
                       // confirmDialog.show(getSupportFragmentManager(),"ConfirmFragmentDialog");

                    }

                });;
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "reading from the database failed....", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //called when button is pressed
    public void gotoLocationView(View view){

        Intent intent = new Intent(this, UserLocationActivity.class);
        startActivity(intent);


    }

/*    public static class ConfirmLocRequestDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.dialog_sendLocationRequestToUser)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Pair<Context, String> senderData  = new Pair<Context, String>(getContext(), appFCMid) ;
                            Pair<Context, String> receeiverData  = new Pair<Context, String>(getContext(), "Manfred") ;

                            new ServletPostAsyncTask().execute(senderData,receeiverData);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
