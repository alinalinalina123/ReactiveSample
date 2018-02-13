package podolyanchik.alina.reactivesample.model

import java.io.Serializable

class Note: Serializable {
    var id: Int = 0
    var title: String? = null
    var description: String? = null

    constructor() {}

    constructor(id: Int, title: String, description: String) {
        this.id = id
        this.title = title
        this.description = description
    }
}