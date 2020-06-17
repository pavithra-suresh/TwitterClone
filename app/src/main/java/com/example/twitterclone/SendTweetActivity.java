package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTweetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTweet;

    private ListView viewTweetsListView;
    private Button btnViewTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);

        edtTweet = findViewById(R.id.edtSendTweet);

        viewTweetsListView = findViewById(R.id.viewTweetsListView);
        btnViewTweets = findViewById(R.id.btnViewTweets);
        btnViewTweets.setOnClickListener(this);


    }

    public void sendTweet(View view) {

        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet", edtTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    FancyToast.makeText(SendTweetActivity.this, ParseUser.getCurrentUser().getUsername() + "'s tweet" + "(" + edtTweet.getText().toString() + ")" + " is saved!!!",
                            Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                } else {
                    FancyToast.makeText(SendTweetActivity.this, e.getMessage(), Toast.LENGTH_SHORT,
                            FancyToast.ERROR, true).show();
                }

                progressDialog.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {

        final ArrayList<HashMap<String, String>> tweetList = new ArrayList<>();
        final SimpleAdapter adapter = new SimpleAdapter(SendTweetActivity.this, tweetList, android.R.layout.simple_list_item_2, new String[]{"tweetUserName", "tweetValue"}, new int[]{android.R.id.text1, android.R.id.text2});
        try {
            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("MyTweet");
            parseQuery.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseObject tweetObject : objects) {
                            HashMap<String, String> userTweet = new HashMap<>();
                            userTweet.put("tweetUserName", tweetObject.getString("user"));
                            userTweet.put("tweetValue", tweetObject.getString("tweet"));
                            tweetList.add(userTweet);

                        }

                        viewTweetsListView.setAdapter(adapter);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}