package guilherme.battlleship.other;

import android.database.Cursor;

public class score {

    private int id;
    private String playerName;
    private String difficulty;
    private int points;
    private int plays;
    private int ships_left;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public int getShips_left() {
        return ships_left;
    }

    public void setShips_left(int ships_left) {
        this.ships_left = ships_left;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public score(
            int id,
            String playerName,
            String difficulty,
            int points,
            int plays,
            int ships_left,
            long time
    ) {
        this.id = id;
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.points = points;
        this.plays = plays;
        this.ships_left = ships_left;
        this.time = time;
    }

    public static score fromCursor(Cursor res) {

        int id = res.getInt(res.getColumnIndex(database_connection.COLUMN1));
        String playerName = res.getString(res.getColumnIndex(database_connection.COLUMN2));
        String difficulty = res.getString(res.getColumnIndex(database_connection.COLUMN3));
        int points = res.getInt(res.getColumnIndex(database_connection.COLUMN4));
        int plays = res.getInt(res.getColumnIndex(database_connection.COLUMN5));
        int ships_left = res.getInt(res.getColumnIndex(database_connection.COLUMN6));
        long time= res.getLong(res.getColumnIndex(database_connection.COLUMN7));
        return new score(id, playerName, difficulty, points, plays, ships_left, time);
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
                ", time=" + time +
                '}';
    }
}
