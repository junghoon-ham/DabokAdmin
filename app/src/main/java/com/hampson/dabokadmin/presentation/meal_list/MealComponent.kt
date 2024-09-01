package com.hampson.dabokadmin.presentation.meal_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.hampson.dabokadmin.R
import com.hampson.dabokadmin.domain.model.Meal

@Composable
fun MealComponent(
    meal: Meal,
    onDelete: () -> Unit,
    onUpdate: () -> Unit,
    onCopy: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.large,
        onClick = { expanded = !expanded }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column {
                Text(
                    text = meal.date,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisSpacing = 6.dp
                ) {
                    meal.menuList.forEach { menu ->
                        FilterChip(
                            selected = false,
                            onClick = { },
                            label = { Text(text = menu.name) },
                            leadingIcon = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (expanded) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ItemActionButton(
                            actionText = stringResource(id = R.string.delete_meal),
                            actionColor = MaterialTheme.colorScheme.error,
                            actionIcon = Icons.Outlined.DeleteOutline,
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            onDelete()
                        }

                        ItemActionButton(
                            actionText = stringResource(id = R.string.update_meal),
                            actionColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            actionIcon = Icons.Outlined.Edit,
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            onUpdate()
                        }

                        ItemActionButton(
                            actionText = stringResource(id = R.string.copy_meal),
                            actionColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            actionIcon = Icons.Outlined.CopyAll,
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                        ) {
                            onCopy()
                        }
                    }
                }
            }

            Icon(
                modifier = Modifier.align(Alignment.TopEnd),
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ItemActionButton(
    actionText: String,
    actionColor: Color,
    actionIcon: ImageVector,
    modifier: Modifier,
    onClick: () -> Unit
) {
    AssistChip(
        modifier = modifier,
        onClick = onClick,
        colors = AssistChipDefaults.assistChipColors(
            leadingIconContentColor = actionColor,
            labelColor = actionColor,
        ),
        leadingIcon = {
            Icon(
                imageVector = actionIcon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        },
        label = {
            Text(
                text = actionText,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                )
            )
        }
    )
}