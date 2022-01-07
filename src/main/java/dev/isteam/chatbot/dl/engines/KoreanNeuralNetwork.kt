package dev.isteam.chatbot.dl.engines

import org.deeplearning4j.nn.conf.BackpropType
import org.deeplearning4j.nn.conf.GradientNormalization
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.layers.OutputLayer
import org.deeplearning4j.nn.conf.layers.variational.VariationalAutoencoder
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.lossfunctions.LossFunctions

object KoreanNeuralNetwork {

    fun buildNeuralNetwork(inputSize: Int, outputSize: Int): MultiLayerNetwork {
        var conf = NeuralNetConfiguration.Builder()
            .seed(1337)
            .list()
            .layer(
                0, VariationalAutoencoder.Builder()
                    .nIn(inputSize)
                    .nOut(1024)
                    .encoderLayerSizes(1024, 512, 256, 128)
                    .decoderLayerSizes(128, 256, 512, 1024)
                    .lossFunction(Activation.RELU, LossFunctions.LossFunction.MSE)
                    .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
                    .dropOut(0.8)
                    .build()
            )
            .layer(
                1, OutputLayer.Builder()
                    .nIn(1024).nOut(outputSize)
                    .activation(Activation.SOFTMAX)
                    .lossFunction(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                    .build()
            )
            .backpropType(BackpropType.Standard)
            .build()
        return MultiLayerNetwork(conf)
    }

}