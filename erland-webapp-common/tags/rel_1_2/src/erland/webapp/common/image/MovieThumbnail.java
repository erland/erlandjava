package erland.webapp.common.image;

/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.util.Log;

import javax.imageio.ImageIO;
import javax.media.*;
import javax.media.control.TrackControl;
import javax.media.datasink.DataSinkErrorEvent;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.datasink.EndOfStreamEvent;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.*;
import javax.media.util.BufferToImage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

public class MovieThumbnail implements ThumbnailCreatorInterface, ControllerListener, DataSinkListener {
    private static Object sync = new Object();
    private Processor p;
    private Object waitSync = new Object();
    private Object waitEndSync = new Object();
    private boolean stateTransitionOK = true;
    private long noOfFrames;
    private BufferedImage thumbnail;
    private int width;
    private int height;
    private boolean bGenerateThumbnail;
    private int frameInterval;
    private int noOfColumns;
    private int noOfRows;

    /**
     * This DataSourceHandler class reads from a DataSource and display
     * information of each frame of data received.
     */
    class DataSourceHandler implements DataSink, BufferTransferHandler {
        DataSource source;
        PullBufferStream pullStrms[] = null;
        PushBufferStream pushStrms[] = null;

        // Data sink listeners.
        private Vector listeners = new Vector(1);

        // Stored all the streams that are not yet finished (i.e. EOM
        // has not been received.
        SourceStream unfinishedStrms[] = null;

        // Loop threads to pull data from a PullBufferDataSource.
        // There is one thread per each PullSourceStream.
        Loop loops[] = null;

        Buffer readBuffer;
        private int thumbnailWidth;
        private int smallThumbnailWidth;
        private int smallThumbnailHeight;
        private int thumbnailHeight;
        private int currentRow;
        private int currentColumn;

        /**
         * A thread class to implement a processing loop.
         * This loop reads data from a PullBufferDataSource.
         */
        class Loop extends Thread {

            DataSourceHandler handler;
            PullBufferStream stream;
            boolean paused = true;
            boolean killed = false;

            public Loop(DataSourceHandler handler, PullBufferStream stream) {
                this.handler = handler;
                this.stream = stream;
                start();
            }

            public synchronized void restart() {
                paused = false;
                notify();
            }

            /**
             * This is the correct way to pause a thread; unlike suspend.
             */
            public synchronized void pause() {
                paused = true;
            }

            /**
             * This is the correct way to kill a thread; unlike stop.
             */
            public synchronized void kill() {
                killed = true;
                notify();
            }

            /**
             * This is the processing loop to pull data from a
             * PullBufferDataSource.
             */
            public void run() {
                while (!killed) {
                    try {
                        while (paused && !killed) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                    }

                    if (!killed) {
                        boolean done = handler.readPullData(stream);
                        if (done)
                            pause();
                    }
                }
            }
        }

        /**
         * Sets the media source this <code>MediaHandler</code>
         * should use to obtain content.
         */
        public void setSource(DataSource source) throws IncompatibleSourceException {

            // Different types of DataSources need to handled differently.
            if (source instanceof PushBufferDataSource) {

                pushStrms = ((PushBufferDataSource) source).getStreams();
                unfinishedStrms = new SourceStream[pushStrms.length];

                // Set the transfer handler to receive pushed data from
                // the push DataSource.
                for (int i = 0; i < pushStrms.length; i++) {
                    pushStrms[i].setTransferHandler(this);
                    unfinishedStrms[i] = pushStrms[i];
                }


            } else if (source instanceof PullBufferDataSource) {

                pullStrms = ((PullBufferDataSource) source).getStreams();
                unfinishedStrms = new SourceStream[pullStrms.length];

                // For pull data sources, we'll start a thread per
                // stream to pull data from the source.
                loops = new Loop[pullStrms.length];
                for (int i = 0; i < pullStrms.length; i++) {
                    loops[i] = new Loop(this, pullStrms[i]);
                    unfinishedStrms[i] = pullStrms[i];
                }

            } else {

                // This handler only handles push or pull buffer datasource.
                throw new IncompatibleSourceException();

            }

            this.source = source;
            readBuffer = new Buffer();
        }


