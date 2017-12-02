package bbitb.com.donateplus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomesList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomesList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomesList extends ListFragment {


    //creating an instance of the list view
    private ListView mlistview;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomesList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomesList.
     */
    // TODO: Rename and change types and number of parameters
    public static HomesList newInstance(String param1, String param2) {
        HomesList fragment = new HomesList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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

        View view = inflater.inflate(R.layout.fragment_homes_list, container, false);
        mlistview = (ListView) view.findViewById(android.R.id.list);

        //1
        final ArrayList<ChildrensHomes> homeList = ChildrensHomes.getChildrensHomesFromFile("homes.json", getContext());


        mlistview.setAdapter(new HomeAdapter(getContext(), homeList));
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ChildrensHomes selectedHome = homeList.get(position);

                Intent detailIntent = new Intent(getContext(), HomeProfile.class);

                detailIntent.putExtra("title", selectedHome.title);
                detailIntent.putExtra("url", selectedHome.instructionUrl);

                startActivity(detailIntent);
            }
        });

        return view;
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_homes_list, container, false);

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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


    public String title;
    public String description;
    public String imageUrl;
    public String instructionUrl;
    public String label;

    public static ArrayList<HomesList> getHomesFromFile(String filename, Context context){
        final ArrayList<HomesList> homesList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("homes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray homes = json.getJSONArray("homes");

            // Get Recipe objects from data
            for(int i = 0; i < homes.length(); i++){
                HomesList home = new HomesList();

                home.title = homes.getJSONObject(i).getString("title");
                home.description = homes.getJSONObject(i).getString("description");
                home.imageUrl = homes.getJSONObject(i).getString("image");
                home.instructionUrl = homes.getJSONObject(i).getString("url");
                home.label = homes.getJSONObject(i).getString("dietLabel");

                homesList.add(home);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return homesList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}


