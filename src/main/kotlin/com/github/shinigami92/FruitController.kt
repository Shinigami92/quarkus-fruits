package com.github.shinigami92.fruits

import com.github.shinigami92.others.Other
import io.smallrye.mutiny.Uni
import org.jboss.logging.Logger
import org.jboss.resteasy.reactive.ResponseStatus
import org.jboss.resteasy.reactive.RestPath
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.BadRequestException
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.NotFoundException
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/fruit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
class FruitController(var fruitService: FruitService) {

    @Inject
    lateinit var logger: Logger

    @GET
    @Path("/{id}")
    fun findById(@RestPath("id") id: Long): Uni<Fruit> {
        return Uni.createFrom().nullItem<Fruit>().onItem().ifNull().failWith {
            logger.warn("Fruit not found: $id")
            NotFoundException("Fruit not found")
        }
    }

    @POST
    @ResponseStatus(201)
    fun create(@Valid fruit: Fruit): Uni<Fruit> {
        if (fruit.id != null) {
            logger.error("Fruit ID must not be set")
            throw BadRequestException("Fruit ID must not be set")
        }

        return Uni.createFrom().item(fruit)
    }

    @GET
    @Path("/other")
    fun other(): Other {
        return fruitService.other()
    }
}
