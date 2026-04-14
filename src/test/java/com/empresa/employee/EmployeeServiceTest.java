package com.empresa.employee;

import com.empresa.employee.dto.EmployeeDTO;
import java.util.List;
import com.empresa.employee.exception.ResourceNotFoundException;
import com.empresa.employee.model.Employee;
import com.empresa.employee.model.EstadoEmpleado;
import com.empresa.employee.repository.EmployeeRepository;
import com.empresa.employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee empleado;
    private EmployeeDTO empleadoDTO;

    @BeforeEach
    void setUp() {
        empleado = new Employee();
        empleado.setId(1L);
        empleado.setNombre("Juan");
        empleado.setApellido("Perez");
        empleado.setEmail("juan@empresa.com");
        empleado.setCargo("Backend Developer");
        empleado.setSalario(4400000.0);
        empleado.setFechaIngreso(LocalDate.of(2024, 1, 15));
        empleado.setEstado(EstadoEmpleado.ACTIVO);

        empleadoDTO = new EmployeeDTO();
        empleadoDTO.setNombre("Juan");
        empleadoDTO.setApellido("Perez");
        empleadoDTO.setEmail("juan@empresa.com");
        empleadoDTO.setCargo("Backend Developer");
        empleadoDTO.setSalario(4400000.0);
        empleadoDTO.setFechaIngreso(LocalDate.of(2024, 1, 15));
        empleadoDTO.setEstado(EstadoEmpleado.ACTIVO);
    }

    @Test
    void obtenerPorId_existente_retornaDTO() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(empleado));

        EmployeeDTO resultado = employeeService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@empresa.com", resultado.getEmail());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_noExistente_lanzaExcepcion() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.obtenerPorId(99L));
    }

    @Test
    void crear_emailNuevo_retornaDTO() {
        when(employeeRepository.existsByEmail("juan@empresa.com")).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(empleado);

        EmployeeDTO resultado = employeeService.crear(empleadoDTO);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void crear_emailDuplicado_lanzaExcepcion() {
        when(employeeRepository.existsByEmail("juan@empresa.com")).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> employeeService.crear(empleadoDTO));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void eliminar_existente_ejecutaDelete() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.eliminar(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
    @Test
    void obtenerTodos_retornaLista() {
        when(employeeRepository.findAll())
                .thenReturn(List.of(empleado));

        List<EmployeeDTO> resultado = employeeService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void actualizar_existente_retornaDTOActualizado() {
        empleadoDTO.setNombre("Carlos");
        empleadoDTO.setSalario(5000000.0);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(empleado));
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(empleado);

        EmployeeDTO resultado = employeeService.actualizar(1L, empleadoDTO);

        assertNotNull(resultado);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void actualizar_noExistente_lanzaExcepcion() {
        when(employeeRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.actualizar(99L, empleadoDTO));

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void eliminar_noExistente_lanzaExcepcion() {
        when(employeeRepository.existsById(99L))
                .thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.eliminar(99L));

        verify(employeeRepository, never()).deleteById(any());
    }
}