package fr.wcs.hackathon2;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.db.rossdeckview.FlingChief;
import com.db.rossdeckview.FlingChiefListener;
import com.db.rossdeckview.RossDeckView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FlingChiefListener.Actions, FlingChiefListener.Proximity {

    private final static int DELAY = 1000;

    private List<Pair<String, String>> mItems;

    private DeckAdapter mAdapter;

    private View mLeftView;

    private View mUpView;

    private View mRightView;

    private View mDownView;

    private int[] mColors;

    private String[] mNames;

    private String[] mImages;

    private int mCount = 0;

    FirebaseDatabase mDatabase;
    FirebaseUser mUser;
    DatabaseReference myRef;

    private String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColors  = getResources().getIntArray(R.array.cardsBackgroundColors);
        mItems = new ArrayList<>();

        mAdapter = new DeckAdapter(this, mItems);
        mNames = new String[87];
        mImages = new String[87];

        RossDeckView mDeckLayout = (RossDeckView) findViewById(R.id.decklayout);
        mDeckLayout.setAdapter(mAdapter);
        mDeckLayout.setActionsListener(this);
        mDeckLayout.setProximityListener(this);

        mLeftView = findViewById(R.id.left);
        mUpView = findViewById(R.id.up);
        mRightView = findViewById(R.id.right);
        mDownView = findViewById(R.id.down);

        mDatabase = FirebaseDatabase.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserID = mUser.getUid();
        myRef = mDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int index = 0;
                for (DataSnapshot star : dataSnapshot.getChildren()) {
                    if(!star.getKey().equals(mUserID)) {
                        String starName = star.child("name").getValue(String.class);
                        String starImage = star.child("image").getValue(String.class);
                        mNames[Integer.valueOf(star.getKey())] = starName;
                        mImages[Integer.valueOf(star.getKey())] = starImage;
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mItems.add(newItem());
        mItems.add(newItem());
        mItems.add(newItem());
    }

    @Override
    public boolean onDismiss(@NonNull FlingChief.Direction direction, @NonNull View view) {

        Toast.makeText(this, "Dismiss to " + direction, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDismissed(@NonNull View view) {

        mItems.remove(0);
        mAdapter.notifyDataSetChanged();
        newItemWithDelay(DELAY);
        return true;
    }

    @Override
    public boolean onReturn(@NonNull View view) {
        return true;
    }

    @Override
    public boolean onReturned(@NonNull View view) {
        return true;
    }

    @Override
    public boolean onTapped() {
        Toast.makeText(this, "Tapped", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onDoubleTapped() {
        Toast.makeText(this, "Double tapped", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onProximityUpdate(@NonNull float[] proximities, @NonNull View view) {

        mLeftView.setScaleY((1 - proximities[0] >= 0) ? 1 - proximities[0] : 0);
        mUpView.setScaleX((1 - proximities[1] >= 0) ? 1 - proximities[1] : 0);
        mRightView.setScaleY((1 - proximities[2] >= 0) ? 1 - proximities[2] : 0);
        mDownView.setScaleX((1 - proximities[3] >= 0) ? 1 - proximities[3] : 0);
    }


    private Pair<String, String> newItem(){

        Pair<String, String> res = new Pair<>(mNames[mCount], mImages[mCount]);
        mCount = (mCount >= mNames.length - 1) ? 0 : mCount + 1;
        return res;
    }


    private void newItemWithDelay(int delay){

        final Pair<String, String> res = newItem();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mItems.add(res);
                mAdapter.notifyDataSetChanged();
            }
        }, delay);
    }

}