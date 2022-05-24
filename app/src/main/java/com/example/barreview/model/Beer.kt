package com.example.barreview.model

import java.util.*

data class Beer (val id: String?,val color: String?,val name: String?, val brand: String?, var rating: Float?, val created_at:Date?){
    constructor() : this( null,null, null, null, null,null) {}
}