package com.teamhandsome.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {

	public FileReader() {
		
	}
	
	public String readFileToString(String path) throws FileNotFoundException{
		StringBuilder builder = new StringBuilder();
		File file = new File(path);
		if(file.exists()){
			String line = "";
			try {
			    InputStream fis = new FileInputStream(file);
			    InputStreamReader isr = new InputStreamReader(fis);
			    BufferedReader br = new BufferedReader(isr);
			    while ((line = br.readLine()) != null) {
			        builder.append(line);
			        builder.append("\n");
			    }
			}catch(IOException e){
				
			}
		}else{
			throw new FileNotFoundException();
		}
		return builder.toString();
	}
	
}
