package cn.ff.demoai4j.mapper;

import cn.ff.demoai4j.entity.Appointment;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentMapper {

    Appointment getById(Long id);

    int deleteById(Long id);

    int insert(Appointment appointment);
}
