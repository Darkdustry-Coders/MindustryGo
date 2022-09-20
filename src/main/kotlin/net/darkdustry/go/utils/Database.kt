package net.darkdustry.go.utils

import arc.files.Fi
import org.iq80.leveldb.*
import org.iq80.leveldb.impl.Iq80DBFactory
import java.io.*

object Database {
    private val mainDir = Fi(".mindustrygo")
    private val options = Options().createIfMissing(true)!!

    fun init() {
        if (!mainDir.exists()) mainDir.mkdirs()
    }

    fun write(key: ByteArray, value: ByteArray) {
        val db: DB = Iq80DBFactory.factory.open(
            File("${mainDir.absolutePath()}/Players"),
            options
        )

        db.use {
            db.put(key, value)
        }
    }

    fun get(key: ByteArray): ByteArray {
        val db: DB = Iq80DBFactory.factory.open(
            File("${mainDir.absolutePath()}/Players"),
            options
        )

        db.use {
            return db.get(key)
        }
    }

    fun rewrite(key: ByteArray, value: ByteArray) {
        val db: DB = Iq80DBFactory.factory.open(
            File("${mainDir.absolutePath()}/Players"),
            options
        )

        db.use {
            db.delete(key).apply {
                db.put(key, value)
            }
        }
    }

    fun delete(key: ByteArray) {
        val db: DB = Iq80DBFactory.factory.open(
            File("${mainDir.absolutePath()}/Players"),
            options
        )

        db.use {
            db.delete(key)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Serializable> fromByteArray(byteArray: ByteArray): T {
        val byteArrayInputStream = ByteArrayInputStream(byteArray)
        val objectInput: ObjectInput
        objectInput = ObjectInputStream(byteArrayInputStream)
        val result = objectInput.readObject() as T
        objectInput.close()
        byteArrayInputStream.close()
        return result
    }

    fun Serializable.toByteArray(): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(this)
        objectOutputStream.flush()
        val result = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()
        objectOutputStream.close()
        return result
    }
}