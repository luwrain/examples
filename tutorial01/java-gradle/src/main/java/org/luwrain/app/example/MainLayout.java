package org.luwrain.app.example;

import org.luwrain.core.*;
import org.luwrain.app.base.*;
import org.luwrain.controls.*;

public class MainLayout extends LayoutBase {
private NavigationArea area;
public MainLayout(App app) {
super(app);
this.area = new SimpleArea(getControlContext(), "hello, world", new String[] {"a","b"});
setAreaLayout(this.area, null);
}
}