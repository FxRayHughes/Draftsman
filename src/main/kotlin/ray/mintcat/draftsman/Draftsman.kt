package ray.mintcat.draftsman

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject

object Draftsman: Plugin() {

    @TInject(value = ["settings.yml"], locale = "LOCALE-PRIORITY")
    lateinit var settings: TConfig
        private set

}