package com.wstro.app.common.utils;

import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;

/**
 * Utility methods for closing io streams and database cursors.
 */
public class CloseUtils {
    private CloseUtils() {
    }

    /**
     * If the argument is non-null, close the Closeable ignoring any {@link IOException}.
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignore.
            }
        }
    }

    /** If the argument is non-null, close the cursor. */
    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }
}