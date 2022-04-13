package com.kuro.trip_favo.ui.viewModel

import androidx.lifecycle.*
import com.kuro.trip_favo.data.api.DetailArea
import com.kuro.trip_favo.data.api.RakutenAreaResult
import com.kuro.trip_favo.data.api.SmallArea
import com.kuro.trip_favo.data.repositry.AreaRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val areaRepository: AreaRepository = AreaRepository()
) : ViewModel() {


    private val _areaResult: MutableLiveData<RakutenAreaResult> = MutableLiveData()
    val areaResult: LiveData<RakutenAreaResult> = _areaResult

    private val _isError: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    val selectedMiddlePosition: MutableLiveData<Int> = MutableLiveData(0)
    val selectedSmallPosition: MutableLiveData<Int> = MutableLiveData(0)
    val selectedDetailPosition: MutableLiveData<Int> = MutableLiveData(0)

    val middleAreas = areaResult.map {
        it.areaClasses.largeAreas[0].largeClass
            .drop(1)
            .flatMap {
                it.middleAreas!!
            }
    }
    val middleAreaNameList = middleAreas.map {
        it.map { it.middleClass[0].middleClassName }
    }


    val smallAreas = MediatorLiveData<List<SmallArea>>()
    val smallAreaNameList = smallAreas.map {
        it.map { it.smallClass[0].smallClassName }
    }

    val detailAreas = MediatorLiveData<List<DetailArea>>()
    val detailAreaNameList = detailAreas.map {
        it.map { it.detailClass.detailClassName }
    }

    init {

        setUpSmallAreas()
        setUpDetailAreas()

        viewModelScope.launch {
            runCatching {
                areaRepository.getArea()
            }.onSuccess {
                if (it.isSuccessful) {
                    _areaResult.value = it.body()
                } else {
                    throw IllegalStateException()
                }
            }.onFailure {
                _isError.value = true
            }

        }
    }

    private fun setUpSmallAreas() {

        smallAreas.addSource(middleAreas) { middleAreas ->
            if (selectedMiddlePosition.value == null) {
                smallAreas.value = emptyList()
            } else {
                val middleArea = middleAreas.find {
                    it.middleClass[0].middleClassCode == middleAreas[selectedMiddlePosition.value
                        ?: 0].middleClass[0].middleClassCode
                }
                smallAreas.value =
                    middleArea?.middleClass?.drop(1)?.flatMap { it.smallAreas ?: emptyList() }

            }
        }
        smallAreas.addSource(selectedMiddlePosition) { position ->
            val middleArea = middleAreas.value?.find {
                it.middleClass[0].middleClassCode == middleAreas.value?.get(position)?.middleClass?.get(
                    0
                )?.middleClassCode
            }
            smallAreas.value =
                middleArea?.middleClass?.drop(1)?.flatMap { it.smallAreas ?: emptyList() }
                    ?: emptyList()

        }
    }

    private fun setUpDetailAreas() {

        detailAreas.addSource(smallAreas) { smallAreas ->
            if (selectedSmallPosition.value == null) {
                detailAreas.value = emptyList()
            } else {
                val smallArea = smallAreas.find {
                    it.smallClass[0].smallClassCode == smallAreas[selectedSmallPosition.value
                        ?: 0].smallClass[0].smallClassCode
                }
                detailAreas.value =
                    smallArea?.smallClass?.drop(1)?.flatMap { it.detailAreas ?: emptyList() }
                        ?: emptyList()

            }
        }
        detailAreas.addSource(selectedSmallPosition) { position ->

            val smallArea = smallAreas.value?.find {
                it.smallClass[0].smallClassCode == smallAreas.value?.get(position)?.smallClass?.get(
                    0
                )?.smallClassCode
            }
            detailAreas.value =
                smallArea?.smallClass?.drop(1)?.flatMap { it.detailAreas ?: emptyList() }
                    ?: emptyList()

        }
    }

    fun getMiddleAreaCode(): String? {

        return middleAreas.value?.get(
            selectedMiddlePosition.value ?: 0
        )?.middleClass?.get(0)?.middleClassCode

    }

    fun getSmallAreaCode(): String? {

        return smallAreas.value?.get(
            selectedSmallPosition.value ?: 0
        )?.smallClass?.get(0)?.smallClassCode
    }

    fun getDetailAreaCode(): String? {

        return detailAreas.value?.getOrNull(
            selectedDetailPosition.value ?: 0
        )?.detailClass?.detailClassCode
    }
}