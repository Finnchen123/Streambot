package ch.nicolasovic.bot;

public class Console {
	
	public static void write(String text) {
		System.out.print(text);
	}
	
	public static void writeLine(String text) {
		System.out.println(text);
	}
	
	public static void writeSpacing() {
		System.out.println("***********************************");
	}
	
	public static void outputHeading(String text) {
		writeSpacing();
		writeLine(text);
		writeSpacing();
	}
	
	public static void outputInformation(String text) {
		writeLine("INFO : " + text);
	}
	
	public static void outputError(String text) {
		writeLine("ERROR: " + text);
	}
}
