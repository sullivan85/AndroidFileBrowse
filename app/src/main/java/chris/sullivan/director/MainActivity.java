package chris.sullivan.director;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import chris.sullivan.director.adapters.FileAdapter;
import chris.sullivan.director.file.FileType;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

        final FileAdapter adapter = new FileAdapter( getBaseContext(), FileType.External );
        adapter.setCustomViewAdapter( new FileAdapter.CustomViewAdapter() {
            @Override
            public View getView(int i, File f, ViewGroup container) {
                View view = LayoutInflater.from( container.getContext() ).inflate( R.layout.adapter_custom_item, container, false );

                ImageView icon = (ImageView) view.findViewById(R.id.icon);
                TextView name = (TextView) view.findViewById(R.id.text);

                icon.setBackgroundColor( Color.BLACK );
                name.setText( f.getName() );

                return view;
            }
        });

        ListView list = (ListView) findViewById(R.id.files);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE );
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.select( position );
            }
        });
        list.setAdapter( adapter );
    }

}