package com.library.common.config

import com.library.common.http.interceptor.IReturnCodeErrorInterceptor
import com.library.common.http.interceptor.IVersionDifInterceptor
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import retrofit2.Retrofit
import java.lang.RuntimeException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * @author yangbw
 * @date 2020/3/13.
 * module：
 * description：
 */
object AppConfig {
    private var retrofit: Retrofit? = null
    //服务地址
    private var baseUrl: String? = null
    //returnCode 正常态的值 真对不同接口返回支持单正常态值的返回，也支持增删改查不同正常态值的返回
    private var retSuccess: String? = null
    private var retSuccessList: List<String>? = null
    //日志开关
    private var logOpen = false
    //连接超时时间 单位秒
    private var connectTimeout = 10L
    //读超时时间
    private var readTimeout = 10L
    //写超时时间
    private var writeTimeout = 10L
    //异常处理的 相关拦截器
    //oKHttp拦截器
    private var okHttpInterceptors: MutableList<Interceptor>? = null
    //接口返回ReturnCode不是正常态拦截器
    private var retCodeInterceptors: MutableList<IReturnCodeErrorInterceptor>? = null
    //服务端版本和本地版本不一致拦截器
    private var versionDifInterceptors: MutableList<IVersionDifInterceptor>? = null
    //是否开启缓存
    private var cacheOpen = false
    private var configBuilder: ConfigBuilder? = null
    //是否开启aRouter
    private var aRouterOpen = true
    //设置多个BaseUrl，配合默认的DOMAIN_NAME
    private var mDomainNameHub: HashMap<String, HttpUrl>? = null
    private var isMoreBaseUrl = false
    //设置默认的DOMAIN_NAME
    const val DOMAIN_NAME = "BASE_DOMAIN_NAME"
    private const val DOMAIN_NAME_HEADER = "$DOMAIN_NAME: "
    /**
     * 如果在 Url 地址中加入此标识符, 意味着您想对此 Url 开启超级模式,
     * 框架会将 '=' 后面的数字作为 PathSize, 来确认最终需要被超级模式替换的 BaseUrl
     */
    const val IDENTIFICATION_PATH_SIZE = "#baseurl_path_size="
    //多个baseUrl就对应的不同returnCode 正常态的值
    private var retSuccessMap: HashMap<String, List<String>>? = null

    @Synchronized
    fun builder(): ConfigBuilder {
        if (configBuilder == null) {
            configBuilder =
                ConfigBuilder()
        }
        return configBuilder as ConfigBuilder
    }

    /**
     * get set方法
     *
     * @return
     */
    fun getRetrofit(): Retrofit {
        return retrofit ?: throw RuntimeException("retrofit is null")
    }

    fun getBaseUrl(): String {
        return baseUrl ?: throw RuntimeException("baseUrl is null")
    }

    fun getRetSuccess(): String? {
        return retSuccess
    }

    fun getRetSuccessList(): List<String> {
        return retSuccessList ?: ArrayList()
    }

    fun isARouterOpen(): Boolean {
        return aRouterOpen
    }

    fun getConnectTimeout(): Long {
        return connectTimeout
    }

    fun getReadTimeout(): Long {
        return readTimeout
    }

    fun getWriteTimeout(): Long {
        return writeTimeout
    }

    fun getOkHttpInterceptors(): List<Interceptor> {
        return okHttpInterceptors ?: ArrayList()
    }

    fun getRetCodeInterceptors(): List<IReturnCodeErrorInterceptor> {
        return retCodeInterceptors as MutableList<IReturnCodeErrorInterceptor>
    }

    fun getVersionDifInterceptors(): List<IVersionDifInterceptor>? {
        return versionDifInterceptors
    }

    fun isCacheOpen(): Boolean {
        return cacheOpen
    }

    fun getConfigBuilder(): ConfigBuilder? {
        return configBuilder
    }

    fun getMDomainNameHub(): HashMap<String, HttpUrl>? {
        return mDomainNameHub
    }


    fun getMoreBaseUrl(): Boolean {
        return isMoreBaseUrl
    }