        /**
         * For completeness, DataSink's require this method.
         * But we don't need it.
         */
        public void setOutputLocator(MediaLocator ml) {
        }


        public MediaLocator getOutputLocator() {
            return null;
        }


        public String getContentType() {
            return source.getContentType();
        }


        /**
         * Our DataSink does not need to be opened.
         */
        public void open() {
        }


        public void start() {
            try {
                source.start();
            } catch (IOException e) {
                System.err.println(e);
            }

            // Start the processing loop if we are dealing with a
            // PullBufferDataSource.
            if (loops != null) {
                for (int i = 0; i < loops.length; i++)
                    loops[i].restart();
            }
        }


        public void stop() {
            try {
                source.stop();
            } catch (IOException e) {
                System.err.println(e);
            }

            // Start the processing loop if we are dealing with a
            // PullBufferDataSource.
            if (loops != null) {
                for (int i = 0; i < loops.length; i++)
                    loops[i].pause();
            }
        }


        public void close() {
            stop();
            if (loops != null) {
                for (int i = 0; i < loops.length; i++)
                    loops[i].kill();
            }
        }


        public void addDataSinkListener(DataSinkListener dsl) {
            if (dsl != null)
                if (!listeners.contains(dsl))
                    listeners.addElement(dsl);
        }


        public void removeDataSinkListener(DataSinkListener dsl) {
            if (dsl != null)
                listeners.removeElement(dsl);
        }


        protected void sendEvent(DataSinkEvent event) {
            if (!listeners.isEmpty()) {
                synchronized (listeners) {
                    Enumeration list = listeners.elements();
                    while (list.hasMoreElements()) {
                        DataSinkListener listener =
                                (DataSinkListener) list.nextElement();
                        listener.dataSinkUpdate(event);
                    }
                }
            }
        }


        /**
         * This will get called when there's data pushed from the
         * PushBufferDataSource.
         */
        public void transferData(PushBufferStream stream) {

            try {
                stream.read(readBuffer);
            } catch (IOException e) {
                System.err.println(e);
                sendEvent(new DataSinkErrorEvent(this, e.getMessage()));
                return;
            }

            //printDataInfo(readBuffer);
            if (!readBuffer.isEOM()) {
                if (bGenerateThumbnail && (readBuffer.getSequenceNumber() - 1) % frameInterval == 0) {
                    addToJPEG(readBuffer);
                }
            } else {
                noOfFrames = readBuffer.getSequenceNumber();
            }

            // Check to see if we are done with all the streams.
            if (readBuffer.isEOM() && checkDone(stream)) {
                sendEvent(new EndOfStreamEvent(this));
            }
        }


        /**
         * This is called from the Loop thread to pull data from
         * the PullBufferStream.
         */
        public boolean readPullData(PullBufferStream stream) {
            try {
                stream.read(readBuffer);
            } catch (IOException e) {
                System.err.println(e);
                return true;
            }

            //printDataInfo(readBuffer);

            //writeJPEG(readBuffer);
            if (readBuffer.isEOM()) {
                // Check to see if we are done with all the streams.
                if (checkDone(stream)) {
                    System.err.println("All done!");
                    close();
                }
                return true;
            }
            return false;
        }


        /**
         * Check to see if all the streams are processed.
         */
        public boolean checkDone(SourceStream strm) {
            boolean done = true;

            for (int i = 0; i < unfinishedStrms.length; i++) {
                if (strm == unfinishedStrms[i])
                    unfinishedStrms[i] = null;
                else if (unfinishedStrms[i] != null) {
                    // There's at least one stream that's not done.
                    done = false;
                }
            }
            return done;
        }


