package com.empresa.employee.controller;

import com.empresa.employee.dto.EmployeeDTO;
import com.empresa.employee.model.EstadoEmpleado;
import com.empresa.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Empleados", description = "Gestión de empleados")
@RestController
@RequestMapping("/api/v1/empleados")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Listar todos los empleados")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> obtenerTodos() {
        return ResponseEntity.ok(employeeService.obtenerTodos());
    }

    @Operation(summary = "Obtener empleado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo empleado")
    @PostMapping
    public ResponseEntity<EmployeeDTO> crear(@RequestBody @Valid EmployeeDTO dto) {
        return ResponseEntity.status(201).body(employeeService.crear(dto));
    }

    @Operation(summary = "Actualizar empleado")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar empleado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        employeeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar empleados por nombre o apellido")
    @GetMapping("/buscar")
    public ResponseEntity<List<EmployeeDTO>> buscar(@RequestParam String termino) {
        return ResponseEntity.ok(employeeService.buscar(termino));
    }

    @Operation(summary = "Filtrar empleados por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EmployeeDTO>> obtenerPorEstado(@PathVariable EstadoEmpleado estado) {
        return ResponseEntity.ok(employeeService.obtenerPorEstado(estado));
    }
}