package com.glitter.server

fun main(){
    println("http://0.0.0.0:7676/GlitterVersion?AppName=shipscan".getWebResource(1000*10))
    println("http://0.0.0.0:7676/GetGlitter?AppName=shipscan&version=1.0.1".getWebResource(1000*10))
}