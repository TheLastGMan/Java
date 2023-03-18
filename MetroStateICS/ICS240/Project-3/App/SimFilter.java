package App;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SimFilter extends FileFilter
{
	@Override
    public boolean accept(File f){
        return f.getName().toLowerCase().endsWith(".sim") || f.isDirectory();
    }
    @Override
    public String getDescription(){
        return "Sim File (*.sim)";
    }
}
