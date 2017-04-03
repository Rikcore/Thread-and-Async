package bugbusterzcorp.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView iV = (ImageView)findViewById(R.id.iV);

       /* Thread nvThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap monImage = loadImageFromNetwork(URL);
                iV.post(new Runnable() {
                    @Override
                    public void run() {
                        iV.setImageBitmap(monImage);
                    }
                });

            }
        });
        nvThread.start();*/

        new ImageDownloadTask(iV).execute(URL);
    }


    private static final String URL = "https://s-media-cache-ak0.pinimg.com/736x/eb/00/c5/eb00c55fef4b63c2e1542ace9e4faab5.jpg";

    public Bitmap loadImageFromNetwork(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect(); InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        catch (IOException e) {
            return null; }
    }




    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView iV;

        public ImageDownloadTask(ImageView iv) {
            iV = iv;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImageFromNetwork(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iV.setImageBitmap(bitmap);
        }
    }
}
