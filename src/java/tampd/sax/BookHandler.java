/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampd.sax;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tampd.dto.BookDTO;

/**
 *
 * @author USER
 */
public class BookHandler extends DefaultHandler {
    
    private String id, bookTitle, price, authorTitlee, firstName, lastName;
    private int count;
    private BookDTO book;
    private final String priceFrom;
    private final String priceTo;
    private String currentTagName;
    private boolean foundAuthorTitle, foundPrice, found;
    private List<BookDTO> listBook;
    
    public List<BookDTO> getListBook() {
        return listBook;
    }
    
    public BookHandler(String priceFrom, String priceTo) {
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.found = false;
        this.foundAuthorTitle = false;
        this.foundPrice = false;
        this.count = 0;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes); //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            currentTagName = qName;
            if (qName.equals("author")) {
                String authorTitle = attributes.getValue("title");
                if (authorTitle.equals("Mr")) {
                    authorTitlee = authorTitle;
                    foundAuthorTitle = true;
                }
            }
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length); //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            String str = new String(ch, start, length);
            if (currentTagName.equals("id")) {
                id = str.trim();
            } else if (currentTagName.equals("title")) {
                bookTitle = str.trim();
            } else if (currentTagName.equals("firstname")) {
                firstName = str.trim();
            } else if (currentTagName.equals("lastname")) {
                lastName = str.trim();
            } else if (currentTagName.equals("price")) {
                price = str.trim();
                if (Double.parseDouble(price) >= Double.parseDouble(priceFrom) && Double.parseDouble(price) <= Double.parseDouble(priceTo) && foundAuthorTitle) {
                    
                    found = true;
                }
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        
        currentTagName = "";
        
        if (qName.equals("book")) {
            if (found) {
                if (this.listBook == null) {
                    this.listBook = new ArrayList<>();
                }
                book = new BookDTO();
                book.setId(id);
                book.setBookTitle(bookTitle);
                book.setPrice(Double.parseDouble(price));
                book.setFirstName(firstName);
                book.setLastName(lastName);
                book.setAuthorTitle(authorTitlee);
                listBook.add(book);
            }
            found = false;
            book = null;
            foundAuthorTitle = false;
            foundPrice = false;
        }
    }
    
}
