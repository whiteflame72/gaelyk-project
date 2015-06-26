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
import java.util.*;
/**
 * This class is used for common methods.
 **/
//SharedMethods
public class NumberUtil
{
	static Random rm = new Random();
	public static int getRandomIntBetween(int mi,int ma)
	{
		int n = rm.nextInt();
		while (n < mi || n > ma){
			n = rm.nextInt();
		}
		return n;
	}
	
	public static String parseNumber(String s){
	StringBuffer buf = new StringBuffer(s);
	for (int ct = 0; ct < buf.length();ct++)
	{
	
		//((int)buf.charAt(ct) + " TOT " + buf.charAt(ct) + ":" + ct);
		int tc = (int)buf.charAt(ct);
		if (tc != 46 && (tc < 48 || tc > 57))
		{
			//(tc + " DELETE CGA " + ct);
			buf.deleteCharAt(ct);
		}
	}
	return buf.toString();

	
	}
	
	
	public static boolean isNumeric(String s){
				if (!StringUtil.isRealString(s))
					return false;
				String an = "0123456789";	
				char[] c = s.toCharArray();
				for (int ct = 0; ct < c.length;ct++)
					if (an.indexOf(c[ct]) < 0)
						return false;
				return true;	
	}
	
}