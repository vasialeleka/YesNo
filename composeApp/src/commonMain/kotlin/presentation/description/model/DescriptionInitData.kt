package presentation.description.model

import base.InitData

class DescriptionInitData(
    val id : String,
    val colorHex : String,
    val title : String,
    val description : List<String>
) : InitData