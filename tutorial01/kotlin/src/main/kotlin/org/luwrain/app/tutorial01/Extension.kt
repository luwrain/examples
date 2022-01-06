package org.luwrain.app.vk2

import org.luwrain.base.*
import org.luwrain.core.*
import org.luwrain.core.extensions.*

class Extension: EmptyExtension() {
    override fun getCommands(luwrain: Luwrain): Array<Command> {
	return arrayOf(SimpleShortcutCommand("vk2"))
    }

    override fun getExtObjects(luwrain: Luwrain): Array<ExtensionObject> {
	return arrayOf(SimpleShortcut("vk2", App::class.java))
    }

override fun i18nExtension(luwrain: Luwrain, i18n: org.luwrain.i18n.I18nExtension) {
		i18n.addCommandTitle("en", "vk2", "VKontakte")
	i18n.addCommandTitle("ru", "vk2", "ВКонтакте")
	    i18n.addStrings("ru", "luwrain.vk2", Strings())
}

}
