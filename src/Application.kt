package com.glitter.server

import com.glitter.server.util.File_Refresh
import com.glitter.server.util.ZipUtil
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.client.*
import io.ktor.features.CORS
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

//@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient() {
        install(CORS) {
            method(HttpMethod.Options)
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            method(HttpMethod.Delete)
            method(HttpMethod.Patch)
            header(HttpHeaders.AccessControlAllowHeaders)
            header(HttpHeaders.ContentType)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            anyHost()
        }
    }
    routing {
        //取得Glitter版本號
        get("/GlitterVersion") {
            call.respondText(File("resources/${call.parameters["AppName"]}/version").readText())
        }
        //下載Glitter
        get("/GetGlitter") {
            val nowVersion = File("resources/${call.parameters["AppName"]}/version").readText()
            while(ZipUtil.UnZip){Thread.sleep(1000)}
            var versionRout=File("resources/${call.parameters["AppName"]}/VersionHistory")
            if(!versionRout.exists()){
                versionRout.mkdir()
            }
            if (!File("$versionRout/${nowVersion}.zip").exists()) {
                println("unzip")
                ZipUtil.unzip("resources/${call.parameters["AppName"]}",nowVersion)
            }
            val file=File("$versionRout/${call.parameters["version"]}.zip")
            if(file.exists()){
                call.respondFile(File("$versionRout/${call.parameters["version"]}.zip"))
            }else{
                call.respondText("nodata")
            }
        }
        //文件更新
        File_Refresh.fileRefresh(this, arrayOf("resources"))
    }
}

