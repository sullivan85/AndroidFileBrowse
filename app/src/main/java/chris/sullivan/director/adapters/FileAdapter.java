package chris.sullivan.director.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import chris.sullivan.director.R;
import chris.sullivan.director.file.FileUtil;

/**
 * Created by chris on 5/10/15.
 */
public class FileAdapter extends BaseAdapter {

    private Context context;
    private FileUtil util;
    private File start, current, parent;

    private ArrayList<File> files;

    private boolean showHidden = true;

    public FileAdapter( Context context, int fileType )
    {
        this.context = context;
        util = FileUtil.getInstance( context, fileType );
        start = util.getStartDir();
        files = new ArrayList<>();

        files.add( start );
        for( int i = 0; i < start.listFiles().length; i++)
        {
            files.add( start.listFiles()[i] );
        }
    }

    public void showHidden( boolean show ) { showHidden = show; }

    public File select( int pos )
    {
        File f = (File) getItem( pos );

        // if just a normal file return it
        if( f.isFile() ) return f;

        current = f;
        files = new ArrayList<>();
        files.add( start );
        if( current.getName().equals( start.getName() ) )
        {
            addChildren( start );
        }
        else if( parent != null && current == parent )
        {
            parent = parent.getParentFile();
            if( parent.getName().equals( start.getName() ) )
            {
                parent = null;
                addChildren( start );
            }
            else
            {
                files.add(parent);
                addChildren(parent);
            }
        }
        else if( current.isDirectory() )
        {
            parent = current;
            files.add( parent );
            addChildren( parent );
        }

        notifyDataSetChanged();
        return current;
    }

    private void addChildren( File dir )
    {
        for( File f : dir.listFiles() )
        {
            if( !showHidden && f.isHidden() )
            {
                files.add(f);
            }
            else
            {
                files.add(f);
            }
        }
    }

    @Override
    public int getCount() { return files.size(); }

    @Override
    public Object getItem(int i) { return files.get( i ); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup container ) {

        File f = (File) getItem(i);
        if( adapter != null )
        {
            return adapter.getView( i, f, container );
        }
        else {
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_file_item, container, false);

            TextView name = (TextView) view.findViewById(R.id.filename);

            if (f == start) {
                name.setText(".");
            } else if (parent != null && f == parent) {
                name.setText("..");
            } else {
                name.setText(f.getName());
            }

            return view;
        }
    }

    private CustomViewAdapter adapter;
    public interface CustomViewAdapter
    {
        public View getView( int i, File f, ViewGroup container );
    }

    public void setCustomViewAdapter( CustomViewAdapter ca )
    {
        adapter = ca;
        notifyDataSetChanged();
    }

}
