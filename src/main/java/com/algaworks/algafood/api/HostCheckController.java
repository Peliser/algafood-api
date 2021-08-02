package com.algaworks.algafood.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostCheckController {

    @GetMapping("/host-check")
    public String checkHost() throws UnknownHostException {
        var host = InetAddress.getLocalHost();
        return host.getHostAddress() + " - " + host.getHostName();
    }

}
