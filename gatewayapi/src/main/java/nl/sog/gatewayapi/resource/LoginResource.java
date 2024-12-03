package nl.sog.gatewayapi.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.core.Response;
import nl.sog.gatewayapi.dto.JwtWrapperDto;

import java.util.HashSet;
import java.util.List;

@PermitAll
public class LoginResource implements LoginApi {

    @Override
    public Response getJWT(String name) {
        String jwt = generateJwt(name);
        JwtWrapperDto jwtDto = new JwtWrapperDto();
        jwtDto.setJsonWebToken(jwt);

        return Response.ok(jwtDto).build();
    }

    private String generateJwt(String name) {
        return Jwt.issuer("http://localhost:9999/")
                  .upn(name)
                  .groups(new HashSet<>(List.of("User")))
                  .sign();
    }
}
