package org.luwrain.app.vk2

import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.client.*
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.users.*
import com.vk.api.sdk.objects.wall.*

import java.util.*

import org.luwrain.core.*
import org.luwrain.app.base.*
import org.luwrain.core.events.*

class App: AppBase<Strings>("luwrain.vk2", Strings::class.java) {
    val wallKey = InputEvent(InputEvent.Special.F5)
        val conversationsKey = InputEvent(InputEvent.Special.F6)
    val friendsKey = InputEvent(InputEvent.Special.F7)

val homeWallPosts = ArrayList<WallpostFull>()
val conversations = ArrayList<ConversationWithMessage>()
val chatMessages = ArrayList<Message>()
val friends = ArrayList<UserFull>()

    lateinit var  vk: VkApiClient
    lateinit var actor: UserActor
    lateinit var operations: Operations

private lateinit var sett: Settings
    private lateinit var mainLayout: MainLayout
        private lateinit var conversationsLayout: ConversationsLayout
    private lateinit var friendsLayout: FriendsLayout

private val usersCache = HashMap<Integer, UserFull>()

override fun onAppInit(): Boolean {
this.sett = RegistryProxy.create(getLuwrain().getRegistry(), "/org/luwrain/app/vk", Settings::class.java)
	val transportClient = HttpTransportClient()
	this.vk = VkApiClient(transportClient)
	this.actor = UserActor(sett.getUserId(0), sett.getAccessToken(""))
	this.operations = Operations(this)
this.mainLayout = MainLayout(this)
this.conversationsLayout = ConversationsLayout(this)
this.friendsLayout = FriendsLayout(this)
setAppName(getStrings().appName())
val taskId = newTaskId()
runTask(taskId, {
operations.updateConversations()
operations.updateHomeWall(mainLayout.wallOffset)
finishedTask(taskId, {
mainLayout.wallArea.refresh()
mainLayout.chatsArea.refresh()
getLuwrain().announceActiveArea()
})
})
return true
}

fun addUsersToCache(ids: List<Integer>) {
val idsToQuery = ArrayList<String>()
ids.forEach({
if (!usersCache.containsKey(it))
idsToQuery.add(it.toString())
})
val resp = vk.users().get(actor).userIds(idsToQuery).fields(Fields.STATUS, Fields.LAST_SEEN, Fields.CITY, Fields.BDATE).execute();
for(i in resp) {
usersCache.put(Integer(i.getId()), i)
}
//Log.debug("proba", "size=" + resp.size + " " + "q=" + idsToQuery.size)
//for(i in idsToQuery.indices)
//usersCache.put(Integer(Integer.parseInt(idsToQuery[i])), resp[i])
}

fun addUsersToCache2(ids: List<Int>) {
val v = ArrayList<Integer>()
ids.forEach({ v.add(Integer(it)) });
addUsersToCache(v);
}

fun getUsersFromCache(ids: List<Int>): List<UserFull> {
val res = ArrayList<UserFull>()
ids.forEach({
val u = usersCache.get(Integer(it))
if (u != null)
res.add(u)
});
return res
}

fun getUserStr(user: UserFull): String {
return user.getFirstName() + " " + user.getLastName()
}


fun getUserStr(id: Int): String {
val user = usersCache.get(Integer(id))
if (user == null)
return Integer.toString(id)
return getUserStr(user)
}

fun openFriends(): Boolean {
val taskId = newTaskId()
return runTask(taskId, {
operations.updateFriends()
finishedTask(taskId, {
layouts().friends()
friendsLayout.friendsArea.refresh()

})
})
}

override fun getDefaultAreaLayout(): AreaLayout {
return mainLayout.getAreaLayout()
}

override fun onEscape(event: InputEvent): Boolean {
closeApp()
return true
}

fun layouts(): Layouts {
return object: Layouts {
override public fun main(){
getLayout().setBasicLayout(mainLayout.getAreaLayout())
getLuwrain().announceActiveArea()
}
override public fun newPost(){
getLayout().setBasicLayout(NewPostLayout(this@App).getAreaLayout())
getLuwrain().announceActiveArea()
}
override public fun conversations() {
getLayout().setBasicLayout(conversationsLayout.getAreaLayout())
getLuwrain().announceActiveArea()
}
override public fun friends() {
setAreaLayout(friendsLayout)
getLuwrain().announceActiveArea()
}
}
}

interface Layouts{
fun main()
fun newPost()
fun conversations();
fun friends()
}
}
