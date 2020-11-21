package guilherme.battlleship.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import guilherme.battlleship.R;
import guilherme.battlleship.other.score;

public class leaderboard_adapter extends BaseAdapter {

    private Context mContext;
    private List<score> items;
    private static LayoutInflater inflater = null;

    public leaderboard_adapter(Context context, List<score> items) {

        this.items = items;
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {

        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.leaderboard_row, parent, false);

        score sc = items.get(position);
        TextView player = vi.findViewById(R.id.PLAYER);
        TextView stats = vi.findViewById(R.id.STATS);
        TextView time = vi.findViewById(R.id.TIME);

        player.setText(sc.getPlayerName());
        String stats_string = String.format("%s: %d\n%s: %d\n%s: %d\n%s: %d%s", mContext.getString(R.string.stats_time), sc.getTime(), mContext.getString(R.string.stats_plays_string), sc.getPlays(), mContext.getString(R.string.stas_ships_left_string), sc.getShips_left(), mContext.getString(R.string.stats_hit_rate), ((17 * 100) / sc.getPlays()), "%");

        stats.setText(stats_string);
        time.setText(sc.getPoints() + " " + mContext.getString(R.string.points_string));
        return vi;
    }
}
