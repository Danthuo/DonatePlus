package bbitb.com.donateplus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyDonations.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyDonations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDonations extends Fragment {

    private static String DONATIONS;
    ListView listview;
    List<donation> donationList;
    private FirebaseAuth firebaseAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyDonations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDonations.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDonations newInstance(String param1, String param2) {
        MyDonations fragment = new MyDonations();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("My Donations");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_donations, container, false);

        listview = (ListView) view.findViewById(R.id.donationListView);
        donationList = new ArrayList<>();

        loadDonationList(view);

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadDonationList(View view){
        final RelativeLayout progressBar = (RelativeLayout) view.findViewById(R.id.progressBar);
        final RelativeLayout noDonationsMessage = (RelativeLayout) view.findViewById(R.id.noDonationsMessage);
        progressBar.setVisibility(View.VISIBLE);
        DONATIONS = getUrl();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, DONATIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            JSONObject JSONdonation = new JSONObject(response);
                            JSONArray donationArray = JSONdonation.getJSONArray("donations");

                            for (int i = 0; i < donationArray.length(); i++) {
                                JSONObject donationOBJ = donationArray.getJSONObject(i);

                                donation donation = new donation(
                                        donationOBJ.getInt("donationID"),
                                        donationOBJ.getString("profileName"),
                                        donationOBJ.getString("donationType"),
                                        donationOBJ.getInt("donationAmount"),
                                        donationOBJ.getString("dropOffLoc"),
                                        donationOBJ.getString("donationStatus"));

                                donationList.add(donation);
                            }
                            DonationListAdapter adapter = new DonationListAdapter(donationList, getContext());
                            listview.setAdapter(adapter);
                        } catch (JSONException e) {
                            noDonationsMessage.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public String getUrl() {
        firebaseAuth = FirebaseAuth.getInstance();
        String userURL = "";

        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity here if user is not logged in
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));
        }else{
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String displayEmail = user.getEmail();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("avrajsingh.000webhostapp.com")
                    .appendPath("donateplus")
                    .appendPath("donationlist_android.php")
                    .appendQueryParameter("user", displayEmail);
            String myUrl = builder.build().toString();
            userURL = myUrl;
        }
        return userURL;
    }
}
