package com.icjardinapps.dm2.durango.db

import android.content.Context
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Properties

/**
 * Clase para la conexion de la base de datos
 *
 * @version 1.0
 * @author DidaktikAppDurango
 */
class ConexionDb(context: Context) {
    /**
     * Atributos de la clase
     */
    private val dbUrl:String
    private val dbUser:String
    private val dbPassword:String
    init {
        // Crear instancia de Properties
        val properties = Properties()

        // Cargar el archivo .properties
        try {
            // Usar el Context para acceder a assets
            val inputStream = context.assets.open("config.properties")
            properties.load(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Obtener valores de las propiedades desde el archivo
        dbUrl = properties.getProperty("db_url") ?: throw IllegalArgumentException("db_url no definido")
        dbUser = properties.getProperty("db_user") ?: throw IllegalArgumentException("db_user no definido")
        dbPassword = properties.getProperty("db_password") ?: throw IllegalArgumentException("db_password no definido")
    }

    /**
     * Obtiene una conexión a la base de datos utilizando los parámetros configurados.
     *
     * @return La conexión a la base de datos si es exitosa, o null en caso de error.
     */
    fun obtenerConexion(): Connection? {
        return try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Guarda un nuevo alumno en la base de datos.
     *
     * @param usuario El nombre del usuario (en este caso, el nombre de usuario que representa al alumno).
     * @return true si la operación es exitosa, false en caso contrario.
     */
    fun guardarAlumnoBBDD(usuario: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query =
                    "INSERT INTO alumno (usuario, nombre, año_nacimiento, id_aplicacion) VALUES (?, ?, ?, ?)"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setString(2, usuario)
                statement.setInt(3, 2000)
                statement.setInt(4, 4)
                statement.executeUpdate()

                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    /**
     * Recupera todas las puntuaciones de los usuarios en la base de datos y las devuelve en orden descendente por nivel.
     *
     * @return Lista de puntuaciones, cada elemento es una cadena que combina el nombre del usuario y su nivel.
     */
    fun todasPuntuaciones(): MutableList<String> {
        val lista: MutableList<String> = mutableListOf()
        val conexion = obtenerConexion()

        if (conexion != null) {
            try {
                val query = "SELECT alumno_usuario, nivel FROM puntuacion where aplicacion_id_aplicacion=4 ORDER BY nivel desc"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    val alumno = resultSet.getString("alumno_usuario")
                    val nivel = resultSet.getInt("nivel")
                    lista.add("$alumno ----------------- $nivel")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return lista
    }

    /**
     * Guarda la puntuación final de un alumno en la base de datos.
     *
     * @param usuario El nombre del usuario.
     * @param puntuacion La puntuación obtenida por el usuario en la actividad.
     * @return true si la operación es exitosa, false en caso contrario.
     */
    fun guardarPuntuacionFinal(usuario: String, puntuacion: Int): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query =
                    "INSERT INTO puntuacion (alumno_usuario, aplicacion_id_aplicacion, nivel) VALUES (?,?,?)"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setInt(2, 4)
                statement.setInt(3, puntuacion)
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }
}
