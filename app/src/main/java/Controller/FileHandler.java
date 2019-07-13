package Controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

import static androidx.core.content.ContextCompat.startActivity;

public class FileHandler {

    FileOutputStream outputStream;

    public void WriteText2Internal(Context mContext, StringBuilder text, String fileName) {

        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File OpenInternalFile(Context mContext, String fileName) {
        return (new File(mContext.getFilesDir(),fileName));
    }

    public boolean ShareFileURI (Context mContext, String fileName) {
        File file = OpenInternalFile(mContext, fileName);

        if (file == null) return false;

        /*
        Intent intent = ShareCompat.IntentBuilder.from(mContext)
                .setStream(uri) // uri from FileProvider
                .setType("text/plain-text")
                .getIntent()
                .setAction(Intent.ACTION_VIEW) //Change if needed
                .setDataAndType(uri, "files/*")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
        */

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);

        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Last GPX created");

        shareIntent.setType("text/plain");

        /*
        Uri uri = FileProvider.getUriForFile(context,"com.package.name.fileprovider",
                file);
        */ // mContext.getPackageName()

        //todo remove hardcored autorithy string
        Uri uri = FileProvider.getUriForFile(mContext, "com.rogeriocarmo.gpx_logger.fileprovider" , file);

        shareIntent .putExtra(Intent.EXTRA_STREAM, uri);

        try {
            mContext.startActivity(Intent.createChooser(shareIntent , "Enviar arquivo GPX:").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
        catch(ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
