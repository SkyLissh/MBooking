package com.skylissh.mbooking.data.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class NullableLocalDateSerializer : KSerializer<LocalDate?> {
  private val delegate = String.serializer().nullable
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING).nullable

  override fun deserialize(decoder: Decoder): LocalDate? {
    val value = delegate.deserialize(decoder)

    return if (value == null || value.isEmpty()) null else LocalDate.parse(value)
  }

  override fun serialize(encoder: Encoder, value: LocalDate?) {
    if (value == null) delegate.serialize(encoder, null)
    else delegate.serialize(encoder, value.toString())
  }
}
