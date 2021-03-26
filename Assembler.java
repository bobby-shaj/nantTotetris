package com.shajari.assembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Assembler {
	private File assemblyCode;
	private BufferedWriter machineCode;
	private Code encoder;
	private SymbolTable symbolTable;
	
	public Assembler(File source, File target) throws IOException {
		assemblyCode = source;
		FileWriter fw = new FileWriter(target);
		machineCode = new BufferedWriter(fw);
		
		encoder = new Code();
		symbolTable = new SymbolTable();
	}
	
	public void translate() throws IOException {
		recordLabelAddress();
		parse();
	}
	
	private void recordLabelAddress() throws IOException {
		Parser parser = new Parser(assemblyCode);
		while (parser.hasMoreCommands()) {
			parser.advance();
			
			CommandType commandType = parser.commandType();
			
			if (commandType.equals(CommandType.L_COMMAND)) {
				String symbol = parser.symbol();
				int address = symbolTable.getProgramAddress();
				symbolTable.addEntry(symbol, address);
			} else {
				symbolTable.incrementProgramAddress();
			}
		}
		parser.close();
	}
		
	private void parse() throws IOException {
		Parser parser = new Parser(assemblyCode);
		while (parser.hasMoreCommands()) {
			parser.advance();
	
			CommandType commandType = parser.commandType();
			String instruction = null;
			
			if (commandType.equals(CommandType.A_COMMAND)) {
				String symbol = parser.symbol();
				Character firstCharacter = symbol.charAt(0);
				boolean isSymbol = (!Character.isDigit(firstCharacter));
				
				String address = null;
				if (isSymbol) {
					boolean symbolExists = symbolTable.contains(symbol);
					
					if (!symbolExists) {
						int dataAddress = symbolTable.getDataAddress();
						symbolTable.addEntry(symbol, dataAddress);
						symbolTable.incrementDataAddress();
					}
						
					address = Integer.toString(
							symbolTable.getAddress(symbol));
				} else {
					address = symbol;
				}
				
				instruction = formatAInstruction(address);
			} else if (commandType.equals(CommandType.C_COMMAND)) {
				String comp = parser.comp();
				String dest = parser.dest();
				String jump = parser.jump();
				instruction = formatCInstruction(comp, dest, jump);
			}
	
			if (!commandType.equals(CommandType.L_COMMAND)) {
				machineCode.write(instruction);
				machineCode.newLine();
			}
		}
		
		parser.close();
		machineCode.flush();
		machineCode.close();
	}

	private String formatAInstruction(String address) {
		String formattedNumber = encoder.formatNumberAsBinary(address);
		return "0" + formattedNumber;
	}
	
	private String formatCInstruction( String comp, String dest, String jump) {
		StringWriter instruction = new StringWriter();
		instruction.append("111");
		instruction.append(encoder.comp(comp));
		instruction.append(encoder.dest(dest));
		instruction.append(encoder.jump(jump));
		return instruction.toString();
	}
}
