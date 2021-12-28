package org.simpledfs.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class ConfigurationParser {

    private static Logger LOGGER = LogManager.getLogger(ConfigurationParser.class);

    public Properties read(String fileName) throws DocumentException {
        File file = new File(fileName);
        SAXReader reader = new SAXReader();
        Properties properties = new Properties();

        Document document = reader.read(file);
        Element node = document.getRootElement();
        List<Element> elementList = node.elements();
        for (Element e : elementList){
            checkFiled(e);
            properties.setProperty(e.elementText("name"), e.elementText("value"));
        }

        return properties;

    }


    private void checkFiled(Element e) throws DocumentException{
        if (e.elementText("name") == null || e.elementText("name").isEmpty()) {
            throw new DocumentException("configuration item <name> is error... ");
        }
        if (e.elementText("value") == null || e.elementText("value").isEmpty()) {
            throw new DocumentException("configuration item <value> is error... ");
        }

    }

}
