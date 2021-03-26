package com.shajari.assembler;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class Launcher {
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.err.println("No source file!");
			System.exit(1);
		}
		
		File sourceFile = new File(args[0].trim());
		if (!sourceFile.exists()) {
			System.err.println("Specified source file could not be found.");
			System.exit(2);
		}
		
		String sourceAbsolutePath = sourceFile.getAbsolutePath();
		String fileName = sourceFile.getName();
		int fileNameExtensionIndex = fileName.lastIndexOf(".");
		String fileNameNoExtension = fileName.substring(0, fileNameExtensionIndex);
		int fileNameIndex = sourceFile.getAbsolutePath().indexOf(sourceFile.getName());
		String sourceDirectory = sourceAbsolutePath.substring(0, fileNameIndex);
		String outputFilePath = sourceDirectory + fileNameNoExtension + ".hack";
		File outputFile = new File(outputFilePath);
		
		try {
			if (outputFile.exists()) {
				outputFile.delete();
			}
			outputFile.createNewFile();

                        Assembler assembler = new Assembler(sourceFile, outputFile);
			assembler.translate();
			
			StringWriter status = new StringWriter();
			status.append("Translation completed successfully on ");
			status.append(sourceAbsolutePath);
			status.append(" ==> ");
			status.append(outputFilePath);
			System.out.println(status.toString());
		} catch (IOException e) {
			System.err.println("An unknown I/O error occurred.");
			System.exit(3);
		}
	}
}