/*
 Copyright (c) 2007-2009 WebAppShowCase DBA Appcloem (http://www.appcloem.com). All rights reserved.
Author: Gabriel Wong
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL WebAppShowCase
OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/


package tools.util;
import java.io.*;
import java.util.*;
import tools.io.*;
/**
 * This class is used for common methods.
 **/
//SharedMethods
public class StreamUtil
{
	static int MaxCHUNK = 20480;




	public static int readLine(InputStream in,byte[] b,int len)throws IOException{
	int ct = 0;
	int off = 0;
	while (  ct < len )
	{
		b[off] = (byte)in.read();
		ct++;
		off++;
		if (b[off - 1] == '\n')
			break;

	}
	return ct;

	}

	public static String readLine(InputStream in)throws IOException{

		return readLine(in,1024);
	}




public static char[] readChars(InputStream in) throws IOException {
        char[] lineBuffer;
        char[] buf;
        int i;

        buf = lineBuffer = new char[128];

        int room = buf.length;
        int offset = 0;
        int c;

loop:   while (true) {
            switch (c = in.read()) {
              case -1: 
              case '\n':
                break loop;

              case '\r':
                int c2 = in.read();
                if ((c2 != '\n') && (c2 != -1)) {
                    if (!(in instanceof PushbackInputStream)) {
                        in = new PushbackInputStream(in);
                    }
                    ((PushbackInputStream)in).unread(c2);
                } else 
                    break loop;

              default:
                if (--room < 0) {
                    buf = new char[offset + 128];
                    room = buf.length - offset - 1;
                    System.arraycopy(lineBuffer, 0, buf, 0, offset);
                    Arrays.fill(lineBuffer, ' ');
                    lineBuffer = buf;
                }
                buf[offset++] = (char) c;
                break;
            }
        }

        if (offset == 0) {
            return null;
        }

        char[] ret = new char[offset];
        System.arraycopy(buf, 0, ret, 0, offset);
        Arrays.fill(buf, ' ');

        return ret;
    }



	public static byte[] readBytes(InputStream in)throws IOException{
	int ct = 0;
	int off = 0;
	ByteArrayOutputStream b = new ByteArrayOutputStream(128);
	byte br = -1;
	while (  true)
	{
		br = (byte)in.read();
		if ((int)br < 0)
			break;

		b.write(br);
		ct++;
		off++;
	//	if ((int)br < 0)
	//		break;

	}
	return b.toByteArray();

	}





	public static String readLine(InputStream in,int len)throws IOException{
	int ct = 0;
	int off = 0;
	ByteArrayOutputStream b = new ByteArrayOutputStream(len);
	byte br = -1;
	while (  ct < len )
	{
		br = (byte)in.read();
		b.write(br);
		ct++;
		off++;
		if (br == '\n')
			break;

	}
	return new String(b.toByteArray());

	}


	public static String substring(String sbinstr,int sfrom,int sto)throws Exception{
			try{
			 sbinstr = sbinstr.substring(sfrom,sto);
			return sbinstr;
			}catch (Throwable e){
				e.printStackTrace();
				tools.util.LogMgr.err(sfrom + ":" + sto + ":" + sbinstr.length() +" StreamUtil.substring " + e.toString());
				//(sbinstr);
			}
			return null;
	}
	


	
	
		public static byte[] readStream(InputStream din,int len)
	{
		return readStream(din,len,MAX_RETRY,true);
	}
	public static byte[] readStream(InputStream din,int len,int mt,boolean wt)
	{
		
		            //DataInputStream din = new DataInputStream(in);
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
		        byte[] rec = null;//new byte[0];
				int chunkSize = 0;
				int readCount = 0;
				int cter = 0;
				int readLen = -1;
				int loc = 0;
		try
		{
        if (len > 0)
        {
            //rec = new byte[len];
        int rtry = 0;
		//if (len > 1000000)System.gc();
		for(readCount = 0; readCount < len; )
        {
			if (!wt && din.available() < 1 )break;
            chunkSize = len - readCount;
            if (chunkSize > MaxCHUNK)
                chunkSize = MaxCHUNK;
            
			if (din.available() < chunkSize) 
				chunkSize = din.available();
			loc = 0;
			rec = new byte[chunkSize];
			loc = 1;
				readLen = din.read(rec,0,chunkSize);
				loc = 2;
            if (readLen == -1) {
                rtry++;
				loc = 3;
                if (rtry > mt)break;
                continue;
            }
			else 
			{
			loc = 4;	
				bout.write(rec,0,readLen);
				loc = 5;
				cter++;
            	rtry = 0;
            	readCount += readLen;
			}
			loc = 6;
        }
        }
        return bout.toByteArray();
		
		}
		catch (Throwable e)
		{
			tools.util.LogMgr.err(readCount + " READ STREAM ERROR 1 " +  len + ":" + chunkSize + ":" + rec.length + ":" + cter + ":" + loc + ":" + readLen);
			e.printStackTrace();
	}
		return bout.toByteArray();
	}

