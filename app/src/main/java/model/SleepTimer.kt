package model

data class SleepTimer(
    val id:Int=0,
    val content:String="",
    val value:Int=0,
)
fun sleepTimerList(): List<SleepTimer> {
    return listOf(
        SleepTimer(id = 0, content = "1 Minutes", value = 1),
        SleepTimer(id = 1, content = "5 Minutes", value = 5),
        SleepTimer(id = 2, content = "10 Minutes", value = 10),
        SleepTimer(id = 3, content = "15 Minutes", value = 15),
        SleepTimer(id = 4, content = "30 Minutes", value = 30),
        SleepTimer(id = 5, content = "60 Minutes", value = 60)
    )
}
