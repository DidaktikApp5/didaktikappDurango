package com.icjardinapps.dm2.durango.db

import android.content.Context
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties

class ConexionDb(context: Context) {
    private val dbUrl:String
    private val dbUser:String
    private val dbPassword:String

    init {
        val properties = Properties()

        try {
            val inputStream = context.assets.open("config.properties")
            properties.load(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dbUrl = properties.getProperty("db_url") ?: throw IllegalArgumentException("db_url no definido")
        dbUser = properties.getProperty("db_user") ?: throw IllegalArgumentException("db_user no definido")
        dbPassword = properties.getProperty("db_password") ?: throw IllegalArgumentException("db_password no definido")
    }

    fun obtenerConexion(): Connection? {
        return try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }
}