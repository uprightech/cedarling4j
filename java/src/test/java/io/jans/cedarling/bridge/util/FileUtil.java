package io.jans.cedarling.bridge.util;

import java.io.File;
import java.io.InputStream;;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class FileUtil {
    
    public static File copyResource(File destdir,final String resourcepath) throws Exception {

        InputStream resourcestream = FileUtil.class.getClassLoader().getResourceAsStream(resourcepath);
        final File outfilepath = new File(destdir,resourcepath);
        try ( OutputStream outstream = new FileOutputStream(outfilepath) ) {
            
            byte [] buffer = new byte[8*1024];
            int bytesread = 0;
            while((bytesread = resourcestream.read(buffer)) != -1) {
                outstream.write(buffer,0,bytesread);
            }
        }
        
        return outfilepath;
    }

    public static String readResourceContent(final String resourcepath) throws Exception {

        InputStream resourcestream = FileUtil.class.getClassLoader().getResourceAsStream(resourcepath);
        byte [] buffer = new byte [7*1024];
        int bytesread = 0;
        StringBuilder sb = new StringBuilder();
        while((bytesread = resourcestream.read(buffer)) != -1) {
            sb.append(new String(buffer,0,bytesread));
        }
        return sb.toString();
    }
}
