package main_Source;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class RawFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()){
			return true;
		}
		String extension = Utils.getExtension(f);
		if (extension.equals(Utils.raw)){
			return true;
		}
		else{
			return false;
		}
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Raw Files";
	}

}