        void printDataInfo(Buffer buffer) {
            //System.err.println("Read from stream: " + stream);
            if (buffer.getFormat() instanceof AudioFormat)
                System.err.println("Read audio data:");
            else
                System.err.println("Read video data:");
            System.err.println("  Time stamp: " + buffer.getTimeStamp());
            System.err.println("  Sequence #: " + buffer.getSequenceNumber());
            System.err.println("  Data length: " + buffer.getLength());

            if (buffer.isEOM())
                System.err.println("  Got EOM!");
        }

        public Object[] getControls() {
            return new Object[0];
        }

        public Object getControl(String name) {
            return null;
        }

        void addToJPEG(Buffer buffer) {
            BufferToImage stopBuffer = new BufferToImage((VideoFormat) buffer.getFormat());
            Image stopImage = stopBuffer.createImage(buffer);
            if (stopImage != null) {
                if (thumbnail == null) {
                    currentRow = 0;
                    currentColumn = 0;
                    if (stopImage.getHeight(null) > stopImage.getWidth(null) * (height * noOfRows) / (width * noOfColumns)) {
                        thumbnailWidth = height * stopImage.getWidth(null) * noOfColumns / (stopImage.getHeight(null) * noOfRows);
                        thumbnailHeight = height;
                    } else {
                        thumbnailWidth = width;
                        thumbnailHeight = width * stopImage.getHeight(null) / stopImage.getWidth(null);
                    }
                    smallThumbnailWidth = thumbnailWidth / noOfColumns;
                    smallThumbnailHeight = thumbnailHeight / noOfRows;

                    if (stopImage.getWidth(null) < smallThumbnailWidth) {
                        smallThumbnailWidth = stopImage.getWidth(null);
                        thumbnailWidth = smallThumbnailWidth * noOfColumns;
                    }
                    if (stopImage.getHeight(null) < smallThumbnailHeight) {
                        smallThumbnailHeight = stopImage.getHeight(null);
                        thumbnailHeight = smallThumbnailHeight * noOfRows;
                    }
                    thumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_RGB);
                } else {
                    currentColumn++;
                    while (currentColumn >= noOfColumns) {
                        currentColumn -= noOfColumns;
                        currentRow++;
                    }
                }
                Graphics g = thumbnail.getGraphics();
                g.drawImage(stopImage, smallThumbnailWidth * currentColumn, smallThumbnailHeight * currentRow, smallThumbnailWidth * currentColumn + smallThumbnailWidth, smallThumbnailHeight * currentRow + smallThumbnailHeight, 0, 0, stopImage.getWidth(null), stopImage.getHeight(null), null);
                g.dispose();
            }
        }
    }

    public MovieThumbnail(int noOfCols, int noOfRows) {
        this.noOfColumns = noOfCols;
        this.noOfRows = noOfRows;
    }

    public BufferedImage create(URL movie, int width) {
        synchronized (sync) {
            MediaLocator ml;
            ml = new MediaLocator(movie);
            if (movie == null) {
                return null;
            }
            DataSource ds = null;

            try {
                ds = Manager.createDataSource(ml);
                long noOfFrames = getNumberOfFrames(ds);
                ds = Manager.createDataSource(ml);
                return createThumbnail(ds, (int) (noOfFrames / (noOfColumns * noOfRows)), noOfColumns, noOfRows, width);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private long getNumberOfFrames(DataSource ds) {
        noOfFrames = 0;
        bGenerateThumbnail = false;
        init(ds);
        return noOfFrames;
    }

    private BufferedImage createThumbnail(DataSource ds, int frameInterval, int noOfColumns, int noOfRows, int width) {
        noOfFrames = 0;
        thumbnail = null;
        bGenerateThumbnail = true;
        this.frameInterval = frameInterval;
        this.width = width;
        this.height = width * 1200 * noOfRows / (1600 * noOfColumns);
        init(ds);
        return thumbnail;
    }

    private boolean init(DataSource ds) {
        Log.println(this, "Opening movie: " + ds.getContentType());

        try {
            p = Manager.createProcessor(ds);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return false;
        } catch (NoProcessorException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            return false;
        }

        p.addControllerListener(this);

        p.configure();
        if (!waitForState(Processor.Configured)) {
            System.err.println("Failed to configure the processor.");
            return false;
        }

        // Get the raw output from the processor.
        p.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW));

        // Change to RGB Video format output and disable audio tracks
        TrackControl tc[] = p.getTrackControls();
        for (int i = 0; i < tc.length; i++) {
            if (tc[i].getFormat() instanceof VideoFormat)
                tc[i].setFormat(new VideoFormat(VideoFormat.RGB));
            else
                tc[i].setEnabled(false);
        }

        p.realize();
        if (!waitForState(Processor.Realized)) {
            System.err.println("Failed to realize the processor.");
            return false;
        }

        // Get the output DataSource from the processor and
        // hook it up to the DataSourceHandler.
        DataSource ods = p.getDataOutput();
        DataSourceHandler handler = new DataSourceHandler();

        try {
            handler.setSource(ods);
        } catch (IncompatibleSourceException e) {
            System.err.println("Cannot handle the output DataSource from the processor: " + ods);
            return false;
        }

        handler.addDataSinkListener(this);
        handler.start();

        // Prefetch the processor.
        p.prefetch();
        if (!waitForState(Processor.Prefetched)) {
            System.err.println("Failed to prefetch the processor.");
            return false;
        }

        // Start the processor.
        p.start();
        try {
            synchronized (waitEndSync) {
                waitEndSync.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Block until the processor has transitioned to the given state.
     * Return false if the transition failed.
     */
    boolean waitForState(int state) {
        synchronized (waitSync) {
            try {
                while (p.getState() < state && stateTransitionOK)
                    waitSync.wait();
            } catch (Exception e) {
            }
        }
        return stateTransitionOK;
    }

    /**
     * Change the plugin list to disable the default RawBufferMux
     * thus allowing the RawSyncBufferMux to be used.
     * This is a handy trick.  You wouldn't know this, would you? :)
     */
    void enableSyncMux() {
        Vector muxes = PlugInManager.getPlugInList(null, null,
                PlugInManager.MULTIPLEXER);
        for (int i = 0; i < muxes.size(); i++) {
            String cname = (String) muxes.elementAt(i);
            if (cname.equals("com.sun.media.multiplexer.RawBufferMux")) {
                muxes.removeElementAt(i);
                break;
            }
        }
        PlugInManager.setPlugInList(muxes, PlugInManager.MULTIPLEXER);
    }


    /**
     * Controller Listener.
     */
    public void controllerUpdate(ControllerEvent evt) {

        if (evt instanceof ConfigureCompleteEvent ||
                evt instanceof RealizeCompleteEvent ||
                evt instanceof PrefetchCompleteEvent) {
            synchronized (waitSync) {
                stateTransitionOK = true;
                waitSync.notifyAll();
            }
        } else if (evt instanceof ResourceUnavailableEvent) {
            synchronized (waitSync) {
                stateTransitionOK = false;
                waitSync.notifyAll();
            }
        } else if (evt instanceof EndOfMediaEvent) {
            p.close();
            synchronized (waitEndSync) {
                waitEndSync.notifyAll();
            }
        } else if (evt instanceof SizeChangeEvent) {
        }
    }


    /**
     * DataSink Listener
     */
    public void dataSinkUpdate(DataSinkEvent evt) {

        if (evt instanceof EndOfStreamEvent) {
            evt.getSourceDataSink().close();
        }
    }

    public static void main(String[] args) {
        try {
            URL url = null;
            if (args.length < 1) {
                System.out.println("No movie as argument");
                return;
            } else {
                url = new File(args[0]).toURL();
            }
            MovieThumbnail me = new MovieThumbnail(4, 2);
            BufferedImage image = me.create(url, 600);
            if (image != null) {
                ImageIO.write(image, "JPEG", new File("C:\\temp\\test\\test.jpg"));
            } else {
                System.out.println("No image generated");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
}