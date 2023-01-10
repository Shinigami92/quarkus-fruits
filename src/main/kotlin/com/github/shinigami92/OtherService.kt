package com.github.shinigami92.others

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.logging.Logger
import java.util.UUID
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.Response

data class Other(var id: UUID? = null)

@Path("/api/others")
@RegisterRestClient(baseUri = "http://localhost:8079")
@RegisterProvider(OtherExceptionMapper::class)
interface OtherService {

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: UUID): Other
}

class OtherExceptionMapper : ResponseExceptionMapper<Exception> {
    @Inject
    lateinit var logger: Logger

    override fun toThrowable(response: Response): Exception {
        // This should log something, even on "java.net.ConnectException: Connection refused"
        logger.warn("OtherService not available: " + response.status)
        return Exception("OtherService not available")
    }
}
