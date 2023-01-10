package com.github.shinigami92.fruits

import com.github.shinigami92.others.Other
import com.github.shinigami92.others.OtherService
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.logging.Logger
import java.util.UUID
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class FruitService(
    @RestClient val otherService: OtherService,
) {

    @Inject
    lateinit var logger: Logger

    fun other(): Other {
        return otherService.findById(UUID.randomUUID())
    }
}
