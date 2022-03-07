/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author USER
 */
public class XMLUtils {

    public static Document parseFileToDom(String filePath) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File f = new File(filePath);
        Document doc = db.parse(f);
        return doc;
    }

    public static XPath createXPath() {
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xPath = xpf.newXPath();
        return xPath;
    }

    public static boolean transformDOMtoResult(Node node, String filePath) throws Exception {
        Source src = new DOMSource(node);
        File f = new File(filePath);
        Result result = new StreamResult(f);
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();
        trans.transform(src, result);
        return true;

    }

    public static void parseFileWithSax(String filePath, DefaultHandler handler) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sax = spf.newSAXParser();
        File file = new File(filePath);
        sax.parse(file, handler);
    }

    public static String getTextContent(XMLStreamReader reader, String tagName) throws Exception {
        if (reader != null) {
            while (reader.hasNext()) {
                int cursor = reader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    if (name.equals(tagName)) {
                        reader.next();
                        String result = reader.getText();
                        return result;
                    }
                }
            }
        }
        return null;

    }

//    public static void updateNodeinStAX(String updateId, String filePath, XMLStreamReader reader) throws Exception {
//        InputStream is = null;
//        OutputStream os = null;
//
//        XMLEventWriter writerEvent = null;
//        XMLEventReader readerEvent = null;
//
//        try {
//            XMLInputFactory xif = XMLInputFactory.newInstance();
//            is = new FileInputStream(filePath);
//            readerEvent = xif.createXMLEventReader(is);
//
//            XMLOutputFactory xof = XMLOutputFactory.newInstance();
//            os = new FileOutputStream(filePath + ".new");
//            writerEvent = xof.createXMLEventWriter(os);
//
//            JAXBContext jaxb = JAXBContext.newInstance(CakeDTO.class);
//            Unmarshaller unmarshall = jaxb.createUnmarshaller();
//            Marshaller marshall = jaxb.createMarshaller();
//            marshall.setProperty(Marshaller.JAXB_FRAGMENT, true);
//
//            while (readerEvent.hasNext()) {
//
//                if (readerEvent.peek().isStartElement()
//                        && readerEvent.peek().asStartElement().getName().getLocalPart().equals("cake")) {
//                    CakeDTO cake = (CakeDTO) unmarshall.unmarshal(readerEvent);
//                    if (cake.getId().getId().equals(updateId)) {
//                        cake.getId().setIsAvailable("false");
//                        cake.setQuantity("0");
//                    }
//                    marshall.marshal(cake, writerEvent);
//
//                }else {
//                    writerEvent.add(readerEvent.nextEvent());
//                }
//            }
//            writerEvent.flush();
//
//            File f = new File(filePath);
//            f.delete();
//
//            f = null;
//            f = new File(filePath + ".new");
//            f.renameTo(new File(filePath));
//
//            reader.close();
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (XMLStreamException ex) {
//            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//           is.close();
//           writerEvent.close();
//           reader.close();
//
//            try {
//                os.close();
//            } catch (IOException ex) {
//                Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }
    public static XMLStreamReader parseStAX(String filePath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        XMLStreamReader reader = xif.createXMLStreamReader(is);
        return reader;
    }

    public static XMLEventReader parseFileToStAXIterator(InputStream is) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(is);
        return reader;
    }

    public static boolean deleteNodeinStAX(String id, String xmlFileName, String realPath) {
        InputStream is = null;
        XMLEventReader reader = null;
        OutputStream os = null;
        XMLEventWriter writer = null;

        try {
            is = new FileInputStream(realPath + xmlFileName);
            reader = parseFileToStAXIterator(is);

            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            os = new FileOutputStream(realPath + xmlFileName + ".new");
            writer = xof.createXMLEventWriter(os);

            boolean delete = false;

            while (reader.hasNext()) {
//                String tmp = reader.getElementText();
                XMLEvent event = reader.nextEvent();
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT
                        && event.asStartElement().getName().getLocalPart().equals("book")) {
//                    System.out.println("id là: " + reader.getElementText());
//                    QName tmp = event.asStartElement().getName();
//                    System.out.println("id : " + tmp);
//                    Attribute attr = event.asStartElement().getAttributeByName(new QName("id"));
//                    String tmp = attr.getValue();
                    String tmp = "";
                    if (event.getEventType() == XMLEvent.CHARACTERS) {
                        System.out.println("test:  ..." + reader.getElementText());
                        tmp = reader.getElementText();
                    }
                    System.out.println("id là: " + tmp);
//                    if (tmp.equals(id)) {
//                        delete = true;
//                        continue;
//                    }
                } else if (event.getEventType() == XMLStreamConstants.END_ELEMENT
                        && event.asEndElement().getName().getLocalPart().equals("id")) {
                    if (delete) {
                        delete = false;
                        continue;
                    }
                } else if (delete) {
                    continue;
                }
                writer.add(event);
            }//end while
            writer.flush();
            writer.close();
            is.close();
            os.close();

            File file = new File(realPath + xmlFileName);
            file.delete();
            file = null;

            file = new File(realPath + xmlFileName + ".new");
            file.renameTo(new File(realPath + xmlFileName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static XMLStreamReader createSTAXReader(String filePath) throws Exception {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        XMLStreamReader reader = xif.createXMLStreamReader(is);
        return reader;
    }

    public static XMLStreamWriter createSTAXWrite(String filePath) throws Exception {
        XMLOutputFactory xof = XMLOutputFactory.newFactory();
        File file = new File(filePath);
        OutputStream os = new FileOutputStream(file);
        XMLStreamWriter writer = xof.createXMLStreamWriter(os, "UTF-8");
        return writer;
    }

    public static String getTextAttribute(XMLStreamReader reader, String tagName, String attr) throws Exception {
        while (reader.hasNext()) {
            int cusor = reader.next();
            if (cusor == XMLStreamConstants.START_ELEMENT) {
                String curtagName = reader.getLocalName();
                if (tagName.equals(curtagName)) {
                    String content = reader.getAttributeValue("", attr);
                    return content;
                }
            }
        }
        return null;
    }
}