	public static void readStreamTo(ObjectInput din,OutputStream out,int len)
{
	readStreamTo(din,out,len,null);
}

	public static void readStreamTo(ObjectInput din,OutputStream out,int len,String name)
	{
		
		            //DataInputStream din = new DataInputStream(in);

		        byte[] rec = new byte[0];
				int readCount = 0;
				int oc = -4;
				
		try
		{
	    if (len > 0)
	    {
	        //rec = new byte[len];
			oc = din.available();
	    int rtry = 0;
		///int MaxCHUNK = 20480;
	    while(readCount < len )
	    {
	        int chunkSize = len - readCount;
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        if (din.available() < chunkSize) 
	        	chunkSize = din.available();
	        int readLen = -1;
			//if (din.available() > 0) 
			rec = new byte[chunkSize];
				readLen = din.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
				out.write(rec,0,readLen);
		        rtry = 0;
		        readCount += readLen;
			}
	    }
	    }
		}
		catch (Throwable e)
		{
			int av = -3;
			try{
				av = din.available();
			}catch (Throwable e2){}
			tools.util.LogMgr.err(av + " READ OBJ STREAM TO ERROR " +  len + ":" + name + ":" + readCount + ":" + oc + ":" + new java.util.Date());
			if (name == null)
				e.printStackTrace();
	}
	}
	




	public static void readStreamTo2(ObjectInput din,OutputStream out,int len,String name)throws IOException
	{
		
		            //DataInputStream din = new DataInputStream(in);

		        byte[] rec = new byte[0];
				int readCount = 0;
				int oc = -4;
		try{		
	    if (len > 0)
	    {
	        //rec = new byte[len];
			oc = din.available();
	    int rtry = 0;
		///int MaxCHUNK = 20480;
	    while(readCount < len )
	    {
	        int chunkSize = len - readCount;
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        if (din.available() < chunkSize) 
	        	chunkSize = din.available();
	        int readLen = -1;
			//if (din.available() > 0) 
			rec = new byte[chunkSize];
				readLen = din.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
				Thread.sleep(1000);
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
				out.write(rec,0,readLen);
		        rtry = 0;
		        readCount += readLen;
			}
	    }
	    }
		}
		catch (Throwable e)
		{
			int av = -3;
			try{
				av = din.available();
			}catch (Throwable e2){}
			if (name == null)
				e.printStackTrace();
				tools.util.LogMgr.err(av + " READ OBJ STREAM TO ERROR " +  len + ":" + name + ":" + readCount + ":" + oc + ":" + new java.util.Date() + ":" + e.toString());
				throw new IOException(av + " READ OBJ STREAM TO ERROR " +  len + ":" + name + ":" + readCount + ":" + oc + ":" + new java.util.Date() + ":" + e.toString());
			
	}
	}
	






	public static void readStreamTo(InputStream in,int len,OutputStream out)
	{
		
		            DataInputStream din = new DataInputStream(in);

		        byte[] rec = new byte[0];
		try
		{
	    if (len > 0)
	    {
	        //rec = new byte[len];
	    int rtry = 0;
	    for(int readCount = 0; readCount < len; )
	    {
	        int chunkSize = len - readCount;
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
            if (din.available() < chunkSize) 
            	chunkSize = din.available();
				
	        int readLen = -1;
			//if (din.available() > 0) 
			rec = new byte[chunkSize];
				readLen = din.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
				Thread.sleep(1000);
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
				out.write(rec,0,readLen);
		        rtry = 0;
		        readCount += readLen;
			}
	    }
	    }
		}
		catch (Throwable e)
		{
			tools.util.LogMgr.err("READ STREAM TO ERROR " +  len);
			e.printStackTrace();
	}
	}
	
	
	
		static int MAX_RETRY = 30;
	//static int MAX_CHUNK = 40480;
	
	
		public static void readStream(InputStream din,byte[] rec)
	{
		            //DataInputStream din = new DataInputStream(new BufferedInputStream(in));

		int len = rec.length;
		try
		{
        if (len > 0)
        {
            //rec = new byte[len];
        int rtry = 0;
        for(int readCount = 0; readCount < len; )
        {
            int chunkSize = len - readCount;
            if (chunkSize > MaxCHUNK)
                chunkSize = MaxCHUNK;
            if (din.available() < chunkSize) 
            	chunkSize = din.available();
				
            int readLen = -1;
            //if (din.available() > 0) 
            	readLen = din.read(rec,readCount,chunkSize);
            if (readLen == -1) {
                rtry++;
				Thread.sleep(1000);
                if (rtry > MAX_RETRY)
				{
					
					break;
				}
                continue;
            }else
			{
	            rtry = 0;
	            readCount += readLen;
			}
        }
        }
		}
		catch (Throwable e)
		{
		tools.util.LogMgr.debug("StreamUtil.readStream ERROR: " + e.toString());
		
			e.printStackTrace();
	}
	//	return rec;
	}
	
	
			public static void readStream(InputStream din,byte[] rec,int off,int len)
	{
		            //DataInputStream din = new DataInputStream(in);

		try
		{
        if (len > 0)
        {
            //rec = new byte[len];
        int rtry = 0;
        for(int readCount = 0; readCount < len; )
        {
            int chunkSize = len - readCount;
            if (chunkSize > MaxCHUNK)
                chunkSize = MaxCHUNK;
            if (din.available() < chunkSize) 
            	chunkSize = din.available();
			
			if (readCount + off + chunkSize > din.available())	
			 chunkSize = din.available() - (readCount + off);
			
           int readLen = -1;
            //if (din.available() > 0) 
				 readLen = din.read(rec,readCount + off,chunkSize);
            if (readLen == -1) {
                rtry++;
                tools.util.LogMgr.debug(readCount + ":" + len + " StreamUtil.readStream Pause: " + rtry);
				
                Thread.sleep(1000);
                if (rtry > MAX_RETRY)
                {
                	tools.util.LogMgr.debug(readCount + " StreamUtil.readStream Timeout " + len);
					break;
            	}
                continue;
            }
			else
			{
            rtry = 0;
            readCount += readLen;
			}
        }
        }
		}
		catch (Throwable e)
		{
			tools.util.LogMgr.err("StreamUtil.readStream Error: " + e.toString());
			e.printStackTrace();
	}
	//	return rec;
	}
	
			public static void readStream(InputStream in,byte[] rec,int off,int len,int mark)
	throws IOException
	{
		byte[] b = new byte[mark];
		readStream(in,b,0,mark);
		readStream(in,rec,off,len);
	}






	public static void write(InputStream in,OutputStream out)throws IOException
	{
		

		        byte[] rec = new byte[0];
		        //int writeLen = 0;
				
		//try
		//{
	    {
	        //rec = new byte[len];
	    int rtry = 0;
		int MaxCHUNK = 20480;
	    while(in.available() > 0)
	    {
	        int chunkSize = in.available();
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        int readLen = -1;
			rec = new byte[chunkSize];
				readLen = in.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
	          //  try{
	         //   Thread.sleep(1000);
	         //}catch (Throwable e){}
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
				out.write(rec,0,readLen);
		        rtry = 0;
		        //writeLen+= readLen;
				
			}
	    }
	    }
	//	}
	//	catch (Throwable e)
	//	{
	//		tools.util.LogMgr.debugger("WRITE STREAM TO ERROR " + e.toString());
			//Thread.dumpStack();
			//e.printStackTrace();
