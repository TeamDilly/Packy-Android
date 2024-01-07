import config.configurationKotlinSerialization
import config.configureCoroutineKotlin
import config.configureHiltKotlin

plugins{
    kotlin("jvm")
}

configureCoroutineKotlin()

configurationKotlinSerialization()

configureHiltKotlin()