package com.tian.common.app.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

object GsonUtil {

    /**
     * Volatile以确保延迟初始化的线程安全
     */
    @Volatile
    private var gson: Gson? = null

    /**
     * json字符串转Bean对象
     *
     * @param json  json字符串
     * @param clazz Bean类类型
     * @param <T>   返回类型
     * @return T，返回Bean对象，可能为null
     */
    @JvmStatic
    fun <T> gsonToBean(json: String?, clazz: Class<T>): T? {
        return getObject(json, clazz)
    }

    /**
     * json字符串转List集合
     *
     * @param json  json字符串
     * @param clazz 类类型
     * @param <T>  返回类型
     * @return List<T>，返回List集合，可能为null
     */
    @JvmStatic
    fun <T> gsonToList(json: String?, clazz: Class<T>): List<T>? {
        return getObject(json, getListType(clazz))
    }

    /**
     * 获取List的TypeToken
     *
     * @param clazz List中元素的类型
     * @param <T>   返回List的TypeToken
     * @return Type
     */
    fun <T> getListType(clazz: Class<T>): Type {
        return TypeToken.getParameterized(List::class.java, clazz).type
    }

    /**
     * json字符串转对象，解析失败，不会抛出异常，会直接返回null
     *
     * @param json json字符串
     * @param type 对象类类型
     * @param <T>  返回类型
     * @return T，返回对象有可能为空
     */
    @JvmStatic
    fun <T> getObject(json: String?, type: Type): T? {
        if (json.isNullOrEmpty()) {
            // 如果json为空或null，直接返回null
            return null
        }

        return try {
            fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            // 如果发生JsonSyntaxException，说明JSON格式错误
            System.err.println("Failed to parse JSON: $json")
            e.printStackTrace()
            null
        } catch (e: Exception) {
            // 其他类型的异常，继续捕获并打印
            System.err.println("Unexpected error while parsing JSON: $json")
            e.printStackTrace()
            null
        }
    }

    /**
     * json字符串转对象，解析失败，将抛出对应的{@link JsonSyntaxException}异常，根据异常可查找原因
     *
     * @param json json字符串
     * @param type 对象类类型
     * @param <T>  返回类型
     * @return T，返回对象不为空
     */
    @JvmStatic
    fun <T> fromJson(json: String?, type: Type): T {
        if (json.isNullOrEmpty()) {
            throw JsonSyntaxException("Input JSON is null or empty.")
        }

        val gsonInstance = buildGson()
        val result: T = gsonInstance.fromJson(json, type)
            ?: throw JsonSyntaxException("The string '$json' could not be deserialized to $type object")
        return result
    }

    /**
     * 将对象转换为JSON字符串。
     *
     * @param object 要转换的对象
     * @return 对象的JSON字符串表示形式
     */
    @JvmStatic
    fun toJson(`object`: Any): String {
        return buildGson().toJson(`object`)
    }

    /**
     * 构建并返回带有自定义适配器的单例Gson实例。
     *
     * @return Gson instance
     */
    private fun buildGson(): Gson {
        return gson ?: synchronized(this) {
            gson ?: GsonBuilder()
                .disableHtmlEscaping()
                .registerTypeAdapter(String::class.java, StringAdapter())
                .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
                .registerTypeAdapter(Integer::class.java, IntegerDefault0Adapter())
                .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
                .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
                .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
                .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
                .create().also { gson = it }
        }
    }

    class StringAdapter : JsonSerializer<String>, JsonDeserializer<String> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): String {
            return if (json is JsonPrimitive) {
                json.asString
            } else {
                json.toString()
            }
        }

        override fun serialize(
            src: String?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class IntegerDefault0Adapter : JsonSerializer<Int>, JsonDeserializer<Int> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Int {
            return try {
                val str = json?.asString
                if (str.isNullOrEmpty() || str == "null") {
                    0
                } else {
                    json.asInt
                }
            } catch (e: Exception) {
                0
            }
        }

        override fun serialize(
            src: Int?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class DoubleDefault0Adapter : JsonSerializer<Double>, JsonDeserializer<Double> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Double {
            return try {
                val str = json?.asString
                if (str.isNullOrEmpty() || str == "null") {
                    0.00
                } else {
                    json.asDouble
                }
            } catch (e: Exception) {
                0.00
            }
        }

        override fun serialize(
            src: Double?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class LongDefault0Adapter : JsonSerializer<Long>, JsonDeserializer<Long> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Long {
            return try {
                val str = json?.asString
                if (str.isNullOrEmpty() || str == "null") {
                    0L
                } else {
                    json.asLong
                }
            } catch (e: Exception) {
                0L
            }
        }

        override fun serialize(
            src: Long?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src)
        }
    }
}