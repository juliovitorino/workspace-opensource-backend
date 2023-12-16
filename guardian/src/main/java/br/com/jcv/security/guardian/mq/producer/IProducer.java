package br.com.jcv.security.guardian.mq.producer;

public interface IProducer<Input, Output> {
    Output dispatch(Input input);
}