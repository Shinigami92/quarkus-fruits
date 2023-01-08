package com.github.shinigami92.fruits

import javax.validation.constraints.NotBlank

data class Fruit(

    var id: Long? = null,

    // @Column(nullable = false, columnDefinition = "TEXT")
    @field:NotBlank
    var name: String = "",
) // : PanacheEntity
