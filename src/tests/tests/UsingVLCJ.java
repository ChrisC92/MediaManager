/**
 *  Using the tutorial in http://capricasoftware.co.uk/#/projects/vlcj/tutorial/my-first-media-player
 *  to test using VLC initially before making the class videoPlayer to be used
 */

package tests;

import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

import javax.swing.*;

public class UsingVLCJ {

    private final JFrame frame;

    private final EmbeddedMediaListPlayerComponent mediaListPlayerComponent;

    public static void main(String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UsingVLCJ();
            }
        });
    }

    public UsingVLCJ() {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mediaListPlayerComponent = new EmbeddedMediaListPlayerComponent();
        frame.setContentPane(mediaListPlayerComponent);
        frame.setVisible(true);
        String filePath = "/Users/ChrisCorner/Documents/Films_Series/Series/Adventure Time - Season 7/S07E01-Bonnie & Neddy.mp4";
        mediaListPlayerComponent.getMediaPlayer().playMedia(filePath);
    }
}
