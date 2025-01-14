package ai.metarank.model

import ai.metarank.model.TrainResult.{FeatureStatus, IterationStatus}
import io.circe.Codec
import io.circe.generic.semiauto._

case class TrainResult(iterations: List[IterationStatus], sizeBytes: Long, features: List[FeatureStatus])

object TrainResult {
  case class IterationStatus(id: Int, millis: Long, trainMetric: Double, testMetric: Double)
  case class FeatureStatus(
      name: String,
      weight: FeatureWeight
  ) {
    def asPrintString = {
      val w = weight match {
        case FeatureWeight.SingularWeight(value) => value.toString
        case FeatureWeight.VectorWeight(values)  => values.mkString("[", ",", "]")
      }
      s"$name: weight=$w"
    }
  }

  implicit val featureStatusCodec: Codec[FeatureStatus]     = deriveCodec
  implicit val iterationStatusCodec: Codec[IterationStatus] = deriveCodec
  implicit val trainResultCodec: Codec[TrainResult]         = deriveCodec
}
