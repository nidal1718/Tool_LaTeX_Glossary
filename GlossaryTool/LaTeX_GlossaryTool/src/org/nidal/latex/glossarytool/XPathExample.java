/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nidal.latex.glossarytool;

import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.xpath.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XPathExample {

  public static void main(String[] args) 
   throws ParserConfigurationException, SAXException, 
          IOException, XPathExpressionException {

    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true); // never forget this!
    DocumentBuilder builder = domFactory.newDocumentBuilder();
  //  Document doc = builder.parse("books.xml");
     Document doc = builder.parse("glossary.tex");

    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();
   //  XPathExample xpath = XPathFactory.newInstance().newXPath();
 //   XPath xpath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xpath.compile("//newglossaryentry['latex']/name/text()");
    // XPathExpression expr = xpath.compile("//book[author='Neal Stephenson']/title/text()");

    Object result = expr.evaluate(doc, XPathConstants.NODESET);
    NodeList nodes = (NodeList) result;
    for (int i = 0; i < nodes.getLength(); i++) {
        System.out.println(nodes.item(i).getNodeValue()); 
    }

  }

    private XPathExpression compile(String bookauthorNeal_Stephensontitletext) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
