package guilherme.battlleship.other;

import android.database.Cursor;

public class score {

    private int id;
    private String playerName;
    private String difficulty;
    private int points;
    private int plays;
    private int ships_left;

    public score(
            int id,
            String playerName,
            String difficulty,
            int points,
            int plays,
            int ships_left
    ) {
        this.id = id;
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.points = points;
        this.plays = plays;
        this.ships_left = ships_left;
    }

    public static score fromCursor(Cursor res) {

        int id = res.getInt(res.getColumnIndex(database_connection.COLUMN1));
        String playerName = res.getString(res.getColumnIndex(database_connection.COLUMN2));
        String difficulty = res.getString(res.getColumnIndex(database_connection.COLUMN3));
        int points = res.getInt(res.getColumnIndex(database_connection.COLUMN4));
        int plays = res.getInt(res.getColumnIndex(database_connection.COLUMN5));
        int ships_left = res.getInt(res.getColumnIndex(database_connection.COLUMN6));
        return new score(id, playerName, difficulty, points, plays, ships_left);
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getPoints() {
        return points;
    }

    public int getPlays() {
        return plays;
    }

    public int getShips_left() {
        return ships_left;
    }

    @Override
    public String toString() {
        return "score{" +
                "id='" + id + '\'' +
                ", playerName='" + playerName + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", points=" + points +
                ", plays=" + plays +
                ", ships_left=" + ships_left +
                '}';
    }
}
