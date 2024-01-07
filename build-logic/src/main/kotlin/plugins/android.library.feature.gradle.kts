import config.configurationKotlinSerialization
import config.configureAndroidCompose
import config.configureAndroidLibrary
import config.configureAndroidTest
import config.configureCoroutineAndroid
import config.configureHiltAndroid
import config.configureKotlinTest

configureAndroidLibrary()

configureAndroidCompose()

configureCoroutineAndroid()

configureHiltAndroid()

configurationKotlinSerialization()

configureAndroidTest()
configureKotlinTest()
