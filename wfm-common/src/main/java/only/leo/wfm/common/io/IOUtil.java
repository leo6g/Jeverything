package only.leo.wfm.common.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * This utility class contains input/output functions.
 */
public class IOUtil {

    /**
     * Copy all data from the input stream to the output stream and close the
     * input stream. Exceptions while closing are ignored.
     *
     * @param in the input stream
     * @param out the output stream (null if writing is not required)
     * @return the number of bytes copied
     */
    public static long copyAndCloseInput(InputStream in, OutputStream out)
            throws IOException {
        try {
            return copy(in, out);
        } catch (Exception e) {
            throw e;
        } finally {
            closeSilently(in);
        }
    }
    public static long copyAndCloseInput(InputStream in, OutputStream out,long length)
            throws IOException {
        try {
            return copy(in, out,length);
        } catch (Exception e) {
            throw e;
        } finally {
            closeSilently(in);
        }
    }

    /**
     * Copy all data from the input stream to the output stream. Both streams
     * are kept open.
     *
     * @param in the input stream
     * @param out the output stream (null if writing is not required)
     * @return the number of bytes copied
     */
    public static long copy(InputStream in, OutputStream out)
            throws IOException {
        return copy(in, out, Long.MAX_VALUE);
    }

    /**
     * Copy all data from the input stream to the output stream. Both streams
     * are kept open.
     *
     * @param in the input stream
     * @param out the output stream (null if writing is not required)
     * @param length the maximum number of bytes to copy
     * @return the number of bytes copied
     */
    public static long copy(InputStream in, OutputStream out, long length)
            throws IOException {
        try {
            long copied = 0;
            int len = 4096000;
            byte[] buffer = new byte[len];
            while (length > 0) {
                len = in.read(buffer, 0, len);
                if (len < 0) {
                    break;
                }
                if (out != null) {
                    out.write(buffer, 0, len);
                }
                copied += len;
                length -= len;
            }
            return copied;
        } catch (Exception e) {
            throw e;
        }
    }

    /*public static long copy(InputStream in, OutputStream out, long length)
            throws IOException {
        try {
            long copied = 0;
            int len = 4096000;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(len);
            ReadableByteChannel rbc = Channels.newChannel(in);
            WritableByteChannel wbc = Channels.newChannel(out);
            while (length > 0) {
                byteBuffer.compact();
                len = rbc.read(byteBuffer);
                if (len < 0) {
                    break;
                }
                if (wbc != null) {
                    byteBuffer.flip();
                    wbc.write(byteBuffer);
                }
                copied += len;
                length -= len;
            }
            return copied;
        } catch (Exception e) {
            throw e;
        }
    }*/


    /**
     * Close an input stream without throwing an exception.
     *
     * @param in the input stream or null
     */
    public static void closeSilently(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }



}

