enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NextWorkState(val status: Status, val mgs: String) {
    companion object {
         var LOADED: NextWorkState
         var LOADING: NextWorkState
         var ERROR: NextWorkState


        init {
            LOADED = NextWorkState(Status.SUCCESS, "Success")
            LOADING = NextWorkState(Status.RUNNING, "Running")
            ERROR = NextWorkState(Status.FAILED, "Wrong")
        }
    }
}
