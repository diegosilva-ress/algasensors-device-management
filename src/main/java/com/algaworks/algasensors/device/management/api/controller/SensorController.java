package com.algaworks.algasensors.device.management.api.controller;

import com.algaworks.algasensors.device.management.api.model.SensorInput;
import com.algaworks.algasensors.device.management.common.IdGenerator;
import com.algaworks.algasensors.device.management.domain.model.Sensor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sensors")
public class SensorController {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Sensor create(@RequestBody SensorInput sensor) {

    return Sensor.builder()
        .id(IdGenerator.generateTSID())
        .name(sensor.getName())
        .ip(sensor.getIp())
        .location(sensor.getLocation())
        .protocol(sensor.getProtocol())
        .model(sensor.getModel())
        .enabled(false)
        .build();
  }

}