    fun getDomainName(): String {
        return DOMAIN_NAME
    }

    fun getDomainNameHeader(): String {
        return DOMAIN_NAME_HEADER
    }

    fun getRetSuccessMap(): HashMap<String, List<String>>? {
        return retSuccessMap
    }

    class ConfigBuilder {
        fun setRetrofit(retrofit: Retrofit?): ConfigBuilder {
            AppConfig.retrofit = retrofit
            return this
        }

        fun setBaseUrl(baseUrl: String?): ConfigBuilder {
            AppConfig.baseUrl = baseUrl
            return this
        }

        fun setRetSuccess(retSuccess: String?): ConfigBuilder {
            AppConfig.retSuccess = retSuccess
            return this
        }

        fun setRetSuccessList(retSuccessList: List<String>?): ConfigBuilder {
            AppConfig.retSuccessList = retSuccessList
            return this
        }

        fun setRetSuccessList(retSuccessList: String): ConfigBuilder {
            AppConfig.retSuccessList =
                listOf(*retSuccessList.split(",").toTypedArray())
            return this
        }

        fun setLogOpen(logOpen: Boolean): ConfigBuilder {
            AppConfig.logOpen = logOpen
            return this
        }

        fun setARouterOpen(aRouterOpen: Boolean): ConfigBuilder {
            AppConfig.aRouterOpen = aRouterOpen
            return this
        }

        fun setConnectTimeout(connectTimeout: Long): ConfigBuilder {
            AppConfig.connectTimeout = connectTimeout
            return this
        }

        fun setReadTimeout(readTimeout: Long): ConfigBuilder {
            AppConfig.readTimeout = readTimeout
            return this
        }

        fun setWriteTimeout(writeTimeout: Long): ConfigBuilder {
            AppConfig.writeTimeout = writeTimeout
            return this
        }

        fun addOkHttpInterceptor(okHttpInterceptor: Interceptor): ConfigBuilder {
            if (okHttpInterceptors == null) {
                okHttpInterceptors = ArrayList()
            }
            okHttpInterceptors?.add(okHttpInterceptor)
            return this
        }

        fun addOkHttpInterceptor(
            mSwitch: Boolean,
            interceptor: Interceptor
        ): ConfigBuilder {
            if (mSwitch) if (okHttpInterceptors == null) {
                okHttpInterceptors = ArrayList()
            }
            okHttpInterceptors?.add(interceptor)
            return this
        }

        fun addRetCodeInterceptors(retCodeInterceptor: IReturnCodeErrorInterceptor): ConfigBuilder {
            if (retCodeInterceptors == null) {
                retCodeInterceptors = ArrayList()
            }
            retCodeInterceptors?.add(retCodeInterceptor)
            return this
        }

        fun addVersionDifInterceptors(versionDifInterceptor: IVersionDifInterceptor): ConfigBuilder {
            if (versionDifInterceptors == null) {
                versionDifInterceptors = ArrayList()
            }
            versionDifInterceptors?.add(versionDifInterceptor)
            return this
        }

        fun setCacheOpen(cacheOpen: Boolean): ConfigBuilder {
            AppConfig.cacheOpen = cacheOpen
            return this
        }

        fun setConfigBuilder(configBuilder: ConfigBuilder?): ConfigBuilder {
            AppConfig.configBuilder = configBuilder
            return this
        }

        fun addDomain(domainName: String, domainUrl: String): ConfigBuilder {
            if (mDomainNameHub == null) {
                mDomainNameHub = HashMap()
            }
            domainUrl.toHttpUrlOrNull()?.let { mDomainNameHub?.put(domainName, it) }
            //设置为多baseurl
            isMoreBaseUrl = true
            return this
        }

        fun addRetSuccess(domainNameKey: String, retSuccess: String): ConfigBuilder {
            if (retSuccessMap == null) {
                retSuccessMap = HashMap()
            }
            val retSuccessList =
                listOf(*retSuccess.split(",").toTypedArray())
            retSuccessMap?.put(domainNameKey, retSuccessList)
            return this
        }

        fun build() {}
    }
}