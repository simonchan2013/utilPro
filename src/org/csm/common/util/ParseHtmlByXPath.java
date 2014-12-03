package org.csm.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.transform.TransformerException;


import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ParseHtmlByXPath {
	
	private static final String PROPERTY_URL = "http://cyberneko.org/html/properties/default-encoding";
	
	private static final String PROPERTY_ENCODE = "utf-8";
	
	private static final String FEATURE_URL = "http://xml.org/sax/features/namespaces";
	
	private static final boolean FEATURE_STATIS = false;
	
	private DOMParser parser;
	
	
	
	public static void main(String[] args) {
		DOMParser parser = new DOMParser();
		try {
			// 设置网页的默认编码
			parser.setProperty("http://cyberneko.org/html/properties/default-encoding","utf-8");
			/*
			 * The Xerces HTML DOM implementation does not support namespaces
			 * and cannot represent XHTML documents with namespace information.
			 * Therefore, in order to use the default HTML DOM implementation with NekoHTML's
			 * DOMParser to parse XHTML documents, you must turn off namespace processing.
			 */
			parser.setFeature("http://xml.org/sax/features/namespaces", false);
			
			// 可以修改获取页面的编码方式，在最后的那个参数
			String urlStr = "http://de.wikipedia.org/wiki/Liste_der_St%C3%A4dte_in_Deutschland";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream(),"utf-8"));
			parser.parse(new InputSource(bufferedReader));
			bufferedReader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Document doc = parser.getDocument();
		
		// xpath语句
		String itemXpath0 = "//DL/DD";
		String itemXpath1 = "//DL/DD/A";
		String itemXpath2 = "//DL/DD/text()";
		NodeList items0;
		NodeList items1;
		NodeList items2;
		
		//NodeList subItems;
		try {
			items0 = XPathAPI.selectNodeList(doc,itemXpath0);
			items1 = XPathAPI.selectNodeList(doc,itemXpath1);
			items2 = XPathAPI.selectNodeList(doc,itemXpath2);
			//System.out.println("found: " + items0.getLength());
			//System.out.println("found: " + items1.getLength());
			//System.out.println("found: " + items2.getLength());
			//System.out.println("\n\n\n");
			
			
			Node item0 = null,item1 = null,item2 = null;
			for (int i=0; i<items0.getLength(); i++) {
				item0 = items0.item(i);
				item1 = items1.item(i);
				item2 = items2.item(i);
				
				//System.out.println(i + "	:" + item0.getTextContent());
				System.out.println(item1.getTextContent() + "," + item2.getTextContent().replaceAll("[ ()]", ""));
				//System.out.println(i + "	:" + item.getNamespaceURI());
			}
		}
		catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}