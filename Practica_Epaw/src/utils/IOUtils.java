/**
 * Classe que Statica usada para leer y escribir los Txt.
 * @author Marc Pérez - 173287
 *
 */

package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IOUtils {
	
	
	
	// --- CREACIÓN DEL FICHERO ---
	
	public static File createFileIfNotExist(String path) {
		File file = null;
		try {
			file = new File(path);
			if (file.exists() == false) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			file = null;
		}
		return file;
	}
	
	// --- LECTURAS SIMPLES ---
	
	public static String getStrOfFiles(String path) {
		BufferedReader reader;
		String floc;
		String line;
		String text = "";
		String finaltext = "";

		/* File */

		try {
			reader = new BufferedReader(new FileReader(path));
			text = "";

			while ((line = reader.readLine()) != null) {
				finaltext = text + line + "\n";
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return finaltext;
	}	
	
	// --- LECTURAS MULTIPLES ---
	
	public static String getStrOfAllFilesInOneDirectory(String path) {
		/* directory to process */
		File inDir;
		File[] flist;
		BufferedReader reader;
		String floc;
		String line;
		String text = "";
		String finaltext = "";

		/* File */

		inDir = new File(path);
		flist = inDir.listFiles();
		for (int f = 0; f < flist.length; f++) {
			floc = flist[f].getAbsolutePath();
			try {
				reader = new BufferedReader(new FileReader(floc));
				text = "";

				while ((line = reader.readLine()) != null) {
					text = text + line + "\n";
				}

			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			finaltext = finaltext + text;
		}
		return finaltext;
	}
	
	// --- ESCRITURA DE LOS RESULTADOS ---
	
	public static boolean writeTxt(String txt, String path) {
		boolean write = false;
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = createFileIfNotExist(path);
			if (file != null) {
				fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				
				bw.write(txt);
			
				write = true;
			}
	
		} catch (IOException e) {
			e.printStackTrace();
			write = false;
		} finally {
			try {
				if (bw != null) { bw.close(); }
				if (fw != null) { fw.close(); }
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return write;
	}
	
}
