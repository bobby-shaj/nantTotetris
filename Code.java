package com.shajari.assembler;

import java.util.Hashtable;

public class Code {
	private Hashtable<String, String> destMnemonics;
	private Hashtable<String, String> compMnemonics;
	private Hashtable<String, String> jumpMnemonics;
	
	public Code() {
		jumpMnemonics = new Hashtable<String, String>();
		populateJumpMnemonics();
		compMnemonics = new Hashtable<String, String>();
		populateCompMnemonics();
		destMnemonics = new Hashtable<String, String>();
		populateDestMnemonics();
	}
	
	private void populateJumpMnemonics() {
		jumpMnemonics.put("NULL", "000");
		jumpMnemonics.put("JGT", "001");
		jumpMnemonics.put("JEQ", "010");
		jumpMnemonics.put("JGE", "011");
		jumpMnemonics.put("JLT", "100");
		jumpMnemonics.put("JNE", "101");
		jumpMnemonics.put("JLE", "110");
		jumpMnemonics.put("JMP", "111");
	}
	
	private void populateCompMnemonics() {
		compMnemonics.put("0", "0101010");
		compMnemonics.put("1", "0111111");
		compMnemonics.put("-1", "0111010");
		compMnemonics.put("D", "0001100");
		compMnemonics.put("A", "0110000");
		compMnemonics.put("M", "1110000");
		compMnemonics.put("!D", "0001101");
		compMnemonics.put("!A", "0110001");
		compMnemonics.put("!M", "1110001");
		compMnemonics.put("-D", "0001111");
		compMnemonics.put("-A", "0110011");
		compMnemonics.put("-M", "1110011");
		compMnemonics.put("D+1", "0011111");
		compMnemonics.put("A+1", "0110111");
		compMnemonics.put("M+1", "1110111");
		compMnemonics.put("D-1", "0001110");
		compMnemonics.put("A-1", "0110010");
		compMnemonics.put("M-1", "1110010");
		compMnemonics.put("D+A", "0000010");
		compMnemonics.put("D+M", "1000010");
		compMnemonics.put("D-A", "0010011");
		compMnemonics.put("D-M", "1010011");
		compMnemonics.put("A-D", "0000111");
		compMnemonics.put("M-D", "1000111");
		compMnemonics.put("D&A", "0000000");
		compMnemonics.put("D&M", "1000000");
		compMnemonics.put("D|A", "0010101");
		compMnemonics.put("D|M", "1010101");
	}
	
	private void populateDestMnemonics() {
		destMnemonics.put("NULL", "000");
		destMnemonics.put("M", "001");
		destMnemonics.put("D", "010");
		destMnemonics.put("MD", "011");
		destMnemonics.put("A", "100");
		destMnemonics.put("AM", "101");
		destMnemonics.put("AD", "110");
		destMnemonics.put("AMD", "111");
	}
	
	public String dest(String mnemonic) {
		if (mnemonic == null || mnemonic.isEmpty()) {
			mnemonic = "NULL";
		}
		
		return destMnemonics.get(mnemonic);
	}
	
	public String comp(String mnemonic) {
		return compMnemonics.get(mnemonic);
	}
	
	public String jump(String mnemonic) {
		if (mnemonic == null || mnemonic.isEmpty()) {
			mnemonic = "NULL";
		}
		
		return jumpMnemonics.get(mnemonic);
	}
	
	public String formatNumberAsBinary(String number) {
		int value = Integer.parseInt(number);
		String binaryNumber = Integer.toBinaryString(value);
		String formattedBinaryNumber =
				String.format("%15s", binaryNumber).replace(' ', '0');
		return formattedBinaryNumber;
	}
}

