package br.com.content4devs.projetogrpcjava.exceptions.handler;

import br.com.content4devs.projetogrpcjava.exceptions.BaseBusinessException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionHandler {

    @GrpcExceptionHandler(BaseBusinessException.class)
    public StatusRuntimeException handleBusinessException(BaseBusinessException e){
        return e.getStatusCode()
                .withCause(e.getCause())
                .withDescription(e.getErrorMessage())
                .asRuntimeException();
    }
}
