package nl.sogeti.leaserateservice.di.carservice.provider;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.HashSet;
import java.util.List;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthTokenProvider implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext requestContext) {
        requestContext.getStringHeaders().add(HttpHeaders.AUTHORIZATION, generateJwtToken());
    }

    private String generateJwtToken() {
        return Jwt.issuer("http://localhost:8000/")
                  .upn("fun user")
                  .groups(new HashSet<>(List.of("Fun-User")))
                  .sign();
    }
}
