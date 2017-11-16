package com.example.ivan.sharecompatexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * https://developer.android.com/reference/android/support/v4/app/ShareCompat.html
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share_action){
            //https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_MIME-%D1%82%D0%B8%D0%BF%D0%BE%D0%B2
            //https://www.niyati.com/blog/android-sharecompat/
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this).setChooserTitle("Titile")
                    .setType(mimeType)
                    .setText("share text")
                    .startChooser();
            return true;
        }
        if(item.getItemId() == R.id.map_action){
            //random example
            String addressString = "1600 Amphitheatre Parkway, CA";

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("geo")
                    .path("0,0")
                    .query(addressString);
            Uri addressUri = builder.build();

            openMap(addressUri);
        }
        if(item.getItemId() == R.id.web_action){
            openWebPage("https://ru.wikipedia.org");
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMap(Uri geoLocation){

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openWebPage(String urlString){
        Uri webpage = Uri.parse(urlString);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
