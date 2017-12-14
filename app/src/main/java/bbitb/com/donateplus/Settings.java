package bbitb.com.donateplus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Settings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;

    private DatabaseReference databaseReference;


    private EditText editTextName;
    private Button buttonSave;

    private TextView textViewUserEmail;
    private TextView textViewName;

    ProgressBar progressBar;







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Settings");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false);


        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        //This can also be put in an onStart method
        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity here if user is not logged in
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));

        }else {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String displayEmail = user.getEmail();

            //View
            textViewUserEmail = (TextView) view.findViewById(R.id.textViewUserEmail);

            //Set Email
            textViewUserEmail.setText(displayEmail);
        }


        textViewName = (TextView) view.findViewById(R.id.textViewName);
        editTextName = (EditText) view.findViewById(R.id.editTextDisplayName);
        buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout);

        progressBar = view.findViewById(R.id.progress_bar);

        loadUserInformation();

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        return view;
    }


    private void saveUserInformation(){

        String displayName = editTextName.getText().toString().trim();

        if(displayName.isEmpty()){
            editTextName.setError("Name Required");
            editTextName.requestFocus();
            return;
        }


        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
        }

    }

    public void loadUserInformation(){

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            if (user.getDisplayName() != null) {
                textViewName.setText(user.getDisplayName());
                editTextName.setText(user.getDisplayName());

            }else {
                textViewName.setText("Donate+ User");
                editTextName.setText(" ");
            }
        }

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


    @Override
    public void onClick(View v) {
        if(v == buttonLogout){
            firebaseAuth.signOut();
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));
        }


        if(v == buttonSave){
            saveUserInformation();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


}
