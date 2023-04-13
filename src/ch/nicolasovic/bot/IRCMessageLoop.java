package ch.nicolasovic.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import ch.nicolasovic.bot.handler.PointHandler;

public abstract class IRCMessageLoop implements Runnable  {
    OutputStream out;
    InputStream in;
    List<String> channelList;
    boolean initial_setup_status;
    PointHandler points;
    Socket server;
    
    IRCMessageLoop(String serverName, int port) {
    	Console.outputHeading("CONNECTION");
    	points = new PointHandler();
    	channelList = new ArrayList<String>();
    	initial_setup_status = false;
		try {
			if(port == 6667) {
				server = new Socket(serverName, port);
			}
			else {
				SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				server = (SSLSocket) sslsocketfactory.createSocket(serverName, port);
			}
	    	out = server.getOutputStream();
	    	Console.outputInformation("Connection established");
	    	
		} catch (UnknownHostException e) {
			Console.outputError("Host unknown");
		} catch (IOException e) {
			Console.outputError("Connection not established");
		}
    }

    public void send(String text) {
    	text = text.replace("ä", "ae").replace("ö", "oe").replace("ü", "ue").replace("ß", "ss");
    	text = text.replace("Ä", "Ae").replace("Ö", "Oe").replace("Ü", "Ue").replace("ẞ", "ss");
        byte[] bytes = (text + "\r\n").getBytes();
        try {
            out.write(bytes);
        }
        catch (IOException info)
        {
        	System.out.println(info);
        }
    }
    
    void nick(String nickname) {
        String msg = "NICK " + nickname;
        send(msg);
    }
    
    void pass(String password) {
    	String msg = "PASS " + password;
        send(msg);
    }
    
    void reqCommands() {
    	String msg = "CAP REQ :twitch.tv/commands";
        send(msg);
    }
    
    void reqMember() {
    	String msg = "CAP REQ :twitch.tv/membership";
        send(msg);
    }

    void join(String channel) {
        if (!initial_setup_status) {
            channelList.add(channel);
            return;
        }
        String msg = "JOIN #" + channel;
        send(msg);
    }
    
    void part(String channel) {
        String msg = "PART " + channel;
        send(msg);
    }
    
    public void privmsg(String text) {
        String msg = "PRIVMSG #finnchen123 :" + text;
        send(msg);
    }
    
    void quit(String reason) {
        String msg = "QUIT :Quit: " + reason;
        send(msg);
    }
    
    abstract void raw(Message msg);
    
    void initial_setup() {
    
        initial_setup_status = true;
        
        
        // now join the channels. you need to wait for message 001 before you join a channel.
        for (String channel: channelList) {
            join(channel);
        }
    }
    
    void processMessage(String ircMessage) {
        Message msg = MessageParser.message(ircMessage);
        if(msg.command.replace(" ", "").equalsIgnoreCase("join")) {
        	points.addUser(msg.nickname);
        }
        if (msg.command.equalsIgnoreCase("privmsg")) {
            if (msg.content.equalsIgnoreCase("\001VERSION\001")) {
            	privmsg("Prototype IRC Client (Built to learn)");
                return;
            }
            raw(msg);
        }
        else if (msg.command.equals("001")) {
            initial_setup();
            return;
        }
        else if (msg.command.equals("ping")) {
            pong(msg.content);
        }
    }
    
    void pong(String server) {
        String msg = "PONG " + server;
        send(msg);
    }
    
    public void receiveConfirmation() {
    	try
        {
        	InputStreamReader inReader = new InputStreamReader(server.getInputStream());
        	BufferedReader reader = new BufferedReader(inReader);
            String line;
            while (!initial_setup_status) {
            	line = null;
            	while((line = reader.readLine()) != null) {
            		processMessage(line);
            		if(initial_setup_status) {
            			break;
            		}
            	}
            	if(line == null) {
            		break;
            	}
            }
        }
        catch (IOException info)
        {
            quit("error in messageLoop");
            info.printStackTrace();
            System.out.println(info);
        }
    }
    
    public void run() {
    	Console.outputHeading("RUNTIME");
    	
        InputStream stream = null;
        
        try
        {
            stream = server.getInputStream();
            MessageBuffer messageBuffer = new MessageBuffer();
            byte[] buffer = new byte[512];
            int count;
            
            while (true) {
                count = stream.read(buffer);
                if (count == -1)
                    break;
                messageBuffer.append(Arrays.copyOfRange(buffer, 0, count));
                while (messageBuffer.hasCompleteMessage()) {
                    String ircMessage = messageBuffer.getNextMessage();
                    processMessage(ircMessage);
                }
            }
        }
        catch (IOException info)
        {
            quit("error in messageLoop");
            info.printStackTrace();
        }
    }
}