package ai.decart.oasis

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class IceCandidate(
	val candidate: String,
	val sdpMid: String,
	val sdpMLineIndex: Int,
)

// outgoing messages

@Serializable
sealed interface LucyRestyleOutgoingMessage

@Serializable
@SerialName("prompt")
data class LucyRestyleOutgoingPromptMessage(
	val prompt: String,
	val enhance_prompt: Boolean,
) : LucyRestyleOutgoingMessage

@Serializable
@SerialName("offer")
data class LucyRestyleOutgoingOfferMessage(
	val sdp: String,
) : LucyRestyleOutgoingMessage

@Serializable
@SerialName("ice-candidate")
data class LucyRestyleOutgoingIceCandidateMessage(
	val candidate: IceCandidate,
) : LucyRestyleOutgoingMessage

// incoming messages

@Serializable
sealed interface LucyRestyleIncomingMessage

@Serializable
@SerialName("ice-candidate")
data class LucyRestyleIncomingIceCandidateMessage(
	val candidate: IceCandidate,
) : LucyRestyleIncomingMessage

@Serializable
@SerialName("answer")
data class LucyRestyleIncomingAnswerMessage(
	val sdp: String,
) : LucyRestyleIncomingMessage

@Serializable
@SerialName("error")
data class LucyRestyleIncomingErrorMessage(
	val error: String,
) : LucyRestyleIncomingMessage

@Serializable
@SerialName("session_id")
data class LucyRestyleIncomingSessionIdMessage(
	val session_id: String,
	val server_port: Int,
	val server_ip: String,
) : LucyRestyleIncomingMessage

@Serializable
@SerialName("prompt_ack")
data class LucyRestyleIncomingPromptAckMessage(
	val prompt: String,
	val success: Boolean,
	val error: String? = null,
) : LucyRestyleIncomingMessage

@Serializable
@SerialName("generation_started")
data class LucyRestyleIncomingGenerationStartedMessage(
    val dummy: String? = null
) : LucyRestyleIncomingMessage
