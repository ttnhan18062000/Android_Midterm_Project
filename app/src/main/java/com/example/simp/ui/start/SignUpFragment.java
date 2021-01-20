package com.example.simp.ui.start;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.simp.R;
import com.example.simp.utility.AccountInfoSingleton;
import com.example.simp.utility.NetworkSingleton;
import com.example.simp.utility.Volley.VolleyMultipartRequest;
import com.example.simp.utility.Volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView signup = view.findViewById(R.id.card_view_signup_commit);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = view.findViewById(R.id.edit_text_name);
                EditText username = view.findViewById(R.id.edit_text_username);
                EditText password = view.findViewById(R.id.edit_text_password);
                if(name.getText().toString().equals("") || username.getText().toString().equals("")||password.getText().toString().equals(""))

                    Toast.makeText(getContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                else
                    signUp(name.getText().toString(),username.getText().toString(),password.getText().toString());
            }
        });

        ImageButton back = view.findViewById(R.id.image_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionSignup_toMain);
            }
        });
    }

    private void signUp(String name, String username, String password) {
        String url = NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/signUp";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                String resultResponse = new String(response.data);
                if (resultResponse.equals("Sign up success")) {
                    Toast.makeText(getContext(), "Sign Up Successfully", Toast.LENGTH_LONG).show();
                    getView().startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                    Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionSignup_toMain);
                    Log.d("@@@", "Updated");
                }
                else if (resultResponse.equals(("Tai Khoan da ton tai")))
                {
                    Toast.makeText(getContext(),"Account already exists",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(),"Error uploading profile",Toast.LENGTH_LONG).show();
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
                params.put("name", name);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        //queue.add(stringRequest);
        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);
    }
}
