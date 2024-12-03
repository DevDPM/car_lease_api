package nl.sog.carservice.security.jwt;

import io.smallrye.jwt.build.Jwt;

import java.util.HashSet;
import java.util.List;

public class GenerateToken {
    /**
     * Generate JWT token
     * Windows in terminal: mvn exec:java -D"exec.mainClass"="nl.sog.customer_service.security.jwt.GenerateToken" -D"exec.classpathScope"="test" -D"smallrye.jwt.sign.key.location"="privateKey.pem"
     */
    public static void main(String[] args) {
        String token =
                Jwt.issuer("https://localhost:9877/")
                        .upn("daniel@example.com")
                        .groups(new HashSet<>(List.of("Fun-User")))
                        .sign();
        System.out.println(token);
    }
}