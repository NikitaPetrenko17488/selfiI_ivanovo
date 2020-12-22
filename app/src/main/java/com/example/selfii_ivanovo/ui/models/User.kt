package com.example.mtelevizor.models

data class User(

    var id:String="",
    var name:String="",
    var number:String="",
    var password:String="",
    var selfi_path:String="",
    var date_selfi:String="",
    var admin:Int=0,
    var raiting:Int=0
)