/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private static final EntityManagerFactory factory;

    static {
        try {
            // Las credenciales se leen de variables de entorno para no guardarlas en el código
            Map<String, String> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url",
                    getenvOr("DB_URL", "jdbc:mariadb://localhost:3306/coleccion_artefactos_db"));
            props.put("jakarta.persistence.jdbc.user", getenvOr("DB_USER", "root"));
            props.put("jakarta.persistence.jdbc.password", getenvOr("DB_PASSWORD", ""));
            factory = Persistence.createEntityManagerFactory("ArtefactoPU", props);
        } catch (Throwable ex) {
            System.err.println("Error inicializando EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static String getenvOr(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return factory;
    }
    
    public static void shutdown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
                      