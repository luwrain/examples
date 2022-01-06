
package org.luwrain.app.tutorial01

import org.luwrain.core.*
import org.luwrain.core.events.*
import org.luwrain.controls.*
import org.luwrain.app.base.*

class MainLayout: LayoutBase {
    val area : NavigationArea

    constructor(app: App): super(app)  {
	this.area = SimpleArea(getControlContext(), "Hello, world", arrayOf("Hello, world!", ""))
	setAreaLayout(area, null);
    }
}
