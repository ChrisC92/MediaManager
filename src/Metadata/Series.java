package Metadata;


/**
 * The series class stores each series as a separate object - holds information on the season and current episode
 */

// TODO: may need to hold information on all episodes, undecided
public class Series {
    private final String season;
    private String currentEp;

    public Series(String season, String currentEpisode) {
        this.season = season;
        currentEp = currentEpisode;
    }

    //TODO: might be worth just making instance variables public
    public String getSeasonName() {
        return season;
    }

    public String currentEp() {
        return currentEp;
    }

    public void changeCurrentEp(String newCurrent) {
        currentEp = newCurrent;
    }


    @Override
    public String toString() {
        return season + "\nCurrent Episode: " + currentEp;
    }
}
