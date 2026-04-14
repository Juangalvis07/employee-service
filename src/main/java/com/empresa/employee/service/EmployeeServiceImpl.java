package com.empresa.employee.service;

import com.empresa.employee.dto.EmployeeDTO;
import com.empresa.employee.exception.ResourceNotFoundException;
import com.empresa.employee.model.Employee;
import com.empresa.employee.model.EstadoEmpleado;
import com.empresa.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    @Transactional
    public EmployeeDTO crear(EmployeeDTO dto) {
        log.info("Creando empleado con email: {}", dto.getEmail());

        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un empleado con el email: "
                    + dto.getEmail());
        }

        Employee empleado = toEntity(dto);
        Employee guardado = repository.save(empleado);
        log.info("Empleado creado con ID: {}", guardado.getId());
        return toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO obtenerPorId(Long id) {
        Employee empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empleado no encontrado con ID: " + id));
        return toDTO(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> obtenerPorEstado(EstadoEmpleado estado) {
        return repository.findByEstado(estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDTO actualizar(Long id, EmployeeDTO dto) {
        log.info("Actualizando empleado con ID: {}", id);

        Employee empleado = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empleado no encontrado con ID: " + id));

        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setCargo(dto.getCargo());
        empleado.setSalario(dto.getSalario());
        empleado.setFechaIngreso(dto.getFechaIngreso());
        empleado.setEstado(dto.getEstado());

        return toDTO(repository.save(empleado));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando empleado con ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Empleado no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> buscar(String termino) {
        return repository.buscarPorNombreOApellido(termino)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ─── Métodos privados de conversión ───────────────────────────────────

    private Employee toEntity(EmployeeDTO dto) {
        return Employee.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .cargo(dto.getCargo())
                .salario(dto.getSalario())
                .fechaIngreso(dto.getFechaIngreso())
                .estado(dto.getEstado() != null
                        ? dto.getEstado()
                        : EstadoEmpleado.ACTIVO)
                .build();
    }

    private EmployeeDTO toDTO(Employee e) {
        return EmployeeDTO.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .email(e.getEmail())
                .cargo(e.getCargo())
                .salario(e.getSalario())
                .fechaIngreso(e.getFechaIngreso())
                .estado(e.getEstado())
                .build();
    }
}