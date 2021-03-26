package com.shajari.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
	private BufferedReader reader;
	private String currentLine;
	private String nextLine;
	
	public Parser(File source) throws IOException {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (!source.exists()) {
			throw new FileNotFoundException(source.getAbsolutePath());
		}
		
		reader = new BufferedReader(new FileReader(source));
		currentLine = null;
		nextLine = getNextLine();
	}
	
	private String getNextLine() throws IOException {
		String nextLine;
		
		do {
			nextLine = reader.readLine();
			
			if (nextLine == null) {
				return null;
			}
		} while (nextLine.trim().isEmpty() || isComment(nextLine));
		
		int commentIndex = nextLine.indexOf("//");
		if (commentIndex != -1) {
			nextLine = nextLine.substring(0, commentIndex - 1);
		}
		
		return nextLine;
	}

	private boolean isComment(String input) {
		return input.trim().startsWith("//");
	}

	@Override
	public void finalize() {
		close();
	}
	
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
		}
	}
	
	public boolean hasMoreCommands() {
		return (nextLine != null);
	}
	
	public void advance() throws IOException {
		currentLine = nextLine;
		nextLine = getNextLine();
	}
	
	public CommandType commandType() {
		String trimmedLine = currentLine.trim();
		
		if (trimmedLine.startsWith("(") && trimmedLine.endsWith(")")) {
			return CommandType.L_COMMAND;
		} else if (trimmedLine.startsWith("@")) {
			return CommandType.A_COMMAND;
		} else {
			return CommandType.C_COMMAND;
		}
	}
	
	public String symbol() {
		String trimmedLine = currentLine.trim();
		
		if (commandType().equals(CommandType.L_COMMAND)) {
			return trimmedLine.substring(1, currentLine.length() - 1);
		} else if (commandType().equals(CommandType.A_COMMAND)) {
			return trimmedLine.substring(1);
		} else {
			return null;
		}
	}
	
	public String dest() {
		String trimmedLine = currentLine.trim();
		int destIndex = trimmedLine.indexOf("=");
		
		if (destIndex == -1) {
			return null;
		} else {
			return trimmedLine.substring(0, destIndex);
		}
	}
	
	public String comp() {
		String trimmedLine = currentLine.trim();
		int destIndex = trimmedLine.indexOf("=");
		if (destIndex != -1) {
			trimmedLine = trimmedLine.substring(destIndex + 1);
		}
		int compIndex = trimmedLine.indexOf(";");
		
		if (compIndex == -1) {
			return trimmedLine;
		} else {
			return trimmedLine.substring(0, compIndex);
		}
	}
	
	public String jump() {
		String trimmedLine = currentLine.trim();
		int compIndex = trimmedLine.indexOf(";");
		
		if (compIndex == -1) {
			return null;
		} else {
			return trimmedLine.substring(compIndex + 1);
		}
	}
}
