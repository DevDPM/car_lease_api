package nl.sogeti.leaserateservice.di.carservice.exception.mapper;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import nl.sogeti.leaserateservice.di.carservice.exception.ApiException;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class ApiExceptionMapper implements ResponseExceptionMapper<ApiException> {

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status >= 400;
    }

    @Override
    public ApiException toThrowable(Response response) {
        return new ApiException(response);
    }
}