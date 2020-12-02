package org.kodein.db

import kotlin.reflect.KClass

public interface DBWrite : KeyMaker {
    public fun <M : Any> put(model: M, vararg options: Options.Write): Key<M>
    public fun <M : Any> put(key: Key<M>, model: M, vararg options: Options.Write)

    public fun <M : Any> delete(type: KClass<M>, key: Key<M>, vararg options: Options.Write)
}

public inline fun <reified M : Any> DBWrite.delete(key: Key<M>, vararg options: Options.Write): Unit = delete(M::class, key, *options)

public inline fun <reified M : Any> DBWrite.deleteById(vararg id: Any, options: Array<out Options.Write> = emptyArray()): Unit = delete(key<M>(*id), *options)

public inline fun <reified M : Any> DBWrite.deleteAll(cursor: Cursor<M>, vararg options: Options.Write): Unit =
        cursor.useKeys { seq -> seq.forEach { delete(it, *options) } }
