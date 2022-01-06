
package org.luwrain.app.tutorial01

import org.luwrain.core.*

class Extension: EmptyExtension() {
    override fun getCommands(luwrain: Luwrain): Array<Command> {
	return arrayOf(SimpleShortcutCommand("tutorial01"))
    }

    override fun getExtObjects(luwrain: Luwrain): Array<ExtensionObject> {
	return arrayOf(SimpleShortcut("tutorial01", App::class.java))
    }

    override fun i18nExtension(luwrain: Luwrain, i18n: org.luwrain.i18n.I18nExtension) {
	i18n.addCommandTitle("en", "tutorial01", "Tutorial 1")
	i18n.addCommandTitle("ru", "tutorial01", "Пример 1")
	i18n.addStrings("en", "luwrain.tutorial01", Strings())
	i18n.addStrings("ru", "luwrain.tutorial01", Strings())
    }
}
