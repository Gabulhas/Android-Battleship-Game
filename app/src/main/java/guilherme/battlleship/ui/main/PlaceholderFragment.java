package guilherme.battlleship.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import guilherme.battlleship.R;
import guilherme.battlleship.other.database_connection;
import guilherme.battlleship.other.score;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_NAME = "section_name";

    private database_connection db;
    private PageViewModel pageViewModel;
    private String difficulty;

    public static PlaceholderFragment newInstance(int index, String difficulty) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_NAME, difficulty);
        Log.d("FRAGINSTANCE", "Diff " + difficulty);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        index = getArguments().getInt(ARG_SECTION_NUMBER);
        difficulty = getArguments().getString(ARG_SECTION_NAME);
        Log.d("FRAGCREATE", "Diff " + difficulty);

        pageViewModel.setIndex(index);


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        //String difficulty = getArguments().getString(ARG_SECTION_NAME);
        ListView myListView = (ListView) root.findViewById(R.id.top_10_diff);
        db = new database_connection(root.getContext());

        ArrayList<score> scores = db.getTopTen(this.difficulty);

        Log.d("SCORES", "onCreateView: " + scores.toString());

        leaderboard_adapter adapter = new leaderboard_adapter(root.getContext(), scores);

        myListView.setAdapter(adapter);
        myListView.setEnabled(true);

        return root;
    }
}