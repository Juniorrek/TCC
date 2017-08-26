package Facade;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import javax.servlet.http.Part;

public class Facade {
        final String path = "C:/Users/Orestes/Desktop/TCC/SobAnalise";
    
        public String getFileName(final Part part) {
                    final String partHeader = part.getHeader("content-disposition");
                    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
                    for (String content : part.getHeader("content-disposition").split(";")) {
                        if (content.trim().startsWith("filename")) {
                            return content.substring(
                                    content.indexOf('=') + 1).trim().replace("\"", "");
                        }
                    }
                    return null;
              }
              
              
              public String salva(Part filePart) throws IOException{
                        final String fileName = getFileName(filePart);
                        OutputStream out = null;
                        InputStream filecontent = null;
                        try {
                                out = new FileOutputStream(new File(path + File.separator + fileName));
                                filecontent = filePart.getInputStream();
                                int read = 0;
                                final byte[] bytes = new byte[1024];
                                while ((read = filecontent.read(bytes)) != -1) {
                                    out.write(bytes, 0, read);
                                }
                                LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", new Object[]{fileName, path});
                        } catch (FileNotFoundException fne) {
                            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", new Object[]{fne.getMessage()});
                        } finally {
                            if (out != null) { out.close(); }
                            if (filecontent != null) { filecontent.close(); }                            
                        }
                        return fileName;
              }
              
}
