package Controller;

import android.content.Context;

import java.io.FileOutputStream;

public class FileHandler {

    FileOutputStream outputStream;

    public void WriteText2Interal(Context mContext, StringBuilder text, String fileName) {

        try {
            outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
