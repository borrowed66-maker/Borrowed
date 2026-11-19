package com.borrowed.app.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.borrowed.app.ui.theme.BorrowedTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/** Values entered on the Add/Edit Item screen. */
data class AddEditItemForm(
    val itemName: String,
    val borrowerName: String,
    val contactNumber: String,
    val lentDate: String,
    val returnDate: String,
    val notes: String
)

/** UI for entering or editing a lending record. Database work is added in Commit 10. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditItemScreen(
    onSave: (AddEditItemForm) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    initialItemName: String = "",
    initialBorrowerName: String = "",
    initialContactNumber: String = "",
    initialLentDate: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
    initialReturnDate: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
    initialNotes: String = ""
) {
    var itemName by rememberSaveable { mutableStateOf(initialItemName) }
    var borrowerName by rememberSaveable { mutableStateOf(initialBorrowerName) }
    var contactNumber by rememberSaveable { mutableStateOf(initialContactNumber) }
    var lentDate by rememberSaveable { mutableStateOf(initialLentDate) }
    var returnDate by rememberSaveable { mutableStateOf(initialReturnDate) }
    var notes by rememberSaveable { mutableStateOf(initialNotes) }
    var datePickerFor by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Add lending record",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Enter the item and borrower details below.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item name") },
            placeholder = { Text("e.g. Calculator") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = borrowerName,
            onValueChange = { borrowerName = it },
            label = { Text("Borrower name") },
            placeholder = { Text("Who borrowed the item?") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            label = { Text("Contact number (optional)") },
            placeholder = { Text("Enter phone number") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lentDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Lending date") },
            trailingIcon = {
                TextButton(onClick = { datePickerFor = "lent" }) { Text("Choose") }
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = returnDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Expected return date") },
            trailingIcon = {
                TextButton(onClick = { datePickerFor = "return" }) { Text("Choose") }
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes (optional)") },
            placeholder = { Text("Add any useful details") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    onSave(
                        AddEditItemForm(
                            itemName = itemName.trim(),
                            borrowerName = borrowerName.trim(),
                            contactNumber = contactNumber.trim(),
                            lentDate = lentDate,
                            returnDate = returnDate,
                            notes = notes.trim()
                        )
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
    }

    if (datePickerFor != null) {
        val currentDate = if (datePickerFor == "lent") lentDate else returnDate
        val parsedDate = runCatching { LocalDate.parse(currentDate) }
            .getOrDefault(LocalDate.now())
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = parsedDate
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { datePickerFor = null },
            confirmButton = {
                TextButton(onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                            .format(DateTimeFormatter.ISO_LOCAL_DATE)
                        if (datePickerFor == "lent") lentDate = selectedDate
                        else returnDate = selectedDate
                    }
                    datePickerFor = null
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { datePickerFor = null }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddEditItemScreenPreview() {
    BorrowedTheme {
        AddEditItemScreen(
            onSave = {},
            onCancel = {}
        )
    }
}
