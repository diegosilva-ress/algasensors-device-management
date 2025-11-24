package com.algaworks.algasensors.device.management.api.controller;

import com.algaworks.algasensors.device.management.api.model.SensorInput;
import com.algaworks.algasensors.device.management.api.model.SensorOutput;
import com.algaworks.algasensors.device.management.common.IdGenerator;
import com.algaworks.algasensors.device.management.domain.model.Sensor;
import com.algaworks.algasensors.device.management.domain.model.SensorID;
import com.algaworks.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/sensors")
@RequiredArgsConstructor
public class SensorController {

  private final SensorRepository sensorRepository;

  @GetMapping("{sensorId}")
  public SensorOutput getOne(@PathVariable TSID sensorId) {
    var sensor = sensorRepository.findById(new SensorID(sensorId))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return convertToModel(sensor);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SensorOutput create(@RequestBody SensorInput input) {

    Sensor sensor = Sensor.builder()
        .id(new SensorID(IdGenerator.generateTSID()))
        .name(input.getName())
        .ip(input.getIp())
        .location(input.getLocation())
        .protocol(input.getProtocol())
        .model(input.getModel())
        .enabled(false)
        .build();

    sensor = sensorRepository.saveAndFlush(sensor);
    return convertToModel(sensor);
  }

  private SensorOutput convertToModel(Sensor sensor) {
    return SensorOutput.builder()
        .id(sensor.getId().getValue())
        .name(sensor.getName())
        .ip(sensor.getIp())
        .location(sensor.getLocation())
        .protocol(sensor.getProtocol())
        .model(sensor.getModel())
        .enabled(sensor.getEnabled())
        .build();
  }

}
