package ai.decart.oasis

import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Loads small no-audio/no-desktop-service shims before the desktop Linux
 * webrtc-java binary on FCL's ARM64 Android runtime.
 */
object AndroidNativeBootstrap {
    private val libraries = listOf(
        "libdbus-1.so.3",
        "libX11.so.6",
        "libXfixes.so.3",
        "libXrandr.so.2",
        "libXcomposite.so.1",
        "libudev.so.1",
        "libpulse.so.0",
    )

    fun prepare() {
        if (System.getProperty("os.arch") != "aarch64") return

        val classLoader = AndroidNativeBootstrap::class.java.classLoader
        val resourceRoot = "oasis-natives/android-aarch64"
        if (classLoader.getResource("$resourceRoot/${libraries.first()}") == null) return

        val directory = Files.createTempDirectory("oasis-fcl-natives")
        directory.toFile().deleteOnExit()

        libraries.forEach { library ->
            val target = directory.resolve(library)
            classLoader.getResourceAsStream("$resourceRoot/$library").use { input ->
                requireNotNull(input) { "Missing bundled FCL native shim: $library" }
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING)
            }
            target.toFile().deleteOnExit()
            Utils.log("Preloading FCL compatibility shim: $library")
            System.load(target.toAbsolutePath().toString())
        }
    }
}
