package com.empresa.employee.repository;

import com.empresa.employee.model.Employee;
import com.empresa.employee.model.EstadoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Spring genera el SQL automáticamente por el nombre del método
    Optional<Employee> findByEmail(String email);

    List<Employee> findByEstado(EstadoEmpleado estado);

    List<Employee> findByCargo(String cargo);

    boolean existsByEmail(String email);

    // Consulta personalizada con JPQL
    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :busqueda, '%'))")
    List<Employee> buscarPorNombreOApellido(String busqueda);
}