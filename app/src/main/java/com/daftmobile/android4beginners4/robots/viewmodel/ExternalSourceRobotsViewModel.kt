package com.daftmobile.android4beginners4.robots.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daftmobile.android4beginners4.robots.model.ListRobotsDataSource
import com.daftmobile.android4beginners4.robots.model.Robot

class ExternalSourceRobotsViewModel: ViewModel(), RobotsViewModel {

    private val mutableLiveData: MutableLiveData<String> = MutableLiveData()
    private val robotDataSource = ListRobotsDataSource()
    private var sortingType: SortingType = SortingType.ASCENDING

    override fun getRobotList(): LiveData<String> {
        var robotList = sortList(robotDataSource.getRobots())

        mutableLiveData.value = getRobotListString(robotList)
        return mutableLiveData
    }

    override fun addRobot(){
        robotDataSource.addNew()

        getRobotList()
    }

    fun sortList(robotList: List<Robot>): List<Robot>{
        var sortedList: List<Robot>

        if(sortingType == SortingType.ASCENDING)
            sortedList = robotList.sortedBy { it.toString().toLowerCase() }
        else
            sortedList = robotList.sortedByDescending { it.toString().toLowerCase() }

        return sortedList
    }

    fun getRobotListString(robotList: List<Robot>): String{
      return robotList.joinToString("\n") { it -> it.toString() }
    }

    fun setSortingType(newType: SortingType){
        if(sortingType != newType) {
            sortingType = newType
            getRobotList()
        }
    }

    fun getSortingType(): SortingType{
        return sortingType
    }
}