//	}
	
	}
	
			public static void writeTimeout(InputStream in,OutputStream out,long l)throws IOException
	{
		writeTimeout(in,out,l,30);
	}
	
		public static void writeTimeout(InputStream in,OutputStream out,long l,int t)throws IOException
	{
		WriteTimeoutEx wto = new WriteTimeoutEx(in,out,l);
		wto.start();
		
			long lo = wto.rec.value;
			while (!wto.fin )
			{
				for (int ct = 0;ct <= t;ct++)
				{
				try{
				Thread.sleep(1000);
				}catch (Throwable e){
					tools.util.LogMgr.err("writeTimeout " + e.toString());
					
					}
				if (wto.rec.value != lo){
					lo = wto.rec.value;
					break;
				}
				if (ct == t){
					//(l + " writeTimeout Complete " + lo);
					wto.interrupt();
					return;	
				}
				}
			}		
		
	}
	
			public static void write(InputStream in,OutputStream out,long mL)throws IOException
	{
		write(in,out,mL,null);
	}
	
		public static void write(InputStream in,OutputStream out,long mL,LongNum ln)throws IOException
	{
		

		        //( " StreamUtil.write "  + mL);
		        byte[] rec = new byte[0];
	
	   int maxretry = MAX_RETRY * 100;
	       
	    int rtry = 0;
		int MaxCHUNK = 228960;
	    long rL = 0;
	    int ct =0;
	    int pc = 0;
	    while(mL > rL)
	    {
	        int chunkSize = in.available();
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        int readLen = -1;
	        if (chunkSize < 1)chunkSize = 1024;
			rec = new byte[chunkSize];
				readLen = in.read(rec,0,chunkSize);
	        if (readLen < 1 ) {
	            rtry++;
	            try{
	            	//(ct + " WAIT " + rL + ":" + readLen);
	            Thread.sleep(10);
	            pc++;
	         }catch (Throwable e){
	         	tools.util.LogMgr.err(" write Long " + e.toString());
	         }
	            if (rtry > maxretry){
	         	tools.util.LogMgr.debug("Ending Read ");
	            	break;
			}
	            continue;
	        }
			else
			{
				//ct ++;
				rL = rL + readLen;
				if (ln != null)
				ln.increment(readLen);
				//(ct + " SLC " + rL + ":" + readLen);
				out.write(rec,0,readLen);
				//(ct + " SLC 2 " + rL + ":" + readLen);
				//out.flush();
		        rtry = 0;
				
			}
	    }
	    
	if (pc > 600)tools.util.LogMgr.debug("READ PAUSED IN MINUTES " + (pc/1200));
	
	}
	
	
	
	
	
	
	
	
		public static void write(InputStream in,OutputStream out,int l)throws IOException
	{
		

		        byte[] rec = new byte[0];
		        //int writeLen = 0;
				
		//try
		//{
	    {
	        //rec = new byte[len];
	    int rtry = 0;
		int MaxCHUNK = 20480;
		int totRead = 0;
	    while(l > totRead)
	    {
	        int chunkSize = in.available();
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        int readLen = -1;
			rec = new byte[chunkSize];
				readLen = in.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
	            if (rtry > MAX_RETRY)break;
	          try{
	            	//(ct + " WAIT " + rL + ":" + readLen);
	            Thread.sleep(1000);
	         }catch (Throwable e){
	         	tools.util.LogMgr.err(" write Int " + e.toString());
	         }
	            continue;
	        }
			else
			{
				totRead = totRead + readLen;
				out.write(rec,0,readLen);
		        rtry = 0;
		        //writeLen+= readLen;
				
			}
	    }
	    }
	//	}
	//	catch (Throwable e)
	//	{
	//		tools.util.LogMgr.debugger("WRITE STREAM TO ERROR " + e.toString());
			//Thread.dumpStack();
			//e.printStackTrace();
