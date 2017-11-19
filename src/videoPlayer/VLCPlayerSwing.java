//TODO: tutorial for resizing: http://capricasoftware.co.uk/#/projects/vlcj/tutorial/direct-rendering
//TODO: add in actions: http://capricasoftware.co.uk/#/projects/vlcj/tutorial/basic-controls
package videoPlayer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

/**
 *  This class uses VLC to open up the file given as a string file path
 *  directly renders the video using swing
 */
public class VLCPlayerSwing {

    private static final int width = 600;

    private static final int height = 400;

    private final JFrame frame;

    private final JPanel videoSurface;

    private final BufferedImage image;

    private final DirectMediaPlayerComponent mediaPlayerComponent;

    public static void playFile(String filePath) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VLCPlayerSwing(filePath);
            }
        });
    }

    public VLCPlayerSwing(String filePath) {
        frame = new JFrame("Play Media");
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        videoSurface = new VideoSurfacePanel();
        frame.setContentPane(videoSurface);
        image = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleImage(width, height);
        BufferFormatCallback bufferFormatCallback = new BufferFormatCallback() {
            @Override
            public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
                return new RV32BufferFormat(width, height);
            }
        };
        mediaPlayerComponent = new DirectMediaPlayerComponent(bufferFormatCallback) {
            @Override
            protected RenderCallback onGetRenderCallback() {
                return new TutorialRenderCallbackAdapter();
            }
        };

        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia(filePath);
    }

    private class VideoSurfacePanel extends JPanel {

        private VideoSurfacePanel() {
            setBackground(Color.black);
            setOpaque(true);
            setPreferredSize(new Dimension(width, height));
            setMinimumSize(new Dimension(width, height));
            setMaximumSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(image, null, 0, 0);
        }
    }

    private class TutorialRenderCallbackAdapter extends RenderCallbackAdapter {

        private TutorialRenderCallbackAdapter() {
            super(new int[width * height]);
        }

        @Override
        protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
            // Simply copy buffer to the image and repaint
            image.setRGB(0, 0, width, height, rgbBuffer, 0, width);
            videoSurface.repaint();
        }
    }
}