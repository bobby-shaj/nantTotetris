package com.shajari.assembler;

import java.util.Hashtable;

public class SymbolTable {
	private static final int DATA_STARTING_ADDRESS = 16;
	private static final int DATA_ENDING_ADDRESS = 16384;
	private static final int PROGRAM_STARTING_ADDRESS = 0;
	private static final int PROGRAM_ENDING_ADDRESS = 32767;
	
	private Hashtable<String, Integer> symbolAddressMap;
	private int programAddress;
	private int dataAddress;
	
	public SymbolTable() {
		initializeSymbolTable();
		programAddress = PROGRAM_STARTING_ADDRESS;
		dataAddress = DATA_STARTING_ADDRESS;
	}
	
	private void initializeSymbolTable() {
		symbolAddressMap = new Hashtable<String, Integer>();
		symbolAddressMap.put("SP", Integer.valueOf(0));
		symbolAddressMap.put("LCL", Integer.valueOf(1));
		symbolAddressMap.put("ARG", Integer.valueOf(2));
		symbolAddressMap.put("THIS", Integer.valueOf(3));
		symbolAddressMap.put("THAT", Integer.valueOf(4));
		symbolAddressMap.put("R0", Integer.valueOf(0));
		symbolAddressMap.put("R1", Integer.valueOf(1));
		symbolAddressMap.put("R2", Integer.valueOf(2));
		symbolAddressMap.put("R3", Integer.valueOf(3));
		symbolAddressMap.put("R4", Integer.valueOf(4));
		symbolAddressMap.put("R5", Integer.valueOf(5));
		symbolAddressMap.put("R6", Integer.valueOf(6));
		symbolAddressMap.put("R7", Integer.valueOf(7));
		symbolAddressMap.put("R8", Integer.valueOf(8));
		symbolAddressMap.put("R9", Integer.valueOf(9));
		symbolAddressMap.put("R10", Integer.valueOf(10));
		symbolAddressMap.put("R11", Integer.valueOf(11));
		symbolAddressMap.put("R12", Integer.valueOf(12));
		symbolAddressMap.put("R13", Integer.valueOf(13));
		symbolAddressMap.put("R14", Integer.valueOf(14));
		symbolAddressMap.put("R15", Integer.valueOf(15));
		symbolAddressMap.put("SCREEN", Integer.valueOf(16384));
		symbolAddressMap.put("KBD", Integer.valueOf(24576));
	}
	
	public void addEntry(String symbol, int address) {
		symbolAddressMap.put(symbol, Integer.valueOf(address));
	}
	
	public boolean contains(String symbol) {
		return symbolAddressMap.containsKey(symbol);
	}
	
	public int getAddress(String symbol) {
		return symbolAddressMap.get(symbol);
	}
	
	public void incrementProgramAddress() {
		programAddress++;
		if (programAddress > PROGRAM_ENDING_ADDRESS) {
			throw new RuntimeException();
		}
	}
	
	public void incrementDataAddress() {
		dataAddress++;
		if (dataAddress > DATA_ENDING_ADDRESS) {
			throw new RuntimeException();
		}
	}
	
	public int getProgramAddress() {
		return programAddress;
	}
	
	public int getDataAddress() {
		return dataAddress;
	}
}
