package com.example.simp.ui.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.AcousticEchoCanceler;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.simp.MainActivity;
import com.example.simp.R;
import com.example.simp.utility.AccountInfoSingleton;
import com.example.simp.utility.NetworkSingleton;
import com.example.simp.utility.Volley.AppHelper;
import com.example.simp.utility.Volley.VolleyMultipartRequest;
import com.example.simp.utility.Volley.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Button mbutton;
    Button mUpdateButton;
    ImageView mAvatar;
    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText country;
    TextInputEditText intro;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        mbutton = v.findViewById(R.id.UpdateAvatarButton);
        mUpdateButton = v.findViewById(R.id.btn_update);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(name,email,country,intro);
                String url = NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/getAvatar?id=" + AccountInfoSingleton.getAccountInfoHolder().getUserID();
                Log.d("@@@", url);
                Picasso.get().load(url).into(((MainActivity)requireActivity()).menuIcon);
                ((MainActivity)requireActivity()).actionButton.postInvalidate();
                getView().postInvalidate();
            }
        });
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        name = v.findViewById(R.id.Name);
        email = v.findViewById(R.id.Email);
        country = v.findViewById(R.id.Where);
        intro = v.findViewById(R.id.Intro);

        mAvatar = (ImageView) v.findViewById(R.id.UpdateImageView);



        return v;
    }

    private void getInformation() {
        String url = NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/info?id=" + AccountInfoSingleton.getAccountInfoHolder().getUserID();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    name.setText(response.getString("name"));
                    email.setText(response.getString("email"));
                    country.setText(response.getString("country"));
                    intro.setText(response.getString("intro"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void update(TextInputEditText name, TextInputEditText email, TextInputEditText country, TextInputEditText intro){
        String sName = name.getText().toString();
        String sEmail = email.getText().toString();
        String sCountry = country.getText().toString();
        String sIntro =  intro.getText().toString();
        String url = null;

        url = NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/change";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");

                    if (status.equals("200")) {
                        // tell everybody you have succed upload image and post strings
                        SharedPreferences sh = getContext().getSharedPreferences("Info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("name",sName);
                        editor.putString("email", sEmail);
                        editor.putString("country", sCountry);
                        editor.putString("intro", sIntro);
                        editor.apply();
                        Toast.makeText(getContext(), "Successfully Update Profile", Toast.LENGTH_LONG).show();
                        Log.d("@@@", "Updated");
                    } else {
                        Toast.makeText(getContext(),"Error uploading profile",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", AccountInfoSingleton.getAccountInfoHolder().getUserID());
                params.put("name", sName);
                params.put("email", sEmail);
                params.put("country", sCountry);
                params.put("intro", sIntro);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView

                params.put("avatar",new DataPart(AccountInfoSingleton.getAccountInfoHolder().getUserID() + ".jpg", AppHelper.getFileDataFromDrawable(getContext(),mAvatar.getDrawable())));
                //params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));
                return params;
            }
        };

        //queue.add(stringRequest);
        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(targetUri));
                mAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //TODO: action
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getInformation();
        String url = NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/getAvatar?id=" + AccountInfoSingleton.getAccountInfoHolder().getUserID();
        Log.d("@@@", url);
        Picasso.get().load(url).into(mAvatar);
    }
}