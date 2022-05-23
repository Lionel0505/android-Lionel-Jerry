package com.example.zanakay;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JeuxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JeuxFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView c1,c2,egal,signe,message;
    Button validerjeux,refresh;
    EditText reponse;
    Jeux jeux;

    public JeuxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JeuxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JeuxFragment newInstance(String param1, String param2) {
        JeuxFragment fragment = new JeuxFragment();
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

    public Jeux jouer(TextView calcul, TextView egal, View rootView){

        Jeux j = Jeux.setJeux(1);

        calcul = rootView.findViewById(R.id.chiffre1);
        egal = rootView.findViewById(R.id.egal);

        calcul.setText(j.getChiffre1() +" " + j.getSigne() + " " + j.getChiffre2());
        egal.setText(j.egal+"");

        return j;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_jeux, container, false);

        validerjeux = rootView.findViewById(R.id.validation_jeux);
        refresh = rootView.findViewById(R.id.refresh);
        reponse = rootView.findViewById(R.id.reponse);

        message = rootView.findViewById(R.id.message);

        jeux = this.jouer(c1,egal,rootView);

        validerjeux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(reponse.getText())){
                    message.setText("Veuillez mettre une reponse.");
                    message.setTextColor(Color.rgb(255,0,0));
                }
                else {
                    int val = Integer.parseInt(String.valueOf(reponse.getText()));
                    if (val == jeux.getReponse()) {
                        message.setText("Bravo! C'est une bonne reponse.");
                        message.setTextColor(Color.rgb(0, 0, 255));
                    } else {
                        message.setText("Mauvaise reponse. Reessayez.");
                        message.setTextColor(Color.rgb(200, 0, 0));
                    }
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("");
                jeux = Jeux.setJeux(1);

                c1 = rootView.findViewById(R.id.chiffre1);
                egal = rootView.findViewById(R.id.egal);

                c1.setText(jeux.getChiffre1() +" " + jeux.getSigne() + " " + jeux.getChiffre2());
                egal.setText(jeux.egal+"");
                reponse.setText("");
            }
        });

        return rootView;
    }
}