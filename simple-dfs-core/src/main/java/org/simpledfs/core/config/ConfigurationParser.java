package org.simpledfs.core.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class ConfigurationParser {

    public static Properties read(String fileName){
        File file = new File(fileName);
        SAXReader reader = new SAXReader();
        Properties properties = new Properties();
        try {
            Document document = reader.read(file);
            Element node = document.getRootElement();
            List<Element> elementList = node.elements();
            for (Element e : elementList){
                properties.setProperty(e.elementText("name"), e.elementText("value"));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return properties;

    }

}
