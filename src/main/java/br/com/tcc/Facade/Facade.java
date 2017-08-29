package br.com.tcc.Facade;

import beans.Texto;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.Part;
import org.rosuda.REngine.REXPMismatchException;
import br.com.tcc.util.Call;

public class Facade {
        final String path = "C:\\Users\\david.INTRANET\\Documents\\testes";
    
        public String getFileName(final Part part) {
            final String partHeader = part.getHeader("content-disposition");
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
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
            } catch (FileNotFoundException fne) {
            } finally {
                if (out != null) { out.close(); }
                if (filecontent != null) { filecontent.close(); }                            
            }
            return fileName;
        }
            
              
              
              
              
              
        public List<Texto> soloArtigo(String nome) throws REXPMismatchException {
            Call c = new Call();
            List<Texto> a = c.soloArtigo(nome, path);
            return a;
        }
              
              
        public List<Texto> manyArtigo(String[] nome) throws REXPMismatchException {
            Call c = new Call();
            List<Texto> a = c.manyArtigo(nome, path);
            return a;
        }
}