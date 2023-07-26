package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.example.model.Data;
import org.example.model.RecommendedProduct;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        try {
            // Get JSON
            File file = new File(Objects.requireNonNull(Main.class.getClassLoader().getResource("Sample_data.json")).getFile());

            // Jackson Object Mapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON
            Data data = objectMapper.readValue(file, Data.class);

            // Start Velocity
            Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            Velocity.init();

            // Get Template
            Template template = Velocity.getTemplate("SecondTask.vm");

            // Create Template
            VelocityContext context = new VelocityContext();
            context.put("geo_id", data.getGeo_id());
            context.put("geo_name", data.getGeo_name());
            context.put("geo_image", data.getGeo_image());
            context.put("geo_link", data.getGeo_link());

            List<RecommendedProduct> recommendedProducts = data.getRecommended_products();
            context.put("recommendedProducts", recommendedProducts);

            // Get Result
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            // Show Result
            System.out.println(writer);

            // Generate new HTML file
            try {
                // Name
                String outputFileName = "SecondTask.html";

                // FileWriter
                FileWriter fileWriter = new FileWriter(outputFileName);
                fileWriter.write(writer.toString());
                fileWriter.close();

                System.out.println("File generated " + outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
