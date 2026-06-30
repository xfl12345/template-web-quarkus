package org.acme.service;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;
import org.acme.HelloGrpc;
import org.acme.HelloReply;
import org.acme.HelloRequest;

@GrpcService
public class HelloGrpcService implements HelloGrpc {

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        return Uni.createFrom().item("Hello " + request.getName() + "!")
                .map(msg -> HelloReply.newBuilder().setMessage(msg).build());
    }

}
