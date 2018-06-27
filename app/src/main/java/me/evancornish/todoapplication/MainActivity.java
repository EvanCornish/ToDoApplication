package me.evancornish.todoapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import java.util.*;
import android.view.*;
import android.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.nio.charset.*;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems=(ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }
    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener
        (
            new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
                {
                    items.remove(position);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                    Log.i("MainActivity","Removed item "+position);
                    return true;
                }
            }
        );
    }
    private File getDataFile()
    {
        return new File(getFilesDir(),"todo.txt");
    }
    private void readItems()
    {
        try
        {
            items=new ArrayList<String>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            items=new ArrayList<>();
        }
    }
    private void writeItems()
    {
        try
        {
            FileUtils.writeLines(getDataFile(),items);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void onAddItem(View v)
    {
        EditText etNewItem=(EditText) findViewById(R.id.etNewItem);
        String itemText=etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        writeItems();
        etNewItem.setText("");
        Toast.makeText(getApplicationContext(),"Item added to list",Toast.LENGTH_SHORT).show();
    }
}
