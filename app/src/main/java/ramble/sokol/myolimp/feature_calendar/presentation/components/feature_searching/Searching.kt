package ramble.sokol.myolimp.feature_calendar.presentation.components.feature_searching

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.UpdateScreenDestination
import ramble.sokol.myolimp.feature_calendar.domain.states.PlanState
import ramble.sokol.myolimp.feature_calendar.domain.utils.PlanTimeStatus
import ramble.sokol.myolimp.feature_calendar.domain.utils.getPlanTimeStatus
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_create.ImageWithText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.CommentText
import ramble.sokol.myolimp.feature_calendar.presentation.components.feature_current_day.PlanItem
import java.time.LocalDate

@Composable
fun Searching (
    state: PlanState,
    navController: NavController,
) {

    val searchingPlans = state.plans
        .filter {
            it.title.contains(state.searchQuery, ignoreCase = true)
        }
        .sortedWith(
            compareBy(
                {
                    it.date
                },
                {
                    it.startHour
                },
                {
                    it.startMinute
                }
            )
        )

    if (searchingPlans.isEmpty()) {

        ImageWithText(
            drawable = R.drawable.ic_calendar_no_plans,
            text = stringResource(R.string.nothing_was_found, state.searchQuery)
        )

    } else {

        val currentPlans = searchingPlans
            .filter {
                (LocalDate.parse(it.date).isEqual(LocalDate.now())
                        && getPlanTimeStatus(
                    it.startHour,
                    it.startMinute,
                    it.endHour,
                    it.endMinute,
                ) == PlanTimeStatus.CURRENT
                        )
            }

        val nextPlans = searchingPlans
            .filter {
                // after today
                LocalDate.parse(it.date).isAfter(LocalDate.now())

                        // today
                        || (LocalDate.parse(it.date).isEqual(LocalDate.now())
                        && getPlanTimeStatus(
                    it.startHour,
                    it.startMinute,
                    it.endHour,
                    it.endMinute,
                ) == PlanTimeStatus.NEXT
                        )
            }

        val previousPlans = searchingPlans
            .filter {
                // before
                LocalDate.parse(it.date).isBefore(LocalDate.now())

                        // today
                        || (LocalDate.parse(it.date).isEqual(LocalDate.now())
                        && getPlanTimeStatus(
                    it.startHour,
                    it.startMinute,
                    it.endHour,
                    it.endMinute,
                ) == PlanTimeStatus.PREVIOUS
                        )
            }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // show current
            if (currentPlans.isNotEmpty()) {

                CommentText(
                    text = stringResource(R.string.current_events)
                )

                currentPlans.forEach {
                    PlanItem(
                        item = it,
                    ) { plan ->
                        navController.navigate(
                            UpdateScreenDestination(plan = plan)
                        )
                    }
                }
            }

            // show next
            if (nextPlans.isNotEmpty()) {

                CommentText(
                    text = stringResource(R.string.next_events)
                )

                nextPlans.forEach {
                    PlanItem(
                        item = it,
                    ) { plan ->
                        navController.navigate(
                            UpdateScreenDestination(plan = plan)
                        )
                    }
                }
            }

            // show previous
            if (previousPlans.isNotEmpty()) {

                CommentText(
                    text = stringResource(R.string.previous_events)
                )

                previousPlans.forEach {
                    PlanItem(
                        item = it,
                    ) { plan ->
                        navController.navigate(
                            UpdateScreenDestination(plan = plan)
                        )
                    }
                }
            }
        }
    }
}
