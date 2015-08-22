package GameFileConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * This class contains three different methods that each one can copy files to
 * a destination, folder to a destination or even folder with sub folders and 
 * files to a destination. This class can be found on inter net.
 * @author Vasilhs Triantaris
 */
public class FileUtilities {

    
    private FileUtilities() {
  
    }
  
    /**
     * This method copes a single file from a path to an other.
     * 
     * @param source  The file source that will be copied to the destination.
     * @param destination  The file destination that the source file will be copied to.
     * @throws IOException 
     */
    public static final void copy( File source, File destination ) throws IOException {
        if( source.isDirectory() ) {
            copyDirectory( source, destination );
        } else {
            copyFile( source, destination );
        }
    }
  
    /**
     * Method that copies an entire folder with sub files or folder in it to a
     * certain destination.
     * 
     * @param source  The source directory with the sub files/directories that will be copied to the destination.
     * @param destination  The destination of the directory with the sub files/directories.
     * @throws IOException 
     */
    public static final void copyDirectory( File source, File destination ) throws IOException {
        if( !source.isDirectory() ) {
            throw new IllegalArgumentException( "Source (" + source.getPath() + ") must be a directory." );
        }
    
        if( !source.exists() ) {
            throw new IllegalArgumentException( "Source directory (" + source.getPath() + ") doesn't exist." );
        }
    
        if( destination.exists() ) {
            throw new IllegalArgumentException( "Destination (" + destination.getPath() + ") exists." );
        }
    
        destination.mkdirs();
        File[] files = source.listFiles();
    
        for( File file : files ) {
            if( file.isDirectory() ) {
                copyDirectory( file, new File( destination, file.getName() ) );
            } else {
                copyFile( file, new File( destination, file.getName() ) );
            }
        }
    }
  
    /**
     * Method that converts the source file / files and the destination file / files
     * into streams so that can be transfered from one place to another.
     * 
     * @param source  The path of the source file.
     * @param destination  The path of the destination.
     * @throws IOException 
     */
    public static final void copyFile( File source, File destination ) throws IOException {
        FileChannel sourceChannel = new FileInputStream( source ).getChannel();
        FileChannel targetChannel = new FileOutputStream( destination ).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        sourceChannel.close();
        targetChannel.close();
    }
}