package com.lifereze.tweeps.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lifereze.tweeps.R;
import com.lifereze.tweeps.adapters.CustomArrayAdapter;

import butterknife.Bind;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.categoriesListView) ListView mListView;
    @BindView(R.id.headerTextView) TextView mTextView;

    private String[] categories = new String[] {"Sports","Celebrities","Politics","Tech","Music","Food","Fashion","Film","Business","Gossip","Trump","Conference","News","Gaming"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Typeface titleFont = Typeface.createFromAsset(getAssets(),"fonts/JOURNAL.TTF");
        mTextView.setTypeface(titleFont);

        mListView.setAdapter(new CustomArrayAdapter(this,categories));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView)view.findViewById(R.id.tweetsCategoryTextView);
                String categoryname = textView.getText().toString();
                Toast.makeText(MainActivity.this,"Viewing "+categoryname,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SelectedCategoryActivity.class);
                intent.putExtra("categoryname",categoryname);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}