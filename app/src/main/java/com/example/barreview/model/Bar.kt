package com.example.barreview.model

data class Bar(val id: String?,val name: String?, val address: String?, val neighborhood:String?, var rating: Float?){
    constructor() : this( null,null, null, "", null) {}
}