package ua.dimaevseenko.format_player.network.result

import com.google.gson.annotations.SerializedName
import ua.dimaevseenko.format_player.model.Program
import ua.dimaevseenko.format_player.model.Programs

data class ProgramsResult(
    @SerializedName("epg")
    private val programsMap: Map<Long, Program>,

    @SerializedName("map")
    private val map: List<Long>,

    @SerializedName("currentData")
    val currentProgram: Program
){
    var programs: Programs? = null
        get() {
            if(field == null){
                field = Programs()
                map.forEach { id ->
                    programsMap[id]?.let { field!!.add(it) }
                }
            }

            return field
        }

    fun requirePrograms(): Programs{
        return programs!!
    }
}