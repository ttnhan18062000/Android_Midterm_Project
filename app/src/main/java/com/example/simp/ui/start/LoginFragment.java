package com.example.simp.ui.start;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.compose.ui.gesture.LongPressGestureFilter;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.simp.MainActivity;
import com.example.simp.R;
import com.example.simp.utility.AccountInfoSingleton;
import com.example.simp.utility.NetworkSingleton;
import com.example.simp.utility.Volley.VolleyMultipartRequest;
import com.example.simp.utility.Volley.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    EditText loginUsername;
    EditText loginPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView cardView = view.findViewById(R.id.card_view_login);
        loginUsername = getActivity().findViewById(R.id.edit_text_username);
        loginPassword = getActivity().findViewById(R.id.edit_text_password);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                String url = NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/getLoginToken";
                VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        String resultResponse = new String(response.data);
                        try {
                            JSONObject result = new JSONObject(resultResponse);
                            String status = result.getString("status");

                            if (status.equals("success")) {
                                // tell everybody you have succed upload image and post strings
                                Toast.makeText(getContext(),"Login sucessfully", Toast.LENGTH_LONG).show();
                                String message = result.getString("message");
                                AccountInfoSingleton.getAccountInfoHolder().setupInfo(message);
                                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionLogin_toCamera);
                                MainActivity mainActivity = (MainActivity)requireActivity();
                                mainActivity.turnActionButton(true);
                                Picasso.get().load(NetworkSingleton.getNetworkInfoHolder().getSERVER() + "/getAvatar?id=" + AccountInfoSingleton.getAccountInfoHolder().getUserID()).into(mainActivity.menuIcon);

                                mainActivity.setLoginStatus(true);
                            } else if (status.equals("fail")){
                                Toast.makeText(getContext(),"Username or password is incorrect",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
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
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                        params.put("username", loginUsername.getText().toString());
                        params.put("password", loginPassword.getText().toString());
                        return params;
                    }
                };
                VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);
            }
        });

        TextView textview = view.findViewById(R.id.text_view_without_login);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionLogin_toCamera);
                MainActivity mainActivity = (MainActivity)requireActivity();
                mainActivity.turnActionButton(true);
                mainActivity.setLoginStatus(true);
            }
        });

        ImageButton back = view.findViewById(R.id.image_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_button_1));
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.actionLogin_toMain);
            }
        });
    }
}
