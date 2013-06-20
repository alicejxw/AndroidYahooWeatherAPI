package com.tuntunkun.chaos.weather;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Exception;

/*
 * Document Object Model Library
 */
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * XML Parser Library
 */
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created with IntelliJ IDEA.
 * User: naoya sawada
 * Date: 13/06/09
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class Weathers extends ArrayList<Weather> {
    /*
     * xml of Yahoo Weather API
     */
    private String xml;

    /*
     * parse data
     */
    private int areacode;
    private String coordinates;

    /*
     * constructor
     */
    public Weathers(String xml) throws XMLParseException {
        this.xml = xml;
        this.areacode = 0;
        this.coordinates = "";

        this.parseXML();
    }

    /*
     * parse xml data
     */
    private void parseXML() throws XMLParseException {
        try {
            /*
             * prepare for parse XML
             */
            DocumentBuilderFactory document_factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder document_builder = document_factory.newDocumentBuilder();

             /*
             * parse XML to DOM(Document Object Model)
             */
            ByteArrayInputStream stream = new ByteArrayInputStream(this.xml.getBytes());
            Document dom = document_builder.parse(stream);

            /*
             * find WeatherAreaCode tag
             */
            NodeList areacode_tags = dom.getElementsByTagName("WeatherAreaCode");
            Node areacode_tag = areacode_tags.item(0);
            if (areacode_tag.getNodeName().equals("WeatherAreaCode")) {
                this.areacode = Integer.parseInt(areacode_tag.getTextContent());
            }

            /*
             * find WeatherAreaCode tag
             */
            NodeList coordinates_tags = dom.getElementsByTagName("Coordinates");
            Node coordinates_tag = coordinates_tags.item(0);
            if (coordinates_tag.getNodeName().equals("Coordinates")) {
                this.coordinates = coordinates_tag.getTextContent();
            }

            /*
             * find Weather tag, and search it in
             */
            NodeList weather_tags = dom.getElementsByTagName("Weather");
            for (int i = 0; i < weather_tags.getLength(); i++) {
                /*
                 * get weather tag
                 */
                Node weather_tag = weather_tags.item(i);
                NodeList weather_elements = weather_tag.getChildNodes();

                /* weather elements */
                String type        = null;
                String date        = null;
                String rainfall    = null;

                /*
                 * Analyze detail of weather tag
                 */
                for (int j = 0; j < weather_elements.getLength(); j++) {
                    Node weather_element = weather_elements.item(j);

                    /*
                     * find Type tag
                     */
                    if (weather_element.getNodeName().equals("Type") == true) {
                        type = weather_element.getTextContent();
                    }

                    /*
                     * find Type date
                     */
                    if (weather_element.getNodeName().equals("Date") == true) {
                        date = weather_element.getTextContent();
                    }

                    /*
                     * find Type rainfall
                     */
                    if (weather_element.getNodeName().equals("Rainfall") == true) {
                        rainfall = weather_element.getTextContent();
                    }
                }

                /*
                 * register weather
                 */
                if (type == null || date == null || rainfall == null) continue;
                this.set(type, date, rainfall);
            }
        } catch (Exception e) {
            throw new XMLParseException("Failure to parse XML\n");
        }
    }

    /*
     * set weather object to own list
     */
    private void set(String type_str, String date_str, String rainfall_str) {
        Weather.TYPE type;
        Date date;
        double rainfall;

        /*
         * parse type
         */
        type = Weather.TYPE.OBSERVATION;
        if (type_str.equals("forecast") == true) type = Weather.TYPE.FORECAST;

        /*
         * parse date
         */
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            date = format.parse(date_str);
        } catch (Exception e) {
            return;
        }

        /*
         * parse rainfall
         */
        rainfall = Double.valueOf(rainfall_str);

        /*
         * add Weather
         */
        this.add(new Weather(type, date, rainfall));
    }

    /*
     * weathers information -> String
     */
    public String toString() {
        return String.format("<weathers areacode=%d, coordinates=%s>", this.areacode, this.coordinates);
    }
}
