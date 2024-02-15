package ramble.sokol.myolimp.feature_library.domain.view_models

import android.content.Context
import android.nfc.Tag
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.feature_library.domain.models.BlockModel
import ramble.sokol.myolimp.feature_library.domain.models.QuestionModel
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryEvent
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryState
import ramble.sokol.myolimp.feature_profile.database.UserDatabase

class LibraryViewModel(context: Context) : ViewModel() {

    companion object {
        private const val TAG = "ViewModelLibrary"
    }

    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private val userDatabase: UserDatabase = UserDatabase(context)
    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)

    private val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            searchArticles()
        }
    }

    init {
        viewModelScope.launch {
            _state.update { curValue ->
                curValue.copy(
                    isLoading = true
                )
            }

            val subjects = libraryRepository.getUserSubjects()

            _state.update {
                it.copy(
                    userSubjects = subjects,
                    bottomSheetSubjectsMap = subjects.associateWith { false }
                )
            }

            searchArticles()
        }
    }

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnSearchQueryUpdated -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
                timer.cancel()
                timer.start()
            }

            is LibraryEvent.OnEmptyQuery -> {
                _state.update {
                    it.copy(
                        searchQuery = ""
                    )
                }
                timer.cancel()
            }

            is LibraryEvent.OnShowFavourites -> {
                _state.update {
                    it.copy(
                        isShowingFavourites = event.isShowing
                    )
                }
                searchArticles()
            }

            is LibraryEvent.OnCheckboxSubject -> {
                _state.update { libraryState ->
                    libraryState.copy(
                        bottomSheetSubjectsMap = libraryState.bottomSheetSubjectsMap.mapValues {
                            if (it.key == event.subjectName) it.value.not() else it.value
                        },
                    )
                }
            }

            is LibraryEvent.OnFilterSubjectFromBottomSheet -> {
                _state.update { libraryState ->
                    libraryState.copy(
                        filteredSubjects = state.value.userSubjects.filter { subject ->
                            state.value.bottomSheetSubjectsMap[subject] ?: false
                        },
                    )
                }
            }
        }
    }

    private fun searchArticles() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
//            libraryRepository.getArticles(
//                page = state.value.currentPage,
//                isShowFavourites = state.value.isShowingFavourites,
//                query = state.value.searchQuery,
//                onSuccess = { articles->
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            articles = articles
//                        )
//                    }
//                },
//                onError = { error->
//
//                    Log.i(TAG, "error occurred - $error")
//
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            isError = true
//                        )
//                    }
//                }
//            )

            _state.update {
                it.copy(
                    isLoading = false,
                    articles = listOf(
                        ArticleModel(
                            id = 1,
                            title = "Современная литература",
                            tags = listOf("Литература", "МХК"),
                            subject = "Литература",
                            image = "https://blog.skillfactory.ru/wp-content/uploads/2023/02/kotlin-chto-eto-za-yazyk-gde-i-kak-ispolzuetsya.jpg",
                            isFavourite = false,
                            blocks = listOf(
                                BlockModel(
                                    id = 1,
                                    blockText = "Kotlin — это язык программирования, созданный компанией JetBrains. С момента выхода первой официальной версии языка в 2016 году, всего за год он занял место в топ-50 в рейтинге TIOBE (индекс, оценивающий популярность языков программирования на основе подсчета результатов поисковых запросов, содержащих название языка) и не сдает позиций.Kotlin — это статически типизированный язык программирования (тип переменной известен во время компиляции, то есть еще до запуска программы).\n" +
                                            "\n" +
                                            "В отличие от Java, где программы строятся на классах, основным строительным блоком программы на Kotlin является функция. Однако Kotlin также поддерживает объектно-ориентированный подход к программированию.",
                                    sequenceNumber = 1,
                                    type = "t",
                                    isDifficult = false,
                                    questions = listOf(
                                        QuestionModel(
                                            id = 1,
                                            questionText = "Kotlin?",
                                            answer = "Kotlin"
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            }
        }
    }
}
