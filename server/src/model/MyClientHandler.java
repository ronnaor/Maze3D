package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyClientHandler implements ClinetHandler{

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		BufferedReader in=new BufferedReader(new InputStreamReader(inFromClient));
		PrintWriter out=new PrintWriter(outToClient);
		
		String line;
		try{
			while(!(line=in.readLine()).equals("exit")){
				StringBuilder sb=new StringBuilder(line);
				out.println(sb.reverse().toString());
				out.flush();
			}
			out.println("good bye");
			out.flush();
		}catch(Exception e){}
	}

}
