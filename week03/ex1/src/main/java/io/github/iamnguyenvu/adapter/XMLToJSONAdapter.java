package io.github.iamnguyenvu.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ADAPTER PATTERN - XML to JSON Conversion
 */

// Target Interface - System hiện tại cần JSON
interface JSONProvider {
    String getUserAsJSON(int userId) throws Exception;
    String getProductAsJSON(int productId) throws Exception;
}

// Adaptee - Legacy system cung cấp XML
@Data
@NoArgsConstructor
@AllArgsConstructor
class XMLDataService {
    
    public String getUserAsXML(int userId) {
        return String.format(
            "<?xml version=\"1.0\"?>\n" +
            "<user>\n" +
            "  <id>%d</id>\n" +
            "  <name>User %d</name>\n" +
            "  <email>user%d@example.com</email>\n" +
            "</user>",
            userId, userId, userId
        );
    }

    public String getProductAsXML(int productId) {
        return String.format(
            "<?xml version=\"1.0\"?>\n" +
            "<product>\n" +
            "  <id>%d</id>\n" +
            "  <name>Product %d</name>\n" +
            "  <price>%.2f</price>\n" +
            "</product>",
            productId, productId, productId * 10.99
        );
    }
}

// Adapter - Chuyển đổi XML sang JSON
public class XMLToJSONAdapter implements JSONProvider {
    private XMLDataService xmlService;
    private XmlMapper xmlMapper;
    private ObjectMapper jsonMapper;

    public XMLToJSONAdapter(XMLDataService xmlService) {
        this.xmlService = xmlService;
        this.xmlMapper = new XmlMapper();
        this.jsonMapper = new ObjectMapper();
    }

    @Override
    public String getUserAsJSON(int userId) throws Exception {
        String xml = xmlService.getUserAsXML(userId);
        User user = xmlMapper.readValue(xml, User.class);
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
    }

    @Override
    public String getProductAsJSON(int productId) throws Exception {
        String xml = xmlService.getProductAsXML(productId);
        Product product = xmlMapper.readValue(xml, Product.class);
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(product);
    }
}

// DTOs
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private int id;
    private String name;
    private String email;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Product {
    private int id;
    private String name;
    private double price;
}
