package util;

import java.io.*;
import java.util.*;

public abstract class FileUtil {

	public static void saveToFile(String fileName, String toWrite) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter("results/" + fileName + ".txt"));
			file.write(toWrite);
			file.close();
			System.out.println("File saved succesfully!.");
		}

		catch (FileNotFoundException fnfe) {
			System.out.println("The file could not be saved.");
		} catch (IOException ioe) {
			new RuntimeException("I/O Error.");
		}
	}

//	private void txtToPDF() {
//		<dependency>
//	    <groupId>org.apache.pdfbox</groupId>
//	    <artifactId>pdfbox</artifactId>
//	    <version>2.0.30</version> <!-- La versión puede cambiar, verifica la última versión en el repositorio Maven -->
//		</dependency>
//	}

	public static String setFileName() {
		String code = "";
		String base = "0123456789abcdefghijklmnopqrstuvwxyz";
		int length = 8;
		for (int i = 0; i < length; i++) {
			int numero = (int) (Math.random() * (base.length()));
			code += base.charAt(numero);
		}
		return code;
	}
}
