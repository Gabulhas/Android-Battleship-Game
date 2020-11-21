package guilherme.battlleship.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import guilherme.battlleship.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link leaderboard_placeholder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class leaderboard_placeholder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "difficulty";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public leaderboard_placeholder() {
        // Required empty public constructor
    }

    public static leaderboard_placeholder newInstance(String difficulty) {
        leaderboard_placeholder fragment = new leaderboard_placeholder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, difficulty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard_placeholder, container, false);
    }
}