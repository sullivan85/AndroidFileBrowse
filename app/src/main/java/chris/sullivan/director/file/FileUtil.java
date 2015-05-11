package chris.sullivan.director.file;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chris on 5/10/15.
 */
public class FileUtil  {

    private static Context mContext;
    private static FileType type;

    public static FileUtil getInstance( Context context, int fileType )
    {
        mContext = context;
        type = new FileType( context, fileType );
        if( util == null )
        {
            util = new FileUtil();
        }

        return util;
    }

    private static FileUtil util;
    private FileUtil()
    {

    }

    public File getStartDir() { return type.getFile(); }

    public boolean clear()
    {
        return clear( type.getFile() );
    }

    private boolean clear( File file )
    {
        if( file.isDirectory() )
        {
            File[] children = file.listFiles();

            // delete recursive children first
            for( File child : children )
            {
                clear(child);
            }

            // delete parent
            return file.delete();
        }
        else
        {
            // delete regular file
            return file.delete();
        }
    }

    public boolean contains( String name )
    {
        return contains( type.getFile(), name );
    }

    private boolean contains( File file, String name )
    {
        if( file.isDirectory() )
        {
            File[] children = file.listFiles();
            for( File child : children )
            {
                if( contains( child, name ) )
                {
                    return true;
                }
            }
        }

        return file.getName().equalsIgnoreCase( name );
    }

    public boolean delete( String name )
    {
        return clear(new File(type.getFile(), name));
    }

    public String read( String name )
    {
        return read( new File( type.getFile(), name ) );
    }

    public String read( File file )
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream( file );

            byte[] byteData = new byte[fis.available()];
            fis.read( byteData );

            return new String( byteData );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if( fis != null )
                    fis.close();
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean write( String name, String data )
    {
        return write(new File(type.getFile(), name), data);
    }

    public boolean write( File file, String data )
    {
        try
        {
            // if file wasn't created yet, make one
            if( !file.exists() )
            {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream( file );
            byte[] byteData = data.getBytes();

            // write data
            fos.write( byteData );

            // immediately clear buffer onto file
            fos.flush();
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /** DEBUGGING USE **/
    public void dump()
    {
        dump( type.getFile() );
    }

    private void dump( File file )
    {
        if( file.isDirectory() )
        {
            Log.d("Directory", file.getAbsolutePath());
            File[] children = file.listFiles();

            // dump date for children
            for( File child : children )
            {
                dump( child );
            }
        }
        else
        {
            Log.d( "File", file.getAbsolutePath() );
        }
    }

}
