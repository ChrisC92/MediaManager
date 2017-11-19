/**
 *  Using the tutorial in http://capricasoftware.co.uk/#/projects/vlcj/tutorial/my-first-media-player
 *  to test using VLC initially before making the class videoPlayer to be used
 */

package tests;

import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import videoPlayer.VLCPlayerSwing;

import javax.swing.*;

public class VLCJInitialPlay {

    public static void usingSwing() {
        VLCPlayerSwing.playFile("/Users/ChrisCorner/Documents/Films_Series/Series/Dragonball ALL EPISODES/006 - Keep An Eye On The Dragon Balls.rmvb");
    }

    public static void usingFX() {

    }

    public static void main(String[] args) {
        usingSwing();
    }
}
