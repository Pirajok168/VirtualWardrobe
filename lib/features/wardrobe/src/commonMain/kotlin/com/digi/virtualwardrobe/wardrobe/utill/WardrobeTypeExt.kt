package com.digi.virtualwardrobe.wardrobe.utill

import com.digi.virtualwardrobe.wardrobe.domain.models.WardrobeType
import virtualwardrobe.lib.features.wardrobe.generated.resources.Res
import virtualwardrobe.lib.features.wardrobe.generated.resources.t_short_type
import virtualwardrobe.lib.features.wardrobe.generated.resources.hoodie_type
import virtualwardrobe.lib.features.wardrobe.generated.resources.jeans_type

fun WardrobeType.toName() =
    when(this) {
        WardrobeType.TSHORT -> Res.string.t_short_type
        WardrobeType.HOODIE -> Res.string.hoodie_type
        WardrobeType.JEANS -> Res.string.jeans_type
    }