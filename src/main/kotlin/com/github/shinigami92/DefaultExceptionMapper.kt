package com.github.shinigami92

import io.quarkus.security.ForbiddenException
import io.quarkus.security.UnauthorizedException
import org.jboss.logging.Logger
import java.io.PrintWriter
import java.io.StringWriter
import javax.inject.Inject
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class DefaultExceptionMapper : ExceptionMapper<Exception> {

    @Inject
    lateinit var logger: Logger

    override fun toResponse(e: Exception): Response {
        logger.error(e.message, e)

        val status = status(e)

        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))

        val entity = mapOf(
            "title" to e.javaClass.simpleName,
            "message" to e.message,
            "status" to status.statusCode,
            "stacktrace" to sw.toString().split("\n").map { it.trim() },
        )

        return Response.status(status).entity(entity).build()
    }

    private fun status(e: Exception): Status {
        return when (e) {
            is IllegalArgumentException -> Status.BAD_REQUEST
            is BadRequestException -> Status.BAD_REQUEST
            is UnauthorizedException -> Status.UNAUTHORIZED
            is ForbiddenException -> Status.FORBIDDEN
            is NotFoundException -> Status.NOT_FOUND
            is WebApplicationException -> Status.fromStatusCode(e.response.status)
            else -> Status.INTERNAL_SERVER_ERROR
        }
    }
}