//	}
	
	}
	
	
	
	public static void decodeBase64(InputStream inp,OutputStream out)throws IOException
	{
		
	String li = null;
	LineNumberReader in = new LineNumberReader(new InputStreamReader(inp));
	//EZServletInputStream in = new EZServletInputStream(inp);
	byte[] rec = new byte[0];
	TempDataOutputStream toud = new TempDataOutputStream(inp.available());
	while ((li = in.readLine()) != null)
	{
		li = li.trim();
		if (li.length() > 0)
		{
		/*if (bs.endsWith("\n"))
		{

			bs = bs.substring(0,bs.length() -1 );
		}
		if (bs.endsWith("\r"))
		{

			bs = bs.substring(0,bs.length() -1 );
		}*/
		//if (li.indexOf("\n") > -1 || li.indexOf("\r") > -1 || (li.length()/4) * 4 != li.length())
		//	//("BEG64:" + li + ":END64");
		
			//rec = Base64.decode(li.getBytes());
			rec = li.getBytes();
			toud.write(rec,0,rec.length);
		}
	}
	//("BEG64: toudL " + toud.length());
		toud.close();
		decodeBase642(toud.getTempData().get(),out);
		toud.reset();
	
	}
	
	
		public static void decodeBase642(InputStream in,OutputStream out)throws IOException
	{
		
//("decodeBase642 LENGTH " + in.available());
		        byte[] rec = new byte[0];
	    int rtry = 0;
		int MaxCHUNK = 20480;
	    while(in.available() > 0)
	    {
	        int chunkSize = in.available();
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        int readLen = -1;
			rec = new byte[chunkSize];
				readLen = in.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
			//if (in.available() < 1	)
			/*{
				String bs = new String(rec).trim();
				if (bs.endsWith("\n"))
					bs = bs.substring(0,bs.length() -1 );
				if (bs.endsWith("\r"))
					bs = bs.substring(0,bs.length() -1 );
				bs = StringUtil.replaceSubstring(bs,"\n","");
				bs = StringUtil.replaceSubstring(bs,"\r","");
				rec = bs.getBytes();
			}*/
			 //if((rec.length/4) * 4 != rec.length)
			 //	("BAD DECB64 LENGTH " + rec.length);
			 if (readLen != rec.length)System.out.println(rec.length + " INVALID DECODE LEN " + readLen);
				rec = Base642.decode(rec);
				//if (b == null)
				//	b = bs.getBytes();
				
				
				
				out.write(rec,0,rec.length);
		        rtry = 0;
				
			}
	    }
	}
	
	
	
	
	
	public static void write(InputStreamReader in,Writer out)
	{
		write (new LineNumberReader(in),new PrintWriter(out));
	}

	public static void write(LineNumberReader in,PrintWriter out)
	{
		        byte[] rec = new byte[0];
		try
		{
			String li = null;
		    while((li = in.readLine()) != null)
				out.println(li);
		}
		catch (Throwable e)
		{
			tools.util.LogMgr.debugger("WRITE LineNumberReader TO ERROR " + e.toString());
			//Thread.dumpStack();
			//e.printStackTrace();
	}
	
	}




	public static void write(InputStream in,ObjectOutput out)
	{
		

		        byte[] rec = new byte[0];
		        int readLen = -1;
				//int writeLen = 0;
				
		try
		{
	    {
	        //rec = new byte[len];
	    int rtry = 0;
		int MaxCHUNK = 20480;
	    while(in.available() > 0)
	    {
	        int chunkSize = in.available();
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        readLen = -1;
			rec = new byte[chunkSize];
				readLen = in.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
				out.write(rec,0,readLen);
				//writeLen+= readLen;
		        rtry = 0;
			}
	    }
	    }
		}
		catch (Throwable e)
		{
			tools.util.LogMgr.err("WRITE OBJ STREAM TO ERROR " );
			e.printStackTrace();
	}
	//("OBJWRITELEN " + writeLen);
	}
	


	public static void write2(InputStream in,ObjectOutput out)throws IOException
	{
		

		        byte[] rec = new byte[0];
		        int readLen = -1;
				//int writeLen = 0;
				
	//	try
	//	{
	    {
	        //rec = new byte[len];
	    int rtry = 0;
		int MaxCHUNK = 20480;
	    while(in.available() > 0)
	    {
	        int chunkSize = in.available();
	        if (chunkSize > MaxCHUNK)
	            chunkSize = MaxCHUNK;
	        readLen = -1;
			rec = new byte[chunkSize];
				readLen = in.read(rec,0,chunkSize);
	        if (readLen == -1) {
	            rtry++;
	            if (rtry > MAX_RETRY)break;
	            continue;
	        }
			else
			{
				out.write(rec,0,readLen);
				//writeLen+= readLen;
		        rtry = 0;
			}
	    }
	    }
	//	}
	//	catch (Throwable e)
		//{
			//tools.util.LogMgr.err("WRITE OBJ STREAM TO ERROR " );
			//e.printStackTrace();
//	}
	//("OBJWRITELEN " + writeLen);
	}
	




}


class WriteTimeoutEx extends ThreadEx{
	protected LongNum rec = new LongNum();;
	InputStream in;
	OutputStream out;
	long len;
	WriteTimeoutEx(InputStream i,OutputStream o,long l){
		//rec = ln;	
		in = i;
		out = o;
		len = l;
	}
	protected void exec() throws Exception{
		
			 StreamUtil.write(in,out,len,rec);

	}

}