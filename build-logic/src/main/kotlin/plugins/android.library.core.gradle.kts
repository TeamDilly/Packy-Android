import config.configurationKotlinSerialization
import config.configureAndroidLibrary
import config.configureAndroidTest
import config.configureCoroutineAndroid
import config.configureHiltAndroid
import config.configureKotlinTest

configureAndroidLibrary()

configurationKotlinSerialization()

configureCoroutineAndroid()

configureHiltAndroid()

configureAndroidTest()
configureKotlinTest()