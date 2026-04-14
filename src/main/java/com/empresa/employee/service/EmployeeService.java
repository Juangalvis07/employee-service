package com.empresa.employee.service;

import com.empresa.employee.dto.EmployeeDTO;
import com.empresa.employee.model.EstadoEmpleado;
import java.util.List;

public interface EmployeeService {

    EmployeeDTO crear(EmployeeDTO dto);

    EmployeeDTO obtenerPorId(Long id);

    List<EmployeeDTO> obtenerTodos();

    List<EmployeeDTO> obtenerPorEstado(EstadoEmpleado estado);

    EmployeeDTO actualizar(Long id, EmployeeDTO dto);

    void eliminar(Long id);

    List<EmployeeDTO> buscar(String termino);
}