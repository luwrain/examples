package org.luwrain.app.example;

import org.luwrain.core.*;

public class Extension extends EmptyExtension {
@Override
public Command[] getCommands(Luwrain luwrain) {
return new Command[] {new SimpleShortcutCommand("example")};
}

@Override
public ExtensionObject[] getExtObjects(Luwrain luwrain) {
return new ExtensionObject[] {new SimpleShortcut("example", App.class)};
}

@Override
public void i18nExtension(Luwrain luwrain, org.luwrain.i18n.I18nExtension i18n) {
i18n.addCommandTitle("en", "example", "example 1");
	i18n.addCommandTitle("ru", "example", "Пример 1");
	i18n.addStrings("en", Strings.NAME, new Strings());
	i18n.addStrings("ru", Strings.NAME, new Strings());
}

}