package chris.sullivan.director.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by chris on 5/10/15.
 */
public class FileType {

    public static final int Cache = 0;
    public static final int Internal = 1;
    public static final int External = 2;

    private File file;

    public FileType( Context context ) { this( context, Cache ); }
    public FileType( Context context, int type )
    {
        switch( type )
        {
            case Cache:
                file = context.getCacheDir();
                break;
            case Internal:
                file = context.getFilesDir();
                break;
            case External:
                if( isExternalAvailable() )
                {
                    file = Environment.getExternalStorageDirectory();
                }
                break;
            default:
                file = context.getCacheDir();
                break;
        }
    }

    public File getFile()
    {
        return file;
    }

    private boolean isExternalAvailable()
    {
        String state = Environment.getExternalStorageState();
        if( Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }
        return false;
    }

}
