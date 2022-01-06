package org.luwrain.app.vk2

import com.vk.api.sdk.objects.messages.*;

import org.luwrain.core.*
import org.luwrain.core.events.*
import org.luwrain.controls.*
import org.luwrain.app.base.*

class MainLayout: LayoutBase {
    val wallArea: ListArea
    val chatsArea: ListArea
    val friendshipRequestsArea: ListArea

var wallOffset = 0

    constructor(app: App): super(app)  {
    	val conversationsAction = action("conversations", "Беседы", app.conversationsKey, { app.layouts().conversations(); true })
	val friendsAction = action("friends", app.getStrings().actionFriends(), app.friendsKey, { app.openFriends() })

val newPostAction = action("new-post", "Новый пост", InputEvent(InputEvent.Special.INSERT), { app.layouts().newPost(); true })
val wallPrevPageAction = action("prev-page", "Предыдущая страница", InputEvent(InputEvent.Special.PAGE_UP), { onWallPage(-20) })
val wallNextPageAction = action("next-page", "Следующая страница", InputEvent(InputEvent.Special.PAGE_DOWN), { onWallPage(20) })
	this.wallArea = ListArea({
	    val params = ListArea.Params()
	    params.context = getControlContext()
	    params.name = app.getStrings().wallAreaName()
	    params.model = ListUtils.ListModel(app.homeWallPosts)
	    params.appearance = WallAppearance(app)
	    params}())
	    val wallActions = actions(newPostAction, wallPrevPageAction, wallNextPageAction, conversationsAction, friendsAction)

	this.chatsArea = ListArea({
	    val params = ListArea.Params()
	    params.context = getControlContext()
	    params.name = "Диалоги"
	    params.model = ListUtils.ListModel(app.conversations)
	    params.clickHandler = ListArea.ClickHandler {area, index, obj -> onChatsClick(obj) }
	    params.appearance = ConversationsAppearance(app)
	    params}())

	this.friendshipRequestsArea = ListArea({
	    val params = ListArea.Params()
	    params.context = getControlContext()
	    params.name = "Запросы в друзья"
	    params.model = ListUtils.FixedModel()
	    params.appearance = ListUtils.DefaultAppearance(getControlContext())
	    params}())

	    setAreaLayout(AreaLayout.LEFT_TOP_BOTTOM,
	    wallArea, wallActions,
	    chatsArea, actions(conversationsAction, friendsAction),
	    friendshipRequestsArea, actions(conversationsAction, friendsAction))
    }

fun onChatsClick(item: Any): Boolean {
if (item !is ConversationWithMessage || app !is App)
return false
val taskId = app.newTaskId()
return app.runTask(taskId, {
app.operations.updateMessagesHistory(item)
app.finishedTask(taskId, {
app.layouts().conversations()
})
})
}

private fun onWallPage(step: Int): Boolean {
if (app !is App)
return false
if (wallOffset + step < 0)
return false
wallOffset += step
val taskId = app.newTaskId()
return app.runTask(taskId, {
app.operations.updateHomeWall(wallOffset)
app.finishedTask(taskId, {
wallArea.refresh()
wallArea.reset(false)
})
});
}
}
