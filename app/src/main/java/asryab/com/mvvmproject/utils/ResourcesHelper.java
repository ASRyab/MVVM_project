package asryab.com.mvvmproject.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class ResourcesHelper {

    public static String getIntroAssetsDrawablePath(final int numberIntro) {
        return String.format("file:///android_asset/%s/%s.jpg", Arguments.File.FOLDER_INTRO, numberIntro);
    }

    private static File getCachingDir(final Context context) {
        return context.getCacheDir();
    }

    public static String getIntroFileDrawablePath(final Context context, final int numberIntro) {
        final File mountIntroFolder = getIntroFileDirectory(context);
        if (mountIntroFolder != null) {
            final File imageIntro = new File(mountIntroFolder, getIntroFileName(numberIntro));
            if (imageIntro.exists())
                return imageIntro.getPath();
        }

        return null;
    }

    public static File getIntroFileDirectory(final Context context) {
        final File mountIntroFolder = new File(getCachingDir(context), Arguments.File.FOLDER_INTRO);

        if (mountIntroFolder.exists())
            return mountIntroFolder;
        else if (mountIntroFolder.mkdir())
            return mountIntroFolder;

        return null;
    }

    public static String getIntroFileName(final int numberIntro) {
        return String.format("%s.jpg", numberIntro);
    }

    public static boolean saveUrlImageIntoFile(final String urlImage, final File fileDestination) throws IOException {
        final URL url = new URL(urlImage);
        final URLConnection connection = url.openConnection();
        final InputStream inStream = connection.getInputStream();
        try {
            if (!fileDestination.exists() && !fileDestination.createNewFile())
                return false;
            OutputStream output = new FileOutputStream(fileDestination);
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                int totalBytesSaved = 0;
                while ((bytesRead = inStream.read(buffer, 0, buffer.length)) >= 0) {
                    totalBytesSaved += bytesRead;
                    output.write(buffer, 0, bytesRead);
                }

                return (totalBytesSaved == connection.getContentLength());
            } finally {
                output.close();
            }
        } finally {
            inStream.close();
        }
    }

}
