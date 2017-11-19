package actions;

import videoPlayer.VLCPlayerSwing;

import java.util.Queue;

/**
 *  A queue is given into the class of episodes to play, it plays one after one another
 *  and will update the current episode in the series file
 */
public class Playback {


    public static void playSelectedQueue(Queue<String> playlist) {
        for(String file : playlist) {
            VLCPlayerSwing.playFile(file);
        }
    }


}
