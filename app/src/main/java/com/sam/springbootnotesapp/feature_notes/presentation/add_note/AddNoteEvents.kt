package com.sam.springbootnotesapp.feature_notes.presentation.add_note

sealed class AddNoteEvents {

    data class OnTitleValueChange(val value:String):AddNoteEvents()

    data class OnDescriptionValueChange(val value: String):AddNoteEvents()

    object OnConfirmClicked:AddNoteEvents()

    object OnExpandedChange:AddNoteEvents()

    object OnDismissRequest:AddNoteEvents()

    data class OnItemClicked(val category:String):AddNoteEvents()

}