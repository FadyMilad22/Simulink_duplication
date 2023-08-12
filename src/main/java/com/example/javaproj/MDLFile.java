package com.example.javaproj;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MDLFile {
    public static Document readFile(String fileName) throws NotVaildMDLFileException {
        Document doc = null;

        String extension = fileName.substring(fileName.lastIndexOf("."));
        // Check extension
        if (!(extension.equals(".mdl")))
            throw new NotVaildMDLFileException("Not an MDL file.");
        // Check if file is empty
        else if (new File(fileName).length() == 0)
            throw new EmptyMDLFileException("The file is empty.");
        else {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                Scanner input = new Scanner(new File(fileName));
                boolean neededXmlFile = false;
                String line = "";
                StringBuilder xml = new StringBuilder();
                while (input.hasNext()) {
                    if (!neededXmlFile) {
                        line = input.nextLine();
                        //System.out.println(line);
                        if (line.contains("/simulink/systems/system_root.xml"))
                            neededXmlFile = true;
                    } else {
                        line = input.nextLine();
                        xml.append(line + "\n");
                        if (line.contains("</System>"))
                            break;
                    }
                }
                System.out.println(xml);
                // Optional, but recommended
                // Process XML securely, avoid attacks like XML External Entities (XXE)
                dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Parse XML content
                doc = db.parse(new ByteArrayInputStream(xml.toString().getBytes(StandardCharsets.UTF_8)));

            }catch (ParserConfigurationException | SAXException | IOException e) {
                    e.printStackTrace();
                }}
            return doc;}


                public static Block[] assembleBlocks(Document doc){
                //NodeList content = doc.getElementsByTagName("System");
                //System.out.println(content.getLength());


                NodeList Blocks = doc.getElementsByTagName("Block");
                //System.out.println(Blocks.getLength());

                Block[] fileBlocks = new Block[Blocks.getLength()];
                for(int i = 0; i < Blocks.getLength(); i++)
                {
                    Node node = Blocks.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        fileBlocks[i] = new Block();
                        fileBlocks[i].type = element.getAttribute("BlockType");
                        //System.out.println(element.getAttribute("BlockType"));

                        //System.out.println(fileBlocks[i].type = element.getAttribute("BlockType"));
                        fileBlocks[i].name = element.getAttribute("Name");
                        fileBlocks[i].SID = element.getAttribute("SID");
                        NodeList propertyList = element.getElementsByTagName("P");
                        fileBlocks[i].properties = new String[propertyList.getLength()][2];
                        for(int j = 0; j < propertyList.getLength();j++)
                        {
                        fileBlocks[i].properties[j][0] = ((Element)propertyList.item(j)).getAttribute("Name");
                        fileBlocks[i].properties[j][1] = propertyList.item(j).getTextContent();
                        }
                    }
                }

                return fileBlocks;}


public static Line[] assembleLines(Document doc){
                NodeList Lines = doc.getElementsByTagName("Line");
                Line[] fileLines = new Line[Lines.getLength()];
                for(int i = 0; i < Lines.getLength(); i++)
                {
                    Node node = Lines.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element ElementL = (Element) node;
                        NodeList propertyList = ElementL.getElementsByTagName("P");
                        fileLines[i] = new Line();

                        for(int j = 0; j < propertyList.getLength();j++)
                        {
                        if((j>0)&&(((Element)propertyList.item(j)).getAttribute("Name")).equals("ZOrder")) 
                        break;                          
                        fileLines[i].properties[j][0] = ((Element)propertyList.item(j)).getAttribute("Name");
                        fileLines[i].properties[j][1] = propertyList.item(j).getTextContent();
                        }


                        NodeList branches = ElementL.getElementsByTagName("Branch");
                        System.out.println(branches.getLength() + "#");
                        fileLines[i].BranchesFromLine = new Line[branches.getLength()];
                        for(int k = 0; k < branches.getLength();k++)
                        {
                            Node nodeB = branches.item(k);
                            Element ElementB = (Element) nodeB;
                            
                            fileLines[i].BranchesFromLine[k] = new Line();
                            System.out.println(k);
                            NodeList branchesPropertyList = ElementB.getElementsByTagName("P");
                            
                            fileLines[i].BranchesFromLine[k].properties = new String[branchesPropertyList.getLength()][2];
                            for(int j = 0; j < branchesPropertyList.getLength();j++)
                            {                        
                            fileLines[i].BranchesFromLine[k].properties[j][0] = ((Element)branchesPropertyList.item(j)).getAttribute("Name");
                            fileLines[i].BranchesFromLine[k].properties[j][1] = branchesPropertyList.item(j).getTextContent();
                            }
                            fileLines[i].BranchesFromLine[k].print();
                        }
                    }

                }

    return fileLines;}

}

