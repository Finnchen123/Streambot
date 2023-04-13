package ch.nicolasovic.bot;

import java.nio.charset.StandardCharsets;

public class MessageBuffer {
    String buffer;
    
    public MessageBuffer() {
        buffer = "";
    }
    
    public void append(byte[] bytes) {
    	String utf8EncodedString = new String(bytes, StandardCharsets.UTF_8);
        buffer += utf8EncodedString;
    	
    	//buffer += new String(bytes);
    }
    
    public boolean hasCompleteMessage() {
        if (buffer.contains("\r\n"))
            return true;
        else
            return false;
    }
    
    public String getNextMessage() {
        int index = buffer.indexOf("\r\n");
        String message = "";
        
        if (index > -1) {
            message = buffer.substring(0, index);
            buffer = buffer.substring(index + 2);
        }
        
        return message;
    }
}