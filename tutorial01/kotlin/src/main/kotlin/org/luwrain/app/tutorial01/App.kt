
package org.luwrain.app.tutorial01

import java.util.*

import org.luwrain.core.*
import org.luwrain.app.base.*

class App: AppBase<Strings>("luwrain.tutorial01", Strings::class.java) {
    private lateinit var mainLayout: MainLayout

override fun onAppInit(): AreaLayout {
this.mainLayout = MainLayout(this)
setAppName(getStrings().appName())
return mainLayout.getAreaLayout()
}

override fun onEscape(): Boolean {
closeApp()
return true
}
}
