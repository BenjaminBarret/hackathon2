package fr.wcs.hackathon2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Singleton {

    private String[] mNames = new String[87];

    private String[] mImages = new String[87];



    public static Singleton sIntance = null;

    public static Singleton getsIntance() {
        if (sIntance == null) {
            sIntance = new Singleton();
        }
        return sIntance;
    }

    public Singleton() {
        loadArray();
    }

    public String[] getmNames() {
        return mNames;
    }

    public String[] getmImages() {
        return mImages;
    }

    public void loadArray() {

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDatabase.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int index = 0;
                for (DataSnapshot star : dataSnapshot.getChildren()) {
                    if(star.getKey().equals(Integer.toString(index))) {
                        String starName = star.child("name").getValue(String.class);
                        String starImage = star.child("image").getValue(String.class);
                        mNames[Integer.valueOf(star.getKey())] = starName;
                        mImages[Integer.valueOf(star.getKey())] = starImage;
                    }
                    index = index + 1;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
