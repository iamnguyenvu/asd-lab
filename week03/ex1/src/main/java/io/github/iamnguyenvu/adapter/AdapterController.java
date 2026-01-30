package io.github.iamnguyenvu.adapter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RestController
@RequestMapping("/api/adapter")
public class AdapterController {

    @GetMapping("/demo")
    public String runDemo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        try {
            demoXMLToJSON();
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        return "<pre>" + baos.toString() + "</pre>";
    }

    private void demoXMLToJSON() {
        System.out.println("=".repeat(70));
        System.out.println("ADAPTER PATTERN - XML TO JSON CONVERSION DEMO");
        System.out.println("=".repeat(70));

        // Legacy XML service
        XMLDataService xmlService = new XMLDataService();

        System.out.println("\n📜 LEGACY SYSTEM (XML):");
        System.out.println("─".repeat(70));
        System.out.println(xmlService.getUserAsXML(101));
        System.out.println();
        System.out.println(xmlService.getProductAsXML(501));

        // Adapter converts to JSON
        XMLToJSONAdapter adapter = new XMLToJSONAdapter(xmlService);

        System.out.println("\n🔄 ADAPTED TO JSON:");
        System.out.println("─".repeat(70));
        
        try {
            System.out.println("User as JSON:");
            System.out.println(adapter.getUserAsJSON(101));
            
            System.out.println("\nProduct as JSON:");
            System.out.println(adapter.getProductAsJSON(501));
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        System.out.println("\n✅ Adapter successfully bridges XML and JSON systems!");
    }
}
