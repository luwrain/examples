package org.luwrain.app.example;

import org.luwrain.core.*;
import org.luwrain.app.base.*;

public class App extends AppBase<Strings> {
public App() {
super(Strings.NAME, Strings.class);
}
private MainLayout mainLayout;
@Override
protected AreaLayout onAppInit() {
this.mainLayout = new MainLayout(this);
setAppName(getStrings().appName());
return mainLayout.getAreaLayout();
}

@Override
public boolean onEscape() {
closeApp();
return true;
}

}