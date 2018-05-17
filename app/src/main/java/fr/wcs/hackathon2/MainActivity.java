package fr.wcs.hackathon2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String mSide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //API
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://cdn.rawgit.com/akabab/superhero-api/0.2.0/api/all.json";

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for( int g=0; g<response.length();g++) {
                                JSONObject stars = (JSONObject) response.get(g);
                                int id = stars.getInt("id");
                                double height = stars.getDouble("height");
                                int mass = stars.getInt("mass");
                                String name = stars.getString("name");
                                String gender = stars.getString("gender");
                                String homeworld = stars.getString("homeworld");
                                String specie = stars.getString("species");
                                String image = stars.getString("image");
                                String haircolor = stars.getString("haircolor");
                                String eyecolor = stars.getString("eyecolor");
                                String skincolor = stars.getString("skincolor");
                                JSONObject affiliations = stars.getJSONObject("affiliations");
                                if (affiliations.has("Galactic Empire") || affiliations.has("Sith") || affiliations.has("Confederacy of Independent Systems")) {
                                    mSide = "The Dark Side of The Force";
                                }  else if (affiliations.has("Jedi Order") || affiliations.has("New Republic") || affiliations.has("Resistance")) {
                                    mSide = "The Light Side of The Force";
                                } else {
                                    mSide = "Neutre";
                                }
                                StarModel starHeroes = new StarModel(id, height, mass, name, gender, homeworld, specie, image, haircolor, eyecolor, skincolor, mSide);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );
        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonArrayRequest);
    }
}
